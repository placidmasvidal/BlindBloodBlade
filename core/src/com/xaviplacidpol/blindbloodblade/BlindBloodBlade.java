package com.xaviplacidpol.blindbloodblade;

import com.badlogic.gdx.Game;
import com.xaviplacidpol.blindbloodblade.screens.MenuScreen;

/**
 * Main Class
 */
public class BlindBloodBlade extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
