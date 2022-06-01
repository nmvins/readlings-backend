package com.example.server.controller;

import com.example.server.model.Book;
import com.example.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */

@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping(path="/get")
    public Set<Book> getAllBooks(){
        return this.bookService.getAllBooks();
    }

    @GetMapping(path="/countBooks")
    public Long countBooks(){
        return this.bookService.countBook();
    }


    @GetMapping(path="/countUsers")
    public Long countUsers(){
        return this.bookService.countUsers();
    }


    @PostMapping(path="/create")
    public ResponseEntity<Book> create(@RequestBody Book Book){
        try {
            this.bookService.saveBook(Book);
            return new ResponseEntity<Book>(Book, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<Book>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(path="/update")
    public ResponseEntity<Book> update(@RequestBody Book Book){
        try {
            this.bookService.saveBook(Book);
            return new ResponseEntity<Book>(Book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Book>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<Book> findById(@PathVariable("bookId") String bookId) {
        Optional<Book> resultat = bookService.findBybookId(bookId);
        if (resultat.isPresent())
            return new ResponseEntity<>(resultat.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<Book> delete(@PathVariable("bookId") String bookId){
        Optional<Book> resultat = bookService.findBybookId(bookId);
        if(resultat.isPresent()) {
            this.bookService.deleteBook(resultat.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
