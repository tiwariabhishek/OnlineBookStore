package com.impetus.bookstore.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.impetus.bookstore.dto.ActiveBookRequestDTO;
import com.impetus.bookstore.dto.ActiveSubscriptionDTO;
import com.impetus.bookstore.dto.BookEntityDTO;
import com.impetus.bookstore.dto.UserEntityDTO;
import com.impetus.bookstore.exception.ServiceLayerException;
import com.impetus.bookstore.services.BookService;
import com.impetus.bookstore.services.MailService;
import com.impetus.bookstore.services.UserService;
import com.impetus.bookstore.utils.PropertyConstants;
import com.impetus.bookstore.utils.ShoppingCart;

@RestController
@Scope("request")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private MailService mailService;

	@Autowired
	ShoppingCart shoppingCart;

	@Autowired(required = true)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/admin/userDetails", method = RequestMethod.GET)
	public ModelAndView welcomeAdmin() throws ServiceLayerException {
		Map<String, ArrayList<ActiveBookRequestDTO>> activeBookRequestDTOs = userService
				.getAllUserRequests();
		List<ActiveSubscriptionDTO> activeSubscriptionDTOs = new ArrayList<ActiveSubscriptionDTO>();
		List<ActiveBookRequestDTO> acBookRequestDTOs = new ArrayList<ActiveBookRequestDTO>();
		List<UserEntityDTO> userEntityDTOs = new ArrayList<UserEntityDTO>();
		List<BookEntityDTO> bookEntityDTOs = new ArrayList<BookEntityDTO>();
		for (Entry<String, ArrayList<ActiveBookRequestDTO>> entry : activeBookRequestDTOs
				.entrySet()) {
			ActiveSubscriptionDTO activeSubscriptionDTO = userService
					.getActiveSubscriptionDTO(entry.getKey());
			UserEntityDTO userEntityDTO = userService.getUserByEmail(entry
					.getKey());
			List<ActiveBookRequestDTO> acDtos = entry.getValue();
			for (ActiveBookRequestDTO activeBookRequestDTO : acDtos) {
				BookEntityDTO book = bookService.getBook(activeBookRequestDTO
						.getBookId());
				bookEntityDTOs.add(book);
				activeSubscriptionDTOs.add(activeSubscriptionDTO);
				userEntityDTOs.add(userEntityDTO);
				acBookRequestDTOs.add(activeBookRequestDTO);
			}
		}
		ModelAndView modelAndView = new ModelAndView("userrequests");
		modelAndView.addObject("bookEntityDTO", bookEntityDTOs);
		modelAndView.addObject("activeSubscriptionDTO", activeSubscriptionDTOs);
		modelAndView.addObject("userEntityDTO", userEntityDTOs);
		modelAndView.addObject("activeBookRequestDTO", acBookRequestDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "/admin/getProfile", method = RequestMethod.GET)
	public ModelAndView getProfile(
			@RequestParam(value = "email", required = false) String email)
			throws ServiceLayerException {
		UserEntityDTO userEntityDTO = userService.getUserByEmail(email);
		ModelAndView modelAndView = new ModelAndView("userprofile");
		modelAndView.addObject("user", userEntityDTO);
		return modelAndView;
	}

	@RequestMapping(value = "/admin/approveRequest", method = RequestMethod.GET)
	public String approveRequest(
			@RequestParam(value = "requestId", required = false) long requestId,
			@RequestParam(value = "email", required = false) String email)
			throws ServiceLayerException {
		String response = userService.approveUserRequest(email, requestId);
		if (response.indexOf("DELIVERED") > 0) {
			mailService.sendEmailNotifications(email,
					PropertyConstants.FIRST_NAME,
					PropertyConstants.REQUEST_SUBJECT,
					PropertyConstants.REQUEST_APPROVED);
		} else if (response.indexOf("RETURNED") > 0) {
			mailService.sendEmailNotifications(email,
					PropertyConstants.FIRST_NAME,
					PropertyConstants.RETURN_SUBJECT,
					PropertyConstants.RETURN_MESSAGE);
		}
		return response;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView welcome(Principal principal)
			throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("index");
		for (Long bookId : shoppingCart.getBooks()) {
			bookService.addToCart(principal.getName(), bookId);
		}
		shoppingCart.getBooks().clear();
		return modelAndView;
	}

	@RequestMapping(value = "/user/requestStatus", method = RequestMethod.GET)
	public ModelAndView welcomeUser(Principal principal)
			throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("userrequests");
		List<ActiveBookRequestDTO> requestedBookRequests = userService
				.getAllBookRequests(principal.getName());
		List<BookEntityDTO> reqBooks = new ArrayList<BookEntityDTO>();
		List<ActiveBookRequestDTO> issuedBookRequests = new ArrayList<ActiveBookRequestDTO>();
		List<BookEntityDTO> issuedBooks = new ArrayList<BookEntityDTO>();
		List<ActiveBookRequestDTO> pendingBookRequests = new ArrayList<ActiveBookRequestDTO>();
		List<BookEntityDTO> pendingBooks = new ArrayList<BookEntityDTO>();
		for (ActiveBookRequestDTO activeBookRequestDTO : requestedBookRequests) {
			BookEntityDTO book = bookService.getBook(activeBookRequestDTO
					.getBookId());
			if (activeBookRequestDTO.getBookStatus().equals("DELIVERED")) {
				issuedBookRequests.add(activeBookRequestDTO);
				issuedBooks.add(book);
			} else if (activeBookRequestDTO.getBookStatus().indexOf("PENDING") == 0) {
				pendingBookRequests.add(activeBookRequestDTO);
				pendingBooks.add(book);
			}
			reqBooks.add(book);
		}
		List<BookEntityDTO> recommendedBooks = bookService
				.getAllRecommendedBooks(principal.getName());
		modelAndView.addObject("issuedBookRequests", issuedBookRequests);
		modelAndView.addObject("pendingBookRequests", pendingBookRequests);
		modelAndView.addObject("issuedBooks", issuedBooks);
		modelAndView.addObject("pendingBooks", pendingBooks);
		modelAndView.addObject("recommendedBooks", recommendedBooks);
		return modelAndView;
	}

	@RequestMapping(value = "/user/transactions", method = RequestMethod.GET)
	public ModelAndView getTransactions(Principal principal)
			throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("transactions");
		List<ActiveBookRequestDTO> requestedBookRequests = userService
				.getAllBookRequests(principal.getName());
		List<BookEntityDTO> reqBooks = new ArrayList<BookEntityDTO>();
		for (ActiveBookRequestDTO activeBookRequestDTO : requestedBookRequests) {
			BookEntityDTO book = bookService.getBook(activeBookRequestDTO
					.getBookId());
			reqBooks.add(book);
		}
		ActiveSubscriptionDTO activeSubscriptionDTO = userService
				.getActiveSubscriptionDTO(principal.getName());
		modelAndView.addObject("requestedBookRequests", requestedBookRequests);
		modelAndView.addObject("requestedBooks", reqBooks);
		modelAndView.addObject("activeSubscription", activeSubscriptionDTO);
		return modelAndView;
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public ModelAndView getUserProfile(Principal principal)
			throws ServiceLayerException {
		UserEntityDTO userEntityDTO = userService.getUserByEmail(principal
				.getName());
		ActiveSubscriptionDTO activeSubscriptionDTO = userService
				.getActiveSubscriptionDTO(principal.getName());
		ModelAndView modelAndView = new ModelAndView("userprofile");
		modelAndView.addObject("user", userEntityDTO);
		modelAndView.addObject("activeSubscriptionDTO", activeSubscriptionDTO);
		return modelAndView;
	}

	@RequestMapping(value = "/user/getprofileforupdate", method = RequestMethod.GET)
	public ModelAndView getUserProfileForUpdate(Principal principal)
			throws ServiceLayerException {
		UserEntityDTO userEntityDTO = userService.getUserByEmail(principal
				.getName());
		ModelAndView modelAndView = new ModelAndView("updateprofile");
		modelAndView.addObject("user", userEntityDTO);
		return modelAndView;
	}

	@RequestMapping(value = "/user/updateprofile", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(
			@ModelAttribute UserEntityDTO userEntityDTO, Principal principal)
			throws ServiceLayerException {
		userEntityDTO.setEmail(principal.getName());
		userService.updateUser(userEntityDTO);
		ModelAndView modelAndView = getUserProfile(principal);
		return modelAndView;
	}

	@RequestMapping("/addToCart")
	public String addToCart(
			@RequestParam(value = "bookId", required = false) long bookId,
			Principal principal) throws ServiceLayerException {
		String message = null;
		if (principal != null) {
			message = bookService.addToCart(principal.getName(), bookId);
		} else {
			if (shoppingCart.getBooks().contains(bookId)) {
				message = PropertyConstants.CART_MESSAGE;
			} else {
				shoppingCart.getBooks().add(bookId);
			}
		}
		return message;
	}

	@RequestMapping("/removeFromCart")
	public boolean removeFromCart(
			@RequestParam(value = "bookId", required = false) long bookId,
			Principal principal) throws ServiceLayerException {
		if (principal != null) {
			bookService.removeFromCart(principal.getName(), bookId);
		} else {
			shoppingCart.getBooks().remove(bookId);
		}
		return true;
	}

	@RequestMapping("/viewCart")
	public ModelAndView viewCart(Principal principal)
			throws ServiceLayerException {
		List<BookEntityDTO> bookEntityDTOs = new ArrayList<BookEntityDTO>();
		ModelAndView modelAndView = new ModelAndView("usercart");
		if (!shoppingCart.getBooks().isEmpty()) {
			for (long bookId : shoppingCart.getBooks()) {
				bookEntityDTOs.add(bookService.getBook(bookId));
			}
		} else if (principal != null) {
			bookEntityDTOs.addAll(bookService.getUserCart(principal.getName()));
			UserEntityDTO userEntityDTO = userService.getUserByEmail(principal
					.getName());
			modelAndView.addObject("user", userEntityDTO);
			if (userEntityDTO.getBalance() < 0) {
				modelAndView.addObject("customisedMsg",
						PropertyConstants.ENDOFSUBSCRIPTION);
			}
		}
		modelAndView.addObject("cartList", bookEntityDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "/requestBook", method = RequestMethod.POST)
	public ModelAndView requestBook(
			@ModelAttribute UserEntityDTO userEntityDTO, Principal principal)
			throws ServiceLayerException {
		ModelAndView modelAndView;
		if (principal != null) {
			List<BookEntityDTO> bookEntityDTOs = new ArrayList<BookEntityDTO>();
			bookEntityDTOs.addAll(bookService.getUserCart(principal.getName()));
			userService.setUserNewAddress(userEntityDTO, principal.getName());
			for (BookEntityDTO bookEntityDTO : bookEntityDTOs) {
				userService.requestBook(principal.getName(),
						bookEntityDTO.getBookId());
			}
			for (BookEntityDTO bookEntityDTO : bookEntityDTOs) {
				bookService.removeFromCart(principal.getName(),
						bookEntityDTO.getBookId());
			}
			modelAndView = new ModelAndView("redirect:/store");
			mailService.sendEmailNotifications(principal.getName(),
					PropertyConstants.FIRST_NAME,
					PropertyConstants.REQUEST_SUBJECT,
					PropertyConstants.REQUEST_MESSAGE);
		} else {
			modelAndView = new ModelAndView("index");
		}
		return modelAndView;
	}

	@RequestMapping(value = "user/returnBook", method = RequestMethod.GET)
	public String returnBook(
			@RequestParam(value = "requestId", required = false) long requestId,
			Principal principal) throws ServiceLayerException {
		userService.returnBook(requestId);
		mailService.sendEmailNotifications(principal.getName(),
				PropertyConstants.FIRST_NAME,
				PropertyConstants.REQUEST_SUBJECT,
				PropertyConstants.RETURN_REQUEST);
		return PropertyConstants.OP_SUCCESS;
	}

	@RequestMapping(value = "user/cancelRequest", method = RequestMethod.GET)
	public String cancelRequest(
			@RequestParam(value = "requestId", required = false) long requestId,
			Principal principal) throws ServiceLayerException {
		userService.cancelRequest(requestId, principal.getName());
		mailService.sendEmailNotifications(principal.getName(),
				PropertyConstants.FIRST_NAME,
				PropertyConstants.REQUEST_SUBJECT,
				PropertyConstants.CALCEL_REQUEST);
		return PropertyConstants.OP_SUCCESS;
	}

	@RequestMapping(value = "admin/declineUserRequest", method = RequestMethod.GET)
	public String declineRequest(
			@RequestParam(value = "requestId", required = false) long requestId,
			@RequestParam(value = "email", required = false) String email)
			throws ServiceLayerException {
		userService.cancelRequest(requestId, email);
		mailService.sendEmailNotifications(email, PropertyConstants.FIRST_NAME,
				PropertyConstants.REQUEST_SUBJECT,
				PropertyConstants.DECLINE_REQUEST);
		return PropertyConstants.OP_SUCCESS;
	}

}
