package com.example.server.repository;

import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Noemi on 09.04.2022
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<List<User>> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByPassword(String password);
}
