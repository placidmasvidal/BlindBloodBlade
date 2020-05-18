package com.xaviplacidpol.blindbloodblade;

import 	com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;
import com.xaviplacidpol.blindbloodblade.screens.GameScreen;
import com.xaviplacidpol.blindbloodblade.screens.MenuScreen;

import java.util.HashSet;
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

		gameData = Gdx.app.getPreferences("bbbdata");
		scoresSet = new HashSet<>();
		scoresSet.add(19282);
		scoresSet.add(17384);
		scoresSet.add(19280);
		scoresSet.add(21919);
		scoresSet.add(26738);

		baseScreen = new BaseScreen(this);

	}


	@Override
	public void dispose() {
        int i = 1;
        for(Integer score : scoresSet){
			gameData.putInteger("score"+i, score);
            i++;
        }
			gameData.flush();
    }

}
