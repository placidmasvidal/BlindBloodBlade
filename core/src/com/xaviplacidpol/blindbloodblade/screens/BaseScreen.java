package com.jumpdontdie;

import com.badlogic.gdx.Screen;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;

public abstract class BaseScreen implements Screen {

    protected BlindBloodBlade game;

    public BaseScreen(BlindBloodBlade game){
        this.game = game;
    }
    @Override
    public void show() {
        //game
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
