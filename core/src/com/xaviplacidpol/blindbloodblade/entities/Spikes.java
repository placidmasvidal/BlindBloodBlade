package com.xaviplacidpol.blindbloodblade.entities;

import  com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Utils;

/**
 * Lethal spikes object where the player will die
 */
public class Spikes {
    //Vector 2 to hold the spike's position
    final public Vector2 position;

    public  Spikes(Vector2 position){
        // Set position
        this.position = position;
    }

    /**
     * Render the spikes
     * @param batch
     */
    public void render(SpriteBatch batch){

        final TextureRegion region = Assets.instance.spikesAssets.spike;
        //Draw spikes
        Utils.drawTextureRegionScaled(batch, region, position.x, position.y, 0.3f);

    }
}
