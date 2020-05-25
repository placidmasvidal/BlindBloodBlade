package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
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
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;

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

    public ScoreScreen(final BlindBloodBlade game) {

        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_BACKGROUND).stop();

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


        textStyle = new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscoresfont, null);

        for (int i = 1; i<=5; i++){
            try {
                lblScores = new Label(game.gameData.getString("score" + i).substring(0, game.gameData.getString("score" + i).length() - 1), textStyle);
                lblScores.setPosition(lblTitlePos.x + lblTitlePos.x / 3, lblTitlePos.y - lblScores.getHeight() * i);

                Image avatar = getAvatar(game.gameData.getString("score" + i).substring(game.gameData.getString("score" + i).length() - 1));
                avatar.setPosition(lblScores.getX() - avatar.getWidth() * 2, lblScores.getY());
                stage.addActor(avatar);
            } catch(Exception e){
                lblScores= new Label("0", textStyle);
                lblScores.setPosition(lblTitlePos.x + lblTitlePos.x / 3, lblTitlePos.y - lblScores.getHeight() * i);
            }
            stage.addActor(lblScores);


        }

        if(game.music) {
            SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).play();
        } else {
            SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_SCORE_SCREEN).stop();
        }

        stage.addActor(lblTitle);


    }

    private Image getAvatar(String id) {

        Image avatar = null;

        switch(id){

            case "1": avatar = new Image(Assets.instance.ninjaAssets.ninjaAvatar);
                break;
            case "2": avatar = new Image(Assets.instance.roninAssets.roninAvatar);
                break;
            case "3": avatar = new Image(Assets.instance.automataAssets.automataAvatar);
                break;

        }

        return avatar;
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

}
