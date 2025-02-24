package com.matchoddsapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchOddsDto {
    private Long id;

    @Positive(message = "Match id must be a positive number")
    private Long matchId;

    @NotBlank(message = "Specifier is required")
    private String specifier;

    @NotNull(message = "Odd is required")
    @Positive(message = "Odd must be a positive number")
    private Double odd;
}
