package com.example.server.repository;

import com.example.server.model.PredefinedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Noemi on 09.04.2022
 */
public interface PredefinedBookRepository extends JpaRepository<PredefinedBook, String> {

    @Query("select l from PredefinedBook l where l.title Like %?1%")
    List<PredefinedBook> findByBookNameContain(String pattern);

    @Query("SELECT COUNT(u) FROM PredefinedBook u")
    Long countBook();

    @Query("SELECT COUNT(u) FROM User u")
    Long countUsers();
}
