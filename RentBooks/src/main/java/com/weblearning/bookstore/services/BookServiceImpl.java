package com.weblearning.bookstore.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weblearning.bookstore.dto.ActiveBookRequestDTO;
import com.weblearning.bookstore.dto.BookEntityDTO;
import com.weblearning.bookstore.dto.SearchResultsDTO;
import com.weblearning.bookstore.exception.DAOLayerException;
import com.weblearning.bookstore.exception.ServiceLayerException;
import com.weblearning.bookstore.persistance.BookDAO;
import com.weblearning.bookstore.persistance.entities.Book;
import com.weblearning.bookstore.persistance.entities.BookCategory;
import com.weblearning.bookstore.persistance.entities.BookReqHistory;
import com.weblearning.bookstore.persistance.entities.RequestBook;
import com.weblearning.bookstore.persistance.entities.UserCart;
import com.weblearning.bookstore.persistance.entities.WishList;
import com.weblearning.bookstore.utils.PropertyConstants;
import com.weblearning.bookstore.utils.Utilities;
import com.weblearning.bookstore.utils.datatransfer.DTOToPersistance;
import com.weblearning.bookstore.utils.datatransfer.PersistanceToDTO;

@Service("bookService")
public class BookServiceImpl implements BookService {

	private static final Logger LOGGER = Logger
			.getLogger(BookServiceImpl.class);

	@Autowired
	BookDAO bookDAO;

