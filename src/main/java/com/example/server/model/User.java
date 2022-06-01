package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    private String score;
    private String currentLevel;
    private String currentArea;

    @ElementCollection
    private List<String> speed;
    @ElementCollection
    private List<String> attention;
    @ElementCollection
    private List<String> loginDate;
    @ElementCollection
    private List<String> logoutDate;
    private String age;

    @ManyToMany
    private List<Challenge> unlockedChallenges;

    @ManyToMany
    private List<Challenge> doneChallenges;

    public List<String> getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(List<String> loginDate) {
        this.loginDate = loginDate;
    }

    public void setLogoutDate(List<String> logoutDate) {
        this.logoutDate = logoutDate;
    }

    public List<String> getLogoutDate() {
        return logoutDate;
    }

    @ManyToMany
    private List<Book> books;

    @ManyToMany/*(mappedBy="user")*/
    private List<PredefinedBook> predefinedBooks;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getScore() {
        return score;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public List<Challenge> getUnlockedChallenges() {
        return unlockedChallenges;
    }

    public List<Challenge> getDoneChallenges() {
        return doneChallenges;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<PredefinedBook> getPredefinedBooks() {
        return predefinedBooks;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(String currentArea) {
        this.currentArea = currentArea;
    }

    public List<String> getSpeed() {
        return speed;
    }

    public List<String> getAttention() {
        return attention;
    }

    public String getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setSpeed(List<String> speed) {
        this.speed = speed;
    }

    public void setAttention(List<String> attention) {
        this.attention = attention;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setUnlockedChallenges(List<Challenge> unlockedChallenges) {
        this.unlockedChallenges = unlockedChallenges;
    }

    public void setDoneChallenges(List<Challenge> doneChallenges) {
        this.doneChallenges = doneChallenges;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setPredefinedBooks(List<PredefinedBook> predefinedBooks) {
        this.predefinedBooks = predefinedBooks;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
