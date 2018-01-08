package com.weblearning.bookstore.utils.datatransfer;

import java.util.Date;

import com.weblearning.bookstore.dto.ActiveBookRequestDTO;
import com.weblearning.bookstore.dto.ActiveSubscriptionDTO;
import com.weblearning.bookstore.dto.BookEntityDTO;
import com.weblearning.bookstore.dto.SubscriptionPlanDTO;
import com.weblearning.bookstore.dto.UserEntityDTO;
import com.weblearning.bookstore.persistance.entities.ActiveBookRequests;
import com.weblearning.bookstore.persistance.entities.ActiveSubscription;
import com.weblearning.bookstore.persistance.entities.Book;
import com.weblearning.bookstore.persistance.entities.BookDetails;
import com.weblearning.bookstore.persistance.entities.BookReqHistory;
import com.weblearning.bookstore.persistance.entities.SubscriptionPlan;
import com.weblearning.bookstore.persistance.entities.UserDetails;
import com.weblearning.bookstore.persistance.entities.UserEntity;
import com.weblearning.bookstore.persistance.entities.UserHistory;
import com.weblearning.bookstore.utils.PropertyConstants;
import com.weblearning.bookstore.utils.Utilities;

public class PersistanceToDTO {

	public static BookEntityDTO bookEntityToDTO(Book book) {
		BookEntityDTO bookEntityDTO = new BookEntityDTO();
		BookDetails bookDetails = book.getBookDetails();
		bookEntityDTO.setAuthor(bookDetails.getAuthor());
		bookEntityDTO.setIsbn(book.getIsbn());
		bookEntityDTO.setBookId(bookDetails.getBookId());
		bookEntityDTO.setCategory(book.getBookCategory().getCategoryName());
		bookEntityDTO.setDescription(bookDetails.getDescription());
		bookEntityDTO.setImageurl(bookDetails.getImageurl());
		bookEntityDTO.setLanguage(bookDetails.getLanguage());
		bookEntityDTO.setNumPages(bookDetails.getNumPages());
		bookEntityDTO.setNumTimesRented(bookDetails.getNumTimesRented());
		bookEntityDTO.setPrice(bookDetails.getPrice());
		bookEntityDTO.setPublisher(bookDetails.getPublisher());
		bookEntityDTO.setRating(bookDetails.getRating());
		bookEntityDTO.setDeleted(book.isDeleted());
		bookEntityDTO.setTitle(bookDetails.getTitle());
		bookEntityDTO.setNoOfCopies(book.getNoOfCopies());
		return bookEntityDTO;
	}

	public static SubscriptionPlanDTO subscriptionPlanToDTO(
			SubscriptionPlan subscriptionPlan) {
		SubscriptionPlanDTO subscriptionPlanDTO = new SubscriptionPlanDTO();
		subscriptionPlanDTO.setMaxBooks(subscriptionPlan.getMaxBooks());
		subscriptionPlanDTO.setPlanCost(subscriptionPlan.getPlanCost());
		subscriptionPlanDTO.setPlanDuration(subscriptionPlan.getPlanDuration());
		subscriptionPlanDTO.setPlanId(subscriptionPlan.getPlanId());
		return subscriptionPlanDTO;
	}

	public static ActiveBookRequestDTO activeBookRequestToDTO(
			ActiveBookRequests activeBookRequests) {
		ActiveBookRequestDTO activeBookRequestDTO = new ActiveBookRequestDTO();
		activeBookRequestDTO.setBookDeliveryDate(Utilities
				.formatDate(activeBookRequests.getBookDeliveryDate()));
		activeBookRequestDTO.setBookId(activeBookRequests.getBookId());
		activeBookRequestDTO.setBookRequestDate(Utilities
				.formatDate(activeBookRequests.getBookRequestDate()));
		activeBookRequestDTO.setBookReturnDate(Utilities
				.formatDate(activeBookRequests.getBookReturnDate()));
		activeBookRequestDTO.setRequestId(activeBookRequests.getRequestId());
		Short bookDeliveryStatus = activeBookRequests.getBookDeliveryStatus();
		Short bookReturnStatus = activeBookRequests.getBookReturnStatus();
		String bookStatusString;
		if (bookDeliveryStatus == PropertyConstants.STATUS_PENDING) {
			bookStatusString = "PENDING BOOK REQUEST";
		} else if (bookReturnStatus == PropertyConstants.STATUS_PENDING) {
			bookStatusString = "PENDING RETURN REQUEST";
		} else if (bookDeliveryStatus == PropertyConstants.STATUS_CLOSED
				&& bookReturnStatus != PropertyConstants.STATUS_CLOSED) {
			bookStatusString = "DELIVERED";
		} else {
			bookStatusString = "RETURNED";
		}
		activeBookRequestDTO.setBookStatus(bookStatusString);
		return activeBookRequestDTO;
	}

