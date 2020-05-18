package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.entities.Bridges;
import com.xaviplacidpol.blindbloodblade.entities.Ground;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;
import com.xaviplacidpol.blindbloodblade.entities.Spikes;

public class Level {
    // Add a ninjaPlayer member variable
    NinjaPlayer ninjaPlayer;

    // Add an Array of Grounds
    Array<Ground> grounds;

    // Add an Array of Spikes
    Array<Spikes> spikes;

    // Add an Array of Spikes
    Array<Bridges> bridges;

    // TODO
    public Viewport viewport;

    /*public Level(){

        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer();

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Initialize the spikes array
        spikes = new Array<Spikes>();

        // Initialize the bridges array
        bridges = new Array<Bridges>();

        // Add addDebugPlatforms
        addDebugGrounds();

        //Add spikes
        addSpikes();

    }*/

    public Level(Viewport viewport){
        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer(viewport);

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Initialize the spikes array
        spikes = new Array<Spikes>();

        // Add addDebugPlatforms
        addDebugGrounds();

        // Initialize the bridges array
        bridges = new Array<Bridges>();

        //Add spikes
        addSpikes();

        Gdx.input.setInputProcessor(ninjaPlayer);

        //Add spikes
        addBridges();

        //TODO
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

        // Render NinjaPlayer
        ninjaPlayer.render(batch);

        batch.end();
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

    public NinjaPlayer getNinjaPlayer() {
        return ninjaPlayer;
    }
}