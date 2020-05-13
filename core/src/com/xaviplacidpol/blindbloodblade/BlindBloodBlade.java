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
<<<<<<< HEAD

//		menuScreen = new MenuScreen(this);
//		this.setScreen(menuScreen);

		setScreen(new GameScreen());

=======
		setScreen(new MenuScreen());
>>>>>>> 44a22a2e769b1ebd3fb36ebdbfc0c2b6ce8eb154
	}

}
