package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.screens.BaseScreen;

public class Scene2DScreen extends BaseScreen {

    private Stage stage;

    public Scene2DScreen(BlindBloodBlade game) {
        super(game);
    }


    @Override
    public void show() {
//        super.show();

        stage = new Stage();
    }
}
