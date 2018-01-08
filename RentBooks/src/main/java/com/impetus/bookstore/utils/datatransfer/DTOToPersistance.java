package com.impetus.bookstore.utils.datatransfer;

import com.impetus.bookstore.dto.BookEntityDTO;
import com.impetus.bookstore.dto.UserEntityDTO;
import com.impetus.bookstore.persistance.entities.Book;
import com.impetus.bookstore.persistance.entities.BookDetails;
import com.impetus.bookstore.persistance.entities.UserDetails;
import com.impetus.bookstore.persistance.entities.UserEntity;

public class DTOToPersistance {

	public static Book BookEntityDTOToPersistance(Book book,
			BookEntityDTO bookEntityDTO) {
		if (book == null) {
			book = new Book();
		}
		BookDetails bookDetails = book.getBookDetails();
		if (bookDetails == null) {
			bookDetails = new BookDetails();
		}
		book.setIsbn(bookEntityDTO.getIsbn());
		book.setNoOfCopies(bookEntityDTO.getNoOfCopies());
		bookDetails.setAuthor(bookEntityDTO.getAuthor());
		bookDetails.setBook(book);
		bookDetails.setDescription(bookEntityDTO.getDescription());
		bookDetails.setLanguage(bookEntityDTO.getLanguage());
		bookDetails.setNumPages(bookEntityDTO.getNumPages());
		bookDetails.setPrice(bookEntityDTO.getPrice());
		bookDetails.setPublisher(bookEntityDTO.getPublisher());
		bookDetails.setTitle(bookEntityDTO.getTitle());
		bookDetails.setBook(book);
		book.setBookDetails(bookDetails);
		return book;
	}

	public static UserEntity userEntityDTOToPersistance(
			UserEntityDTO userEntityDTO, UserEntity userEntity,
			boolean registerFlag) {
		UserDetails userDetails = new UserDetails();
		if (userEntity == null) {
			userEntity = new UserEntity();
		} else {
			userDetails = userEntity.getUserdetails();
		}
		if (registerFlag) {
			userEntity.setEmail(userEntityDTO.getEmail());
			userEntity.setEnabled(true);
			userDetails.setDob(userEntityDTO.getDob());
			userDetails.setGender(userEntityDTO.getGender());
		}
		userDetails.setCity(userEntityDTO.getCity());
		userDetails.setAddress(userEntityDTO.getAddress());
		userDetails.setCountry(userEntityDTO.getCountry());
		userDetails.setMobile(userEntityDTO.getMobile());
		userDetails.setName(userEntityDTO.getName());
		userDetails.setState(userEntityDTO.getState());
		userDetails.setZipcode(userEntityDTO.getZipcode());
		userDetails.setUser(userEntity);
		userEntity.setUserdetails(userDetails);
		return userEntity;
	}
}
