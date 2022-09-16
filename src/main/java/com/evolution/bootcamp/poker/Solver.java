package com.evolution.bootcamp.poker;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solver {

    private static boolean isTest = false;
    private final static String ORDER = "23456789TJQKA"; //А = 65, B = 66, C = 67 
    private final static String typePlay_5Draw = "five-card-draw";

    public static class HandRank {

        public Map<String, Integer> duplicates;
        public String hand;
        public int strength;
        public ArrayList<String> faces;

        public HandRank(String hand, int strength, Map<String, Integer> duplicates, ArrayList<String> faces) {
            this.duplicates = duplicates;
            this.hand = hand;
            this.strength = strength;
            this.faces = faces;
        }
    }

    public static void main(String[] args) {
        isTest = true;
        String inputline = "five-card-draw 4s5hTsQh9h Qc8d7cTcJd 5s5d7s4dQd 3cKs4cKdJs 2hAhKh4hKc 7h6h7d2cJc As6d5cQsAc";
        System.out.println((isTest? inputline + "\n" : "") 
                                + process(inputline));
    }

    static String process(String line) {
        
        if (line == null || line.isEmpty()) {
            return "Empty line...";
        }

        String[] words = line.split(" ");

        if (words[0].equals(typePlay_5Draw)) {
            return FiveCardDraw(words);
        } else {
            return "Haven't implemented yet...";
        }

    }

    private static String FiveCardDraw(String[] hands) {

        HashMap<HandRank, Integer> maphands = setStrengthsForHands(hands);
        
        return maphands.entrySet()
                .stream() //convert collections
                .collect(Collectors.toMap(
                        e -> e.getKey().hand,
                        e -> e.getValue()))
                .entrySet().stream() //sort Order
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new))
                .entrySet().stream() // Format 2 String
                .map(e -> e.getKey() + (isTest ? "(" + e.getValue().toString() + ")" : ""))
                .collect(Collectors.joining(" "));

    }

    public static HashMap<HandRank, Integer> setStrengthsForHands(String[] hands) {

        HashMap<HandRank, Integer> maphands = new HashMap<>();

        List<String> list = new ArrayList<>(Arrays.asList(hands));
        list.removeIf(n -> n.equals(typePlay_5Draw));

        list.stream()
                .forEach(h -> {

                    char[] mchar = h.toCharArray();
                    ArrayList<String> faces = new ArrayList<>();
                    ArrayList<String> suits = new ArrayList<>();

                    for (int i = 0; i < mchar.length; i++) {
                        if (i % 2 == 0) {
                            String face = String.valueOf((char) (77 - ORDER.indexOf(mchar[i])));
                            faces.add(face);
                        } else {
                            String suit = String.valueOf(mchar[i]);
                            suits.add(suit);
                        }
                    }
                    Collections.sort(faces);
                    Collections.sort(suits);

                    Map<String, Integer> duplicates = countsDoubleFaces(faces);

                    int strength = valueStrengthShirt(faces, suits, duplicates);

                    maphands.put(new HandRank(h, strength, duplicates, faces), strength);

                });

        changeStrengthSameRank(maphands);

        return maphands;
    }

    private static void changeStrengthSameRank(HashMap<HandRank, Integer> maphands) {

        boolean isSameRank = true;
        int iteration = 0;

        while (isSameRank || iteration > 4) {

            HashMap<HandRank, Integer> values = maphands.entrySet()
                    .stream()
                    .filter(e -> {
                        //System.out.format("Filter: %s\n", e.getKey().hand.toString() + "=" + e.getValue().toString());
                        return Collections.frequency(maphands.values(), e.getValue()) > 1;
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (a, b) -> a,
                            HashMap::new));

            if (values.isEmpty()) {
                isSameRank = false;
            }

            for (Map.Entry<HandRank, Integer> entry : values.entrySet()) {
                for (Map.Entry<HandRank, Integer> entmap : maphands.entrySet()) {
                    if (entmap.getValue().equals(entry.getValue())) {
                        int countface = iteration;
                        while (countface < entmap.getKey().faces.size()) {
                            String face = entmap.getKey().faces.get(countface);
                            if (entmap.getKey().duplicates.containsKey(face)) {
                                countface++;
                                continue;
                            }
                            int ch = (77 - face.charAt(0));
                            entmap.setValue(entmap.getValue() - ch);
                            break;
                        }
                    }
                }
            }
            iteration++;

        }
    }

    private static int valueStrengthShirt(ArrayList<String> faces, ArrayList<String> suits, Map<String, Integer> duplicates) {

        int strength = 90000;

        boolean isStraight
                = faces.stream()
                        .filter(e
                                -> e.charAt(0) - faces.get(0).charAt(0) != faces.indexOf(e)
                        )
                        .findAny().isEmpty();

        boolean isCare = duplicates.entrySet().stream().anyMatch(entry -> entry.getValue().equals(4));

        boolean isFullHouse = duplicates.entrySet().stream().anyMatch(entry -> entry.getValue().equals(3))
                && duplicates.entrySet().stream().anyMatch(entry -> entry.getValue().equals(2));

        boolean isThree = duplicates.entrySet().stream().anyMatch(entry -> entry.getValue().equals(3));
        boolean isTwoPairs = duplicates.entrySet().stream().filter(entry -> entry.getValue().equals(2)).count() > 1;
        boolean isPairs = duplicates.entrySet().stream().anyMatch(entry -> entry.getValue().equals(2));
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

    private static Map<String, Integer> countsDoubleFaces(ArrayList<String> faces) {

        Map<String, Integer> duplicates = faces.stream()
                .collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum));

        duplicates.entrySet().removeIf(entries -> entries.getValue() < 2);

        return duplicates;

    }

    private static int strengthBiggerCard(Map<String, Integer> duplicates, int ind) {

        return duplicates.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(ind))
                .map(entry -> (77 - entry.getKey().charAt(0)) * 100)
                .findFirst()
                .orElse(0);

    }

}
