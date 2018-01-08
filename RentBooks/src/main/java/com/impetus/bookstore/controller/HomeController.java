package com.impetus.bookstore.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.impetus.bookstore.dto.UserEntityDTO;
import com.impetus.bookstore.exception.ServiceLayerException;
import com.impetus.bookstore.services.MailService;
import com.impetus.bookstore.services.UserService;
import com.impetus.bookstore.utils.PropertyConstants;
import com.impetus.bookstore.utils.Utilities;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired(required = true)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = { "/", "welcomePage" }, method = RequestMethod.GET)
	public String welcomepage() {
		return "index";
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public String changePasswordView() {
		return "changepassword";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ModelAndView changePassword(
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "password", required = false) String passwrd,
			@RequestParam(value = "oldpassword", required = false) String oldpassword,
			Principal principal) throws ServiceLayerException {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(email + " " + passwrd + " " + oldpassword);
		if (principal != null) {
			boolean changed = userService.changePassword(principal.getName(),
					passwrd, oldpassword);
			String message = changed ? PropertyConstants.CHANGEPASSWORD_SUCCESS
					: PropertyConstants.CHANGEPASSWORD_FAILURE;
			modelAndView.setViewName("changepassword");
			modelAndView.addObject("customisedMsg", message);
		} else {
			modelAndView.setViewName("index");
			modelAndView.addObject("customisedMsg",
					PropertyConstants.FORGOTPASSWORD_MESSAGE);
			String password = Utilities.getCurrentTimeStamp();
			userService.changePassword(email, password, null);
			mailService.sendEmailNotifications(email, "User",
					PropertyConstants.CHANGEPASSOWRD_SUBJECT,
					PropertyConstants.CHANGEPASSWORD_MESSAGE + password);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String welcome() {
		return "index";
	}

	@RequestMapping(value = "/signup/planId={id}", method = RequestMethod.GET)
	public String signup() {
		return "signup";
	}

	@RequestMapping(value = "/isAlreadyRegistered", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkForEmailExistance(
			@RequestParam(value = "email", required = false) String email)
			throws ServiceLayerException {
		boolean result = userService.checkEmailForNull(email) == null;
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute UserEntityDTO userEntityDTO)
			throws ServiceLayerException {
		String message = "";
		ModelAndView modelAndView = new ModelAndView("index");
		userService.registerUser(userEntityDTO);
		message = PropertyConstants.SIGNUP_SUCCESS;
		modelAndView.addObject("customisedMsg", message);
		return modelAndView;
	}

	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public String aboutUsPage() {
		return "aboutus";
	}

	@RequestMapping(value = "/contactus", method = RequestMethod.GET)
	public String contactUsPage() {
		return "contactus";
	}

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String helpPage() {
		return "help";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String errorPage() {
		return "error";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("error");

		return model;

	}
}
