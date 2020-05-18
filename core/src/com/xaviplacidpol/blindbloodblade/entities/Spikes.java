package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

public class Spikes {
    // Add a Vector 2 to hold the spike's position
    final public Vector2 position;

    public  Spikes(Vector2 position){
        // Set position
        this.position = position;
    }

    public void render(SpriteBatch batch){
        // render function
        final TextureRegion region = Assets.instance.spikesAssets.spike;

        batch.draw(
                region.getTexture(),
                position.x,
                position.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.3f,
                0.3f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false
        );
    }
}
