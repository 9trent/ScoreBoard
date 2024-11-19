package org.example.util;

import java.util.Arrays;
import java.util.List;

public class TeamUtil {
  public static List<String> getAllowedTeams() {
    return Arrays.asList("Mexico", "Canada", "Spain", "Brazil", "Germany", "France", "Uruguay", "Italy", "Argentina", "Australia");
  }

  private TeamUtil() {
  }
}
