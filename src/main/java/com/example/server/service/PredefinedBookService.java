package com.example.server.service;

import com.example.server.model.Book;
import com.example.server.model.PredefinedBook;

import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */
public interface PredefinedBookService {

    Set<PredefinedBook> getAllBooks();
    Set<PredefinedBook> getBooksNameContain(String pattern);
    boolean saveBook(PredefinedBook Book);
    boolean deleteBook(PredefinedBook Book);
    Optional<PredefinedBook> findBybookId(String bookId);
    PredefinedBook updateBook(PredefinedBook Book);
    Long countBook();
    Long countUsers();
}
