package com.evolution.bootcamp.poker;

public enum GameType {
	FIVECARDDRAW("five-card-draw"),
	TEXASHOLDEM("texas-holdem"),
	OMAHAHOLDEM("omaha-holdem");

	private String title;

	GameType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
