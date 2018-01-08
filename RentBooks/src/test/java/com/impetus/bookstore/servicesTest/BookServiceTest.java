package com.impetus.bookstore.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.impetus.bookstore.configuration.AppConfig;
import com.impetus.bookstore.dto.BookEntityDTO;
import com.impetus.bookstore.dto.SearchResultsDTO;
import com.impetus.bookstore.exception.ServiceLayerException;
import com.impetus.bookstore.services.BookService;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class BookServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	BookService bookService;

	@Before
	public void setUp() throws Exception {
		BookEntityDTO bookEntityDTO = new BookEntityDTO();
		bookEntityDTO.setAuthor("Ed Offley");
		bookEntityDTO.setCategory("Mathematics");
		bookEntityDTO
				.setDescription("The United States experienced its most harrowing military disaster of World War II not in 1941 at Pearl Harbor but in the period from 1942 to 1943, in Atlantic coastal waters from Newfoundland to the Caribbean.");
		bookEntityDTO.setPublisher("Basic Books");
		bookEntityDTO.setIsbn("9780465013975");
		bookEntityDTO.setLanguage("English");
		bookEntityDTO
				.setTitle("Turning the Tide: How a Small Band of Allied Sailors Defeated the U-boats and Won the Battle of the Atlantic");
		bookService.addBook(bookEntityDTO, null);

		bookEntityDTO.setAuthor("Herbert Schieldt");
		bookEntityDTO.setCategory("Computers And Technology");
		bookEntityDTO
				.setDescription("Java complete reference is a comprehensive guide for java beginners.");
		bookEntityDTO.setPublisher("Mcgraw-Hill Osborne Media");
		bookEntityDTO.setIsbn("9780071808552");
		bookEntityDTO.setLanguage("English");
		bookEntityDTO.setTitle("Java: The Complete Reference");
		bookService.addBook(bookEntityDTO, null);

		bookEntityDTO.setAuthor("Jeff Hobbs");
		bookEntityDTO.setCategory("Biographies And Memoirs");
		bookEntityDTO.setPublisher("Scribner");
		bookEntityDTO
				.setDescription("A heartfelt, and riveting biography of the short life of a talented young African-American man who escapes the slums of Newark for Yale University only to succumb to the dangers of the streets and of one\'s own nature when he returns home.");
		bookEntityDTO.setIsbn("9781476731902");
		bookEntityDTO.setLanguage("English");
		bookEntityDTO
				.setTitle("The Short and Tragic Life of Robert Peace: A Brilliant Young Man Who Left Newark for the Ivy League");
		bookEntityDTO.setNumPages(417);
		bookService.addBook(bookEntityDTO, null);

		bookEntityDTO
				.setAuthor(" David N. Gilbert, Henry F. Chambers, George M. Eliopoulos");
		bookEntityDTO.setCategory("Medical Books");
		bookEntityDTO
				.setDescription("JProducto completamente nuevo ideal para estudiantes de medicinas o medicos la guia sanford la mas completa");
		bookEntityDTO.setPublisher("Antimicrobial Therapy");
		bookEntityDTO.setIsbn("9781930808782");
		bookEntityDTO.setLanguage("English");
		bookEntityDTO.setTitle("The Sanford Guide To Antimicrobial Therapy");
		bookService.addBook(bookEntityDTO, null);
		bookService.addToCart("abhishek@gmail.com", 1);
		bookService.addToWishList("abhishek@gmail.com", 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFetchBooks() throws ServiceLayerException {
		SearchResultsDTO searchResultsDTO1 = new SearchResultsDTO();
		String author = "Ed Offley";
		searchResultsDTO1.setSearchText(author);
		searchResultsDTO1.setAuthor(author);
		assertEquals(1, bookService.fetchBooks(searchResultsDTO1).size());

		SearchResultsDTO searchResultsDTO2 = new SearchResultsDTO();
		String category = "Medical Books";
		searchResultsDTO2.setSearchText(category);
		searchResultsDTO2.setCategory(category);
		assertEquals(1, bookService.fetchBooks(searchResultsDTO2).size());

		SearchResultsDTO searchResultsDTO3 = new SearchResultsDTO();
		String title = "The Short and Tragic Life of Robert Peace: A Brilliant Young Man Who Left Newark for the Ivy League";
		searchResultsDTO3.setSearchText(title);
		searchResultsDTO3.setTitle(title);
		assertEquals(1, bookService.fetchBooks(searchResultsDTO3).size());
	}

	@Test
	public void testAddBooks() throws ServiceLayerException {
		BookEntityDTO bookEntityDTO = new BookEntityDTO();
		bookEntityDTO.setAuthor("H. A. Rey");
		bookEntityDTO.setCategory("Childrens Books");
		bookEntityDTO
				.setDescription("This lively story captures George\'s adventure of becoming the first space monkey from the classic Curious George Gets a Medal.");
		bookEntityDTO.setPublisher("Houghton Miffin Company");
		bookEntityDTO.setIsbn("9780618120697");
		bookEntityDTO.setLanguage("English");
		bookEntityDTO.setTitle("Curious George");
		assertTrue(bookService.addBook(bookEntityDTO, null));
	}

	@Test
	public void testUpdateBook() throws ServiceLayerException {
		BookEntityDTO bookEntityDTO = new BookEntityDTO();
		assertTrue(bookService.updateBook("1", bookEntityDTO, null));
	}

	@Test
	public void testDeleteBook() throws ServiceLayerException {
		assertTrue(bookService.deleteBook(1));
	}

	@Test
	public void testGetAllBooks() throws ServiceLayerException {
		assertNotNull(bookService.getAllBooks());
	}

	@Test
	public void testGetBook() throws ServiceLayerException {
		assertNotNull(bookService.getBook(1));
	}

	@Test
	public void testGetBookSuggestions() throws ServiceLayerException {
		assertNotNull(bookService.getBookSuggestions("Sanford"));
	}

	@Test
	public void testAddToCart() throws ServiceLayerException {
		assertNotNull(bookService.addToCart("abhishek@gmail.com", 1));
	}

	@Test
	public void testAddToWishList() throws ServiceLayerException {
		assertNotNull(bookService.addToWishList("abhishek@gmail.com", 2));
	}

	@Test
	public void testGetUserCart() throws ServiceLayerException {
		assertNotNull(bookService.getUserCart("abhishek@gmail.com"));
	}

	@Test
	public void testGetWishList() throws ServiceLayerException {
		assertNotNull(bookService.getWishList("abhishek@gmail.com"));
	}

	@Test
	public void testRemoveFromCart() throws ServiceLayerException {
		assertNotNull(bookService.removeFromCart("abhishek@gmail.com", 1));
	}

	@Test
	public void testRemoveFromWishList() throws ServiceLayerException {
		assertNotNull(bookService.removeFromWishList("abhishek@gmail.com", 1));
	}

	@Test
	public void testGetAllRecommendedBooks() throws ServiceLayerException {
		assertNotNull(bookService.getAllRecommendedBooks("abhishek@gmail.com"));
	}

	@Test
	public void testGetAllUserRequestsFromHistory()
			throws ServiceLayerException {
		assertNotNull(bookService.getAllUserRequestsFromHistory(1));
	}
}
