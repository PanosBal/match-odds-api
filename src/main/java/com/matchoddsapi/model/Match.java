package com.matchoddsapi.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    @Column(name = "match_date")
    private LocalDate matchDate;

    @NotNull
    @Column(name = "match_time")
    private LocalTime matchTime;

    @NotBlank
    @Column(name = "team_a")
    private String teamA;

    @NotBlank
    @Column(name = "team_b")
    private String teamB;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Sport sport;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchOdds> odds = new ArrayList<>();
}