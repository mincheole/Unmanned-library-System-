package DTO;

import java.sql.Date;

public class book {
	int isbn;
	String title;
	String author;
	String publisher;
	Date publication_year;
	String category;

	public book(int p_isbn, String p_title, String p_author,
				String p_publisher,	Date p_publication_year, String p_category) {
		this.isbn = p_isbn;
		this.title = p_title;
		this.author = p_author;
		this.publisher = p_publisher;
		this.publication_year = p_publication_year;
		this.category = p_category;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setNum(int p_isbn) {
		this.isbn = p_isbn;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String p_title) {
		this.title = p_title;
	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String p_author) {
		this.author = p_author;
	}
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String p_publisher) {
		this.publisher = p_publisher;
	}
	public Date getBookyear() {
		return publication_year;
	}

	public void setBookyear(Date p_publication_year) {
		this.publication_year = p_publication_year;
	}
	public String getCategory() {
		return category;
	}

	public void set(String p_category) {
		this.category = p_category;
	}

}
