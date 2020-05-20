package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.entities.BloodSplash;
import com.xaviplacidpol.blindbloodblade.entities.Bridges;
import com.xaviplacidpol.blindbloodblade.entities.Enemy;
import com.xaviplacidpol.blindbloodblade.entities.Ground;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;
import com.xaviplacidpol.blindbloodblade.entities.Spikes;
import com.xaviplacidpol.blindbloodblade.utils.Cam;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Level implements Disposable {

    private BlindBloodBlade game;

    private Set<Integer> scoresSet;

    // Add a ninjaPlayer member variable
    NinjaPlayer ninjaPlayer;

    // Add an Array of Grounds
    Array<Ground> grounds;

    // Add an Array of Spikes
    Array<Spikes> spikes;

    public Viewport viewport;

    // Add an Array of Bridges
    Array<Bridges> bridges;

    // Add an Array of Enemies
    Array<Enemy> enemies;

    //Add blood splash to the enemy position when this enemy is killed
    private Array<BloodSplash> bloodSplashes;

    //Array with the random fixed blood splashes to the screen
    private Array<BloodSplash> bloodSplashesScreen;

    //Boolean to control if reached end of level
    public boolean levelEnd;


    public Level(Viewport viewport, BlindBloodBlade game){

        this.game = game;
        scoresSet = new HashSet<>();
 /*       scoresSet.add(19286);
        scoresSet.add(17388);
        scoresSet.add(19281);
        scoresSet.add(21990);
        scoresSet.add(26722);
*/
        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer(viewport, this);

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Initialize the spikes array
        spikes = new Array<Spikes>();

        // Initialize the bridges array
        bridges = new Array<Bridges>();

        //Initialize the enemyes array
        enemies = new Array<Enemy>();

        // Initialize both arrays for blood splashes
        bloodSplashes = new Array<BloodSplash>();
        bloodSplashesScreen = new Array<BloodSplash>();

        // Add addDebugPlatforms
        addDebugGrounds();

        //Add spikes
        addSpikes();

        //Set input touch screen for ninjaPlayer
        Gdx.input.setInputProcessor(ninjaPlayer);

        //Add bridges
        addBridges();

        //Add enemies
        addEnemies();

        //Initialize level end to false
        levelEnd = false;

        //TODO POL revisar viewport + cam en resize
        this.viewport = viewport;

    }

    /**
     * Update level and all components
     * @param delta
     */
    public void update(float delta){
        // Update NinjaPlayer
        ninjaPlayer.update(delta, grounds);

        endlessGame();
    }

    /**
     * Restart player position for endless game when player reached end point
     * Clean all blood splashes in enemies position
     * Relive all enemies
     *
     */
    private void endlessGame() {
        // Restart player position for endless game if player reached end point, otherwise just return
        if(ninjaPlayer.getPosition().x > Constants.ENDLESS_POSITION){
            levelEnd = true;
            //Clean old blood splashes in the enemies position
            bloodSplashes.clear();
            //Relive all enemies
            for(Enemy enemy : enemies){
                enemy.setAlive(true);
            }
        }
    }

    /**
     * Renderize level and all components
     * @param batch
     */
    public void render(SpriteBatch batch){

//        batch.begin();
        // Render all grounds in the grounds array
        for(Ground ground : grounds){
            ground.render(batch);
        }

        // Render all spikes
        for(Spikes spike : spikes){
            spike.render(batch);
        }

        // Render all bridges
        for(Bridges bridge : bridges){
            bridge.render(batch);
        }

        // Render all enemiew
        for(Enemy enemy : enemies){
            enemy.render(batch);
        }

        // Render bloodSplashes
        for(BloodSplash bloodSplash : bloodSplashes){
            bloodSplash.render(batch);
        }

        // Render NinjaPlayer
        ninjaPlayer.render(batch);

//        batch.end();
        if(!ninjaPlayer.isAlive()) {
            dispose();
        }
    }

    /**
     *  Add here all the grounds, grounds are necessary for the ninja to not fall out,
     *  then is important to be rendered in order
     *
     */
    private void addDebugGrounds(){
        grounds.add(new Ground(0, 40, 400, 40));
        grounds.add(new Ground(541, 40, 400, 40));
        //grounds.add(new Ground(461, 40, 200, 60));

    }

    private void addSpikes() {
        spikes.add(new Spikes(new Vector2(401, 0)));
        spikes.add(new Spikes(new Vector2(471, 0)));
    }

    private void addBridges() {
        bridges.add(new Bridges(new Vector2(999, 0)));
    }

    private void addEnemies(){
        enemies.add(new Enemy(new Vector2(550, 60)));
    }

    public NinjaPlayer getNinjaPlayer() {
        return ninjaPlayer;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<Spikes> getSpikes() {
        return spikes;
    }

    public BlindBloodBlade getGame() {
        return game;
    }

    public Array<BloodSplash> getBloodSplashesScreen() {
        return bloodSplashesScreen;
    }

    /**
     * Generate a blood Splash to the enemy position where died
     * @param position
     */
    public void spawnBloodSplash(Vector2 position){
        bloodSplashes.add(new BloodSplash(position));
    }

    /**
     * Generate a random blood splash for the BloodSplashOverlay
     */
    public void addBloodSplash(){
        bloodSplashesScreen.add(new BloodSplash(new Vector2(
                MathUtils.random(viewport.getWorldWidth()),
                MathUtils.random(viewport.getWorldHeight())
        )));
    }

    @Override
    public void dispose() {

        int i = 1;
        scoresSet.add(ninjaPlayer.getScore());

        List<Integer> scores = new ArrayList<>();

        for(Integer score : scoresSet){
            scores.add(score);
        }

        java.util.Collections.sort(scores, Collections.reverseOrder());

        for(Integer score : scores){
            game.gameData.putInteger("score"+i, score);
            i++;
        }

/*        for(Integer score : scoresSet){
            game.gameData.putInteger("score"+i, score);
            i++;
        }
        scoresSet.remove(0);
*/
//        game.gameData.putInteger("score5", ninjaPlayer.getScore());
        game.gameData.flush();
    }
}
