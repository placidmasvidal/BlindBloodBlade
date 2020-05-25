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

	@Override
	public void create() {

		gameData = Gdx.app.getPreferences("bbbdata3");

		baseScreen = new BaseScreen(this);

	}


	@Override
	public void dispose() {

    }

}
