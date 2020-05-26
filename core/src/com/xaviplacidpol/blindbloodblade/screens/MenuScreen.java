package com.xaviplacidpol.blindbloodblade.screens;

import   com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;


public class MenuScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Stage stage;
    private BlindBloodBlade game;
    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Image background;
    private Drawable drawable;
    private ImageButton startButton;
    private ImageButton scoreButton;
    private ImageButton setupButton;
    private Label.LabelStyle textStyle;
    private Label textLbl;

    public MenuScreen(BlindBloodBlade game) {

        this.game = game;

        initComponents();

        initStageContent();

        setStageContent();

        addContentToStage();

        loadSound(game);

    }

    /**
     * Plays or stops game sound as user indicated in setup screen
     * @param game main class of the app that stores the sound map
     * to let acces from any package
     */
    private void loadSound(BlindBloodBlade game) {
        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).stop();

        if(game.music) {
            SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_BACKGROUND).play();
        } else {
            SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_BACKGROUND).stop();
        }
    }

    /**
     * Initializes those components used to show screen content
     */
    private void initComponents() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.SCREEN_W, Constants.SCREEN_H);
        viewport = new StretchViewport(Constants.SCREEN_W, Constants.SCREEN_H, camera);
        stage = new Stage(viewport);
    }

    /**
     * Initializs the content to shown
     */
    private void initStageContent() {
        background = new Image(Assets.instance.splashScreenAssets.backgroundRegion);
        drawable = new TextureRegionDrawable(Assets.instance.splashScreenAssets.startButtonRegion);
        startButton = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(Assets.instance.splashScreenAssets.scoreButtonRegion);
        scoreButton = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(Assets.instance.splashScreenAssets.setupButtonRegion);
        setupButton = new ImageButton(drawable);

        textStyle = new Label.LabelStyle(Assets.instance.splashScreenAssets.bbbattackfont, null);
        textLbl = new Label(Constants.MAIN_TITLE, textStyle);
    }

    /**
     * Set attributes of the elements to shown
     */
    private void setStageContent() {
        background.setPosition(0, 0);
        background.setWidth(Constants.SCREEN_W);
        background.setHeight(Constants.SCREEN_H);
        background.setHeight(300);

        textLbl.setPosition(Constants.SCREEN_W/2 - textLbl.getWidth()/2, Constants.SCREEN_H/2 - textLbl.getHeight()/2+textLbl.getHeight()*2);

        startButton.setPosition(196, 80);
        startButton.setWidth(128);
        startButton.setHeight(64);
        startButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));

                if(game.sound) {
                    SoundAssetsManager.bbbsounds.get("swordslash").play();
                }
            }
        });

        scoreButton.setPosition(196, startButton.getY()-startButton.getHeight()/2 - 32f);
        scoreButton.setWidth(128);
        scoreButton.setHeight(64);
        scoreButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ScoreScreen(game));
            }
        });

        setupButton.setPosition(420, 20);
        setupButton.setWidth(64);
        setupButton.setHeight(64);
        setupButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SetupScreen newSetupScreen = new SetupScreen(game);
                game.setScreen(newSetupScreen);
                if(!game.music){
                    newSetupScreen.getOnButton().setVisible(false);
                    newSetupScreen.getOffButton().setVisible(true);
                }
                if(!game.sound){
                    newSetupScreen.getOnButton2().setVisible(false);
                    newSetupScreen.getOffButton2().setVisible(true);
                }
            }
        });

    }

    /**
     * Add Actor elements to the stage to draw all at a time
     */
    private void addContentToStage() {
        stage.addActor(background);
        stage.addActor(textLbl);
        stage.addActor(startButton);
        stage.addActor(scoreButton);
        stage.addActor(setupButton);
    }

    /**
     * Show method inherited from ScreenAdapter is called every time the screen get the focus
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); //inputs will affect all stage actors
    }

    /**
     * Show method inherited from ScreenAdapter is called every time the screen loses the focus
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(stage); //inputs will affect all stage actors
    }

    /**
     * Show method inherited from ScreenAdapter draw elements on the screen at every frame
     * given by delta time
     */
    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    /**
     * free resources
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
