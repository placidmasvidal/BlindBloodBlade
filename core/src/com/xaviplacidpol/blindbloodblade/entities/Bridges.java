package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xaviplacidpol.blindbloodblade.utils.Assets;

public class Bridges {
    float top;
    float bottom;
    float left;
    float right;

    /**
     * Constructor with the position and the size of the ground
     *
     */
    public Bridges(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    /**
     * renderize the bridge
     * @param batch
     */
    public void render(SpriteBatch batch) {

        float width = right - left;
        float height = top - bottom;

        // Draw the ground using the NinePatch
        Assets.instance.bridgeAssets.groundNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }
}
