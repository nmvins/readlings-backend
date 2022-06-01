package com.example.server.controller;

import com.example.server.model.PredefinedBook;
import com.example.server.service.PredefinedBookService;
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
@RequestMapping("predefinedBooks")
public class PredefinedBookController {

    @Autowired
    PredefinedBookService predefinedBookService;

    @GetMapping(path="/get")
    public Set<PredefinedBook> getAllPredefinedBooks(){
        return this.predefinedBookService.getAllBooks();
    }

    @GetMapping(path="/countBooks")
    public Long countBooks(){
        return this.predefinedBookService.countBook();
    }


    @GetMapping(path="/countUsers")
    public Long countUsers(){
        return this.predefinedBookService.countUsers();
    }


    @PostMapping(path="/create")
    public ResponseEntity<PredefinedBook> create(@RequestBody PredefinedBook Book){
        try {
            this.predefinedBookService.saveBook(Book);
            return new ResponseEntity<PredefinedBook>(Book, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<PredefinedBook>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(path="/update")
    public ResponseEntity<PredefinedBook> update(@RequestBody PredefinedBook Book){
        try {
            this.predefinedBookService.saveBook(Book);
            return new ResponseEntity<PredefinedBook>(Book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<PredefinedBook>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<PredefinedBook> findById(@PathVariable("bookId") String bookId) {
        Optional<PredefinedBook> resultat = predefinedBookService.findBybookId(bookId);
        if (resultat.isPresent())
            return new ResponseEntity<>(resultat.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<PredefinedBook> delete(@PathVariable("bookId") String bookId){
        Optional<PredefinedBook> resultat = predefinedBookService.findBybookId(bookId);
        if(resultat.isPresent()) {
            this.predefinedBookService.deleteBook(resultat.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
