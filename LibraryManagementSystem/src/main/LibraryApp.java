package main;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.DatabaseSetup;

public class LibraryApp {

    public static void main(String[] args) {

        DatabaseSetup.createTables();

        AuthorDAO authorDAO = new AuthorDAO();
        authorDAO.addAuthor("J.K. Rowling");

        BookDAO bookDAO = new BookDAO();
        bookDAO.addBook("Harry Potter", 1);

        bookDAO.viewBooks();

        bookDAO.issueBook(1);

        bookDAO.returnBook(1);
    }
}