	public static UserEntityDTO userEntityToDTO(UserEntity userEntity) {
		UserEntityDTO userEntityDTO = new UserEntityDTO();
		userEntityDTO.setEmail(userEntity.getEmail());
		UserDetails userDetails = userEntity.getUserdetails();
		userEntityDTO.setAddress(userDetails.getAddress());
		userEntityDTO.setCity(userDetails.getCity());
		userEntityDTO.setCountry(userDetails.getCountry());
		userEntityDTO.setDob(userDetails.getDob());
		userEntityDTO.setGender(userDetails.getGender());
		userEntityDTO.setMobile(userDetails.getMobile());
		userEntityDTO.setName(userDetails.getName());
		userEntityDTO.setNewAddress(userDetails.getNewAddress());
		userEntityDTO.setState(userDetails.getState());
		userEntityDTO.setZipcode(userDetails.getZipcode());
		userEntityDTO.setBalance(userEntity.getBalance());
		return userEntityDTO;
	}

	public static ActiveSubscriptionDTO activeSubscriptionToDTO(
			ActiveSubscription activeSubscription) {
		ActiveSubscriptionDTO activeSubscriptionDTO = new ActiveSubscriptionDTO();
		activeSubscriptionDTO.setHistoryId(-1);
		activeSubscriptionDTO.setPlanEndDate(Utilities
				.formatDate(activeSubscription.getPlanEndDate()));
		activeSubscriptionDTO.setPlanId(activeSubscription.getPlanId());
		activeSubscriptionDTO.setPlanStartDate(Utilities
				.formatDate(activeSubscription.getPlanStartDate()));
		activeSubscriptionDTO.setRemBooks(activeSubscription.getRemBooks());
		activeSubscriptionDTO.setRemDays(new Long((activeSubscription
				.getPlanEndDate().getTime() - new Date().getTime())
				/ PropertyConstants.DAYDURATION_MILLIS).intValue());
		return activeSubscriptionDTO;
	}

	public static ActiveSubscriptionDTO userHistoryToDTO(
			UserHistory activeSubscription) {
		ActiveSubscriptionDTO activeSubscriptionDTO = new ActiveSubscriptionDTO();
		activeSubscriptionDTO.setHistoryId(activeSubscription.getHistoryId());
		activeSubscriptionDTO.setPlanEndDate(Utilities
				.formatDate(activeSubscription.getPlanEndDate()));
		activeSubscriptionDTO.setPlanId(activeSubscription.getPlanId());
		activeSubscriptionDTO.setPlanStartDate(Utilities
				.formatDate(activeSubscription.getPlanStartDate()));
		activeSubscriptionDTO.setRemDays(new Long((activeSubscription
				.getPlanEndDate().getTime() - new Date().getTime())
				/ PropertyConstants.DAYDURATION_MILLIS).intValue());
		return activeSubscriptionDTO;
	}
	public static ActiveBookRequestDTO bookReqHistoryToDTO(
			BookReqHistory activeBookRequests) {
		ActiveBookRequestDTO activeBookRequestDTO = new ActiveBookRequestDTO();
		activeBookRequestDTO.setBookDeliveryDate(Utilities
				.formatDate(activeBookRequests.getBookDeliveryDate()));
		activeBookRequestDTO.setBookId(activeBookRequests.getBookId());
		activeBookRequestDTO.setBookReturnDate(Utilities
				.formatDate(activeBookRequests.getBookReturnDate()));
		activeBookRequestDTO.setRequestId(activeBookRequests.getRequestId());
		Short bookDeliveryStatus = activeBookRequests.getBookDeliveryStatus();
		Short bookReturnStatus = activeBookRequests.getBookReturnStatus();
		String bookStatusString;
		if (bookDeliveryStatus == PropertyConstants.STATUS_PENDING) {
			bookStatusString = "PENDING BOOK REQUEST";
		} else if (bookReturnStatus == PropertyConstants.STATUS_PENDING) {
			bookStatusString = "PENDING RETURN REQUEST";
		} else if (bookDeliveryStatus == PropertyConstants.STATUS_CLOSED
				&& bookReturnStatus != PropertyConstants.STATUS_CLOSED) {
			bookStatusString = "DELIVERED";
		} else {
			bookStatusString = "RETURNED";
		}
		activeBookRequestDTO.setBookStatus(bookStatusString);
		return activeBookRequestDTO;
	}
}
