package com.matchoddsapi.repository;

import com.matchoddsapi.model.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, Long> {
    List<MatchOdds> findByMatchId(Long matchId);
    void deleteByMatchId(Long matchId);
    boolean existsByMatchIdAndSpecifier(Long matchId, String specifier);
}