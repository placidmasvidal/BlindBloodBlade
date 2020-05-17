package com.xaviplacidpol.blindbloodblade;

import 	com.badlogic.gdx.Game;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;
import com.xaviplacidpol.blindbloodblade.screens.GameScreen;
import com.xaviplacidpol.blindbloodblade.screens.MenuScreen;

/**
 * Main Class
 */
public class BlindBloodBlade extends Game {

	private BaseScreen baseScreen;

	@Override
	public void create() {

		baseScreen = new BaseScreen(this);

	}

}
