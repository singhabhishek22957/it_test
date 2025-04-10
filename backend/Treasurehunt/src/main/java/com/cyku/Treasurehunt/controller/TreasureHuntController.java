package com.cyku.Treasurehunt.controller;

import com.cyku.Treasurehunt.entity.Team;
import com.cyku.Treasurehunt.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TreasureHuntController {

    @Autowired
    private TeamRepository teamRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> teamRegistration(@RequestBody String teamCode) {
        teamCode = teamCode.replace("\"", "").trim();

        if (teamCode.isEmpty()) {
            return new ResponseEntity<>("Team code cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        if (teamRepository.existsByTeam(teamCode)) {
            return new ResponseEntity<>("Team already registered.", HttpStatus.CONFLICT);
        }
        teamRepository.save(new Team(teamCode));
        return new ResponseEntity<>("Registration successful.", HttpStatus.OK);
    }

    @GetMapping("/teams/status")
    public ResponseEntity<List<Map<String, String>>> getAllTeamStatuses() {
        List<Team> teams = teamRepository.findAll();

        List<Map<String, String>> response = teams.stream().map(team -> {
            Map<String, String> map = new HashMap<>();
            map.put("team", team.getTeam());
            map.put("status", team.isStatus() ? "Qualified" : "Disqualified");
            return map;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/teams/clear")
    public ResponseEntity<String> clearAllTeams() {
        teamRepository.deleteAll();
        return new ResponseEntity<>("All teams have been deleted.", HttpStatus.OK);
    }
}
