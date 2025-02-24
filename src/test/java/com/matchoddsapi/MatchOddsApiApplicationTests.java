package com.matchoddsapi;

import com.matchoddsapi.dto.MatchOddsDto;
import com.matchoddsapi.model.Match;
import com.matchoddsapi.model.MatchOdds;
import com.matchoddsapi.repository.MatchOddsRepository;
import com.matchoddsapi.repository.MatchRepository;
import com.matchoddsapi.service.MatchOddsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import com.matchoddsapi.exception.ResourceNotFoundException;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchOddsApiApplicationTests {

    @Mock
    private MatchOddsRepository matchOddsRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchOddsService matchOddsService;

    private Match testMatch;
    private MatchOdds testMatchOdds;
    private MatchOddsDto testMatchOddsDto;

    @BeforeEach
    void setUp() {
        testMatch = new Match();
        testMatch.setId(1L);

        testMatchOdds = new MatchOdds();
        testMatchOdds.setId(1L);
        testMatchOdds.setMatch(testMatch);
        testMatchOdds.setSpecifier("1");
        testMatchOdds.setOdd(1.5);

        testMatchOddsDto = new MatchOddsDto();
        testMatchOddsDto.setMatchId(1L);
        testMatchOddsDto.setSpecifier("1");
        testMatchOddsDto.setOdd(1.5);
    }

    @Test
    void whenCreateMatchOdds_withValidData_thenReturnMatchOddsDto() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(testMatch));
        when(matchOddsRepository.save(any(MatchOdds.class))).thenReturn(testMatchOdds);
        when(matchOddsRepository.existsByMatchIdAndSpecifier(1L, "1")).thenReturn(false);

        MatchOddsDto result = matchOddsService.createMatchOdds(testMatchOddsDto);

        assertNotNull(result);
        assertEquals(testMatchOddsDto.getSpecifier(), result.getSpecifier());
        assertEquals(testMatchOddsDto.getOdd(), result.getOdd());
        verify(matchOddsRepository).save(any(MatchOdds.class));
    }

    @Test
    void whenCreateMatchOdds_withInvalidSpecifier_thenThrowException() {
        testMatchOddsDto.setSpecifier("INVALID");
        when(matchRepository.findById(1L)).thenReturn(Optional.of(testMatch));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> matchOddsService.createMatchOdds(testMatchOddsDto));
        assertEquals("Specifier must be either '1', 'X', or '2'", exception.getMessage());
        verify(matchOddsRepository, never()).save(any());
    }

    @Test
    void whenCreateMatchOdds_withDuplicateSpecifier_thenThrowException() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(testMatch));
        when(matchOddsRepository.existsByMatchIdAndSpecifier(1L, "1")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> matchOddsService.createMatchOdds(testMatchOddsDto));
        assertTrue(exception.getMessage().contains("already exists for match"));
        verify(matchOddsRepository, never()).save(any());
    }

    @Test
    void whenCreateMatchOdds_withNonExistentMatch_thenThrowException() {
        when(matchRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> matchOddsService.createMatchOdds(testMatchOddsDto));
        verify(matchOddsRepository, never()).save(any());
    }

    @Test
    void whenDeleteMatchOdds_withExistingId_thenNoException() {
        when(matchOddsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(matchOddsRepository).deleteById(1L);

        assertDoesNotThrow(() -> matchOddsService.deleteMatchOdds(1L));
        verify(matchOddsRepository).deleteById(1L);
    }

    @Test
    void whenDeleteMatchOdds_withNonExistentId_thenThrowException() {
        when(matchOddsRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> matchOddsService.deleteMatchOdds(1L));
        verify(matchOddsRepository, never()).deleteById(any());
    }
}