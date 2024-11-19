package org.example;

import org.example.api.Match;
import org.example.api.ScoreBoard;
import org.example.exception.ScoreBoardException;

import java.util.ArrayList;
import java.util.List;

public class WorldCupScoreBoard implements ScoreBoard {

  private final List<Match> matches;
  private final List<String> allowedTeamNames;

  public WorldCupScoreBoard(List<String> allowedTeamNames) {
    this.matches = new ArrayList<>();
    this.allowedTeamNames = allowedTeamNames;
  }

  @Override
  public void startGame(Team homeTeam, Team awayTeam) {

    if (homeTeam == null || awayTeam == null) {
      throw new ScoreBoardException("Team cannot be null.");
    }

    validateTeamNames(homeTeam.getName(), awayTeam.getName());

    matches.add(new SoccerMatch(homeTeam, awayTeam));
  }

  @Override
  public void finishGame(String homeTeamName, String awayTeamName) {

    boolean removed = matches.removeIf(match ->
        (match.getHomeTeam().getName().equals(homeTeamName) && match.getAwayTeam().getName().equals(awayTeamName)) ||
            (match.getHomeTeam().getName().equals(awayTeamName) && match.getAwayTeam().getName().equals(homeTeamName))
    );

    if (removed) {
      System.out.println("Match finished: " + homeTeamName + " vs " + awayTeamName + " removed from the scoreboard.");
    } else {
      System.out.println("Match not found: " + homeTeamName + " vs " + awayTeamName + ", no match has been finished.");
    }
  }

  @Override
  public void updateScore(Team homeTeam, Team awayTeam) {

  }

  @Override
  public List<Match> getSummary() {
    return matches;
  }

  private void validateTeamNames(String homeTeam, String awayTeam) {

    if (!allowedTeamNames.contains(homeTeam) || !allowedTeamNames.contains(awayTeam)) {
      throw new ScoreBoardException("Team name is not in the allowed list.");
    }

    if (homeTeam.equals(awayTeam)) {
      throw new ScoreBoardException("A team cannot play against itself.");
    }

    if (isTeamAlreadyPlaying(homeTeam, awayTeam)) {
      throw new ScoreBoardException("One or both teams are already playing.");
    }
  }

  private boolean isTeamAlreadyPlaying(String homeTeam, String awayTeam) {

    return matches.stream()
        .anyMatch(match -> match.getHomeTeam().getName().equals(homeTeam) || match.getAwayTeam().getName().equals(homeTeam) ||
            match.getHomeTeam().getName().equals(awayTeam) || match.getAwayTeam().getName().equals(awayTeam));
  }
}