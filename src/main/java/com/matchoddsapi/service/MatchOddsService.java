package com.matchoddsapi.service;

import com.matchoddsapi.dto.MatchOddsDto;
import com.matchoddsapi.exception.ResourceNotFoundException;
import com.matchoddsapi.model.Match;
import com.matchoddsapi.model.MatchOdds;
import com.matchoddsapi.repository.MatchOddsRepository;
import com.matchoddsapi.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@Service
public class MatchOddsService {

    private final MatchOddsRepository matchOddsRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchOddsService(MatchOddsRepository matchOddsRepository, MatchRepository matchRepository) {
        this.matchOddsRepository = matchOddsRepository;
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public List<MatchOddsDto> getMatchOddsByMatchId(Long matchId) {
        return matchOddsRepository.findByMatchId(matchId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MatchOddsDto getMatchOddsById(Long id) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match odds not found with id: " + id));
        return convertToDto(matchOdds);
    }

    @Transactional
    public MatchOddsDto createMatchOdds(MatchOddsDto matchOddsDto) {
        Match match = matchRepository.findById(matchOddsDto.getMatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + matchOddsDto.getMatchId()));

        if (!Arrays.asList("1", "X", "2").contains(matchOddsDto.getSpecifier())) {
            throw new IllegalArgumentException("Specifier must be either '1', 'X', or '2'");
        }

        if (matchOddsRepository.existsByMatchIdAndSpecifier(matchOddsDto.getMatchId(), matchOddsDto.getSpecifier())) {
            throw new IllegalArgumentException(
                    String.format("Specifier '%s' already exists for match id=%d",
                            matchOddsDto.getSpecifier(),
                            matchOddsDto.getMatchId())
            );
        }

        MatchOdds matchOdds = convertToEntity(matchOddsDto);
        matchOdds.setMatch(match);

        MatchOdds savedMatchOdds = matchOddsRepository.save(matchOdds);
        return convertToDto(savedMatchOdds);
    }

    @Transactional
    public MatchOddsDto updateMatchOdds(Long id, MatchOddsDto matchOddsDto) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match odds not found with id: " + id));

        matchOdds.setSpecifier(matchOddsDto.getSpecifier());
        matchOdds.setOdd(matchOddsDto.getOdd());

        if (matchOddsDto.getMatchId() != null && !matchOddsDto.getMatchId().equals(matchOdds.getMatch().getId())) {
            Match newMatch = matchRepository.findById(matchOddsDto.getMatchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + matchOddsDto.getMatchId()));
            matchOdds.setMatch(newMatch);
        }

        MatchOdds updatedMatchOdds = matchOddsRepository.save(matchOdds);
        return convertToDto(updatedMatchOdds);
    }

    @Transactional
    public void deleteMatchOdds(Long id) {
        if (!matchOddsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match odds not found with id: " + id);
        }
        matchOddsRepository.deleteById(id);
    }

    MatchOddsDto convertToDto(MatchOdds matchOdds) {
        MatchOddsDto dto = new MatchOddsDto();
        dto.setId(matchOdds.getId());
        dto.setMatchId(matchOdds.getMatch().getId());
        dto.setSpecifier(matchOdds.getSpecifier());
        dto.setOdd(matchOdds.getOdd());
        return dto;
    }

    private MatchOdds convertToEntity(MatchOddsDto dto) {
        MatchOdds matchOdds = new MatchOdds();
        if (dto.getId() != null) {
            matchOdds.setId(dto.getId());
        }
        matchOdds.setSpecifier(dto.getSpecifier());
        matchOdds.setOdd(dto.getOdd());
        return matchOdds;
    }
}