package com.weblearning.bookstore.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weblearning.bookstore.dto.ActiveBookRequestDTO;
import com.weblearning.bookstore.dto.ActiveSubscriptionDTO;
import com.weblearning.bookstore.dto.UserEntityDTO;
import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.exception.ServiceLayerException;
import com.weblearning.bookstore.persistance.BookDAO;
import com.weblearning.bookstore.persistance.SubscriptionPlanDAO;
import com.weblearning.bookstore.persistance.UserDetailsDAO;
import com.weblearning.bookstore.persistance.entities.ActiveBookRequests;
import com.weblearning.bookstore.persistance.entities.ActiveSubscription;
import com.weblearning.bookstore.persistance.entities.Book;
import com.weblearning.bookstore.persistance.entities.BookRecommendations;
import com.weblearning.bookstore.persistance.entities.SubscriptionPlan;
import com.weblearning.bookstore.persistance.entities.UserCart;
import com.weblearning.bookstore.persistance.entities.UserEntity;
import com.weblearning.bookstore.persistance.entities.UserHistory;
import com.weblearning.bookstore.persistance.entities.WishList;
import com.weblearning.bookstore.utils.PropertyConstants;
import com.weblearning.bookstore.utils.Utilities;
import com.weblearning.bookstore.utils.datatransfer.DTOToPersistance;
import com.weblearning.bookstore.utils.datatransfer.PersistanceToDTO;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger
			.getLogger(UserServiceImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsDAO userDetailsDAO;

	@Autowired
	private BookDAO bookDAO;

	@Autowired
	private SubscriptionPlanDAO subscriptionPlanDAO;

	@Override
	@Transactional(readOnly = true)
	public List<UserHistory> getUserHistory(String email)
			throws ServiceLayerException {
		List<UserHistory> userHistories = null;
		try {
			userHistories = userDetailsDAO.getUserHistory(email);
			return userHistories;
		} catch (DAOLayerException e) {
			String message = "Failed to get user history";
			myServiceLayerException(message, e);
		}
		return userHistories;
	}

	@Override
	@Transactional
	public boolean registerUser(UserEntityDTO userEntityDTO)
			throws ServiceLayerException {
		try {
			ActiveSubscription activeSubscription = new ActiveSubscription();
			UserEntity userEntity = DTOToPersistance
					.userEntityDTOToPersistance(userEntityDTO, null, true);
			WishList wishList = new WishList();
			UserCart userCart = new UserCart();
			BookRecommendations bookRecommendations = new BookRecommendations();

			activeSubscription.setDetailsAddedToHistory(false);
			activeSubscription.setPlanStartDate(new Date());
			activeSubscription.setPlanId(userEntityDTO.getPlanId());

			SubscriptionPlan subscriptionPlan = subscriptionPlanDAO
					.getSubscriptionPlan(activeSubscription.getPlanId());

			activeSubscription.setPlanEndDate(Utilities.computeDate(
					activeSubscription.getPlanStartDate(),
					subscriptionPlan.getPlanDuration()));
			activeSubscription.setRemBooks(subscriptionPlan.getMaxBooks());
			userEntity.setBalance(subscriptionPlan.getPlanCost());

			userEntity.setPassword(passwordEncoder.encode(userEntityDTO
					.getPassword()));
			userEntity.setRoleId(PropertyConstants.ROLE_USER);

			userEntity.setActiveSubscription(activeSubscription);
			activeSubscription.setUser(userEntity);
			userEntity.setWishList(wishList);
			wishList.setUser(userEntity);
			userEntity.setUserCart(userCart);
			userCart.setUser(userEntity);
			userEntity.setSubscriptionPlan(subscriptionPlan);
			userEntity.setBookRecommendations(bookRecommendations);
			bookRecommendations.setUser(userEntity);

			userDetailsDAO.registerUser(userEntity);
		} catch (DAOLayerException e) {
			String message = "Failed to register user.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, ArrayList<ActiveBookRequestDTO>> getAllUserRequests()
			throws ServiceLayerException {
		ArrayList<ActiveBookRequestDTO> activeBookRequestDTOs = null;
		Map<String, ArrayList<ActiveBookRequestDTO>> userRequests = new LinkedHashMap<String, ArrayList<ActiveBookRequestDTO>>();
		try {
			List<String> userEmailList = userDetailsDAO.getAllUserEmails();
			for (String email : userEmailList) {
				activeBookRequestDTOs = new ArrayList<ActiveBookRequestDTO>();
				List<ActiveBookRequests> activeBookRequests = userDetailsDAO
						.getAllUserRequests(email);
				for (ActiveBookRequests activeBookRequest : activeBookRequests) {
					ActiveBookRequestDTO activeBookRequestDTO = PersistanceToDTO
							.activeBookRequestToDTO(activeBookRequest);
					activeBookRequestDTOs.add(activeBookRequestDTO);
				}
				userRequests.put(email, activeBookRequestDTOs);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to get all user requests.";
			myServiceLayerException(message, e);
		}
		return userRequests;
	}

	@Override
	@Transactional
	public boolean requestBook(String email, long bookId)
			throws ServiceLayerException {
		try {
			ActiveSubscription activeSubscription = subscriptionPlanDAO
					.getActiveSubscriptionByEmail(email);
			UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
			int remBooks = activeSubscription.getRemBooks();
			if (remBooks > 0) {
				ActiveBookRequests activeBookRequests = new ActiveBookRequests();
				activeBookRequests.setBookId(bookId);
				activeBookRequests.setBookRequestDate(new Date());
				activeBookRequests
						.setBookDeliveryStatus(PropertyConstants.STATUS_PENDING);
				activeBookRequests
						.setBookReturnStatus(PropertyConstants.STATUS_UNKNOWN);
				activeSubscription.getActiveRequests().add(activeBookRequests);
				Book book = bookDAO.getBook(bookId);
				int bookCost = book.getBookDetails().getPrice();
				int balance = userEntity.getBalance();
				if (balance >= bookCost) {
					balance -= bookCost;
					userEntity.setBalance(balance);
					userDetailsDAO.updateUser(userEntity);
				} else {
					return false;
				}
				activeSubscription.setRemBooks(remBooks - 1);
				userDetailsDAO.saveBookRequest(activeSubscription);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to request Book";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean returnBook(long requestId) throws ServiceLayerException {
		try {
			ActiveBookRequests activeBookRequest = bookDAO
					.getActiveBookRequest(requestId);
			activeBookRequest
					.setBookReturnStatus(PropertyConstants.STATUS_PENDING);
			userDetailsDAO.updateActiveBookRequests(activeBookRequest);
		} catch (DAOLayerException e) {
			String message = "Failed to return Book.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean cancelRequest(long requestId, String email)
			throws ServiceLayerException {
		try {
			ActiveSubscription activeSubscription = subscriptionPlanDAO
					.getActiveSubscriptionByEmail(email);
			ActiveBookRequests activeBookRequest = bookDAO
					.getActiveBookRequest(requestId);
			UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
			if (activeBookRequest.getBookDeliveryStatus() == PropertyConstants.STATUS_PENDING) {
				activeSubscription.getActiveRequests()
						.remove(activeBookRequest);
				int remBooks = activeSubscription.getRemBooks();
				activeSubscription.setRemBooks(remBooks + 1);
				subscriptionPlanDAO.saveActiveSubscription(activeSubscription);
				Book book = bookDAO.getBook(activeBookRequest.getBookId());
				int bookCost = book.getBookDetails().getPrice();
				int balance = userEntity.getBalance();
				balance += bookCost;
				userEntity.setBalance(balance);
				userDetailsDAO.updateUser(userEntity);
			} else if (activeBookRequest.getBookReturnStatus() == PropertyConstants.STATUS_PENDING) {
				activeBookRequest
						.setBookReturnStatus(PropertyConstants.STATUS_UNKNOWN);
				userDetailsDAO.updateActiveBookRequests(activeBookRequest);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to cancel request.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public String approveUserRequest(String email, long requestId)
			throws ServiceLayerException {
		String response = null;
		try {
			ActiveBookRequests activeBookRequest = bookDAO
					.getActiveBookRequest(requestId);
			ActiveSubscription activeSubscription = subscriptionPlanDAO
					.getActiveSubscriptionByEmail(email);
			int remBooks = activeSubscription.getRemBooks();
			Book book = bookDAO.getBook(activeBookRequest.getBookId());
			int numCopies = book.getNoOfCopies();
			if (activeBookRequest.getBookDeliveryStatus() == PropertyConstants.STATUS_PENDING) {
				if (numCopies == 0) {
					return "OUT OF STOCK";
				}
				Date delDate = new Date();
				response = Utilities.formatDate(delDate);
				activeBookRequest.setBookDeliveryDate(delDate);
				activeBookRequest
						.setBookDeliveryStatus(PropertyConstants.STATUS_CLOSED);
				response += ",DELIVERED";
				book.setNoOfCopies(numCopies - 1);
				int numRented = book.getBookDetails().getNumTimesRented();
				book.getBookDetails().setNumTimesRented(numRented + 1);
				bookDAO.updateBook(book);
			} else if (activeBookRequest.getBookReturnStatus() == PropertyConstants.STATUS_PENDING) {
				UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
				Date retDate = new Date();
				activeBookRequest.setBookReturnDate(retDate);
				response = Utilities.formatDate(retDate);
				activeBookRequest
						.setBookReturnStatus(PropertyConstants.STATUS_CLOSED);
				response += ",RETURNED";
				activeSubscription.setRemBooks(remBooks + 1);
				int bookCost = book.getBookDetails().getPrice();
				int balance = userEntity.getBalance();
				balance += bookCost;
				userEntity.setBalance(balance);
				userDetailsDAO.updateUser(userEntity);
				book.setNoOfCopies(numCopies + 1);
				bookDAO.updateBook(book);
				subscriptionPlanDAO.saveActiveSubscription(activeSubscription);
			}
			userDetailsDAO.updateActiveBookRequests(activeBookRequest);
		} catch (DAOLayerException e) {
			String message = "Failed to apporve user request.";
			myServiceLayerException(message, e);
		}
		return response;
	}

	@Override
	@Transactional
	public List<UserEntity> getAllUsersWithActiveSubscription()
			throws ServiceLayerException {
		List<UserEntity> userEntities = null;
		try {
			userEntities = userDetailsDAO.getAllUsersWithActiveSubscription();
		} catch (DAOLayerException e) {
			String message = "Failed to get all users with active subscriptions.";
			myServiceLayerException(message, e);
		}
		return userEntities;
	}

	@Override
	@Transactional
	public boolean updateUser(UserEntityDTO userEntityDTO)
			throws ServiceLayerException {
		try {
			UserEntity userEntity = userDetailsDAO.getUserByEmail(userEntityDTO
					.getEmail());
			userEntity = DTOToPersistance.userEntityDTOToPersistance(
					userEntityDTO, userEntity, false);
			userDetailsDAO.updateUser(userEntity);
		} catch (DAOLayerException e) {
			String message = "Failed to update user";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public UserEntityDTO getUserByEmail(String email)
			throws ServiceLayerException {
		UserEntityDTO userEntityDTO = null;
		try {
			UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
			userEntityDTO = PersistanceToDTO.userEntityToDTO(userEntity);
		} catch (DAOLayerException e) {
			String message = "Failed to get user by email.";
			myServiceLayerException(message, e);
		}
		return userEntityDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<ActiveBookRequestDTO> getAllBookRequests(String email)
			throws ServiceLayerException {
		ArrayList<ActiveBookRequestDTO> activeBookRequestDTOs = null;
		try {
			activeBookRequestDTOs = new ArrayList<ActiveBookRequestDTO>();
			List<ActiveBookRequests> activeBookRequests = userDetailsDAO
					.getAllUserRequests(email);
			for (ActiveBookRequests activeBookRequest : activeBookRequests) {
				ActiveBookRequestDTO activeBookRequestDTO = PersistanceToDTO
						.activeBookRequestToDTO(activeBookRequest);
				activeBookRequestDTOs.add(activeBookRequestDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to get all book requests.";
			myServiceLayerException(message, e);
		}
		return activeBookRequestDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean checkEmailForNull(String email) throws ServiceLayerException {
		Boolean value = null;
		try {
			value = userDetailsDAO.getUserStatus(email);
		} catch (DAOLayerException e) {
			String message = "Failed to check email.";
			myServiceLayerException(message, e);
		}
		return value;
	}

	@Override
	@Transactional(readOnly = true)
	public ActiveSubscriptionDTO getActiveSubscriptionDTO(String email)
			throws ServiceLayerException {
		ActiveSubscriptionDTO activeSubscriptionDTO = null;
		try {
			ActiveSubscription activeSubscription = subscriptionPlanDAO
					.getActiveSubscriptionByEmail(email);
			activeSubscriptionDTO = PersistanceToDTO
					.activeSubscriptionToDTO(activeSubscription);
		} catch (DAOLayerException e) {
			String message = "Failed to fetch active subscription at service layer.";
			myServiceLayerException(message, e);
		}
		return activeSubscriptionDTO;
	}

	@Override
	@Transactional
	public boolean setUserNewAddress(UserEntityDTO userEntityDTO, String email)
			throws ServiceLayerException {
		try {
			UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
			userEntity.getUserdetails().setNewAddress(
					userEntityDTO.getNewAddress());
			userDetailsDAO.updateUser(userEntity);
		} catch (DAOLayerException e) {
			String message = "Failed to update user address.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean changePassword(String email, String password,
			String oldpassword) throws ServiceLayerException {
		boolean matched = false;
		try {
			UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
			if (oldpassword == null) {
				userEntity.setPassword(passwordEncoder.encode(password));
			} else {
				matched = passwordEncoder.matches(oldpassword,
						userEntity.getPassword());
				if (matched) {
					userEntity.setPassword(passwordEncoder.encode(password));
				}
			}
			userDetailsDAO.updateUser(userEntity);
		} catch (DAOLayerException e) {
			String message = "Failed to change password.";
			myServiceLayerException(message, e);
		}
		return matched;
	}

	@Override
	public void myServiceLayerException(String message,
			DAOLayerException daoLayerException) throws ServiceLayerException {
		LOGGER.error(message, daoLayerException);
		throw new ServiceLayerException(message);
	}
}