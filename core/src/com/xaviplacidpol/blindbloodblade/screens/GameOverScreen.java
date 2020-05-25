package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLInterceptor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameOverScreen extends ScreenAdapter {

    private BlindBloodBlade game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;
    private Label.LabelStyle textStyle;
    private Label lblTitle;
    private Vector2 lblTitlePos;
    private Set<String> scoresSet;

    // Timer controls
    private float gameOverStartTimer;
    private boolean gameOverEnd;

    public GameOverScreen(final BlindBloodBlade game) {

        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_LEVEL_FAST).stop();

        if(game.sound) {
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_GAME_OVER).play();
        }

        this.game = game;

        scoresSet = new HashSet<>();

        for(int i =1; i<=5; i++) {
            scoresSet.add(game.gameData.getString("score"+i));
        }

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.SCREEN_W, Constants.SCREEN_H);
        viewport = new StretchViewport(Constants.SCREEN_W, Constants.SCREEN_H, camera);
        stage = new Stage(viewport);

        textStyle = new Label.LabelStyle(Assets.instance.gameOverScreenAssets.bbbgameoverfont, null);
        lblTitle = new Label(Constants.GAME_OVER, textStyle);
        lblTitlePos = new Vector2(Constants.SCREEN_W/2 - lblTitle.getWidth()/2, Constants.SCREEN_H/2 - lblTitle.getHeight()*2+ lblTitle.getHeight()*2 - lblTitle.getHeight()/3);
        lblTitle.setPosition(lblTitlePos.x, lblTitlePos.y);

        stage.addActor(lblTitle);

        //Initialize timer controls
        gameOverStartTimer = TimeUtils.nanoTime();
        gameOverEnd = false;

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

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GLInterceptor.GL_COLOR_BUFFER_BIT);

        stage.draw();

        //Control if screen touched and pause time end to return MenuScreen
        if (Gdx.input.isTouched() && gameOverEnd) {
            // Reset gameOverEnd
            gameOverEnd = false;
            game.setScreen(new MenuScreen(game));

            dispose();
        }

        gameOverPause();

    }

    /**
     * Control the timer to pause the game when GameOverScreen was loaded
     * @return gameOverEnd True if pause time reached max gameOver duration
     */
    private boolean gameOverPause() {
            //Find how long we are paused in game over screen
            float gameOverDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - gameOverStartTimer);
            //If reached max gameOver duration then pause time is done
            if(gameOverDuration > Constants.MAX_GAMEOVER_DURATION){
                gameOverEnd = true;
            }

        return gameOverEnd;
    }

    @Override
    public void dispose() {

        String scoreString = String.valueOf(game.getScore());
        String idString = String.valueOf(game.getPlayerId());

        scoresSet.add(scoreString+idString);
        scoresSet.add("00");
        scoresSet.add("00");
        scoresSet.add("00");
        scoresSet.add("00");
        scoresSet.add("00");
        List<Integer> scores = new ArrayList<>();

        for(String score : scoresSet){
            if(!score.isEmpty())
                scores.add(Integer.valueOf(score));
        }

        java.util.Collections.sort(scores, Collections.reverseOrder());

        int i = 1;
        for(Integer score : scores){
            game.gameData.putString("score"+i, String.valueOf(score));
            i++;
        }

        game.gameData.flush();

        stage.dispose();
        batch.dispose();

    }


}
