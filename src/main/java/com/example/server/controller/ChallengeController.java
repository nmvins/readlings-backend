package com.example.server.controller;


import com.example.server.model.Challenge;
import com.example.server.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * @author Noemi on 09.04.2022
 */

@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("challenges")
public class ChallengeController {

    @Autowired
    ChallengeService challengeService;

    @GetMapping
    public Set<Challenge> getAllChallenges(){
        return this.challengeService.getAllChallenges();
    }


    @PostMapping(path="/create")
    public ResponseEntity<Challenge> create(@RequestBody Challenge challenge){
        try {
            this.challengeService.saveChallenge(challenge);
            return new ResponseEntity<Challenge>(challenge, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<Challenge>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(path="/update")
    public ResponseEntity<Challenge> update(@RequestBody Challenge challenge){
        try {
            this.challengeService.saveChallenge(challenge);
            return new ResponseEntity<Challenge>(challenge, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Challenge>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/{challengeId}")
    public ResponseEntity<Challenge> findById(@PathVariable("challengeId") String challengeId) {
        Optional<Challenge> resultat = challengeService.findByChallengeId(challengeId);
        if (resultat.isPresent())
            return new ResponseEntity<>(resultat.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{challengeId}")
    public ResponseEntity<Challenge> delete(@PathVariable("challengeId") String challengeId){
        Optional<Challenge> resultat = challengeService.findByChallengeId(challengeId);
        if(resultat.isPresent()) {
            this.challengeService.deleteChallenge(resultat.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
