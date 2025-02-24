package com.matchoddsapi.service;

import com.matchoddsapi.dto.MatchDto;
import com.matchoddsapi.model.Match;
import com.matchoddsapi.repository.MatchRepository;
import com.matchoddsapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private final MatchRepository matchRepository;

    @Autowired
    private final MatchOddsService matchOddsService;

    @Autowired
    public MatchService(MatchRepository matchRepository, MatchOddsService matchOddsService) {
        this.matchRepository = matchRepository;
        this.matchOddsService = matchOddsService;
    }

    @Transactional
    public List<MatchDto> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MatchDto getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
        return convertToDto(match);
    }

    @Transactional
    public MatchDto createMatch(MatchDto matchDto) {
        Match match = convertToEntity(matchDto);
        Match savedMatch = matchRepository.save(match);

        if (matchDto.getOdds() != null && !matchDto.getOdds().isEmpty()) {
            matchDto.getOdds().forEach(oddDto -> {
                oddDto.setMatchId(savedMatch.getId());
                matchOddsService.createMatchOdds(oddDto);
            });
        }

        return convertToDto(savedMatch);
    }

    @Transactional
    public MatchDto updateMatch(Long id, MatchDto matchDto) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));

        match.setDescription(matchDto.getDescription());
        match.setMatchDate(matchDto.getMatchDate());
        match.setMatchTime(matchDto.getMatchTime());
        match.setTeamA(matchDto.getTeamA());
        match.setTeamB(matchDto.getTeamB());
        match.setSport(matchDto.getSport());

        Match updatedMatch = matchRepository.save(match);
        return convertToDto(updatedMatch);
    }

    @Transactional
    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }

    private MatchDto convertToDto(Match match) {
        MatchDto dto = new MatchDto();
        dto.setId(match.getId());
        dto.setDescription(match.getDescription());
        dto.setMatchDate(match.getMatchDate());
        dto.setMatchTime(match.getMatchTime());
        dto.setTeamA(match.getTeamA());
        dto.setTeamB(match.getTeamB());
        dto.setSport(match.getSport());

        dto.setOdds(match.getOdds().stream()
                .map(matchOddsService::convertToDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private Match convertToEntity(MatchDto dto) {
        Match match = new Match();
        if (dto.getId() != null) {
            match.setId(dto.getId());
        }
        match.setDescription(dto.getDescription());
        match.setMatchDate(dto.getMatchDate());
        match.setMatchTime(dto.getMatchTime());
        match.setTeamA(dto.getTeamA());
        match.setTeamB(dto.getTeamB());
        match.setSport(dto.getSport());

        return match;
    }
}
