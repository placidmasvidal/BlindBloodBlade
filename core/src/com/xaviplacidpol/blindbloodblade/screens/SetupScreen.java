package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLInterceptor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;

public class SetupScreen extends ScreenAdapter {

    private BlindBloodBlade game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;
    private Image background;
    private Image music;
    private Image sound;
    private Image controlsB;
    private Image controls;
    private ImageButton onButton;
    private ImageButton offButton;
    private ImageButton onButton2;
    private ImageButton offButton2;
    private ImageButton backButton;
    private Drawable drawable;

    public SetupScreen(BlindBloodBlade game){

        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_BACKGROUND).stop();

        this.game = game;

        initComponents();

        initStageContent();

        setStageContent();

        addContentToStage();

        if(game.music) SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).play();
        else SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).stop();
    }

    private void initComponents() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.SCREEN_W, Constants.SCREEN_H);
        viewport = new StretchViewport(Constants.SCREEN_W, Constants.SCREEN_H, camera);
        stage = new Stage(viewport);

    }

    private void initStageContent() {

        background = new Image(Assets.instance.splashScreenAssets.backgroundRegion);
        music = new Image(Assets.instance.setupScreenAssets.musicButtonRegion);
        sound = new Image(Assets.instance.setupScreenAssets.soundButtonRegion);
        controlsB = new Image(Assets.instance.setupScreenAssets.controlsButtonRegion);
        controls = new Image(Assets.instance.setupScreenAssets.controlsRegion);

        drawable = new TextureRegionDrawable(Assets.instance.setupScreenAssets.offButtonRegion);
        offButton = new ImageButton(drawable);
        offButton2 = new ImageButton(drawable);

        drawable = new TextureRegionDrawable(Assets.instance.setupScreenAssets.onButtonRegion);
        onButton = new ImageButton(drawable);
        onButton2 = new ImageButton(drawable);

        drawable = new TextureRegionDrawable(Assets.instance.scoreScreenAssets.backButtonRegion);
        backButton = new ImageButton(drawable);

    }

    private void setStageContent() {

        background.setPosition(0, 0);
        background.setWidth(Constants.SCREEN_W);
        background.setHeight(300);

        music.setPosition(64, 200);
        music.setWidth(128);
        music.setHeight(64);

        sound.setPosition(64, 112);
        sound.setWidth(128);
        sound.setHeight(64);

        controlsB.setPosition(64, 20);
        controlsB.setWidth(128);
        controlsB.setHeight(64);

        controls.setPosition(238, 20);
        controls.setWidth(210);
        controls.setHeight(82);

        offButton.setPosition(232, 216);
        offButton.setWidth(64);
        offButton.setHeight(32);
        offButton.setVisible(false);
        offButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                offButton.setVisible(false);
                onButton.setVisible(true);
                game.music = true;
                SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).play();
            }
        });

        offButton2.setPosition(232, 130);
        offButton2.setWidth(64);
        offButton2.setHeight(32);
        offButton2.setVisible(false);
        offButton2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                offButton2.setVisible(false);
                onButton2.setVisible(true);
                game.sound = true;

            }
        });

        onButton.setPosition(232, 216);
        onButton.setWidth(64);
        onButton.setHeight(32);
//        if(offButton.isVisible()) onButton.setVisible(false);
        onButton.setVisible(true);
        onButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onButton.setVisible(false);
                offButton.setVisible(true);
                game.music = false;
                SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).stop();

            }
        });

        onButton2.setPosition(232, 130);
        onButton2.setWidth(64);
        onButton2.setHeight(32);
        onButton2.setVisible(true);
        onButton2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                offButton2.setVisible(true);
                onButton2.setVisible(false);
                game.sound = false;

            }
        });

        backButton.setPosition(430, 210);
        backButton.setWidth(40);
        backButton.setHeight(40);
        backButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new MenuScreen(game));

            }
        });

    }

    private void addContentToStage() {

        stage.addActor(background);
        stage.addActor(music);
        stage.addActor(sound);
        stage.addActor(controlsB);
        stage.addActor(controls);
        stage.addActor(offButton);
        stage.addActor(onButton);
        stage.addActor(offButton2);
        stage.addActor(onButton2);
        stage.addActor(backButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); //inputs will affect all stage actors
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(stage); //inputs will affect all stage actors
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0.6f, 0.7f, 1f);
        Gdx.gl.glClear(GLInterceptor.GL_COLOR_BUFFER_BIT);

        stage.draw();

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }


    //GETTERS AND SETTERS
    public ImageButton getOnButton() {
        return onButton;
    }

    public ImageButton getOffButton() {
        return offButton;
    }

    public ImageButton getOnButton2() {
        return onButton2;
    }

    public ImageButton getOffButton2() {
        return offButton2;
    }

}
