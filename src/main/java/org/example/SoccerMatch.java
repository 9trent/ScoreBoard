package org.example;

import org.example.api.Match;

public class SoccerMatch implements Match {

  private Team homeTeam;
  private Team awayTeam;

  public SoccerMatch(Team homeTeam, Team awayTeam) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
  }

  @Override
  public Team getHomeTeam() {
    return homeTeam;
  }

  @Override
  public Team getAwayTeam() {
    return awayTeam;
  }
}
