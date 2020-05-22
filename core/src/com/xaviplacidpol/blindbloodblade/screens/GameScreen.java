package com.xaviplacidpol.blindbloodblade.screens;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.overlays.BloodSplashOverlay;
import com.xaviplacidpol.blindbloodblade.scenes.Level;
import com.xaviplacidpol.blindbloodblade.scenes.StatsHud;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Cam;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SetupValues;
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

    // Blood Splash Overlay
    BloodSplashOverlay bloodSplashOverlay;

    public GameScreen(BlindBloodBlade game){
        this.game = game;

        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_BACKGROUND).stop();

        if(SetupValues.music) {
            SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_LEVEL_FAST).play();
        } else {
            SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_LEVEL_FAST).stop();
        }


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

        // Initialize the BloodSplashOverlay witch will print the fixed blood splashes
//        bloodSplashOverlay = new BloodSplashOverlay();
        bloodSplashOverlay = new BloodSplashOverlay(level);
        bloodSplashOverlay.init();


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
        // Update the bloodSplashOverlay viewport
        bloodSplashOverlay.viewport.update(width, height, true);

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
        batch.begin();
        // Render the level
        level.render(batch);

        // IMPORTANT!! Render the blood splash overlay after level rendering, and before statsHud
        bloodSplashOverlay.render(batch);

        batch.end();
        statsHud.render();



        //Apply endless game
        restartLevel();


    }

    /**
     * Apply endless Game
     * Checks if level ended, if true, then return the ninja to the start point, and set
     * levelEnd to false again, otherwise return with no result
     */
    private void restartLevel() {
        if(level.levelEnd){
            //Repositioning ninja player to the start point
            level.getNinjaPlayer().setPosition(new Vector2(200, Constants.PLAYER_EYE_HEIGHT + 40));
//            cam.camera = level.viewport.getCamera();
//            cam.target = level.getNinjaPlayer();

            //Repositioning camera
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            //Level restarted, then set levelEnd to false
            level.levelEnd = false;
        }
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
