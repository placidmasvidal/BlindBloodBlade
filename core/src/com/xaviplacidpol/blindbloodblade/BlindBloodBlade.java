package com.xaviplacidpol.blindbloodblade;

import 	com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;
import com.xaviplacidpol.blindbloodblade.screens.GameScreen;
import com.xaviplacidpol.blindbloodblade.screens.MenuScreen;

/**
 * Main Class
 */
public class BlindBloodBlade extends Game {

	public static Preferences gameData;

	private BaseScreen baseScreen;

	@Override
	public void create() {

		gameData = Gdx.app.getPreferences("bbbdata");
		baseScreen = new BaseScreen(this);

	}

}
