package com.xaviplacidpol.blindbloodblade.screens;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.scenes.Level;
import com.xaviplacidpol.blindbloodblade.scenes.StatsHud;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Cam;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;


public class GameScreen extends ScreenAdapter {

    public static final String TAG = GameScreen.class.getName();

    protected BlindBloodBlade game;

    // Add a level
    Level level;

    // Add a SpriteBatch
    SpriteBatch batch;

    //Add a Hud
    StatsHud statsHud;

    // Add an ExtendViewport
    ExtendViewport viewport;

    // Add the Cam
    Cam cam;

    public GameScreen(BlindBloodBlade game){
        this.game = game;
//        SoundAssetsManager.bbbmusics.get("fastlevel").play();
        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_BACKGROUND).stop();
        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_LEVEL_FAST).play();
//        Assets.instance.soundAssets.sakuraAmbienceStage.play();
//        Assets.instance.soundAssets.superFastLevel.play();
//        Assets.instance.soundAssets.thrillerStage.play();
    }

    @Override
    public void show() {
        //Initialize the Assets instance
 //       AssetManager am = new AssetManager();
 //       Assets.instance.init(am);

        // Initialize the viewport
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        // Initialize the cam
        cam = new Cam();

        // Initialize Level
        level = new Level(viewport, game);

        // Initialize the SpriteBatch
        batch = new SpriteBatch();

        statsHud = new StatsHud(batch, level.getNinjaPlayer());

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
        statsHud.update(delta);
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
        statsHud.render();

    }

    /**
     * Configure the cam
     */
    private void setCam() {
        cam.camera = level.viewport.getCamera();
        cam.target = level.getNinjaPlayer();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


}