package com.weblearning.bookstore.dto;


public class ActiveBookRequestDTO {

	private long requestId;
	private String bookRequestDate;
	private String bookDeliveryDate;
	private String bookReturnDate;
	private String bookStatus;
	private long bookId;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getBookRequestDate() {
		return bookRequestDate;
	}

	public void setBookRequestDate(String bookRequestDate) {
		this.bookRequestDate = bookRequestDate;
	}

	public String getBookDeliveryDate() {
		return bookDeliveryDate;
	}

	public void setBookDeliveryDate(String bookDeliveryDate) {
		this.bookDeliveryDate = bookDeliveryDate;
	}

	public String getBookReturnDate() {
		return bookReturnDate;
	}

	public void setBookReturnDate(String bookReturnDate) {
		this.bookReturnDate = bookReturnDate;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

}
