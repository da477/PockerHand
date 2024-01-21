package com.evolution.bootcamp.poker.command;

import com.evolution.bootcamp.poker.HandRank;
import com.evolution.bootcamp.poker.Solver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FiveCardDrawSolver implements Command {

    private final static String ORDER = "23456789TJQKA"; //А = 65, B = 66, C = 67

    @Override
    public String execute(List<String> inputLine) throws Exception {
        return FiveCardDraw(inputLine);
    }

    private static String FiveCardDraw(List<String> list) {

        List<HandRank> handRanks = setStrengthsForHands(list);

        Collections.sort(handRanks);

        return handRanks.stream()
                .map(rank -> rank.getHand() + (Solver.isTest() ? "(" + rank.getStrength() + ")" : ""))
                .collect(Collectors.joining(" "));
    }

    public static List<HandRank> setStrengthsForHands(List<String> list) {
        List<HandRank> handRanks = list.stream()
                .map(h -> {
                    List<String> faces = new ArrayList<>();
                    List<String> suits = new ArrayList<>();

                    for (int i = 0; i < h.length(); i += 2) {
                        String face = String.valueOf((char) (77 - ORDER.indexOf(h.charAt(i))));
                        String suit = String.valueOf(h.charAt(i + 1));
                        faces.add(face);
                        suits.add(suit);
                    }

                    Collections.sort(faces);
                    Collections.sort(suits);

                    Map<String, Integer> duplicates = countsDoubleFaces(faces);
                    int strength = valueStrengthShirt(faces, suits, duplicates);

                    return new HandRank(h, strength, duplicates, faces);
                })
                .collect(Collectors.toList());

        changeStrengthSameRank(handRanks);
        return handRanks;
    }

    private static void changeStrengthSameRank(List<HandRank> handRanks) {
        boolean isSameRank = true;
        int iteration = 0;

        while (isSameRank && iteration <= 4) {
            final int finalIteration = iteration;
            List<HandRank> sameRankHands = handRanks.stream()
                    .filter(rank -> Collections.frequency(handRanks.stream().map(HandRank::getStrength).collect(Collectors.toList()), rank.getStrength()) > 1)
                    .sorted(Comparator.comparing(handRank -> handRank.getFaces().get(finalIteration)))
                    .collect(Collectors.toList());

            if (sameRankHands.isEmpty()) {
                isSameRank = false;
            }

            for (HandRank rank : sameRankHands) {
                List<String> faces = rank.getFaces();
                for (int countface = iteration; countface < faces.size(); countface++) {
                    String face = faces.get(countface);
                    if (!rank.getDuplicates().containsKey(face)) {
                        int ch = (77 - face.charAt(0));
                        rank.setStrength(rank.getStrength() - ch);
                        break;
                    }
                }
            }
            iteration++;
        }
    }

    private static int valueStrengthShirt(List<String> faces, List<String> suits, Map<String, Integer> duplicates) {
        int strength = 90000;

        boolean isStraight = faces.stream()
                .map(face -> face.charAt(0) - faces.get(0).charAt(0))
                .distinct()
                .count() == 1; // Check if the differences between face values are all the same

        boolean isCare = duplicates.containsValue(4);
        boolean isFullHouse = duplicates.containsValue(3) && duplicates.containsValue(2);
        boolean isThree = duplicates.containsValue(3);
        boolean isTwoPairs = duplicates.values().stream().filter(val -> val == 2).count() > 1;
        boolean isPairs = duplicates.containsValue(2);
        boolean isFlush = suits.get(0).equals(suits.get(4));

        if (isFlush && isStraight) {
            strength = 10000;
        } else if (isCare) {
            strength = 20000 - strengthBiggerCard(duplicates, 4);
        } else if (isFullHouse) {
            strength = 30000;
        } else if (isFlush) {
            strength = 40000;
        } else if (isStraight) {
            strength = 50000;
        } else if (isThree) {
            strength = 60000 - strengthBiggerCard(duplicates, 3);
        } else if (isTwoPairs) {
            strength = 70000;
        } else if (isPairs) {
            strength = 80000 - strengthBiggerCard(duplicates, 2);
        }
        return strength;
    }

    private static Map<String, Integer> countsDoubleFaces(List<String> faces) {

        return faces.stream()
                .collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum))
                .entrySet().stream()
                .filter(entries -> entries.getValue() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    private static int strengthBiggerCard(Map<String, Integer> duplicates, int ind) {
        return duplicates.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == ind)
                .map(entry -> (77 - entry.getKey().charAt(0)) * 100)
                .findFirst()
                .orElse(0);
    }

}
