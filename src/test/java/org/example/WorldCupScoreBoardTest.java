package org.example;

import org.example.exception.ScoreBoardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldCupScoreBoardTest {

  private static final Team MEXICO_TEAM = new Team("Mexico", 0);
  private static final Team POLAND_TEAM = new Team("Poland", 0);
  private static final Team GERMANY_TEAM = new Team("Germany", 0);
  private static final Team ITALY_TEAM = new Team("Italy", 0);

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
  // 7. negative case - one team name is null

  @Test
  void shouldStartGame_whenInvoked_givenValidTeamsName() {

    // given
    int expectedNumberOfMatches = 2;

    // when
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    worldCupScoreBoard.startGame(GERMANY_TEAM, ITALY_TEAM);

    //then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
  }

  @Test
  void shouldThrowException_whenStartingGame_givenOneTeamIsNull() {

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
    Team anotherHomeTeam = new Team("Argentina", 0);
    String expectedExceptionMessage = "One or both teams are already playing.";

    // when
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(anotherHomeTeam, POLAND_TEAM));

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
  void shouldThrowException_whenStartingGame_givenTeamNameIsNull() {

    // given
    Team nullNameTeam = new Team(null, 0);
    String expectedExceptionMessage = "Team name is not in the allowed list.";

    // when
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(nullNameTeam, MEXICO_TEAM));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  // finishing game
  // 1. positive case
  // 2. positive case - finishing match but team names swapped
  // 3. positive case - non-existing match - do nothing
  // 4. positive case - non-existing match i.e. team name is null - do nothing
  @ParameterizedTest
  @CsvSource({
      "Germany, Italy, 1",
      "Italy, Germany, 1",
      "Poland, Germany, 2",
      "null, Germany, 2"
  })
  void shouldFinishGameOrDoNothing_whenInvoked_givenVariousParameters(String homeTeamName, String awayTeamName, int expectedNumberOfMatches) {

    // given
    worldCupScoreBoard.startGame(MEXICO_TEAM, POLAND_TEAM);
    worldCupScoreBoard.startGame(GERMANY_TEAM, ITALY_TEAM);

    // when
    worldCupScoreBoard.finishGame(homeTeamName, awayTeamName);

    // then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
  }

  @Test
  void updateScore() {
  }

  @Test
  void getSummary() {
  }
}