package com.evolution.bootcamp.poker;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class OmahaHoldemSolverTest {

  @Test
  public void testOh5c6dAcAsQs() {
    assertEquals(
      "8d7cTcJd 6h7d2cJc Qd3cKs4c Kh4hKc7h KdJs2hAh 5s5d7s4d TsQh9hQc",
      Solver.process("omaha-holdem 5c6dAcAsQs TsQh9hQc 8d7cTcJd 5s5d7s4d Qd3cKs4c KdJs2hAh Kh4hKc7h 6h7d2cJc"));
  }

  @Test
  public void testOh3d4s5dJsQd() {
    assertEquals(
      "9h7h2dTc 7cThKs5s 7sJdKcAs 8d9s5c4h 5hJh2s7d Qh8cTsJc 8s2h6s8h",
      Solver.process("omaha-holdem 3d4s5dJsQd 8s2h6s8h 7cThKs5s 5hJh2s7d 8d9s5c4h 7sJdKcAs 9h7h2dTc Qh8cTsJc"));
  }

  @Test
  public void testOh3d3s4d6hJc() {
    assertEquals(
      "Qc8dAd6c KsAsTcTs Js2dKd8c 7dQsAc5d Jh2h3c9c",
      Solver.process("omaha-holdem 3d3s4d6hJc Js2dKd8c KsAsTcTs Jh2h3c9c Qc8dAd6c 7dQsAc5d"));
  }

}
