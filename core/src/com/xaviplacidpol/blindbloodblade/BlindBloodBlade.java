package com.xaviplacidpol.blindbloodblade;

import 	com.badlogic.gdx.Game;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;
import com.xaviplacidpol.blindbloodblade.screens.GameScreen;
import com.xaviplacidpol.blindbloodblade.screens.MenuScreen;

/**
 * Main Class
 */
public class BlindBloodBlade extends Game {

	@Override
	public void create() {


//		menuScreen = new MenuScreen(this);
//		this.setScreen(menuScreen);

		setScreen(new GameScreen());


	}

}
