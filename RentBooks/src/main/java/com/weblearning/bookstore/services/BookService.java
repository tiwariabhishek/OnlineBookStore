package com.weblearning.bookstore.services;

import java.util.List;

import com.weblearning.bookstore.dto.ActiveBookRequestDTO;
import com.weblearning.bookstore.dto.BookEntityDTO;
import com.weblearning.bookstore.dto.SearchResultsDTO;
import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.exception.ServiceLayerException;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

	public List<BookEntityDTO> fetchBooks(SearchResultsDTO searchResultsDTO)
			throws ServiceLayerException;

	public List<String> getBookSuggestions(String searchText)
			throws ServiceLayerException;

	public List<BookEntityDTO> getWishList(String email)
			throws ServiceLayerException;

	public String addToWishList(String email, long bookId)
			throws ServiceLayerException;

	public boolean uploadBooksCSV() throws ServiceLayerException;

	public boolean generatePDFReport() throws ServiceLayerException;

	public boolean addBook(BookEntityDTO bookEntityDTO, MultipartFile file)
			throws ServiceLayerException;

	public List<BookEntityDTO> getAllBooks() throws ServiceLayerException;

	public List<BookEntityDTO> getAllRecommendedBooks(String email)
			throws ServiceLayerException;

	public BookEntityDTO getBook(long bookId) throws ServiceLayerException;

	public boolean updateBook(String bookId, BookEntityDTO bookEntityDTO,
			MultipartFile file) throws ServiceLayerException;

	public boolean deleteBook(long bookId) throws ServiceLayerException;

	public String addToCart(String email, long bookId)
			throws ServiceLayerException;

	public List<BookEntityDTO> getUserCart(String email)
			throws ServiceLayerException;

	public boolean removeFromCart(String email, long bookId)
			throws ServiceLayerException;

	public boolean removeFromWishList(String email, long bookId)
			throws ServiceLayerException;

	public List<ActiveBookRequestDTO> getAllUserRequestsFromHistory(
			long historyId) throws ServiceLayerException;

	public void myServiceLayerException(String message,
			DAOLayerException daoLayerException) throws ServiceLayerException;

}
