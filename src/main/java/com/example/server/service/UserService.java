package com.example.server.service;

import com.example.server.model.Book;
import com.example.server.model.Challenge;
import com.example.server.model.PredefinedBook;
import com.example.server.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Noemi on 09.04.2022
 */
public interface UserService {

    List<User> getAllusers();
    boolean saveUser(User user);
    boolean deleteUser(User user);
    Optional<List<User>> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User>  findById(int id);
    User findByEmailAndPassword(String email,String password);

    boolean addBookToUser(Book Book, User user);
    boolean userHasBook(String bookId, String email);
    List<Book> getUserBooks(String email);
    boolean removeBookFromUser(Book Book, User user);

    boolean addPredefinedBookToUser(PredefinedBook predefinedBook, User user);
    boolean removePredefinedBookFromUser(PredefinedBook predefinedBook, User user);
    List<PredefinedBook> getUserPredefinedBooks(String email);

    boolean addUnlockedChallengeToUser(Challenge unlockedChallenge, User user);
    boolean removeUnlockedChallengeFromUser(Challenge unlockedChallenge, User user);
    List<Challenge> getUserUnlockedChallenges(String email);

    boolean addDoneChallengeToUser(Challenge doneChallenge, User user);
    boolean removeDoneChallengeFromUser(Challenge doneChallenge, User user);
    List<Challenge> getUserDoneChallenges(String email);

    String getUserScore(String email);
    boolean updateScore(String email, String score);

    String getUserLevel(String email);
    boolean updateLevel(String email, String level);

    String getUserArea(String email);
    boolean updateArea(String email, String area);

    boolean updateUsername(String email, String username);
    boolean updatePassword(String email, String oldPass, String newPAss);

    String signin(String email, String password);
    String signup(User user);

    boolean updateAttention(String email, String attention);
    boolean updateSpeed(String email, String speed);
    boolean updateAge(String email, String age);

    boolean updateLoginDate(String email, String date);
    boolean updateLogoutDate(String email, String date);
}
