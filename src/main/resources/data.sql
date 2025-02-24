-- Matches table inserts
INSERT INTO matches (description, match_date, match_time, team_a, team_b, sport)
VALUES ('OSFP-PAO', '2021-03-31', '12:00:00', 'OSFP', 'PAO', 1);

INSERT INTO matches (description, match_date, match_time, team_a, team_b, sport)
VALUES ('AEK-PAOK', '2021-04-01', '15:30:00', 'AEK', 'PAOK', 1);

INSERT INTO matches (description, match_date, match_time, team_a, team_b, sport)
VALUES ('PAO-AEK', '2021-04-03', '19:00:00', 'PAO', 'AEK', 1);

INSERT INTO matches (description, match_date, match_time, team_a, team_b, sport)
VALUES ('PAOK-OSFP', '2021-04-04', '20:15:00', 'PAOK', 'OSFP', 1);

INSERT INTO matches (description, match_date, match_time, team_a, team_b, sport)
VALUES ('ARIS-OSFP', '2021-04-07', '18:45:00', 'ARIS', 'OSFP', 1);

-- Match odds table inserts (1=Home win, X=Draw, 2=Away win)
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (1, '1', 2.10);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (1, 'X', 3.20);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (1, '2', 3.40);

INSERT INTO match_odds (match_id, specifier, odd)
VALUES (2, '1', 2.30);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (2, 'X', 3.10);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (2, '2', 2.90);

INSERT INTO match_odds (match_id, specifier, odd)
VALUES (3, '1', 1.95);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (3, 'X', 3.30);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (3, '2', 3.80);

INSERT INTO match_odds (match_id, specifier, odd)
VALUES (4, '1', 2.40);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (4, 'X', 3.20);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (4, '2', 2.85);

INSERT INTO match_odds (match_id, specifier, odd)
VALUES (5, '1', 2.70);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (5, 'X', 3.25);
INSERT INTO match_odds (match_id, specifier, odd)
VALUES (5, '2', 2.50);