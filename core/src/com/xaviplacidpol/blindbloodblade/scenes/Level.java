package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.xaviplacidpol.blindbloodblade.entities.Ground;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;

public class Level {
    // Add a ninjaPlayer member variable
    NinjaPlayer ninjaPlayer;

    // Add an Array of Grounds
    Array<Ground> grounds;

    public Level(){
        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer();

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Add addDebugPlatforms
        addDebugGrounds();
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
        grounds.add(new Ground(0, 40, 200, 60));

        grounds.add(new Ground(275, 40, 100, 60));

        //grounds.add(new Ground(300, 150, 200, 60));



    }
}