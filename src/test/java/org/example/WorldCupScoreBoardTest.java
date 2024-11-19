package org.example;

import org.example.exception.ScoreBoardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldCupScoreBoardTest {

  private static final Team MEXICO_TEAM = new Team("Mexico", 0);
  private static final Team POLAND_TEAM = new Team("Poland", 0);

  WorldCupScoreBoard worldCupScoreBoard;

  @BeforeEach
  void setUp() {
    List<String> allowedTeams = Arrays.asList("Mexico", "Canada", "Spain", "Brazil", "Germany", "France", "Uruguay", "Italy", "Argentina", "Australia",
        "Poland");
    worldCupScoreBoard = new WorldCupScoreBoard(allowedTeams);
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
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    worldCupScoreBoard.startGame(new Team("Germany", 0), new Team("Italy", 0));

    //then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
  }

  @Test
  void shouldThrowException_whenStartingGame_givenOneTeamNameIsNull() {

    // given
    String expectedExceptionMessage = "Team cannot be null.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(MEXICO_TEAM, null));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenOneTeamNameSameAsTeamNameInExistingMatch() {

    // given
    Team newHomeTeam = new Team("Argentina", 0);
    String expectedExceptionMessage = "One or both teams are already playing.";

    // when
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(newHomeTeam, POLAND_TEAM));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenHomeAndAwayTeamNamesSame() {

    // given
    String expectedExceptionMessage = "A team cannot play against itself.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(POLAND_TEAM, POLAND_TEAM));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenInvalidTeamName() {

    // given
    Team awayTeam = new Team("123Poland", 0);
    String expectedExceptionMessage = "Team name is not in the allowed list.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(MEXICO_TEAM, awayTeam));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenTeamNamesSwapped() {

    // given
    String expectedExceptionMessage = "One or both teams are already playing.";

    // when
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(POLAND_TEAM, MEXICO_TEAM));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
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