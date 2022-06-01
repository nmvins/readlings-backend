package com.example.server.repository;

import com.example.server.model.Book;
import com.example.server.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Noemi on 09.04.2022
 */
public interface ChallengeRepository extends JpaRepository<Challenge, String> {

    @Query("select l from Challenge l where l.title Like %?1%")
    List<Challenge> findByChallengeNameContain(String pattern);
}
