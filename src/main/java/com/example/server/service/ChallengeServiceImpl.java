package com.example.server.service;

import com.example.server.model.Challenge;
import com.example.server.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    ChallengeRepository challengeRepository;

    @Override
    public Set<Challenge> getAllChallenges() {
        return new HashSet<Challenge>( this.challengeRepository.findAll());
    }

    @Override
    public boolean saveChallenge(Challenge challenge) {
        if(this.challengeRepository.save(challenge)!=null) return true;
        else
            return false;
    }

    @Override
    public Set<Challenge> getChallengesNameContain(String pattern) {
        return new HashSet<>(this.challengeRepository.findByChallengeNameContain(pattern));
    }

    @Override
    public boolean deleteChallenge(Challenge challenge) {
        this.challengeRepository.delete(challenge);
        return true;
    }

    @Override
    public Optional<Challenge> findByChallengeId(String challengeId) {
        return this.challengeRepository.findById(challengeId);
    }

    @Override
    public Challenge updateChallenge(Challenge challenge) {
        return this.challengeRepository.save(challenge);
    }
}
