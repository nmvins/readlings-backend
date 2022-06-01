package com.example.server.service;

import com.example.server.model.Book;
import com.example.server.model.Challenge;

import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */
public interface ChallengeService {

    Set<Challenge> getAllChallenges();
    Set<Challenge> getChallengesNameContain(String pattern);
    boolean saveChallenge(Challenge challenge);
    boolean deleteChallenge(Challenge challenge);
    Optional<Challenge> findByChallengeId(String challengeId);
    Challenge updateChallenge(Challenge challenge);
}
