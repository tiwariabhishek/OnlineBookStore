package com.weblearning.bookstore.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.exception.ServiceLayerException;
import com.weblearning.bookstore.persistance.BookDAO;
import com.weblearning.bookstore.utils.Utilities;
import com.weblearning.bookstore.utils.ValueComparator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.weblearning.bookstore.persistance.UserDetailsDAO;
import com.weblearning.bookstore.persistance.entities.ActiveBookRequests;
import com.weblearning.bookstore.persistance.entities.ActiveSubscription;
import com.weblearning.bookstore.persistance.entities.Book;
import com.weblearning.bookstore.persistance.entities.BookRecommendations;
import com.weblearning.bookstore.persistance.entities.BookReqHistory;
import com.weblearning.bookstore.persistance.entities.RequestBook;
import com.weblearning.bookstore.persistance.entities.UserEntity;
import com.weblearning.bookstore.persistance.entities.UserHistory;
import com.weblearning.bookstore.utils.PropertyConstants;

@EnableScheduling
@Component
@Transactional
public class SchedulerServiceImpl implements SchedulerService {

	private static final Logger LOGGER = Logger
			.getLogger(SchedulerServiceImpl.class);

	@Autowired
	UserDetailsDAO userDetailsDAO;

	@Autowired
    BookDAO bookDAO;

	@Autowired
	MailService mailService;

	@Override
	@Scheduled(cron = "*/5 * * * * ?")
	public void csvFileUpload() {
	}

	@Override
	@Scheduled(cron = "0 59 23 * * ?")
	public void recommendBooks() throws ServiceLayerException {
		try {
			List<String> userEmails = userDetailsDAO.getAllUserEmails();
			for (String email : userEmails) {
				UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
				Map<String, ArrayList<Long>> authorMap = new HashMap<String, ArrayList<Long>>();
				ValueComparator valueComparatorForAuthorMap = new ValueComparator(
						authorMap);
				Map<String, ArrayList<Long>> categoryMap = new HashMap<String, ArrayList<Long>>();
				ValueComparator valueComparatorForCategoryMap = new ValueComparator(
						categoryMap);
				Map<String, ArrayList<Long>> authorSortedMap = new TreeMap<String, ArrayList<Long>>(
						valueComparatorForAuthorMap);
				Map<String, ArrayList<Long>> categorySortedMap = new TreeMap<String, ArrayList<Long>>(
						valueComparatorForCategoryMap);
				Set<UserHistory> userHistory = userEntity.getUserHistory();
				List<Long> bookIdFromHistory = Utilities
						.getAllBookIdsFromHistory(userHistory);
				Set<ActiveBookRequests> activeBookRequests = userEntity
						.getActiveSubscription().getActiveRequests();
				List<Long> bookIdFromActiveRequests = Utilities
						.getAllBookIdsFromActiveRequests(activeBookRequests);
				for (Long bookId : bookIdFromHistory) {
					Book book = bookDAO.getBook(bookId);
					String author = book.getBookDetails().getAuthor();
					String category = book.getBookCategory().getCategoryName();
					if (!authorMap.containsKey(author)) {
						authorMap.put(author, new ArrayList<Long>());
					}
					ArrayList<Long> bookIdList = authorMap.get(author);
					bookIdList.add(bookId);
					authorMap.put(author, bookIdList);
					if (!categoryMap.containsKey(category)) {
						categoryMap.put(category, new ArrayList<Long>());
					}
					bookIdList = categoryMap.get(category);
					bookIdList.add(bookId);
					categoryMap.put(category, bookIdList);
				}
				for (Long bookId : bookIdFromActiveRequests) {
					Book book = bookDAO.getBook(bookId);
					String author = book.getBookDetails().getAuthor();
					String category = book.getBookCategory().getCategoryName();
					if (!authorMap.containsKey(author)) {
						authorMap.put(author, new ArrayList<Long>());
					}
					ArrayList<Long> bookIdList = authorMap.get(author);
					bookIdList.add(bookId);
					authorMap.put(author, bookIdList);
					if (!categoryMap.containsKey(category)) {
						categoryMap.put(category, new ArrayList<Long>());
					}
					bookIdList = categoryMap.get(category);
					bookIdList.add(bookId);
					categoryMap.put(category, bookIdList);
				}
				authorSortedMap.putAll(authorMap);
				categorySortedMap.putAll(categoryMap);
				BookRecommendations bookRecommendations = userEntity
						.getBookRecommendations();
				bookRecommendations.getBookIds().clear();

				for (Entry<String, ArrayList<Long>> entry : authorSortedMap
						.entrySet()) {
					String author = entry.getKey();
					List<Long> bookIds = entry.getValue();
					Collections.sort(bookIds);
					List<Book> authorBooks = bookDAO.fetchBooksByAuthor(author);
					for (Book book : authorBooks) {
						if (Collections.binarySearch(bookIds, book.getBookId()) < 0) {
							RequestBook requestBook = new RequestBook();
							requestBook.setBookId(book.getBookId());
							bookRecommendations.getBookIds().add(requestBook);
						}
					}
				}
				for (Entry<String, ArrayList<Long>> entry : categorySortedMap
						.entrySet()) {
					String category = entry.getKey();
					List<Long> bookIds = entry.getValue();
					Collections.sort(bookIds);
					List<Book> categoryBooks = bookDAO
							.fetchBooksByCategory(category);
					for (Book book : categoryBooks) {
						if (Collections.binarySearch(bookIds, book.getBookId()) < 0) {
							RequestBook requestBook = new RequestBook();
							requestBook.setBookId(book.getBookId());
							bookRecommendations.getBookIds().add(requestBook);
						}
					}
				}
				userEntity.setBookRecommendations(bookRecommendations);
				bookRecommendations.setUser(userEntity);
				if (!authorMap.isEmpty() || !categoryMap.isEmpty()) {
					userDetailsDAO.updateUser(userEntity);
				}
			}
		} catch (DAOLayerException e) {
			String message = "Failed to recommend books.";
			LOGGER.error(message);
			throw new ServiceLayerException(message);
		}
	}

