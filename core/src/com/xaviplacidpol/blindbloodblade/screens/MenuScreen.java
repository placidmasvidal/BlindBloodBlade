package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    public Image background;

    private GlyphLayout textLayout;

    public MenuScreen(BlindBloodBlade game) {

        this.game = game;

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.SCREEN_W, Constants.SCREEN_H);
        viewport = new StretchViewport(Constants.SCREEN_W, Constants.SCREEN_H, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); //inputs will affect all stage actors

        background = new Image(Assets.instance.splashScreenAssets.backgroundRegion);
/*
        textLayout = new GlyphLayout();
        textLayout.setText(Assets.instance.splashScreenAssets.bbbattackfont, Constants.MAIN_TITLE);
*/

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        update(delta);

        background.setPosition(0, 0);
        background.setWidth(Constants.SCREEN_W);
        background.setHeight(Constants.SCREEN_H);
        background.setHeight(300);
        stage.addActor(background);

        Label.LabelStyle textStyle = new Label.LabelStyle(Assets.instance.splashScreenAssets.bbbattackfont, null);
        Label textLbl = new Label(Constants.MAIN_TITLE, textStyle);
//        textLbl.setPosition(Constants.SCREEN_W/2 - textLbl.getWidth()/2, Constants.SCREEN_H/2 - textLbl.getHeight()/2);
        textLbl.setPosition(Constants.SCREEN_W/2 - textLbl.getWidth()/2, Constants.SCREEN_H/2 - textLbl.getHeight()/2+textLbl.getHeight());
        stage.addActor(textLbl);

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