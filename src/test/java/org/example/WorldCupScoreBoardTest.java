package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldCupScoreBoardTest {

  WorldCupScoreBoard worldCupScoreBoard;

  @BeforeEach
  void setUp() {
    worldCupScoreBoard = new WorldCupScoreBoard();
  }

  // starting game
  // 1. positive case
  // 2. negative case - ono or both teams are null
  // 3. negative case - one team name is same as team name in existing match
  // 4. negative case - same name for homeTeam and awayTeam
  // 5. negative case - one team name is invalid
  // 6. negative case - names swapped

  @Test
  void shouldStartGame_whenInvoked_givenValidTeamsName() {

    // given
    int expectedNumberOfMatches = 2;

    // when
    worldCupScoreBoard.startGame(new Team("Mexico", 0), new Team("Poland", 0));
    worldCupScoreBoard.startGame(new Team("Germany", 0), new Team("Italy", 0));

    //then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
  }

  @Test
  void startGame() {
  }

  @Test
  void finishGame() {
  }

  @Test
  void updateScore() {
  }

  @Test
  void getSummary() {
  }
}