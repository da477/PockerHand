package com.evolution.bootcamp.poker;

import com.evolution.bootcamp.poker.Solver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TexasHoldemSolverTest {

  @Test
  public void testTh5c6dAcAsQs() {
    assertEquals(
      "2cJc Kh4h=Ks4c Kc7h KdJs 6h7d 2hAh",
      Solver.process("texas-holdem 5c6dAcAsQs Ks4c KdJs 2hAh Kh4h Kc7h 6h7d 2cJc"));
  }

  @Test
  public void testTh2h5c8sAsKc() {
    assertEquals(
      "Jc6s Qs9h 3cKh KdQh",
      Solver.process("texas-holdem 2h5c8sAsKc Qs9h KdQh 3cKh Jc6s"));
  }

  @Test
  public void testTh3d4s5dJsQd() {
    assertEquals(
      "9h7h 2dTc KcAs 7sJd TsJc Qh8c 5c4h",
      Solver.process("texas-holdem 3d4s5dJsQd 5c4h 7sJd KcAs 9h7h 2dTc Qh8c TsJc"));
  }

}
