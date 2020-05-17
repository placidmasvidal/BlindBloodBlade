package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xaviplacidpol.blindbloodblade.scenes.Level;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Cam;
import com.xaviplacidpol.blindbloodblade.utils.Constants;


public class GameScreen extends ScreenAdapter {

//    private BlindBloodBlade game;
//
//    public GameScreen(BlindBloodBlade game) {
//        super(game);
//
//        this.game = game;
//    }
//
//    @Override
//    public void show() {
//        super.show();
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0,0,1,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//    }
//
//    @Override
//    public void pause() {
//        super.pause();
//    }
//
//    @Override
//    public void resume() {
//        super.resume();
//    }
//
//    @Override
//    public void hide() {
//        super.hide();
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//
//    }

    public static final String TAG = GameScreen.class.getName();

    // Add a level
    Level level;

    // Add a SpriteBatch
    SpriteBatch batch;

    // Add an ExtendViewport
    ExtendViewport viewport;

    // Add the Cam
    Cam cam;


    @Override
    public void show() {
        //Initialize the Assets instance
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        // Initialize the cam
        cam = new Cam();

        // Initialize the SpriteBatch
        batch = new SpriteBatch();

        // Initialize the viewport
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        // Configure the cam
        setCam();
    }

    /**
     * resize window
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        //  Update the viewport
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose of the Assets instance
        Assets.instance.dispose();

    }

    @Override
    public void render(float delta) {
        // Level update
        level.update(delta);
        // Apply the cam
        cam.update(delta);
        // Apply the viewport
        viewport.apply();

        // Clear the screen to the BACKGROUND_COLOR
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the SpriteBatch's projection matrix
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Render the level
        level.render(batch);

    }

    /**
     * Configure the cam
     */
    private void setCam() {
        level = new Level(viewport);
        cam.camera = level.viewport.getCamera();
        cam.target = level.getNinjaPlayer();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


}