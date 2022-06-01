package com.example.server.service;

import com.example.server.model.Book;
import com.example.server.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Set<Book> getAllBooks() {
        return new HashSet<Book>( this.bookRepository.findAll());
    }

    @Override
    public boolean saveBook(Book book) {
        if(this.bookRepository.findByBookNameContain(book.getTitle()).size() > 0) {
            return true;
        }
        else {
            if(this.bookRepository.save(book)!=null) return true;
            else return false;
        }
    }

    @Override
    public Set<Book> getBooksNameContain(String pattern) {
        return new HashSet<>(this.bookRepository.findByBookNameContain(pattern));
    }

    @Override
    public boolean deleteBook(Book Book) {
        this.bookRepository.delete(Book);
        return true;
    }

    @Override
    public Optional<Book> findBybookId(String bookId) {
        return this.bookRepository.findById(bookId);
    }

    @Override
    public Book updateBook(Book Book) {
        return this.bookRepository.save(Book);
    }

    @Override
    public Long countBook() {
        return this.bookRepository.countBook();
    }

    @Override
    public Long countUsers() {
        return this.bookRepository.countUsers();

    }
}
