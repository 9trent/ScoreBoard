package org.example;

import org.example.api.Match;
import org.example.api.ScoreBoard;
import org.example.exception.ScoreBoardException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorldCupScoreBoard implements ScoreBoard {

  private final List<Match> matches;
  private final List<String> allowedTeamNames;

  public WorldCupScoreBoard(List<String> allowedTeamNames) {
    this.matches = new ArrayList<>();
    this.allowedTeamNames = allowedTeamNames;
  }

  @Override
  public void startGame(String homeTeam, String awayTeam) {

    if (homeTeam == null || awayTeam == null) {
      throw new ScoreBoardException("Team cannot be null.");
    }

    validateTeamNames(homeTeam, awayTeam);

    matches.add(new SoccerMatch(new Team(homeTeam, 0), new Team(awayTeam, 0)));
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

    if (homeTeam.getScore() < 0 || awayTeam.getScore() < 0) {
      throw new ScoreBoardException("Score cannot be negative.");
    }

    Optional<Match> matchToUpdate = matches.stream()
        .filter(match -> match.getHomeTeam().getName().equals(homeTeam.getName()) && match.getAwayTeam().getName().equals(awayTeam.getName())
            || match.getHomeTeam().getName().equals(awayTeam.getName()) && match.getAwayTeam().getName().equals(homeTeam.getName()))
        .findFirst();

    matchToUpdate.ifPresentOrElse(match -> {
      if (match.getHomeTeam().getName().equals(homeTeam.getName())) {
        match.getHomeTeam().setScore(homeTeam.getScore());
        match.getAwayTeam().setScore(awayTeam.getScore());
      } else {
        match.getHomeTeam().setScore(awayTeam.getScore());
        match.getAwayTeam().setScore(homeTeam.getScore());
      }
    }, () -> {
      System.out.println("Match not found: " + homeTeam + " vs " + awayTeam + ". Starting a new match with initial score 0-0.");
      startGame(homeTeam.getName(), awayTeam.getName());
    });
  }

  @Override
  public List<Match> getSummary() {

    return matches.stream()
        .sorted(Comparator.comparingInt(Match::getTotalScore)
            .reversed()
            .thenComparing((match1, match2) -> Integer.compare(matches.indexOf(match2), matches.indexOf(match1))))
        .collect(Collectors.toList());
  }

  private void validateTeamNames(String homeTeamName, String awayTeamName) {

    if (!allowedTeamNames.contains(homeTeamName) || !allowedTeamNames.contains(awayTeamName)) {
      throw new ScoreBoardException("Team name is not in the allowed list.");
    }

    if (homeTeamName.equals(awayTeamName)) {
      throw new ScoreBoardException("A team cannot play against itself.");
    }

    if (isTeamAlreadyPlaying(homeTeamName, awayTeamName)) {
      throw new ScoreBoardException("One or both teams are already playing.");
    }
  }

  private boolean isTeamAlreadyPlaying(String homeTeamName, String awayTeamName) {

    return matches.stream()
        .anyMatch(match -> match.getHomeTeam().getName().equals(homeTeamName) || match.getAwayTeam().getName().equals(homeTeamName) ||
            match.getHomeTeam().getName().equals(awayTeamName) || match.getAwayTeam().getName().equals(awayTeamName));
  }
}