package com.evolution.bootcamp.poker;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FiveCardDrawSolverTest {

    @Test
    public void test5cd4s5hTsQh9h() {
//    Description: sorting various poker hands including
//    a high card, straight, two pair, and full house combinations.
        assertEquals(
                "4s5hTsQh9h Qc8d7cTcJd 5s5d7s4dQd 7h6h7d2cJc 3cKs4cKdJs 2hAhKh4hKc As6d5cQsAc",
                Solver.process("five-card-draw 4s5hTsQh9h Qc8d7cTcJd 5s5d7s4dQd 3cKs4cKdJs 2hAhKh4hKc 7h6h7d2cJc As6d5cQsAc"));
    }

    @Test
    public void test5cd7h4s4h8c9h() {
//    4c8h2h6c9c: This is a high card hand, with the highest card being Nine.
//    Ah9d6s2cKh: This is a high card hand, with the highest card being Ace.
//    Kd9sAs3cQs: This is a high card hand, with the highest card being Ace.
//    7h4s4h8c9h: This is a one pair hand, with the pair consisting of two fours.
//    Tc5h6dAc5c: This is another one pair hand, with the pair consisting of two fives.
        assertEquals(
                "4c8h2h6c9c Ah9d6s2cKh Kd9sAs3cQs 7h4s4h8c9h Tc5h6dAc5c",
                Solver.process("five-card-draw 7h4s4h8c9h Tc5h6dAc5c Kd9sAs3cQs Ah9d6s2cKh 4c8h2h6c9c"));
    }

    @Test
    public void test5cd5s3s4c2h9d() {
//    5s3s4c2h9d: This is a high card hand, with the highest card being Nine.
//    4h6s8hJd5d: This is a high card hand, with the highest card being Jack.
//    5c3cQdTd9s: This is a high card hand, with the highest card being Queen.
//    8dKsTc6c2c: This is a high card hand, with the highest card being King.
//    8c3d7h7dTs: This is a one pair hand, with the pair consisting of two Sevens.
//    KhJs9c5h9h: This is a one pair hand, with the pair consisting of two Nines.
//    AhQhKcQc2d: This is a one pair hand, with the pair consisting of two Queens.
        assertEquals(
                "5s3s4c2h9d 4h6s8hJd5d 5c3cQdTd9s 8dKsTc6c2c 8c3d7h7dTs KhJs9c5h9h AhQhKcQc2d",
                Solver.process("five-card-draw 5s3s4c2h9d 4h6s8hJd5d 8dKsTc6c2c 5c3cQdTd9s AhQhKcQc2d KhJs9c5h9h 8c3d7h7dTs"));
    }

    @Test
    public void testDifferentFlushes() {
        // Test for different flushes to ensure they are correctly identified
        assertEquals(
                "2h4h5h7h8h 9cTcJcQcKc",
                Solver.process("five-card-draw 9cTcJcQcKc 2h4h5h7h8h"));
    }

    @Test
    public void testFlushVsStraight() {
        // Test for flush versus straight
        assertEquals(
                "3s4s5s6s7s 6h7h8h9hTh",
                Solver.process("five-card-draw 3s4s5s6s7s 6h7h8h9hTh"));
    }

    @Test
    public void testIdenticalFlushStrength() {
        // Test for flushes with identical cards in different suits
        // 2d3d5d6d9d (diamonds)
        // 2h3h5h6h9h (hearts)
        // 2s3s5s6s9s (spades)
        assertEquals(
                "2d3d5d6d9d 2h3h5h6h9h 2s3s5s6s9s",
                Solver.process("five-card-draw 2h3h5h6h9h 2s3s5s6s9s 2d3d5d6d9d"));
    }

    @Test
    public void testComplexOrdering() {
        // Test for complex hand ordering
//    4d5d6d7d8d: a straight flush in diamonds
//    4h5h6h7h8h: a straight flush in hearts
//    4s5s6s7s8s: a straight flush in spades
        assertEquals(
                "4d5d6d7d8d 4h5h6h7h8h 4s5s6s7s8s", //expected
                Solver.process("five-card-draw 4s5s6s7s8s 4h5h6h7h8h 4d5d6d7d8d"));
    }

}