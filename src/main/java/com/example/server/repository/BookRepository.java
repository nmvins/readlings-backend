package com.example.server.repository;

import com.example.server.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Noemi on 09.04.2022
 */
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("select l from Book l where l.title Like %?1%")
    List<Book> findByBookNameContain(String pattern);

    @Query("SELECT COUNT(u) FROM Book u")
    Long countBook();

    @Query("SELECT COUNT(u) FROM User u")
    Long countUsers();
}
