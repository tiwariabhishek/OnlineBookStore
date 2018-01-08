package com.weblearning.bookstore.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ShoppingCart {

	private Set<Long> books = new HashSet<Long>(0);

	public Set<Long> getBooks() {
		return books;
	}

	public void setBooks(Set<Long> books) {
		this.books = books;
	}

}
