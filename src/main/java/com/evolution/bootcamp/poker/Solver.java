package com.evolution.bootcamp.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {

    private static boolean isTest = false;

    public static boolean isTest() {
        return isTest;
    }

    public static void main(String[] args) {

        String[] workingArgs = args;
        if (workingArgs == null || workingArgs.length == 0) {
            String inputline = "five-card-draw 4s5hTsQh9h Qc8d7cTcJd 5s5d7s4dQd 3cKs4cKdJs 2hAhKh4hKc 7h6h7d2cJc As6d5cQsAc";
            System.out.format("args is empty line, add test input line:\n%s\n", inputline);
            isTest = true;
            workingArgs = new String[1];
            workingArgs[0] = inputline;
        }

        for (int i = 0; i < workingArgs.length; i++) {
            String answerCommand = process(workingArgs[i]);
            System.out.println(answerCommand);
        }
    }

    public static String process(String inputLine) {

        String[] inputCommand = inputLine.split(" ");
        String gameName = inputCommand[0]; //;

        List<String> list = new ArrayList<>(Arrays.asList(inputCommand));
        list.remove(0);

        String answerCommand = "";
        for (GameType gameType : GameType.values()) {
            if (gameType.getTitle().equalsIgnoreCase(gameName)) {
                try {
                    answerCommand = CommandExecutor.execute(gameType, list);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
        return answerCommand;
    }

}
