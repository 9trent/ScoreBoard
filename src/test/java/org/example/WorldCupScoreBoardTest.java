package org.example;

import org.example.api.Match;
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
  private static final String BRAZIL = "Brazil";

  WorldCupScoreBoard worldCupScoreBoard;

  @BeforeEach
  void setUp() {
    List<String> allowedTeams = Arrays.asList("Mexico", "Canada", "Spain", "Brazil", "Germany", "France", "Uruguay", "Italy", "Argentina", "Australia",
        "Poland");
    worldCupScoreBoard = new WorldCupScoreBoard(allowedTeams);
  }

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
  void shouldReturnEmptyList_whenInvoked_givenNoMatchesPresent() {

    // when
    List<Match> summary = worldCupScoreBoard.getSummary();

    // then
    assertTrue(summary.isEmpty());
  }

  @Test
  void shouldReturnSingleMatch_whenInvoked_givenOnlyOneMatchPresent() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);

    // when
    List<Match> summary = worldCupScoreBoard.getSummary();

    // then
    assertEquals(1, summary.size());
    assertEquals(MEXICO, summary.get(0).getHomeTeam().getName());
    assertEquals(POLAND, summary.get(0).getAwayTeam().getName());
  }

  @Test
  void shouldReturnMatchesInCorrectOrder_whenInvoked_givenDifferentTotalScores() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    worldCupScoreBoard.startGame(SPAIN, BRAZIL);
    List<Match> expectedMatches = List.of(
        new SoccerMatch(new Team(SPAIN, 3), new Team(BRAZIL, 3)),
        new SoccerMatch(new Team(GERMANY, 2), new Team(ITALY, 2)),
        new SoccerMatch(new Team(MEXICO, 1), new Team(POLAND, 1))
    );

    // when
    worldCupScoreBoard.updateScore(new Team(SPAIN, 3), new Team(BRAZIL, 3));
    worldCupScoreBoard.updateScore(new Team(MEXICO, 1), new Team(POLAND, 1));
    worldCupScoreBoard.updateScore(new Team(GERMANY, 2), new Team(ITALY, 2));

    // then
    List<Match> summary = worldCupScoreBoard.getSummary();
    assertEquals(expectedMatches.size(), summary.size());
    for (int i = 0; i < expectedMatches.size(); i++) {
      assertEquals(expectedMatches.get(i), summary.get(i));
    }
  }

  @Test
  void shouldReturnMatchesInOrderAdded_whenGetSummary_givenNoScoresUpdated() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    worldCupScoreBoard.startGame(SPAIN, BRAZIL);
    List<Match> expectedMatches = List.of(
        new SoccerMatch(new Team(SPAIN, 0), new Team(BRAZIL, 0)),
        new SoccerMatch(new Team(GERMANY, 0), new Team(ITALY, 0)),
        new SoccerMatch(new Team(MEXICO, 0), new Team(POLAND, 0))
    );

    // when
    List<Match> summary = worldCupScoreBoard.getSummary();

    // then
    assertEquals(expectedMatches.size(), summary.size());
    for (int i = 0; i < expectedMatches.size(); i++) {
      assertEquals(expectedMatches.get(i), summary.get(i));
    }
  }

  @Test
  void shouldReturnMatchesInCorrectOrder_whenInvoked_givenMultipleMatchesHaveSameTotalScore() {

    // given
    worldCupScoreBoard.startGame(MEXICO, POLAND);
    worldCupScoreBoard.startGame(GERMANY, ITALY);
    worldCupScoreBoard.startGame(SPAIN, BRAZIL);

    List<Match> expectedMatches = List.of(
        new SoccerMatch(new Team(SPAIN, 2), new Team(BRAZIL, 2)),
        new SoccerMatch(new Team(GERMANY, 1), new Team(ITALY, 3)),
        new SoccerMatch(new Team(MEXICO, 2), new Team(POLAND, 2))
    );

    // when
    worldCupScoreBoard.updateScore(new Team(GERMANY, 1), new Team(ITALY, 3));
    worldCupScoreBoard.updateScore(new Team(MEXICO, 2), new Team(POLAND, 2));
    worldCupScoreBoard.updateScore(new Team(SPAIN, 2), new Team(BRAZIL, 2));

    // then
    List<Match> summary = worldCupScoreBoard.getSummary();
    assertEquals(expectedMatches.size(), summary.size());
    for (int i = 0; i < expectedMatches.size(); i++) {
      assertEquals(expectedMatches.get(i), summary.get(i));
    }
  }
}