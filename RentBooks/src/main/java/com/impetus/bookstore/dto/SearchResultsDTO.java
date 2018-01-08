package com.impetus.bookstore.dto;
public class SearchResultsDTO {

	private String searchText;
	private String title;
	private String author;
	private String category;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "SearchResultsWeb [searchText=" + searchText + ", title="
				+ title + ", author=" + author + ", category=" + category + "]";
	}
}
