package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
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


public class MenuScreen extends ScreenAdapter {


    private SpriteBatch batch;
    private Stage stage;


    private BlindBloodBlade game;

    private OrthographicCamera camera;
    private StretchViewport viewport;

//    public static BitmapFont bbbattackfont;

    private Image background;
    //    private Image startImg;
    private Drawable drawable;
    private ImageButton startButton;
    private ImageButton scoreButton;
    private Label.LabelStyle textStyle;
    private Label textLbl;

    public MenuScreen(BlindBloodBlade game) {

        this.game = game;

        initComponents();

        initStageContent();

        setStageContent();

        addContentToStage();


    }


    private void initComponents() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.SCREEN_W, Constants.SCREEN_H);
        viewport = new StretchViewport(Constants.SCREEN_W, Constants.SCREEN_H, camera);
        stage = new Stage(viewport);
//        Gdx.input.setInputProcessor(stage); //inputs will affect all stage actors
    }

    private void initStageContent() {
        background = new Image(Assets.instance.splashScreenAssets.backgroundRegion);
//        startImg = new Image(Assets.instance.splashScreenAssets.startButtonRegion);
        drawable = new TextureRegionDrawable(Assets.instance.splashScreenAssets.startButtonRegion);
//        startButton = new ImageButton(startImg.getDrawable());
        startButton = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(Assets.instance.splashScreenAssets.scoreButtonRegion);
        scoreButton = new ImageButton(drawable);

        textStyle = new Label.LabelStyle(Assets.instance.splashScreenAssets.bbbattackfont, null);
        textLbl = new Label(Constants.MAIN_TITLE, textStyle);
    }

    private void setStageContent() {
        background.setPosition(0, 0);
        background.setWidth(Constants.SCREEN_W);
        background.setHeight(Constants.SCREEN_H);
        background.setHeight(300);



//        textLbl.setPosition(Constants.SCREEN_W/2 - textLbl.getWidth()/2, Constants.SCREEN_H/2 - textLbl.getHeight()/2);
        textLbl.setPosition(Constants.SCREEN_W/2 - textLbl.getWidth()/2, Constants.SCREEN_H/2 - textLbl.getHeight()/2+textLbl.getHeight()*2);

        startButton.setPosition(196, 80);
        startButton.setWidth(128);
        startButton.setHeight(64);
        startButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen());
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
    }

    private void addContentToStage() {
        stage.addActor(background);


        stage.addActor(textLbl);



        stage.addActor(startButton);


        stage.addActor(scoreButton);
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
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        update(delta);




        stage.draw();

        batch.begin();

//        Assets.instance.splashScreenAssets.bbbattackfont.draw(batch, textLayout, stage.getWidth()-textLayout.width/2+128f,stage.getHeight()-textLayout.height+256f);
//        Assets.instance.splashScreenAssets.bbbattackfont.draw(batch, textLayout, stage.getWidth()-textLayout.width+128f,stage.getHeight()-textLayout.height+64f);
//        Assets.instance.splashScreenAssets.bbbattackfont.draw(batch, textLayout, stage.getWidth()-textLayout.width,stage.getHeight()-textLayout.height);
//        Assets.instance.splashScreenAssets.bbbattackfont.draw(batch, textLayout, stage.getWidth()/2f,stage.getHeight()/2f);
//        Assets.instance.splashScreenAssets.bbbattackfont.draw(batch, textLayout, stage.getWidth()/2-textLayout.width/2,stage.getHeight()/2-textLayout.height/2);
        batch.end();
    }

    private void update(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }



    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}