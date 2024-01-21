package com.evolution.bootcamp.poker;

import com.evolution.bootcamp.poker.command.Command;
import com.evolution.bootcamp.poker.command.FiveCardDrawSolver;
import com.evolution.bootcamp.poker.command.OmahaHoldemSolver;
import com.evolution.bootcamp.poker.command.TexasHoldemSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandExecutor {
	private static final Map<GameType, Command> allKnownCommandsMap = new HashMap<>();

	static {
		allKnownCommandsMap.put(GameType.FIVECARDDRAW, new FiveCardDrawSolver());
		allKnownCommandsMap.put(GameType.TEXASHOLDEM, new TexasHoldemSolver());
		allKnownCommandsMap.put(GameType.OMAHAHOLDEM, new OmahaHoldemSolver());
	}

	private CommandExecutor() {
	}

	public static String execute(GameType gameType, List<String> inputLine) throws Exception {
		return allKnownCommandsMap.get(gameType).execute(inputLine);
	}

	public static Command getCommand(GameType operation) {
		return allKnownCommandsMap.get(operation);
	}
}
