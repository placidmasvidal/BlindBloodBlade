package com.xaviplacidpol.blindbloodblade;

import 	com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;

import java.util.Set;

/**
 * Main Class
 */
public class BlindBloodBlade extends Game {

	public static Preferences gameData;

	private Set<Integer> scoresSet;

	private BaseScreen baseScreen;

	private Integer score;

	private Integer playerId;

	public static boolean music = true;
	public static boolean sound = true;

	@Override
	public void create() {

//		gameData = Gdx.app.getPreferences("bbbscores");
		gameData = Gdx.app.getPreferences("bbbscoresTest3");

		baseScreen = new BaseScreen(this);

	}


	@Override
	public void dispose() {

    }

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}


	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

}
