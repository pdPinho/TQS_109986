package ua.pt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();
	
	@ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDate iso8601Date(String year, String month, String day){
		return LocalDate.of(Integer.parseInt(year), 
							Integer.parseInt(month), 
							Integer.parseInt(day));
	}
 
	@DataTableType
	public Book bookEntry(Map<String, String> tableEntry){
		return new Book(tableEntry.get("title"),
						tableEntry.get("author"),
						LocalDate.parse(tableEntry.get("published")));
	}

	// Populating library
    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void setup(final String title, final String author, LocalDate published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
    }
 
	// Find books between two dates
	@When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
	public void setSearchParameters( LocalDate from, LocalDate to) {
		result = library.findBooks(from, to);
	}

	// Find books by author
	@When("the customer searches for books written by {string}")
	public void findBooksByAuthor(String author){
		result = library.findBooksByAuthor(author);
	}
 
	// Find books by title
	@When("the customer searches for books titled {string}")
	public void findBooksByTitle(String title){
		result = library.findBooksByTitle(title);
	}

	// Using DataTable
	@Given("I have the following books in the store")
	public void verifyBooksByDataTable(DataTable table){
		List<Map<String, String>> rows = table.asMaps();

		for (Map<String,String> map : rows)
			library.addBook(bookEntry(map));
	}

	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}
 
	@Then("Book {int} should have the title {string}")
	public void verifyBookTitleAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}

	@Then("Book {int} should have the author {string}")
	public void verifyBookAuthorAtPosition(final int position, final String author) {
		assertThat(result.get(position - 1).getAuthor(), equalTo(author));
	}

	@Then("Book {int} should have the publish date {iso8601Date}")
	public void verifyBookPublishDateAtPosition(final int position, final LocalDate date) {
		assertThat(result.get(position - 1).getPublished(), equalTo(date));
	}
}