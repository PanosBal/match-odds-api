DROP TABLE IF EXISTS match_odds;
DROP TABLE IF EXISTS matches;

CREATE TABLE matches (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    match_date DATE NOT NULL,
    match_time TIME NOT NULL,
    team_a VARCHAR(255) NOT NULL,
    team_b VARCHAR(255) NOT NULL,
    sport INT NOT NULL
);

CREATE TABLE match_odds (
    id SERIAL PRIMARY KEY,
    match_id BIGINT NOT NULL,
    specifier VARCHAR(255) NOT NULL,
    odd DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE
);
