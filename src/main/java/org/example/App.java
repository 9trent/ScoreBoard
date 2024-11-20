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
    worldCupScoreBoard.startGame("Germany", "France");
    worldCupScoreBoard.startGame("Uruguay", "Italy");

//    worldCupScoreBoard.startGame("Spain", "Germany");
//    worldCupScoreBoard.startGame("France", null);
//    worldCupScoreBoard.startGame("Germany", "France");
//    worldCupScoreBoard.startGame("Germany", "aaa");
//    worldCupScoreBoard.startGame("Germany", "Germany");

    // Update scores
    System.out.println("*** Updating scores... ***");
    worldCupScoreBoard.updateScore(new Team("Germany", 2), new Team("France", 2));
    worldCupScoreBoard.updateScore(new Team("Uruguay", 3), new Team("Italy", 1));
    worldCupScoreBoard.updateScore(new Team("Mexico", 0), new Team("Canada", 1));
    worldCupScoreBoard.updateScore(new Team("Spain", 10), new Team("Brazil", 2));
    worldCupScoreBoard.updateScore(new Team("Germany", 3), new Team("France", 1));
    worldCupScoreBoard.updateScore(new Team("Argentina", 2), new Team("Australia", 2));
    worldCupScoreBoard.updateScore(new Team("Mexico", 3), new Team("Canada", 1));
    worldCupScoreBoard.updateScore(new Team("Uruguay", 6), new Team("Italy", 6));
    worldCupScoreBoard.updateScore(new Team("Argentina", 3), new Team("Australia", 1));
//    worldCupScoreBoard.updateScore("Australia", "Argentina", 3, 1);
//    worldCupScoreBoard.updateScore("Mexico", "Canada", -2, 2);

    // Summary
    System.out.println("*** Getting summaries... ****");
    worldCupScoreBoard.getSummary().forEach(match ->
        System.out.println(match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName() + " - "
            + match.getHomeTeam().getScore() + " : " + match.getAwayTeam().getScore()));

    // Finish a game
    worldCupScoreBoard.finishGame("Spain", "Brazil");
    worldCupScoreBoard.finishGame("Poland", "Brazil");

    // Get summary after finishing a game
    worldCupScoreBoard.getSummary().forEach(match ->
        System.out.println(match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName() + " - "
            + match.getHomeTeam().getScore() + " : " + match.getAwayTeam().getScore()));
  }
}
