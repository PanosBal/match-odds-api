# Match Odds API

A REST API for managing matches and their betting odds.

## Technologies Used

- Java 17
- Spring Boot 3.4.3
- JPA/Hibernate 
- PostgreSQL for database
- Maven
- Docker

## Database Schema

**Match Table:**
```
| Column      | Type          | Constraints        |
|-------------|---------------|--------------------|
| id          | BIGINT        | PK, AUTO_INCREMENT |
| description | VARCHAR(255)  | NOT NULL           |
| match_date  | DATE          | NOT NULL           |
| match_time  | TIME          | NOT NULL           |
| team_a      | VARCHAR(255)  | NOT NULL           |
| team_b      | VARCHAR(255)  | NOT NULL           |
| sport       | INT           | NOT NULL           |
```
**MatchOdds Table:**

```
| Column      | Type          | Constraints        |
|-------------|---------------|--------------------|
| id          | BIGINT        | PK, AUTO_INCREMENT |
| match_id    | BIGINT        | FK, NOT NULL       |
| specifier   | VARCHAR(255)  | NOT NULL           |
| odd         | DOUBLE        | NOT NULL           |
```

## API Endpoints

### Match Endpoints

- `GET http://localhost:8080/api/v1/matches/all`: Get all matches
- `GET http://localhost:8080/api/v1//api/matches/{id}`: Get a match by ID
- `POST http://localhost:8080/api/v1//api/matches`: Create a new match
- `PUT http://localhost:8080/api/v1//api/matches/{id}`: Update an existing match
- `DELETE http://localhost:8080/api/v1//api/matches/{id}`: Delete a match

### MatchOdds Endpoints

- `GET http://localhost:8080/api/v1/odds{id}`: Get a match odd by ID
- `POST http://localhost:8080/api/v1/odds`: Create a new match odd
- `PUT http://localhost:8080/api/v1/odds`: Update an existing match odd
- `DELETE http://localhost:8080/api/v1/odds`: Delete a match odd

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

### Running Locally

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/match-odds-api.git
   cd match-odds-api
   ```

2. Build and run the application with maven & docker
   ```bash
   docker-compose up --build
   ```

## Sample API Usage
Inside the repo you can find a Postman collection (MatchApi.postman_collection.json) with sample requests.

### Create a Match

```bash
curl -X POST http://localhost:8080/api/v1/matches -H "Content-Type: application/json" -d '{
  "description": "OSFP-PAO",
  "matchDate": "2021-03-31",
  "matchTime": "12:00:00",
  "teamA": "OSFP", 
  "teamB": "PAO",
  "sport": "FOOTBALL"
}'
```

### Add Odds to a Match

```bash
curl -X PUT http://localhost:8080/api/v1/odds/1 -H "Content-Type: application/json" -d '{
    "matchId": 1,
    "specifier": "1",
    "odd": 2.1
}'
```
