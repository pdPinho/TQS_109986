Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Background: Add books
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-14
      And a book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
      And a book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-01-01

  Scenario: Search books by publication year
    When the customer searches for books published between 2013-01-01 and 2014-12-30
    Then 2 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'

  Scenario: Search books by author
    When the customer searches for books written by 'Anonymous'
    Then 1 books should have been found
      And Book 1 should have the title 'One good book'

  Scenario: Search books by title
    When the customer searches for books titled 'One good book'
    Then 1 books should have been found
      And Book 1 should have the title 'One good book'
      And Book 1 should have the author 'Anonymous'
      And Book 1 should have the publish date 2013-03-14

  Scenario: Search using data table
    Given I have the following books in the store
      | title                                       | author      | published   |
      | The Devil in the White City                 | Erik Larson | 1999-09-09  |
      | The Lion, the Witch and the Wardrobe        | C.S. Lewis  | 1999-09-08  |
      | In the Garden of Beasts                     | Erik Larson | 2000-01-01  |
    When the customer searches for books written by 'Erik Larson'
    Then 2 books should have been found
      And Book 1 should have the title 'The Devil in the White City'
      And Book 2 should have the title 'In the Garden of Beasts'