	@Override
	@Transactional(readOnly = true)
	public List<BookEntityDTO> fetchBooks(SearchResultsDTO searchResultsDTO)
			throws ServiceLayerException {
		HashSet<Book> bookList = new HashSet<Book>();
		List<BookEntityDTO> bookEntityDTOs = new ArrayList<BookEntityDTO>();
		try {
			String searchText = searchResultsDTO.getSearchText();
			if (searchResultsDTO.getAuthor() != null) {
				bookList.addAll(bookDAO.fetchBooksByAuthor(searchText));
			} else if (searchResultsDTO.getCategory() != null) {
				bookList.addAll(bookDAO.fetchBooksByCategory(searchText));
			} else if (searchResultsDTO.getTitle() != null) {
				bookList.addAll(bookDAO.fetchBooksByTitle(searchText));
			} else {
				bookList = bookDAO.getBookSuggestions(searchText);
			}
			for (Book book : bookList) {
				BookEntityDTO bookEntityDTO = PersistanceToDTO
						.bookEntityToDTO(book);
				bookEntityDTOs.add(bookEntityDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to fetch books.";
			myServiceLayerException(message, e);
		}
		return bookEntityDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean uploadBooksCSV() throws ServiceLayerException {
		try {
			bookDAO.uploadBooksCSV();

		} catch (DAOLayerException e) {
			String message = "Failed to upload CSV.";
			myServiceLayerException(message, e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean generatePDFReport() throws ServiceLayerException {
		try {
			bookDAO.generatePDFReport();
		} catch (DAOLayerException e) {
			String message = "Failed to generate PDF report.";
			myServiceLayerException(message, e);
		}
		return true;

	}

	@Override
	@Transactional
	public boolean addBook(BookEntityDTO bookEntityDTO, MultipartFile file)
			throws ServiceLayerException {
		try {
			Utilities.saveImage(file);
			BookCategory bookCategory = bookDAO.getBookCategory(bookEntityDTO
					.getCategory());
			if (bookCategory == null) {
				bookCategory = new BookCategory();
			}
			Book book = DTOToPersistance.BookEntityDTOToPersistance(null,
					bookEntityDTO);
			if (file != null && !file.getOriginalFilename().equals("")) {
				book.getBookDetails().setImageurl(file.getOriginalFilename());
			}
			bookCategory.setCategoryName(bookEntityDTO.getCategory());
			bookCategory.setDeleted(false);

			bookCategory.getBooks().add(book);
			book.setBookCategory(bookCategory);

			bookDAO.addBook(book);
		} catch (DAOLayerException e) {
			String message = "Failed to add book.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean updateBook(String bookId, BookEntityDTO bookEntityDTO,
			MultipartFile file) throws ServiceLayerException {
		try {
			if (file != null) {
				Utilities.saveImage(file);
			}
			long bookid = Long.parseLong(bookId);
			Book book = bookDAO.getBook(bookid);
			BookCategory bookCategory = bookDAO.getBookCategory(bookEntityDTO
					.getCategory());
			if (bookCategory == null) {
				bookCategory = new BookCategory();
				bookCategory.setCategoryName(bookEntityDTO.getCategory());
			}
			book = DTOToPersistance.BookEntityDTOToPersistance(book,
					bookEntityDTO);
			if (file != null && !file.getOriginalFilename().equals("")) {
				book.getBookDetails().setImageurl(file.getOriginalFilename());
			}
			bookCategory.getBooks().add(book);
			book.setBookCategory(bookCategory);
			bookDAO.updateBook(book);
		} catch (DAOLayerException e) {
			String message = "Failed to update book.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteBook(long bookId) throws ServiceLayerException {
		try {
			Book book = bookDAO.getBook(bookId);
			book.setDeleted(true);
			book.setNoOfCopies(0);
			bookDAO.updateBook(book);
		} catch (DAOLayerException e) {
			String message = "Failed to delete book.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookEntityDTO> getAllBooks() throws ServiceLayerException {
		List<BookEntityDTO> bookEntityDTOs = null;
		try {
			bookEntityDTOs = new ArrayList<BookEntityDTO>();
			List<Book> books = bookDAO.getAllBooks();
			for (Book book : books) {
				if (!book.isDeleted()) {
					BookEntityDTO bookEntityDTO = PersistanceToDTO
							.bookEntityToDTO(book);
					bookEntityDTOs.add(bookEntityDTO);
				}
			}
		} catch (DAOLayerException e) {
			String message = "Failed to get all books.";
			myServiceLayerException(message, e);
		}
		return bookEntityDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public BookEntityDTO getBook(long bookId) throws ServiceLayerException {
		BookEntityDTO bookEntityDTO = null;
		try {
			Book book = bookDAO.getBook(bookId);
			bookEntityDTO = PersistanceToDTO.bookEntityToDTO(book);
		} catch (DAOLayerException e) {
			String message = "Failed to get book by bookId.";
			myServiceLayerException(message, e);
		}
		return bookEntityDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getBookSuggestions(String searchText)
			throws ServiceLayerException {
		List<String> suggestionsByTitle = new ArrayList<String>();
		try {
			suggestionsByTitle.addAll(bookDAO
					.getBookSuggestionsByTitle(searchText));
		} catch (DAOLayerException e) {
			String message = "Failed to get suggestions by title.";
			myServiceLayerException(message, e);
		}
		return suggestionsByTitle;
	}

	@Override
	@Transactional
	public String addToCart(String email, long bookId)
			throws ServiceLayerException {
		String message = null;
		try {
			UserCart userCart = bookDAO.getCart(email);
			Set<RequestBook> bookIds = userCart.getBookIds();
			if (bookIds.size() >= PropertyConstants.MAX_CART_SIZE) {
				message = PropertyConstants.MAX_CART_SIZE_MESSAGE;
			} else {
				for (RequestBook requestBook : bookIds) {
					if (requestBook.getBookId() == bookId) {
						message = PropertyConstants.CART_MESSAGE;
						break;
					}
				}
				if (message == null) {
					RequestBook requestBook = new RequestBook();
					requestBook.setBookId(bookId);
					bookIds.add(requestBook);
					userCart.setBookId(bookIds);
					bookDAO.saveCart(userCart);
				}
			}
		} catch (DAOLayerException e) {
			String mssage = "Failed to add book to cart.";
			myServiceLayerException(mssage, e);
		}
		return message;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookEntityDTO> getWishList(String email)
			throws ServiceLayerException {
		List<BookEntityDTO> bookEntityDTOs = null;
		try {
			bookEntityDTOs = new ArrayList<BookEntityDTO>();
			WishList wishList = bookDAO.getWishList(email);
			for (RequestBook requestBook : wishList.getBookIds()) {
				Book book = bookDAO.getBook(requestBook.getBookId());
				BookEntityDTO bookEntityDTO = PersistanceToDTO
						.bookEntityToDTO(book);
				bookEntityDTOs.add(bookEntityDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to get user wishlist.";
			myServiceLayerException(message, e);
		}
		return bookEntityDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookEntityDTO> getUserCart(String email)
			throws ServiceLayerException {
		List<BookEntityDTO> bookEntityDTOs = null;
		try {
			bookEntityDTOs = new ArrayList<BookEntityDTO>();
			UserCart userCart = bookDAO.getCart(email);
			for (RequestBook requestBook : userCart.getBookIds()) {
				Book book = bookDAO.getBook(requestBook.getBookId());
				BookEntityDTO bookEntityDTO = PersistanceToDTO
						.bookEntityToDTO(book);
				bookEntityDTOs.add(bookEntityDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to get user cart.";
			myServiceLayerException(message, e);
		}
		return bookEntityDTOs;
	}

	@Override
	@Transactional
	public boolean removeFromCart(String email, long bookId)
			throws ServiceLayerException {
		try {
			UserCart userCart = bookDAO.getCart(email);
			Set<RequestBook> userCartBooks = userCart.getBookIds();
			for (RequestBook requestBook : userCartBooks) {
				if (requestBook.getBookId() == bookId) {
					userCartBooks.remove(requestBook);
					break;
				}
			}
			userCart.setBookId(userCartBooks);
			bookDAO.saveCart(userCart);
		} catch (DAOLayerException e) {
			String message = "Failed to remove book from user cart.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional
	public String addToWishList(String email, long bookId)
			throws ServiceLayerException {
		String message = null;
		try {
			WishList wishList = bookDAO.getWishList(email);
			Set<RequestBook> bookIds = wishList.getBookIds();
			if (bookIds.size() >= PropertyConstants.MAX_WISHLIST_SIZE) {
				message = PropertyConstants.MAX_WISHLIST_SIZE_MESSAGE;
			}
			for (RequestBook requestBook : bookIds) {
				if (requestBook.getBookId() == bookId) {
					message = PropertyConstants.WISHLIST_MESSAGE;
					break;
				}
			}
			if (message == null) {
				RequestBook requestBook = new RequestBook();
				requestBook.setBookId(bookId);
				bookIds.add(requestBook);
				wishList.setBookIds(bookIds);
				bookDAO.saveWishList(wishList);
			}
		} catch (DAOLayerException e) {
			String mssage = "Failed to add book to wishlist.";
			myServiceLayerException(mssage, e);
		}
		return message;
	}

	@Override
	@Transactional
	public boolean removeFromWishList(String email, long bookId)
			throws ServiceLayerException {
		try {
			WishList wishList = bookDAO.getWishList(email);
			Set<RequestBook> wishListBooks = wishList.getBookIds();
			for (RequestBook requestBook : wishListBooks) {
				if (requestBook.getBookId() == bookId) {
					wishListBooks.remove(requestBook);
					break;
				}
			}
			wishList.setBookIds(wishListBooks);
			bookDAO.saveWishList(wishList);
		} catch (DAOLayerException e) {
			String message = "Failed to remove book from user wishlist.";
			myServiceLayerException(message, e);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActiveBookRequestDTO> getAllUserRequestsFromHistory(
			long historyId) throws ServiceLayerException {
		List<ActiveBookRequestDTO> activeBookRequestDTOs = new ArrayList<ActiveBookRequestDTO>();
		try {
			List<BookReqHistory> bookReqHistories = bookDAO
					.getAllUserRequestsFromHistory(historyId);
			for (BookReqHistory bookRequest : bookReqHistories) {
				ActiveBookRequestDTO activeBookRequestDTO = PersistanceToDTO
						.bookReqHistoryToDTO(bookRequest);
				activeBookRequestDTOs.add(activeBookRequestDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to fetch book request history.";
			myServiceLayerException(message, e);
		}
		return activeBookRequestDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookEntityDTO> getAllRecommendedBooks(String email)
			throws ServiceLayerException {
		List<BookEntityDTO> recommendedBookDTOs = new ArrayList<BookEntityDTO>();
		try {
			List<RequestBook> recommendedBookIds = bookDAO
					.getAllRecommendedBooks(email);
			for (RequestBook requestBook : recommendedBookIds) {
				Book book = bookDAO.getBook(requestBook.getBookId());
				BookEntityDTO bookEntityDTO = PersistanceToDTO
						.bookEntityToDTO(book);
				recommendedBookDTOs.add(bookEntityDTO);
			}
		} catch (DAOLayerException e) {
			String message = "Failed to fetch recommended books";
			myServiceLayerException(message, e);
		}
		return recommendedBookDTOs;
	}

	@Override
	public void myServiceLayerException(String message,
			DAOLayerException daoLayerException) throws ServiceLayerException {
		LOGGER.error(message, daoLayerException);
		throw new ServiceLayerException(message);
	}

}
