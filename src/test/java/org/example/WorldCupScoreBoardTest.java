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

  private static final String MEXICO = "Mexico";
  private static final String POLAND = "Poland";
  private static final String GERMANY = "Germany";
  private static final String ITALY = "Italy";
  private static final String SPAIN = "Spain";

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
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);

    //then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
  }

  @Test
  void shouldThrowException_whenStartingGame_givenOneTeamIsNull() {

    // given
    String expectedExceptionMessage = "Team cannot be null.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(MEXICO, null));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenOneTeamNameSameAsTeamNameInExistingMatch() {

    // given
    String anotherHomeTeam = "Argentina";
    String expectedExceptionMessage = "One or both teams are already playing.";

    // when
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(anotherHomeTeam, POLAND));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenHomeAndAwayTeamNamesSame() {

    // given
    String expectedExceptionMessage = "A team cannot play against itself.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(POLAND, POLAND));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenInvalidTeamName() {

    // given
    String awayTeam = "123Poland";
    String expectedExceptionMessage = "Team name is not in the allowed list.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(MEXICO, awayTeam));

    //then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldThrowException_whenStartingGame_givenTeamNamesSwapped() {

    // given
    String expectedExceptionMessage = "One or both teams are already playing.";

    // when
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    Exception exception = assertThrows(ScoreBoardException.class, () -> worldCupScoreBoard.startGame(POLAND, MEXICO));

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
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);

    // when
    worldCupScoreBoard.finishGame(homeTeamName, awayTeamName);

    // then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
  }

  // update score
  // 1. positive case
  // 2. positive case - updating by more than 1 goal at a time
  // 3. positive case - updating score down
  // 4. positive case - updating score with team names swapped
  // 5. positive case - updating score for non-existing match and none of the team is already playing - starting a new match
  // 6. negative case - updating score for non-existing match and one of the team is already playing - throw exception
  // 7. negative case - updating score to negative
  @Test
  void shouldUpdateScore_whenInvoked_givenScoreUpdateByOneGoal() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);

    // when
    worldCupScoreBoard.updateScore(new Team(MEXICO, 0), new Team(POLAND, 1));

    // then
    boolean allMatch = worldCupScoreBoard.getSummary().stream()
        .allMatch(match -> {
          if (match.getHomeTeam().getName().equals(MEXICO) && match.getAwayTeam().getName().equals(POLAND)) {
            return match.getHomeTeam().getScore() == 0 && match.getAwayTeam().getScore() == 1;
          }
          return match.getHomeTeam().getScore() == 0 && match.getAwayTeam().getScore() == 0;
        });

    assertTrue(allMatch);
  }

  @Test
  void shouldUpdateScore_whenInvoked_givenScoreUpdateByMultipleGoalsAtATime() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);

    // when
    worldCupScoreBoard.updateScore(new Team(MEXICO, 2), new Team(POLAND, 3));

    // then
    boolean allMatch = worldCupScoreBoard.getSummary().stream()
        .allMatch(match -> {
          if (match.getHomeTeam().getName().equals(MEXICO) && match.getAwayTeam().getName().equals(POLAND)) {
            return match.getHomeTeam().getScore() == 2 && match.getAwayTeam().getScore() == 3;
          }
          return match.getHomeTeam().getScore() == 0 && match.getAwayTeam().getScore() == 0;
        });

    assertTrue(allMatch);
  }

  @Test
  void shouldUpdateScore_whenInvoked_givenScoreUpdateDown() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    worldCupScoreBoard.updateScore(new Team(MEXICO, 5), new Team(POLAND, 6));
    worldCupScoreBoard.getSummary().stream()
        .filter(match -> match.getHomeTeam().getName().equals(MEXICO) && match.getAwayTeam().getName().equals(POLAND))
        .forEach(match -> {
          assertEquals(5, match.getHomeTeam().getScore());
          assertEquals(6, match.getAwayTeam().getScore());
        });

    // when
    worldCupScoreBoard.updateScore(new Team(MEXICO, 2), new Team(POLAND, 3));

    // then
    boolean allMatch = worldCupScoreBoard.getSummary().stream()
        .allMatch(match -> {
          if (match.getHomeTeam().getName().equals(MEXICO) && match.getAwayTeam().getName().equals(POLAND)) {
            return match.getHomeTeam().getScore() == 2 && match.getAwayTeam().getScore() == 3;
          }
          return match.getHomeTeam().getScore() == 0 && match.getAwayTeam().getScore() == 0;
        });

    assertTrue(allMatch);
  }

  @Test
  void shouldUpdateScore_whenInvoked_givenScoreUpdateWithTeamNameSwapped() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);

    // when
    worldCupScoreBoard.updateScore(new Team(POLAND, 2), new Team(MEXICO, 3));

    // then
    boolean allMatch = worldCupScoreBoard.getSummary().stream()
        .allMatch(match -> {
          if (match.getHomeTeam().getName().equals(MEXICO) && match.getAwayTeam().getName().equals(POLAND)) {
            return match.getHomeTeam().getScore() == 3 && match.getAwayTeam().getScore() == 2;
          }
          return match.getHomeTeam().getScore() == 0 && match.getAwayTeam().getScore() == 0;
        });

    assertTrue(allMatch);
  }

  @Test
  void shouldStartNewGame_whenInvoked_givenScoreUpdateForNoneExistingMatchAndNoneOfTheTeamAlreadyPlaying() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    assertEquals(2, worldCupScoreBoard.getSummary().size());
    int expectedNumberOfMatches = 3;

    // when
    worldCupScoreBoard.updateScore(new Team(SPAIN, 2), new Team("Brazil", 1));

    // then
    assertEquals(expectedNumberOfMatches, worldCupScoreBoard.getSummary().size());
    assertTrue(worldCupScoreBoard.getSummary().stream()
        .allMatch(match ->
            match.getHomeTeam().getScore() == 0 && match.getAwayTeam().getScore() == 0
        ));
  }

  @Test
  void shouldNotUpdateScore_whenInvoked_givenScoreUpdateForNoneExistingMatchAndTeamAlreadyPlaying() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    String expectedExceptionMessage = "One or both teams are already playing.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () ->
        worldCupScoreBoard.updateScore(new Team(SPAIN, 2), new Team(ITALY, 1)));

    // then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldNotUpdateScore_whenInvoked_givenScoreUpdateWithNegativeValues() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    String expectedExceptionMessage = "Score cannot be negative.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () ->
        worldCupScoreBoard.updateScore(new Team(GERMANY, 2), new Team(ITALY, -1)));

    // then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }

  @Test
  void shouldNotUpdateScore_whenInvoked_givenScoreUpdateWithNullTeamName() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    String expectedExceptionMessage = "Team cannot be null.";

    // when
    Exception exception = assertThrows(ScoreBoardException.class, () ->
        worldCupScoreBoard.updateScore(new Team(null, 2), new Team(ITALY, 2)));

    // then
    assertTrue(exception.getMessage().contains(expectedExceptionMessage));
  }


  @Test
  void getSummary() {
  }
}