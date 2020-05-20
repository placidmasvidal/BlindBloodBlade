package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.entities.Background;
import com.xaviplacidpol.blindbloodblade.entities.Bridge;
import com.xaviplacidpol.blindbloodblade.entities.Ground;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;
import com.xaviplacidpol.blindbloodblade.entities.Spikes;
import com.xaviplacidpol.blindbloodblade.entities.Block;

public class Level {
    // Add a ninjaPlayer member variable
    NinjaPlayer ninjaPlayer;

    // Add an Array of Grounds
    Array<Ground> grounds;

    // Add an Array of Spikes
    Array<Spikes> spikes;

    // Add an Array of Spikes
    Array<Bridge> bridges;

    // Add an Array of blocks
//    Array<Block> blocks;

    // Add an Array of backgrounds
    Array<Background> backgrounds;

    // TODO
    public Viewport viewport;

    public Level(Viewport viewport){
        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer(viewport);

        // Initialize the bridges array
        bridges = new Array<Bridge>();

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Initialize the spikes array
        spikes = new Array<Spikes>();

        // Initialize the blocks array
//        blocks = new Array<Block>();

        // Initialize the backgrounds array
        backgrounds = new Array<Background>();

        //Add bridges
        addBridges();

        // Add addDebugPlatforms
        addDebugGrounds();

        //Add spikes
        addSpikes();

        //Add backgrounds
        addBackgrounds();

        Gdx.input.setInputProcessor(ninjaPlayer);

        //Add blocks
//        addBlocks();

        //TODO
        this.viewport = viewport;
    }

    /**
     * Update level and all components
     * @param delta
     */
    public void update(float delta){
        // Update NinjaPlayer
        ninjaPlayer.update(delta, grounds, bridges);
    }

    /**
     * Renderize level and all components
     * @param batch
     */
    public void render(SpriteBatch batch){

        batch.begin();

        //Render all backgrounds
        for (Background b : backgrounds) {
            b.render(batch);
        }

        // Render all grounds in the grounds array
        for(Ground ground : grounds){
            ground.render(batch);
        }

        // Render all spikes
        for(Spikes spike : spikes){
            spike.render(batch);
        }

        // Render all bridges
        for(Bridge bridge : bridges){
            bridge.render(batch);
        }

        // Render all bridges
//        for(Block block : blocks){
//            block.render(batch);
//        }

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
        grounds.add(new Ground(2090, 40, 400, 40));
        grounds.add(new Ground(2491, 40, 400, 40));
        grounds.add(new Ground(3730, 40, 400, 40));
        grounds.add(new Ground(4130, 40, 400, 40));
        grounds.add(new Ground(4530, 40, 400, 40));
        grounds.add(new Ground(4930, 40, 400, 40));
    }

    private void addSpikes() {
        spikes.add(new Spikes(new Vector2(401, 0)));
        spikes.add(new Spikes(new Vector2(471, 0)));
        spikes.add(new Spikes(new Vector2(941, 0)));
        spikes.add(new Spikes(new Vector2(1011, 0)));
        spikes.add(new Spikes(new Vector2(1081, 0)));
        spikes.add(new Spikes(new Vector2(1151, 0)));
        spikes.add(new Spikes(new Vector2(1221, 0)));
        spikes.add(new Spikes(new Vector2(1291, 0)));
        spikes.add(new Spikes(new Vector2(1361, 0)));
        spikes.add(new Spikes(new Vector2(1431, 0)));
        spikes.add(new Spikes(new Vector2(1501, 0)));
        spikes.add(new Spikes(new Vector2(1571, 0)));
        spikes.add(new Spikes(new Vector2(1641, 0)));
        spikes.add(new Spikes(new Vector2(1711, 0)));
        spikes.add(new Spikes(new Vector2(1781, 0)));
        spikes.add(new Spikes(new Vector2(1851, 0)));
        spikes.add(new Spikes(new Vector2(1921, 0)));
        spikes.add(new Spikes(new Vector2(1991, 0)));
        spikes.add(new Spikes(new Vector2(2720, 40)));
        spikes.add(new Spikes(new Vector2(2790, 40)));
        spikes.add(new Spikes(new Vector2(2821, 40)));
        spikes.add(new Spikes(new Vector2(2891, 0)));
        spikes.add(new Spikes(new Vector2(2961, 0)));
        spikes.add(new Spikes(new Vector2(3031, 0)));
        spikes.add(new Spikes(new Vector2(3101, 0)));
        spikes.add(new Spikes(new Vector2(3171, 0)));
        spikes.add(new Spikes(new Vector2(3241, 0)));
        spikes.add(new Spikes(new Vector2(3311, 0)));
        spikes.add(new Spikes(new Vector2(3381, 0)));
        spikes.add(new Spikes(new Vector2(3451, 0)));
        spikes.add(new Spikes(new Vector2(3521, 0)));
        spikes.add(new Spikes(new Vector2(3591, 0)));
        spikes.add(new Spikes(new Vector2(3661, 0)));

    }

    private void addBridges() {
        bridges.add(new Bridge(1100, 100, 990, 180));
        bridges.add(new Bridge(2250, 200, 150, 280));
        bridges.add(new Bridge(2570, 300, 850, 380));
    }

//    private void addBlocks() {
//        blocks.add(new Block(2500, 66, 80,50));
//        blocks.add(new Block(2750, 66, 80,50));
//        blocks.add(new Block(3000, 66, 80,50));
//    }

    private void addBackgrounds() {
        backgrounds.add(new Background(0, 503, 640, 500));
    }

    public NinjaPlayer getNinjaPlayer() {
        return ninjaPlayer;
    }
}