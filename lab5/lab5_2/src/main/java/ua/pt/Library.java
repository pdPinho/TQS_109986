package ua.pt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
 
public class Library {
	private final List<Book> store = new ArrayList<>();
 
	public void addBook(final Book book) {
		store.add(book);
	}
 
	public List<Book> findBooks(final LocalDate from, final LocalDate to) { 
		return store.stream().filter(book -> {
			return book.getPublished().isAfter(from) && book.getPublished().isBefore(to);
		}).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
	}

	public List<Book> findBooksByAuthor(final String author){
		return store.stream().filter(book -> {
			return book.getAuthor().equalsIgnoreCase(author);
		}).collect(Collectors.toList());
	}

	public List<Book> findBooksByTitle(final String title){
		return store.stream().filter(book -> {
			return book.getTitle().equalsIgnoreCase(title);
		}).collect(Collectors.toList());
	}
}