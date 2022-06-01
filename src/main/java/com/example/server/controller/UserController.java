package com.example.server.controller;

import com.example.server.model.Book;
import com.example.server.model.Challenge;
import com.example.server.model.PredefinedBook;
import com.example.server.model.User;
import com.example.server.service.BookService;
import com.example.server.service.ChallengeService;
import com.example.server.service.PredefinedBookService;
import com.example.server.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Noemi on 09.04.2022
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    PredefinedBookService predefinedBookService;

    @Autowired
    ChallengeService challengeService;


    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAllusers();
    }

    @PostMapping("/signin")
//    @ApiResponses(value = {//
//            @ApiResponse(code = 400, message = "Something went wrong"), //
//            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public Map<String, String> login(@RequestParam String email, @RequestParam String password) {
        return Collections.singletonMap("token", userService.signin(email, password));
    }

    @PostMapping("/signup")
//    @ApiResponses(value = {//
//            @ApiResponse(code = 400, message = "Something went wrong"), //
//            @ApiResponse(code = 403, message = "Access denied"), //
//            @ApiResponse(code = 422, message = "Username is already in use"), //
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        return new ResponseEntity<>(Collections.singletonMap("token", userService.signup(user)), HttpStatus.OK);
    }


    @GetMapping(path = "/getUser")
    public ResponseEntity<User> getUser(@RequestParam(value = "email", required = true) String email) {
        User user = this.userService.findByEmail(email).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @GetMapping(path="/checkMail")
//    public ResponseEntity<Boolean> checkEmail(@RequestParam(value="email", required=true) String email
//    ){
//        Optional<User> opt = this.userService.findByEmail(email);
//        if(opt.isPresent())
//            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
//        else
//            return new ResponseEntity<Boolean>(false,HttpStatus.OK);
//
//    }

//    @PostMapping(path="/getUser")
//    public ResponseEntity<User> getUser( @RequestBody User user) {
//        User userp = this.userService.findByEmailAndPassword(user.getEmail(),user.getPassword());
//
//        return new ResponseEntity<User>(userp,HttpStatus.OK);
//    }

//    @PostMapping(path="/create")
//    public ResponseEntity<User> create(@RequestBody User user){
//        try {
//            this.userService.saveUser(user);
//            return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
//        }
//        catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//
//        }
//    }

    @GetMapping(path = "/getAge")
    public ResponseEntity<String> getAge(@RequestParam(value = "email", required = true) String email) {
        User user = this.userService.findByEmail(email).get();

        return new ResponseEntity<>(user.getAge(), HttpStatus.OK);
    }

    @PostMapping(path = "/updateUsername")
    public ResponseEntity<Boolean> updateUsername(@RequestBody ObjectNode json) {
        String email;
        String username;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            username = new ObjectMapper().treeToValue(json.get("username"), String.class);
            boolean test = this.userService.updateUsername(email, username);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/updatePassword")
    public ResponseEntity<Boolean> updatePassword(@RequestBody ObjectNode json) {
        String email;
        String oldPass;
        String newPass;

        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            oldPass = new ObjectMapper().treeToValue(json.get("oldPass"), String.class);
            newPass = new ObjectMapper().treeToValue(json.get("newPass"), String.class);

            boolean isUpdated = this.userService.updatePassword(email, oldPass, newPass);
            if (isUpdated)
                return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/addAttentionMeasurement")
    public ResponseEntity<Boolean> addAttentionMeasurement(@RequestBody ObjectNode json) {
        String email;
        String attention;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            attention = new ObjectMapper().treeToValue(json.get("attention"), String.class);
            boolean isAdded = this.userService.updateAttention(email, attention);
            if (isAdded)
                return new ResponseEntity<>(isAdded, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/addSpeedMeasurement")
    public ResponseEntity<Boolean> addSpeedMeasurement(@RequestBody ObjectNode json) {
        String email;
        String speed;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            speed = new ObjectMapper().treeToValue(json.get("speed"), String.class);
            boolean test = this.userService.updateSpeed(email, speed);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/addLoginDate")
    public ResponseEntity<Boolean> addLoginDate(@RequestBody ObjectNode json) {
        String email;
        String date;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            date = new ObjectMapper().treeToValue(json.get("date"), String.class);
            boolean test = this.userService.updateLoginDate(email, date);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/addLogoutDate")
    public ResponseEntity<Boolean> addLogoutDate(@RequestBody ObjectNode json) {
        String email;
        String date;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            date = new ObjectMapper().treeToValue(json.get("date"), String.class);
            boolean test = this.userService.updateLogoutDate(email, date);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/updateAge")
    public ResponseEntity<Boolean> updateAge(@RequestBody ObjectNode json) {
        String email;
        String age;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            age = new ObjectMapper().treeToValue(json.get("age"), String.class);
            boolean test = this.userService.updateAge(email, age);
            if (test){
                return new ResponseEntity<>(test, HttpStatus.OK);}
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }


    @PostMapping(path = "/addBookToUser")
    public ResponseEntity<Book> addBookToUser(@RequestBody ObjectNode json) {
        User user = new User();
        Book BookToAdd = new Book();
        try {
            user = new ObjectMapper().treeToValue(json.get("user"), User.class);
            BookToAdd = new ObjectMapper().treeToValue(json.get("Book"), Book.class);
            boolean isAdded = this.userService.addBookToUser(BookToAdd, user);
            if (isAdded)
                return new ResponseEntity<>(BookToAdd, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/getUserBooks")
    public ResponseEntity<List<Book>> getUserBooks(@RequestParam(value = "email", required = true) String email) {
        List<Book> res = this.userService.getUserBooks(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/getUserHasBook")
    public ResponseEntity<Boolean> getUserHasBook(@RequestParam(value = "email", required = true) String email,
                                                  @RequestParam(value = "bookId", required = true) String bookId
    ) {
        boolean res = this.userService.userHasBook(bookId, email);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping(path = "/deleteBookFromUser")
    public ResponseEntity<Boolean> deleteBookFromUser(@RequestBody ObjectNode json) {
        User user = new User();
        Book BookToRemove = new Book();
        String bookId = json.get("bookId").asText();
        String email = json.get("email").asText();
        user = this.userService.findByEmail(email).get();
        BookToRemove = this.bookService.findBybookId(bookId).get();
        boolean done = this.userService.removeBookFromUser(BookToRemove, user);
        return new ResponseEntity<>(done, HttpStatus.OK);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Book> delete(@PathVariable("id") int id) {
        Optional<User> resultat = this.userService.findById(id);
        if (resultat.isPresent()) {
            this.userService.deleteUser(resultat.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }


    @PostMapping(path = "/addPredefinedBookToUser")
    public ResponseEntity<PredefinedBook> addPredefinedBookToUser(@RequestBody ObjectNode json) {
        User user = new User();
        PredefinedBook BookToAdd = new PredefinedBook();
        try {
            user = new ObjectMapper().treeToValue(json.get("user"), User.class);
            BookToAdd = new ObjectMapper().treeToValue(json.get("PredefinedBook"), PredefinedBook.class);
            boolean test = this.userService.addPredefinedBookToUser(BookToAdd, user);
            if (test)
                return new ResponseEntity<>(BookToAdd, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/getUserPredefinedBooks")
    public ResponseEntity<List<PredefinedBook>> getUserPredefinedBooks(@RequestParam(value = "email", required = true) String email) {
        List<PredefinedBook> res = this.userService.getUserPredefinedBooks(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "/deletePredefinedBookFromUser")
    public ResponseEntity<Boolean> deletePredefinedBookFromUser(@RequestBody ObjectNode json) {
        User user = new User();
        PredefinedBook BookToRemove = new PredefinedBook();
        String bookId = json.get("bookId").asText();
        String email = json.get("email").asText();
        user = this.userService.findByEmail(email).get();
        BookToRemove = this.predefinedBookService.findBybookId(bookId).get();
        boolean done = this.userService.removePredefinedBookFromUser(BookToRemove, user);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    @PostMapping(path = "/addUnlockedChallengeToUser")
    public ResponseEntity<Challenge> addUnlockedChallengeToUser(@RequestBody ObjectNode json) {
        User user = new User();
        Challenge challengeToAdd = new Challenge();
        try {
            user = new ObjectMapper().treeToValue(json.get("user"), User.class);
            challengeToAdd = new ObjectMapper().treeToValue(json.get("UnlockedChallenge"), Challenge.class);
            boolean test = this.userService.addUnlockedChallengeToUser(challengeToAdd, user);
            if (test)
                return new ResponseEntity<>(challengeToAdd, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/getUserUnlockedChallenges")
    public ResponseEntity<List<Challenge>> getUserUnlockedChallenges(@RequestParam(value = "email", required = true) String email) {
        List<Challenge> res = this.userService.getUserUnlockedChallenges(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "/deleteUnlockedChallengeFromUser")
    public ResponseEntity<Boolean> deleteUnlockedChallengeFromUser(@RequestBody ObjectNode json) {
        User user = new User();
        Challenge challengeToRemove = new Challenge();
        String challengeId = json.get("challengeId").asText();
        String email = json.get("email").asText();
        user = this.userService.findByEmail(email).get();
        challengeToRemove = this.challengeService.findByChallengeId(challengeId).get();
        boolean done = this.userService.removeUnlockedChallengeFromUser(challengeToRemove, user);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    @PostMapping(path = "/addDoneChallengeToUser")
    public ResponseEntity<Challenge> addDoneChallengeToUser(@RequestBody ObjectNode json) {
        User user = new User();
        Challenge challengeToAdd = new Challenge();
        try {
            user = new ObjectMapper().treeToValue(json.get("user"), User.class);
            challengeToAdd = new ObjectMapper().treeToValue(json.get("DoneChallenge"), Challenge.class);
            boolean test = this.userService.addDoneChallengeToUser(challengeToAdd, user);
            if (test)
                return new ResponseEntity<>(challengeToAdd, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/getUserDoneChallenges")
    public ResponseEntity<List<Challenge>> getUserDoneChallenges(@RequestParam(value = "email", required = true) String email) {
        List<Challenge> res = this.userService.getUserDoneChallenges(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "/deleteDoneChallengeFromUser")
    public ResponseEntity<Boolean> deleteDoneChallengeFromUser(@RequestBody ObjectNode json) {
        User user = new User();
        Challenge challengeToRemove = new Challenge();
        String challengeId = json.get("challengeId").asText();
        String email = json.get("email").asText();
        user = this.userService.findByEmail(email).get();
        challengeToRemove = this.challengeService.findByChallengeId(challengeId).get();
        boolean done = this.userService.removeDoneChallengeFromUser(challengeToRemove, user);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    @GetMapping(path = "/getUserScore")
    public ResponseEntity<String> getUserScore(@RequestParam(value = "email", required = true) String email) {
        String res = this.userService.getUserScore(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/getUserLevel")
    public ResponseEntity<String> getUserLevel(@RequestParam(value = "email", required = true) String email) {
        String res = this.userService.getUserLevel(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/getUserArea")
    public ResponseEntity<String> getUserArea(@RequestParam(value = "email", required = true) String email) {
        String res = this.userService.getUserArea(email);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/updateScore")
    public ResponseEntity<Boolean> updateScore(@RequestBody ObjectNode json) {
        String email;
        String score;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            score = new ObjectMapper().treeToValue(json.get("score"), String.class);
            boolean test = this.userService.updateScore(email, score);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/updateLevel")
    public ResponseEntity<Boolean> updateLevel(@RequestBody ObjectNode json) {
        String email;
        String level;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            level = new ObjectMapper().treeToValue(json.get("level"), String.class);
            boolean test = this.userService.updateLevel(email, level);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/updateArea")
    public ResponseEntity<Boolean> updateArea(@RequestBody ObjectNode json) {
        String email;
        String area;
        try {
            email = new ObjectMapper().treeToValue(json.get("email"), String.class);
            area = new ObjectMapper().treeToValue(json.get("area"), String.class);
            boolean test = this.userService.updateArea(email, area);
            if (test)
                return new ResponseEntity<Boolean>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }
}
