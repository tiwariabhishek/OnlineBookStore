package com.weblearning.bookstore.persistance;

import java.util.HashSet;
import java.util.List;

import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.persistance.entities.ActiveBookRequests;
import com.weblearning.bookstore.persistance.entities.BookReqHistory;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weblearning.bookstore.persistance.entities.Book;
import com.weblearning.bookstore.persistance.entities.BookCategory;
import com.weblearning.bookstore.persistance.entities.RequestBook;
import com.weblearning.bookstore.persistance.entities.UserCart;
import com.weblearning.bookstore.persistance.entities.WishList;

@Repository
public class BookDAOImpl implements BookDAO {

	private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WishList getWishList(String email) throws DAOLayerException {
		WishList wishList = null;
		try {
			String hql = "select userentity.wishList FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			wishList = (WishList) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to get user wishlist.";
			myDAOLayerException(message, hibernateException);
		}
		return wishList;
	}

	@Override
	public void uploadBooksCSV() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generatePDFReport() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequestBook> getAllRecommendedBooks(String email)
			throws DAOLayerException {
		List<RequestBook> recommendedBooks = null;
		try {
			String hql = "select userentity.bookRecommendations.bookIds FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			recommendedBooks = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch user recommendations.";
			myDAOLayerException(message, hibernateException);
		}
		return recommendedBooks;
	}

