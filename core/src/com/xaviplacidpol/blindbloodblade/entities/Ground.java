package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xaviplacidpol.blindbloodblade.utils.Assets;

public class Ground {
    float top;
    float bottom;
    float left;
    float right;

    /**
     * Constructor with the position and the size of the ground
     *
     */
    public Ground(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    /**
     * renderize the ground
     * @param batch
     */
    public void render(SpriteBatch batch) {


        float width = right - left;
        float height = top - bottom;

        // Draw the ground using the NinePatch
        Assets.instance.groundAssets.groundNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);

    }
}
