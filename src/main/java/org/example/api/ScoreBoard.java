package org.example.api;

import org.example.Team;

import java.util.List;

public interface ScoreBoard {

  void startGame(String homeTeam, String awayTeam);

  void finishGame(String homeTeamName, String awayTeamName);

  void updateScore(Team homeTeam, Team awayTeam);

  List<Match> getSummary();

}
