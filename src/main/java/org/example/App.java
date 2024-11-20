package org.example;

import org.example.util.TeamUtil;

import java.util.List;

public class App {
  public static void main(String[] args) {

    List<String> allowedTeams = TeamUtil.getAllowedTeams();
    WorldCupScoreBoard worldCupScoreBoard = new WorldCupScoreBoard(allowedTeams);

    // Start some games
    worldCupScoreBoard.startGame("Mexico", "Canada");
    worldCupScoreBoard.startGame("Spain", "Brazil");
    worldCupScoreBoard.startGame("Germany","France");
    worldCupScoreBoard.startGame("Uruguay", "Italy");

//    worldCupScoreBoard.startGame(new Team("Spain", 0), new Team("Germany", 0));
//    worldCupScoreBoard.startGame(new Team("France", 0), new Team(null, 0));
//    worldCupScoreBoard.startGame(new Team("Germany", 0), new Team("France", 0));
//    worldCupScoreBoard.startGame(new Team("Germany", 0), new Team("aaa", 0));
//    worldCupScoreBoard.startGame(null, new Team("France", 0));
//    worldCupScoreBoard.startGame(new Team("Germany", 0), new Team("Germany", 0));

    // finish some games
    System.out.println("*** Finishing some games... ***");
    worldCupScoreBoard.finishGame("Spain", "Brazil");
    worldCupScoreBoard.finishGame("Spain", "Poland");
//    worldCupScoreBoard.finishGame(null, "Poland");
    worldCupScoreBoard.finishGame("OldBoys", "Poland");

    worldCupScoreBoard.updateScore(new Team(null, 0), new Team("Germany", 5));
  }
}
