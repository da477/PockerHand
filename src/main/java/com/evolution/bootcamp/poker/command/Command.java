package com.evolution.bootcamp.poker.command;

import java.util.List;

public interface Command {
	String execute(List<String> inputLine) throws Exception;
}
