package com.impetus.bookstore.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.impetus.bookstore.dto.ActiveSubscriptionDTO;
import com.impetus.bookstore.dto.BookEntityDTO;
import com.impetus.bookstore.dto.SearchResultsDTO;
import com.impetus.bookstore.dto.UserEntityDTO;
import com.impetus.bookstore.exception.ServiceLayerException;
import com.impetus.bookstore.services.BookService;
import com.impetus.bookstore.services.UserService;
import com.impetus.bookstore.utils.PropertyConstants;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/favBooks", method = RequestMethod.GET)
	public ModelAndView getUserFavBooks(Model model) {
		ModelAndView mav = new ModelAndView("welcome");
		mav.addObject("message", "Abhishek!!!");
		return mav;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView getSearchResults(
			@RequestParam(value = "work", required = false) String work,
			@ModelAttribute SearchResultsDTO searchResultsDTO)
			throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("searchresults");
		modelAndView.addObject("bookList",
				bookService.fetchBooks(searchResultsDTO));
		if (work != null && work.equalsIgnoreCase("getCategory")) {
			modelAndView.addObject("work", work);
			modelAndView.addObject("category", searchResultsDTO.getCategory());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/getSuggestions", method = RequestMethod.GET)
	public List<String> getSearchSuggestions(
			@RequestParam(value = "term", required = false) String searchTerm)
			throws ServiceLayerException {
		List<String> searchSuggestions = bookService
				.getBookSuggestions(searchTerm);
		return searchSuggestions;
	}

	@RequestMapping(value = "/store", method = RequestMethod.GET)
	public ModelAndView getBookStore(Principal principal)
			throws ServiceLayerException {
		List<BookEntityDTO> bookEntityDTOs = bookService.getAllBooks();
		ModelAndView modelAndView = new ModelAndView("bookstore");
		if (principal != null) {
			UserEntityDTO userEntityDTO = userService.getUserByEmail(principal
					.getName());
			ActiveSubscriptionDTO activeSubscriptionDTO = userService
					.getActiveSubscriptionDTO(principal.getName());
			modelAndView.addObject("userentity", userEntityDTO);
			modelAndView.addObject("activesubscription", activeSubscriptionDTO);
		}
		modelAndView.addObject("bookList", bookEntityDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "store/getBook", method = RequestMethod.GET)
	public ModelAndView getBook(@RequestParam("bookId") long bookId)
			throws ServiceLayerException {
		BookEntityDTO bookEntityDTO = bookService.getBook(bookId);
		ModelAndView modelAndView = new ModelAndView("bookdetails");
		modelAndView.addObject("book", bookEntityDTO);
		return modelAndView;
	}

	@RequestMapping(value = "/admin/deleteBook", method = RequestMethod.GET)
	public boolean deleteBook(
			@RequestParam(value = "bookId", required = false) long bookId)
			throws ServiceLayerException {
		bookService.deleteBook(bookId);
		return true;
	}

	@RequestMapping(value = "/admin/updatebook", method = RequestMethod.GET)
	public ModelAndView getUpdateBookPage(@RequestParam("bookId") long bookId)
			throws ServiceLayerException {
		BookEntityDTO bookEntityDTO = bookService.getBook(bookId);
		ModelAndView modelAndView = new ModelAndView("updatebook");
		modelAndView.addObject("book", bookEntityDTO);
		return modelAndView;

	}

	@RequestMapping(value = "/admin/updateBook", method = RequestMethod.POST)
	public ModelAndView updateBook(@RequestParam("bookId") String bookId,
			@ModelAttribute BookEntityDTO bookEntityDTO,
			@RequestParam("image") MultipartFile image)
			throws ServiceLayerException {
		bookService.updateBook(bookId, bookEntityDTO, image);
		ModelAndView modelAndView = new ModelAndView(
				"redirect:/admin/addupdatedeletebooks");
		return modelAndView;

	}

	@RequestMapping(value = "/admin/addbooks", method = RequestMethod.POST)
	public ModelAndView addBook(@ModelAttribute BookEntityDTO bookEntityDTO,
			@RequestParam("image") MultipartFile image)
			throws ServiceLayerException {
		bookService.addBook(bookEntityDTO, image);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("customisedMsg",
				PropertyConstants.BOOKADD_SUCCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/admin/addupdatedeletebooks", method = RequestMethod.GET)
	public ModelAndView addUpdateDeleteBooks() throws ServiceLayerException {
		List<BookEntityDTO> bookEntityDTOs = bookService.getAllBooks();
		ModelAndView modelAndView = new ModelAndView("addupdatedeletebooks");
		modelAndView.addObject("Books", bookEntityDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "/user/wishList", method = RequestMethod.GET)
	public ModelAndView wishList(Principal principal)
			throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("wishlist");
		List<BookEntityDTO> bookEntityDTOs = bookService.getWishList(principal
				.getName());
		modelAndView.addObject("wishlist", bookEntityDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "/user/addToWishList", method = RequestMethod.GET)
	public String addToWishList(
			@RequestParam(value = "bookId", required = false) long bookId,
			Principal principal) throws ServiceLayerException {
		String message = bookService.addToWishList(principal.getName(), bookId);
		return message;
	}

	@RequestMapping("user/removeFromWishlist")
	public boolean removeFromCart(
			@RequestParam(value = "bookId", required = false) long bookId,
			Principal principal) throws ServiceLayerException {
		bookService.removeFromWishList(principal.getName(), bookId);
		return true;
	}
}
