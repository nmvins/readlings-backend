package com.example.server.service;

import com.example.server.model.Book;

import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */
public interface BookService {

    Set<Book> getAllBooks();
    Set<Book> getBooksNameContain(String pattern);
    boolean saveBook(Book Book);
    boolean deleteBook(Book Book);
    Optional<Book> findBybookId(String bookId);
    Book updateBook(Book Book);
    Long countBook();
    Long countUsers();
}
