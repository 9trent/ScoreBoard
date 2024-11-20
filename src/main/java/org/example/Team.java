package org.example;

public class Team {

  private final String name;
  private int score;

  public Team(String name, int score) {
    this.name = name;
    this.score = score;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  @Override
  public String toString() {
    return name + " " + score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Team team = (Team) o;
    return score == team.score && name.equals(team.name);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + score;
    return result;
  }
}
