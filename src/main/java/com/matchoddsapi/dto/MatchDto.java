package com.matchoddsapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matchoddsapi.model.Sport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class MatchDto {
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Match date is required")
    private LocalDate matchDate;

    @NotNull(message = "Match time is required")
    private LocalTime matchTime;

    @NotBlank(message = "Team A is required")
    private String teamA;

    @NotBlank(message = "Team B is required")
    private String teamB;

    @NotNull(message = "Sport is required")
    private Sport sport;

    private List<MatchOddsDto> odds = new ArrayList<>();
}