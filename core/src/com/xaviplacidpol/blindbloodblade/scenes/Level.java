package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;

public class Level {
    // Add a ninjaPlayer member variable
    NinjaPlayer ninjaPlayer;

    public Level(){
        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer();
    }

    /**
     * Update level and all components
     * @param delta
     */
    public void update(float delta){
        // Update NinjaPlayer
        ninjaPlayer.update(delta);
    }

    /**
     * Renderize level and all components
     * @param batch
     */
    public void render(SpriteBatch batch){
        // Render NinjaPlayer
        batch.begin();
        ninjaPlayer.render(batch);
        batch.end();
    }
}
