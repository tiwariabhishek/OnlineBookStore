package com.weblearning.bookstore.persistance.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BookReqHistory")
public class BookReqHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "requestId")
	private long requestId;

	@Column(name = "bookDeliveryDate")
	private Date bookDeliveryDate;

	@Column(name = "bookReturnDate")
	private Date bookReturnDate;

	@Column(name = "bookDeliveryStatus")
	private short bookDeliveryStatus;

	@Column(name = "bookReturnStatus")
	private short bookReturnStatus;

	@Column(name = "bookId")
	private long bookId;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public Date getBookDeliveryDate() {
		return bookDeliveryDate;
	}

	public void setBookDeliveryDate(Date bookDeliveryDate) {
		this.bookDeliveryDate = bookDeliveryDate;
	}

	public Date getBookReturnDate() {
		return bookReturnDate;
	}

	public void setBookReturnDate(Date bookReturnDate) {
		this.bookReturnDate = bookReturnDate;
	}

	public short getBookDeliveryStatus() {
		return bookDeliveryStatus;
	}

	public void setBookDeliveryStatus(short bookDeliveryStatus) {
		this.bookDeliveryStatus = bookDeliveryStatus;
	}

	public short getBookReturnStatus() {
		return bookReturnStatus;
	}

	public void setBookReturnStatus(short bookReturnStatus) {
		this.bookReturnStatus = bookReturnStatus;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
}
