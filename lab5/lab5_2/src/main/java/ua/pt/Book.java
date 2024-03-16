package ua.pt;

import java.time.LocalDate;
 
public class Book {
	private final String title;
	private final String author;
	private final LocalDate published;
 
	// constructors, getter, setter ommitted
	Book(String title, String author, LocalDate published){
		this.title = title;
		this.author = author;
		this.published = published;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDate getPublished() {
		return published;
	}

	public String getTitle() {
		return title;
	}
}