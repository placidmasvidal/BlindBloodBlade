package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xaviplacidpol.blindbloodblade.utils.Assets;

public class Block {
    float top;
    float bottom;
    float left;
    float right;
    float width;

    /**
     * Constructor with the position and the size of the ground with spikes
     *
     */
    public Block(float left, float top, float width, float height) {
        this.width = width;
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    public float getWidth() {
        return width;
    }

    /**
     * renderize the ground with spikes
     * @param batch
     */
    public void render(SpriteBatch batch) {

        float width = right - left;
        float height = top - bottom;

        // Draw the ground with spikes using the NinePatch
        Assets.instance.groundSpikesAssets.groundNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);

    }
}
