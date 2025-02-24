package com.matchoddsapi.controller;

import com.matchoddsapi.dto.MatchOddsDto;
import com.matchoddsapi.service.MatchOddsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/odds")
public class MatchOddsController {

    private final MatchOddsService matchOddsService;

    @Autowired
    public MatchOddsController(MatchOddsService matchOddsService) {
        this.matchOddsService = matchOddsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchOddsDto> getMatchOddsById(@PathVariable Long id) {
        return ResponseEntity.ok(matchOddsService.getMatchOddsById(id));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MatchOddsDto>> getMatchOddsByMatchId(@PathVariable Long matchId) {
        return ResponseEntity.ok(matchOddsService.getMatchOddsByMatchId(matchId));
    }

    @PostMapping
    public ResponseEntity<MatchOddsDto> createMatchOdds(@Valid @RequestBody MatchOddsDto matchOddsDto) {
        return new ResponseEntity<>(matchOddsService.createMatchOdds(matchOddsDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchOddsDto> updateMatchOdds(@PathVariable Long id, @Valid @RequestBody MatchOddsDto matchOddsDto) {
        return ResponseEntity.ok(matchOddsService.updateMatchOdds(id, matchOddsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatchOdds(@PathVariable Long id) {
        MatchOddsDto odd = matchOddsService.getMatchOddsById(id);
        Long matchId = odd.getMatchId();
        matchOddsService.deleteMatchOdds(id);
        return ResponseEntity.ok(String.format("odd_id=%d for match_id=%d has been deleted successfully.", id, matchId));
    }
}
