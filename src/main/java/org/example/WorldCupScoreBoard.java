package org.example;

import org.example.api.Match;
import org.example.api.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class WorldCupScoreBoard implements ScoreBoard {

  private final List<Match> matches;

  public WorldCupScoreBoard() {
    this.matches = new ArrayList<>();
  }

  @Override
  public void startGame(Team homeTeam, Team awayTeam) {

    matches.add(new SoccerMatch(homeTeam, awayTeam));
  }

  @Override
  public void finishGame(Team homeTeam, Team awayTeam) {

  }

  @Override
  public void updateScore(Team homeTeam, Team awayTeam) {

  }

  @Override
  public List<Match> getSummary() {
    return matches;
  }
}