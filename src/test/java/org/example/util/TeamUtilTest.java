package org.example.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamUtilTest {

  @Test
  void shouldReturnAllowedTeams_whenInvoked() {

    // given
    List<String> expectedTeams = List.of("Mexico", "Canada", "Spain", "Brazil", "Germany", "France", "Uruguay", "Italy", "Argentina", "Australia");

    // when
    List<String> actualTeams = TeamUtil.getAllowedTeams();

    // then
    assertEquals(expectedTeams, actualTeams);
  }
}