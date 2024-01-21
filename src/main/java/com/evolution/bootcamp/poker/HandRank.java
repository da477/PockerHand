package com.evolution.bootcamp.poker;

import java.util.List;
import java.util.Map;

public class HandRank implements Comparable<HandRank> {

    private final Map<String, Integer> duplicates;
    private final String hand;
    private int strength;
    private final List<String> faces;

    public HandRank(String hand, int strength, Map<String, Integer> duplicates, List<String> faces) {
        this.duplicates = duplicates;
        this.hand = hand;
        this.strength = strength;
        this.faces = faces;
    }

    public Map<String, Integer> getDuplicates() {
        return duplicates;
    }

    public String getHand() {
        return hand;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public List<String> getFaces() {
        return faces;
    }

    @Override
    public int compareTo(HandRank other) {
        // First, compare by strength in descending order
        int strengthComparison = Integer.compare(other.strength, this.strength);
        if (strengthComparison != 0) {
            return strengthComparison;
        }
        // If strengths are equal, compare by hand string in alphabetical order
        return this.hand.compareTo(other.getHand());
    }
}
