package com.matchoddsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_odds",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"match_id", "specifier"},
                name = "match_specifier"
        ))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchOdds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String specifier;

    @NotNull
    @Positive
    private Double odd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    @JsonIgnore
    private Match match;
}