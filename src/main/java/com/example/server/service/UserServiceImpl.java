package com.example.server.service;

import com.example.server.exception.CustomException;
import com.example.server.model.*;
import com.example.server.repository.BookRepository;
import com.example.server.repository.ChallengeRepository;
import com.example.server.repository.PredefinedBookRepository;
import com.example.server.repository.UserRepository;
import com.example.server.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Noemi on 09.04.2022
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PredefinedBookRepository predefinedBookRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).get().getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String signup(User user) {
        Challenge first = new Challenge("3", "Boom", "I am one of the seventy-six million babies born in the United States between 1946 and 1964, a Baby Boomer",
        "Easy", "true", "false");
        Challenge second = new Challenge("2", "Between Everywhere and Nowhere", "This is another challenge you can complete. Have fun!",
                "Easy", "true", "false");
        Challenge third = new Challenge("1", "All about Tipu Sultan", "This is the first challenge you can complete. Have fun!",
                "Easy", "true", "false");
        if (!userRepository.existsByEmail(user.getEmail()) && !userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
            user.setCurrentArea("1");
            user.setCurrentLevel("1");
            user.setScore("0");
            user.setUnlockedChallenges(new ArrayList<Challenge>(Arrays.asList(first, second, third)));

            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        } else {
            throw new CustomException("email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public List<User> getAllusers() {
        List<User> clients = new ArrayList<>();
        this.userRepository.findAll().forEach(item -> {
            if (item.getRoles().contains(Role.ROLE_CLIENT) && !item.getRoles().contains(Role.ROLE_ADMIN)) {
                clients.add(item);
            }
        });
        return clients;
    }

    @Override
    public boolean saveUser(User user) {
        user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (this.userRepository.save(user) != null) return true;
        else
            return false;
    }

    @Override
    public boolean deleteUser(User user) {
        this.userRepository.delete(user);
        return true;
    }

    @Override
    public Optional<List<User>> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }


    @Override
    public Optional<User> findById(int id) {
        return this.userRepository.findById(id);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }


    @Override
    public boolean addBookToUser(Book book, User user) {
//        if (this.bookRepository.findByBookNameContain(book.getTitle()).contains(book))
//            System.out.println("Book already exists " + this.bookRepository.findByBookNameContain(book.getTitle()).contains(book));
//        else this.bookRepository.save(book);
//        User user1;
//        user1 = this.userRepository.findByEmail(user.getEmail()).get();
//        if (user1 != null) {

        Book predefBook = this.bookRepository.findByBookNameContain(book.getTitle()).get(0);
        User user1;
        user1 = this.userRepository.findByEmail(user.getEmail()).get();
        if (user1 != null && predefBook != null) {
            if (user1.getBooks().contains(predefBook)) {
                System.out.println("User already has book: " + predefBook.getTitle());
                return true;
            } else if (user1.getBooks().add(book)) {
                this.userRepository.save(user1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeBookFromUser(Book book, User user) {
        if (user != null) {
            if (user.getBooks().remove(book)) {
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Book> getUserBooks(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getBooks();
        }
        return null;
    }


    @Override
    public boolean userHasBook(String isbn, String email) {
        boolean found = false;
        List<Book> books = this.getUserBooks(email);
        for (Book l : books) {
            if (l.getBookId().equals(isbn)) {
                found = true;
            }
        }
        return found;
    }


    @Override
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> opt = this.userRepository.findByEmailAndPassword(email, password);
        if (opt.isPresent())
            return opt.get();
        else
            return null;
    }

    @Override
    public boolean updateUsername(String email, String username) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            user.setUsername(username);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String email, String oldPass, String newPass) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            if (passwordEncoder.matches(oldPass, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPass));
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateAttention(String email, String attention) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            if (user.getAttention().add(attention)) {
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateSpeed(String email, String speed) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            if (user.getSpeed().add(speed)) {
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateAge(String email, String age) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            user.setAge(age);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean addPredefinedBookToUser(PredefinedBook predefinedBook, User user) {
        PredefinedBook predefBook = this.predefinedBookRepository.getById(predefinedBook.getBookId());
        User user1;
        user1 = this.userRepository.findByEmail(user.getEmail()).get();
        if (user1 != null && predefBook != null) {
            if (user1.getPredefinedBooks().contains(predefBook)) {
                System.out.println("User already has book: " + predefBook.getTitle());
                return true;
            } else if (user1.getPredefinedBooks().add(predefinedBook)) {
                this.userRepository.save(user1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removePredefinedBookFromUser(PredefinedBook predefinedBook, User user) {
        if (user != null) {
            if (user.getBooks().remove(predefinedBook)) {
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PredefinedBook> getUserPredefinedBooks(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getPredefinedBooks();
        }
        return null;
    }

    @Override
    public boolean addUnlockedChallengeToUser(Challenge unlockedChallenge, User user) {
        if (this.challengeRepository.findByChallengeNameContain(unlockedChallenge.getTitle()).contains(unlockedChallenge))
            System.out.println("Challenge already exists: " + unlockedChallenge.getTitle());
        else this.challengeRepository.save(unlockedChallenge);
        User user1;
        user1 = this.userRepository.findByEmail(user.getEmail()).get();
        if (user1 != null) {
            if (user1.getUnlockedChallenges().contains(unlockedChallenge)) {
                System.out.println("User already has challenge: " + unlockedChallenge.getTitle());
                return true;
            } else if (user1.getUnlockedChallenges().add(unlockedChallenge)) {
                this.userRepository.save(user1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeUnlockedChallengeFromUser(Challenge unlockedChallenge, User user) {
        if (user != null) {
            if (user.getUnlockedChallenges().remove(unlockedChallenge)) {
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Challenge> getUserUnlockedChallenges(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getUnlockedChallenges();
        }
        return null;
    }

    @Override
    public boolean addDoneChallengeToUser(Challenge doneChallenge, User user) {
        if (this.challengeRepository.findByChallengeNameContain(doneChallenge.getTitle()).contains(doneChallenge))
            System.out.println("Challenge already exists: " + doneChallenge.getTitle());
        else this.challengeRepository.save(doneChallenge);
        User user1;
        user1 = this.userRepository.findByEmail(user.getEmail()).get();
        if (user1 != null) {
            if (user1.getDoneChallenges().contains(doneChallenge)) {
                System.out.println("User already has challenge: " + doneChallenge.getTitle());
                return true;
            } else if (user1.getDoneChallenges().add(doneChallenge)) {
                this.userRepository.save(user1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeDoneChallengeFromUser(Challenge doneChallenge, User user) {
        if (user != null) {
            if (user.getDoneChallenges().remove(doneChallenge)) {
                this.userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Challenge> getUserDoneChallenges(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getDoneChallenges();
        }
        return null;
    }

    @Override
    public String getUserScore(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getScore();
        }
        return null;
    }

    @Override
    public boolean updateScore(String email, String score) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            user.setScore(score);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public String getUserLevel(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getCurrentLevel();
        }
        return null;
    }

    @Override
    public boolean updateLevel(String email, String level) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            user.setCurrentLevel(level);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public String getUserArea(String email) {
        Optional<User> bookOpt = this.userRepository.findByEmail(email);
        User user = bookOpt.isPresent() ? bookOpt.get() : null;
        if (user != null) {
            return user.getCurrentArea();
        }
        return null;
    }

    @Override
    public boolean updateArea(String email, String area) {
        Optional<User> opt = this.userRepository.findByEmail(email);
        User user;
        if (opt.isPresent()) {
            user = opt.get();
            user.setCurrentArea(area);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }
}
