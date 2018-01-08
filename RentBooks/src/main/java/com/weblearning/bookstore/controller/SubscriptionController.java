package com.weblearning.bookstore.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.weblearning.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.weblearning.bookstore.dto.ActiveBookRequestDTO;
import com.weblearning.bookstore.dto.ActiveSubscriptionDTO;
import com.weblearning.bookstore.dto.BookEntityDTO;
import com.weblearning.bookstore.dto.SubscriptionPlanDTO;
import com.weblearning.bookstore.dto.UserEntityDTO;
import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.exception.ServiceLayerException;
import com.weblearning.bookstore.services.BookService;
import com.weblearning.bookstore.services.SubscriptionService;
import com.weblearning.bookstore.utils.PropertyConstants;
import com.weblearning.bookstore.utils.Utilities;

@RestController
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired(required = true)
	public void setXMLService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@RequestMapping(value = "/uploadXML", method = RequestMethod.GET)
	public String uploadXML() {
		return "admin_uploadXML";
	}

	@RequestMapping(value = "admin/addupdatedeletesub", method = RequestMethod.GET)
	public ModelAndView modifySubscription() {
		return new ModelAndView("admin_uploadXML");
	}

	@RequestMapping(value = "admin/savexml", method = RequestMethod.POST)
	public ModelAndView addXML(
			@RequestParam(value = "xmlFile", required = false) MultipartFile multipartFile)
			throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("admin_uploadXML");
		String message = PropertyConstants.WRONG_INPUT;
		String fileName = Utilities.saveFile(multipartFile);
		if (!fileName.equals(PropertyConstants.WRONG_INPUT)) {
			subscriptionService.uploadSubscriptionsXML(fileName);
			message = PropertyConstants.UPLOAD_SUCCESS;
		}
		modelAndView.addObject("customisedMsg", message);
		return modelAndView;
	}

	@RequestMapping(value = "/getSubscriptionDetails", method = RequestMethod.GET)
	public List<SubscriptionPlanDTO> getAllSubscriptions()
			throws ServiceLayerException {
		List<SubscriptionPlanDTO> subscriptionPlanList = subscriptionService
				.getAllSubscriptions();
		return subscriptionPlanList;
	}

	@RequestMapping(value = "user/updatePlan", method = RequestMethod.GET)
	public String upgradeSubscription(
			@RequestParam(value = "planId", required = false) long planId,
			Principal principal) throws ServiceLayerException {
		String message = subscriptionService.changeSubscription(
				principal.getName(), planId);
		return message;
	}

	@RequestMapping(value = "admin/getActiveUsers", method = RequestMethod.GET)
	public ModelAndView getAllUsersWithActiveSubscription()
			throws DAOLayerException, ServiceLayerException {
		List<UserEntityDTO> userEntityDTOs = subscriptionService
				.getAllUsersWithActiveSubscription();
		List<ActiveSubscriptionDTO> activeSubscriptionDTOs = new ArrayList<ActiveSubscriptionDTO>();
		for (UserEntityDTO userEntityDTO : userEntityDTOs) {
			ActiveSubscriptionDTO activeSubscriptionDTO = userService
					.getActiveSubscriptionDTO(userEntityDTO.getEmail());
			activeSubscriptionDTOs.add(activeSubscriptionDTO);
		}
		ModelAndView modelAndView = new ModelAndView("activeusers");
		modelAndView.addObject("activeusers", userEntityDTOs);
		modelAndView.addObject("activeSubscriptionDTO", activeSubscriptionDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "user/history", method = RequestMethod.GET)
	public ModelAndView getUserHistory(Principal principal)
			throws DAOLayerException, ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("userhistory");
		ActiveSubscriptionDTO activeSubscriptionDTO = userService
				.getActiveSubscriptionDTO(principal.getName());
		SubscriptionPlanDTO subscriptionPlanDTO = subscriptionService
				.getSubscriptionPlan(activeSubscriptionDTO.getPlanId());
		List<ActiveSubscriptionDTO> activeSubscriptionDTOs = new ArrayList<ActiveSubscriptionDTO>();
		List<SubscriptionPlanDTO> subscriptionPlanDTOs = new ArrayList<SubscriptionPlanDTO>();
		activeSubscriptionDTOs.add(activeSubscriptionDTO);
		subscriptionPlanDTOs.add(subscriptionPlanDTO);
		List<ActiveSubscriptionDTO> userHistory = subscriptionService
				.getUserSubscriptionHistory(principal.getName());
		for (ActiveSubscriptionDTO uhistory : userHistory) {
			activeSubscriptionDTOs.add(uhistory);
			subscriptionPlanDTO = subscriptionService
					.getSubscriptionPlan(uhistory.getPlanId());
			subscriptionPlanDTOs.add(subscriptionPlanDTO);
		}
		modelAndView.addObject("activesubscription", activeSubscriptionDTOs);
		modelAndView.addObject("subscription", subscriptionPlanDTOs);
		return modelAndView;
	}

	@RequestMapping(value = "user/bookRequestHistory", method = RequestMethod.GET)
	public ModelAndView getUserBookHistory(
			@RequestParam("historyId") long historyId, Principal principal)
			throws DAOLayerException, ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView("bookreqhistory");
		List<ActiveBookRequestDTO> activeBookRequestDTOs = null;
		List<BookEntityDTO> bookEntityDTOs = new ArrayList<BookEntityDTO>();
		if (historyId != -1) {
			activeBookRequestDTOs = bookService
					.getAllUserRequestsFromHistory(historyId);
		} else {
			activeBookRequestDTOs = userService.getAllBookRequests(principal
					.getName());
		}
		for (ActiveBookRequestDTO activeBookRequestDTO : activeBookRequestDTOs) {
			BookEntityDTO bookEntityDTO = bookService
					.getBook(activeBookRequestDTO.getBookId());
			bookEntityDTOs.add(bookEntityDTO);
		}
		modelAndView.addObject("bookrequest", activeBookRequestDTOs);
		modelAndView.addObject("books", bookEntityDTOs);
		return modelAndView;
	}
}