	@Override
	@Scheduled(cron = "0 59 0 * * ?")
	public void addDetailsToHistory() throws ServiceLayerException {
		try {
			List<String> userEmails = userDetailsDAO.getAllUserEmails();
			for (String email : userEmails) {
				UserEntity userEntity = userDetailsDAO.getUserByEmail(email);
				ActiveSubscription activeSubscription = userEntity
						.getActiveSubscription();
				int remDays = new Long((activeSubscription.getPlanEndDate()
						.getTime() - new Date().getTime())
						/ PropertyConstants.DAYDURATION_MILLIS).intValue();
				if (remDays <= PropertyConstants.X_DAYS) {
					userEntity.setBalance(-1);
					userEntity.setEnabled(false);
					Set<UserHistory> userHistories = userEntity
							.getUserHistory();
					UserHistory userHistory = new UserHistory();
					userHistory.setPlanEndDate(activeSubscription
							.getPlanEndDate());
					userHistory.setPlanStartDate(activeSubscription
							.getPlanStartDate());
					userHistory.setPlanId(activeSubscription.getPlanId());
					Set<ActiveBookRequests> activeBookRequests = activeSubscription
							.getActiveRequests();
					Set<BookReqHistory> bookReqHistories = userHistory
							.getBookReqHisory();
					for (ActiveBookRequests activeBookRequest : activeBookRequests) {
						BookReqHistory bookReqHistory = new BookReqHistory();
						bookReqHistory.setBookDeliveryDate(activeBookRequest
								.getBookDeliveryDate());
						bookReqHistory.setBookDeliveryStatus(activeBookRequest
								.getBookDeliveryStatus());
						bookReqHistory.setBookId(activeBookRequest.getBookId());
						bookReqHistory.setBookReturnDate(activeBookRequest
								.getBookReturnDate());
						bookReqHistory.setBookReturnStatus(activeBookRequest
								.getBookReturnStatus());
						bookReqHistory.setRequestId(activeBookRequest
								.getRequestId());
						bookReqHistories.add(bookReqHistory);
					}
					activeSubscription.setDetailsAddedToHistory(true);
					userHistories.add(userHistory);
					userDetailsDAO.updateUser(userEntity);
				}
				if (remDays <= PropertyConstants.DAY) {
					mailService.sendEmailNotifications(email,
							PropertyConstants.FIRST_NAME,
							PropertyConstants.SUBSCRIPTION_ENDED_SUBJECT,
							PropertyConstants.TIMEREM_MONTH_MESSAGE);
				} else if (remDays <= PropertyConstants.WEEK_DAYS) {
					mailService.sendEmailNotifications(email,
							PropertyConstants.FIRST_NAME,
							PropertyConstants.SUBSCRIPTION_ENDED_SUBJECT,
							PropertyConstants.TIMEREM_WEEK_MESSAGE);
				} else if (remDays <= PropertyConstants.MONTH_DAYS) {
					mailService.sendEmailNotifications(email,
							PropertyConstants.FIRST_NAME,
							PropertyConstants.SUBSCRIPTION_ENDED_SUBJECT,
							PropertyConstants.TIMEREM_DAY_MESSAGE);
				}
			}
		} catch (DAOLayerException e) {
			String message = "Failed to add details to history.";
			throw new ServiceLayerException(message);
		}
	}
}