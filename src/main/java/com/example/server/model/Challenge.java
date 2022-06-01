package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noemi on 09.04.2022
 */

@Entity
@Table(name="challenges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {

    @Id
    private String challengeId;
    private String title;
    private String description;
    private String difficulty;
    private String isUnlocked;
    private String isFinished;;

    @JsonIgnore
    @ManyToMany(mappedBy= "unlockedChallenges")
    private List<User> usersUnlocked = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy= "doneChallenges")
    private List<User> usersDone = new ArrayList<>();

    public Challenge(String challengeId, String title, String description, String difficulty, String isUnlocked, String isFinished) {
        this.challengeId = challengeId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.isUnlocked = isUnlocked;
        this.isFinished = isFinished;
    }
}
