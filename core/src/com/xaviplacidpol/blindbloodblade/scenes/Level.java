package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.entities.Bridges;
import com.xaviplacidpol.blindbloodblade.entities.Enemy;
import com.xaviplacidpol.blindbloodblade.entities.Ground;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;
import com.xaviplacidpol.blindbloodblade.entities.Spikes;
import com.xaviplacidpol.blindbloodblade.utils.Cam;

import java.util.HashSet;
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


    public Level(Viewport viewport, BlindBloodBlade game){

        this.game = game;
        scoresSet = new HashSet<>();
        scoresSet.add(19286);
        scoresSet.add(17388);
        scoresSet.add(19281);
        scoresSet.add(21990);
        scoresSet.add(26722);

        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer(viewport);

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Initialize the spikes array
        spikes = new Array<Spikes>();

        // Initialize the bridges array
        bridges = new Array<Bridges>();

        //Initialize the enemyes array
        enemies = new Array<Enemy>();

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

        //TODO POL revisar
        this.viewport = viewport;

    }

    /**
     * Update level and all components
     * @param delta
     */
    public void update(float delta){
        // Update NinjaPlayer
        ninjaPlayer.update(delta, grounds);
    }

    /**
     * Renderize level and all components
     * @param batch
     */
    public void render(SpriteBatch batch){

        batch.begin();
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

        // Render NinjaPlayer
        ninjaPlayer.render(batch);

        batch.end();
        dispose();
    }

    /**
     *  Add here all the grounds, grounds are necessary for the ninja to not fall out,
     *  then is important to be rendered in order
     *
     */
    private void addDebugGrounds(){
        // TODO:  Add here all grounds in the level
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

    @Override
    public void dispose() {
        int i = 1;
        for(Integer score : scoresSet){
            game.gameData.putInteger("score"+i, score);
            i++;
        }
        game.gameData.flush();
    }
}