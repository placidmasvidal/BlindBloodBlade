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
//	private MenuScreen menuScreen;

	@Override
	public void create() {

		baseScreen = new BaseScreen(this);
//		menuScreen = new MenuScreen(BaseScreen.game);
//		this.setScreen(menuScreen);

//		setScreen(new GameScreen());

	}

}
