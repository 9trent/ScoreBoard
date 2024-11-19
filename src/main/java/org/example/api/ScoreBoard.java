package org.example.api;

import org.example.Team;

import java.util.List;

public interface ScoreBoard {

  void startGame(Team homeTeam, Team awayTeam);

  void finishGame(Team homeTeam, Team awayTeam);

  void updateScore(Team homeTeam, Team awayTeam);

  List<Match> getSummary();

}
