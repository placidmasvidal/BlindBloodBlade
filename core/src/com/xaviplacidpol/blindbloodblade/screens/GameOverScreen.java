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

        this.game = game;

        loadSound(game);

        getPersistedScores(game);

        initComponents();

        initStageContent();

        stage.addActor(lblTitle);

        //Initialize timer controls
        gameOverStartTimer = TimeUtils.nanoTime();
        gameOverEnd = false;

    }

    /**
     * Initializs the content to shown
     */
    private void initStageContent() {
        textStyle = new Label.LabelStyle(Assets.instance.gameOverScreenAssets.bbbgameoverfont, null);
        lblTitle = new Label(Constants.GAME_OVER, textStyle);
        lblTitlePos = new Vector2(Constants.SCREEN_W/2 - lblTitle.getWidth()/2, Constants.SCREEN_H/2 - lblTitle.getHeight()*2+ lblTitle.getHeight()*2 - lblTitle.getHeight()/3);
        lblTitle.setPosition(lblTitlePos.x, lblTitlePos.y);
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
     * Get scores persisted in Preferences file and loads them to a Set
     * @param game
     */
    private void getPersistedScores(BlindBloodBlade game) {
        scoresSet = new HashSet<>();

        for(int i =1; i<=5; i++) {
            scoresSet.add(game.gameData.getString("score"+i));
        }
    }

    /**
     * Plays or stops game sound as user indicated in setup screen
     * @param game main class of the app that stores the sound map
     * to let acces from any package
     */
    private void loadSound(BlindBloodBlade game) {
        SoundAssetsManager.bbbmusics.get(SoundAssetsManager.M_LEVEL_FAST).stop();

        if(game.sound) {
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_GAME_OVER).play();
        }
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

    /**
     * free resources
     */
    @Override
    public void dispose() {

        /*
          Preferences class from Libgdx uses dispose() to persist data when
          current class instance is destructed as described in official
          documentation, so next lines had to be here.
        */

        //get the score obtained by the player in current game after Game Over succeed
        String scoreString = String.valueOf(game.getScore());
        //get the id of the player in current game after Game Over succeed
        String idString = String.valueOf(game.getPlayerId());

        //store both (score and id) in a set of String in order to avoid adding repited scores
        //set was previously filled with data recovered from persist file in the constructor
        scoresSet.add(scoreString+idString);

        //copy all set data to a Collection to let sort the data
        List<Integer> scores = new ArrayList<>();

        for(String score : scoresSet){
            if(!score.isEmpty())    //avoid crashes for NPE if there is no data
                scores.add(Integer.valueOf(score));
        }

        //sort collection to show scores from max to min
        java.util.Collections.sort(scores, Collections.reverseOrder());

        //Persist every score to the Preferences persist file
        int i = 1;
        for(Integer score : scores){
            game.gameData.putString("score"+i, String.valueOf(score));
            i++;
        }

        //required to persist data using libgdx Preferences
        game.gameData.flush();


        stage.dispose();
        batch.dispose();

    }


}
