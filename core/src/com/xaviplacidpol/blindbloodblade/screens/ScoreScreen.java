package com.xaviplacidpol.blindbloodblade.screens;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLInterceptor;
import com.badlogic.gdx.math.Vector2;
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

import java.awt.Menu;
import java.util.HashSet;
import java.util.Set;

public class ScoreScreen extends ScreenAdapter {

    private BlindBloodBlade game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;

    private Image background;
    private Drawable drawable;

    private ImageButton backButton;

    private Label.LabelStyle textStyle;
    private Label lblTitle;
    private Vector2 lblTitlePos;

    private Label lblScores;

    private Set<Integer> scoresSet;

    public ScoreScreen(final BlindBloodBlade game) {

        this.game = game;

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.SCREEN_W, Constants.SCREEN_H);
        viewport = new StretchViewport(Constants.SCREEN_W, Constants.SCREEN_H, camera);
        stage = new Stage(viewport);

        background = new Image(Assets.instance.splashScreenAssets.backgroundRegion);
        background.setPosition(0, 0);
        background.setWidth(Constants.SCREEN_W);
        background.setHeight(Constants.SCREEN_H);
        background.setHeight(300);
        stage.addActor(background);

        drawable = new TextureRegionDrawable(Assets.instance.scoreScreenAssets.backButtonRegion);
        backButton = new ImageButton(drawable);
        backButton.setPosition(440, 40);
        backButton.setWidth(40);
        backButton.setHeight(40);
        backButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        stage.addActor(backButton);

        textStyle = new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorefont, null);
        lblTitle = new Label(Constants.SCORE, textStyle);
        lblTitlePos = new Vector2(Constants.SCREEN_W/2 - lblTitle.getWidth()/2, Constants.SCREEN_H/2 - lblTitle.getHeight()/2+ lblTitle.getHeight()+ lblTitle.getHeight()/2);
        lblTitle.setPosition(lblTitlePos.x, lblTitlePos.y);

        scoresSet = new HashSet<>();
        scoresSet.add(19283);
        scoresSet.add(17382);
        scoresSet.add(19281);
        scoresSet.add(21910);
        scoresSet.add(26738);

        int i = 1;
        for(Integer score : scoresSet){
            game.gameData.putInteger("score"+i, score);
            i++;
        }

        textStyle = new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscoresfont, null);
        int j = 1;
        for(Integer score : scoresSet){
            lblScores = new Label(Integer.toString(game.gameData.getInteger("score"+j)), textStyle);
//            lblScores.setPosition(lblTitlePos.x, lblTitlePos.y-lblTitle.getHeight()*(j));
            lblScores.setPosition(lblTitlePos.x+lblTitlePos.x/3, lblTitlePos.y-lblScores.getHeight()*j);
            stage.addActor(lblScores);
            j++;
        }

        stage.addActor(lblTitle);


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
        super.dispose();
    }

}