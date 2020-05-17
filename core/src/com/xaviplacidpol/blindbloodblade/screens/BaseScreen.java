package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Screen;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.utils.Assets;

public class BaseScreen implements Screen {


    protected final BlindBloodBlade game;

    public BaseScreen(final BlindBloodBlade game){
        this.game = game;

        //Initialize the Assets instance
        com.badlogic.gdx.assets.AssetManager am = new com.badlogic.gdx.assets.AssetManager();
        Assets.instance.init(am);

        game.setScreen(new MenuScreen(this.game));
    }

    @Override
    public void show() {

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