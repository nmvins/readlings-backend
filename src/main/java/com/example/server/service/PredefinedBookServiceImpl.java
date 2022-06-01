package com.example.server.service;

import com.example.server.model.Book;
import com.example.server.model.PredefinedBook;
import com.example.server.repository.BookRepository;
import com.example.server.repository.PredefinedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */

@Service
public class PredefinedBookServiceImpl implements PredefinedBookService {

    @Autowired
    PredefinedBookRepository predefinedBookRepository;

    @Override
    public Set<PredefinedBook> getAllBooks() {
        return new HashSet<PredefinedBook>( this.predefinedBookRepository.findAll());
    }

    @Override
    public boolean saveBook(PredefinedBook Book) {
        if(this.predefinedBookRepository.save(Book)!=null) return true;
        else
            return false;
    }

    @Override
    public Set<PredefinedBook> getBooksNameContain(String pattern) {
        return new HashSet<>(this.predefinedBookRepository.findByBookNameContain(pattern));
    }

    @Override
    public boolean deleteBook(PredefinedBook Book) {
        this.predefinedBookRepository.delete(Book);
        return true;
    }

    @Override
    public Optional<PredefinedBook> findBybookId(String bookId) {
        return this.predefinedBookRepository.findById(bookId);
    }

    @Override
    public PredefinedBook updateBook(PredefinedBook Book) {
        return this.predefinedBookRepository.save(Book);
    }

    @Override
    public Long countBook() {
        return this.predefinedBookRepository.countBook();
    }

    @Override
    public Long countUsers() {
        return this.predefinedBookRepository.countUsers();

    }
}
