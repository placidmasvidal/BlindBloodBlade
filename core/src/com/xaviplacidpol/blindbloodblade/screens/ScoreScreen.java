package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.profiling.GLInterceptor;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;

public class ScoreScreen extends ScreenAdapter {

    private final BlindBloodBlade game;

    public ScoreScreen(BlindBloodBlade game) {

        this.game = game;
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.6f, 0.4f, 0.4f, 1f);
        Gdx.gl.glClear(GLInterceptor.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
