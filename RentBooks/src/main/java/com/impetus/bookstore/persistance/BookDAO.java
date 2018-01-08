package com.impetus.bookstore.persistance;

import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;

import com.impetus.bookstore.exception.DAOLayerException;
import com.impetus.bookstore.persistance.entities.ActiveBookRequests;
import com.impetus.bookstore.persistance.entities.Book;
import com.impetus.bookstore.persistance.entities.BookCategory;
import com.impetus.bookstore.persistance.entities.BookReqHistory;
import com.impetus.bookstore.persistance.entities.RequestBook;
import com.impetus.bookstore.persistance.entities.UserCart;
import com.impetus.bookstore.persistance.entities.WishList;

public interface BookDAO {

	public List<Book> fetchBooksByAuthor(String searchText)
			throws DAOLayerException;

	public List<Book> fetchBooksByCategory(String searchText)
			throws DAOLayerException;

	public List<Book> fetchBooksByTitle(String searchText)
			throws DAOLayerException;

	public HashSet<Book> getBookSuggestions(String searchText)
			throws DAOLayerException;

	public HashSet<String> getBookSuggestionsByTitle(String searchText)
			throws DAOLayerException;

	public WishList getWishList(String email) throws DAOLayerException;

	public BookCategory getBookCategory(String categoryName)
			throws DAOLayerException;

	public ActiveBookRequests getActiveBookRequest(long requestId)
			throws DAOLayerException;

	public void uploadBooksCSV() throws DAOLayerException;

	public boolean isBookInStock(long bookId) throws DAOLayerException;

	public UserCart getCart(String email) throws DAOLayerException;

	public void generatePDFReport() throws DAOLayerException;

	public void addBook(Book book) throws DAOLayerException;

	public List<Book> getAllBooks() throws DAOLayerException;

	public Book getBook(long bookId) throws DAOLayerException;

	public String getBookName(long bookId) throws DAOLayerException;

	public void updateBook(Book book) throws DAOLayerException;

	public int countNumberOfRecordsInBook() throws DAOLayerException;

	public void saveCart(UserCart userCart) throws DAOLayerException;

	public void saveWishList(WishList wishList) throws DAOLayerException;

	public List<BookReqHistory> getAllUserRequestsFromHistory(long historyId)
			throws DAOLayerException;

	public List<RequestBook> getAllRecommendedBooks(String email)
			throws DAOLayerException;

	public void myDAOLayerException(String message,
			HibernateException hibernateException) throws DAOLayerException;
}
