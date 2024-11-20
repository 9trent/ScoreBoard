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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SoccerMatch that = (SoccerMatch) o;
    return homeTeam.equals(that.homeTeam) && awayTeam.equals(that.awayTeam);
  }

  @Override
  public int hashCode() {
    int result = homeTeam.hashCode();
    result = 31 * result + awayTeam.hashCode();
    return result;
  }
}