	@Override
	public void addBook(Book book) throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(book);
		} catch (HibernateException hibernateException) {
			String message = "Failed to add book.";
			myDAOLayerException(message, hibernateException);
		}
	}

	@Override
	public void updateBook(Book book) throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().update(book);
		} catch (HibernateException hibernateException) {
			String message = "Failed to update book.";
			myDAOLayerException(message, hibernateException);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> fetchBooksByAuthor(String searchText)
			throws DAOLayerException {
		List<Book> books = null;
		try {
			String hql = "From Book b WHERE b.bookDetails.author like'%"
					+ searchText + "%'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			books = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch books by author.";
			myDAOLayerException(message, hibernateException);
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> fetchBooksByCategory(String searchText)
			throws DAOLayerException {
		List<Book> books = null;
		try {
			String hql = "From Book b WHERE b.bookCategory.categoryName like'%"
					+ searchText + "%'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			books = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch books by category.";
			myDAOLayerException(message, hibernateException);
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> fetchBooksByTitle(String searchText)
			throws DAOLayerException {
		List<Book> books = null;
		try {
			String hql = "From Book b WHERE b.bookDetails.title like'%"
					+ searchText + "%'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			books = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch books by title.";
			myDAOLayerException(message, hibernateException);
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<Book> getBookSuggestions(String searchText)
			throws DAOLayerException {
		HashSet<Book> books = new HashSet<Book>();
		try {
			String hql = "From Book b WHERE b.bookDetails.author like'%"
					+ searchText + "%'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			books.addAll(query.list());
			hql = "From Book b WHERE b.bookDetails.title like'%" + searchText
					+ "%'";
			query = sessionFactory.getCurrentSession().createQuery(hql);
			books.addAll(query.list());
			hql = "From Book b WHERE b.bookCategory.categoryName like'%"
					+ searchText + "%'";
			query = sessionFactory.getCurrentSession().createQuery(hql);
			books.addAll(query.list());
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch books.";
			myDAOLayerException(message, hibernateException);
		}
		return books;
	}

	@Override
	public BookCategory getBookCategory(String categoryName)
			throws DAOLayerException {
		BookCategory bookCategory = null;
		try {
			String hql = "From BookCategory WHERE categoryName='"
					+ categoryName + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			if (!query.list().isEmpty()) {
				bookCategory = (BookCategory) query.list().get(0);
			}
		} catch (HibernateException hibernateException) {
			String message = "Failed to get book category.";
			myDAOLayerException(message, hibernateException);
		}
		return bookCategory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getAllBooks() throws DAOLayerException {
		List<Book> books = null;
		try {
			books = (List<Book>) sessionFactory.getCurrentSession()
					.createCriteria(Book.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch all books.";
			myDAOLayerException(message, hibernateException);
		}
		return books;
	}

	@Override
	public Book getBook(long bookId) throws DAOLayerException {
		Book book = null;
		try {
			String hql = "From Book WHERE bookId=" + bookId;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			book = (Book) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to get book by bookId.";
			myDAOLayerException(message, hibernateException);
		}
		return book;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getBookSuggestionsByTitle(String searchText)
			throws DAOLayerException {
		HashSet<String> bookSuggestions = new HashSet<String>();
		try {
			String hql = "select title from BookDetails b WHERE b.title like'%"
					+ searchText + "%'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setMaxResults(3);
			bookSuggestions.addAll(query.list());
			hql = "select title from BookDetails b WHERE b.author like'%"
					+ searchText + "%'";
			query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setMaxResults(3);
			bookSuggestions.addAll(query.list());
			hql = "select b.bookDetails.title from Book b WHERE b.bookCategory.categoryName like'%"
					+ searchText + "%'";
			query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setMaxResults(3);
			bookSuggestions.addAll(query.list());
		} catch (HibernateException hibernateException) {
			String message = "Failed to get book suggestions by title.";
			myDAOLayerException(message, hibernateException);
		}
		return bookSuggestions;
	}

	@Override
	public ActiveBookRequests getActiveBookRequest(long requestId)
			throws DAOLayerException {
		ActiveBookRequests activeBookRequests = null;
		try {
			String hql = "FROM ActiveBookRequests WHERE requestId=" + requestId;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			activeBookRequests = (ActiveBookRequests) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to get active book request by requestId.";
			myDAOLayerException(message, hibernateException);
		}
		return activeBookRequests;
	}

	@Override
	public UserCart getCart(String email) throws DAOLayerException {
		UserCart userCart = null;
		try {
			String hql = "select userentity.userCart FROM UserEntity userentity WHERE userentity.email='"
					+ email + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			userCart = (UserCart) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch user cart.";
			myDAOLayerException(message, hibernateException);
		}
		return userCart;
	}

	@Override
	public String getBookName(long bookId) throws DAOLayerException {
		String title = null;
		try {
			String hql = "select book.bookDetails.title FROM Book book WHERE book.bookId="
					+ bookId;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			title = (String) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to fetch book title by bookId.";
			myDAOLayerException(message, hibernateException);
		}
		return title;
	}

	@Override
	public boolean isBookInStock(long bookId) throws DAOLayerException {
		int numCopies = 0;
		try {
			String hql = "select book.noOfCopies FROM Book book WHERE book.bookId="
					+ bookId;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			numCopies = (Integer) query.list().get(0);
		} catch (HibernateException hibernateException) {
			String message = "Failed to check book availability.";
			myDAOLayerException(message, hibernateException);
		}
		return numCopies > 0;
	}

	@Override
	public int countNumberOfRecordsInBook() throws DAOLayerException {
		int rowCount = -1;
		try {
			String hql = "SELECT COUNT(bookId) FROM Book";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			String result = query.list().get(0).toString();
			rowCount = Integer.parseInt(result);
		} catch (HibernateException hibernateException) {
			String message = "Failed to count number of records in book table.";
			myDAOLayerException(message, hibernateException);
		}
		return rowCount;
	}

	@Override
	public void saveCart(UserCart userCart) throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(userCart);
		} catch (HibernateException hibernateException) {
			String message = "Failed to save user cart.";
			myDAOLayerException(message, hibernateException);
		}
	}

	@Override
	public void saveWishList(WishList wishList) throws DAOLayerException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(wishList);
		} catch (HibernateException hibernateException) {
			String message = "Failed to save user wishlist.";
			myDAOLayerException(message, hibernateException);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookReqHistory> getAllUserRequestsFromHistory(long historyId)
			throws DAOLayerException {
		List<BookReqHistory> activeBookRequests = null;
		try {
			String hql = "select userhistory.bookReqHisory FROM UserHistory userhistory WHERE userhistory.historyId="
					+ historyId;
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			activeBookRequests = query.list();
		} catch (HibernateException hibernateException) {
			String message = "Failed to get all book request history.";
			myDAOLayerException(message, hibernateException);
		}
		return activeBookRequests;
	}

	@Override
	public void myDAOLayerException(String message,
			HibernateException hibernateException) throws DAOLayerException {
		LOGGER.error(message, hibernateException);
		throw new DAOLayerException(message);
	}

}
