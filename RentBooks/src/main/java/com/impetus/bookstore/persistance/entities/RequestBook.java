package com.impetus.bookstore.persistance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RequestBook")
public class RequestBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "requestBookId")
	private long requestBookId;

	@Column(name = "booId")
	private long bookId;

	public long getRequestBookId() {
		return requestBookId;
	}

	public void setRequestBookId(long requestBookId) {
		this.requestBookId = requestBookId;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

}
