package com.xaviplacidpol.blindbloodblade;

import 	com.badlogic.gdx.Game;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;
import com.xaviplacidpol.blindbloodblade.screens.MenuScreen;

/**
 * Main Class
 */
public class BlindBloodBlade extends Game {

	public MenuScreen menuScreen;

	@Override
	public void create() {

		menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);

	}


}
