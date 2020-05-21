package com.xaviplacidpol.blindbloodblade.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Extra utils class
 * Drawing texture region methods
 */
public class Utils {

    /**
     * Draw texture region with default parameters
     *
     * @param batch
     * @param region
     * @param x position
     * @param y position
     */
    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y) {
        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    /**
     * Draw texture region method with modified scale
     * @param region texture to draw
     * @param scaled modified scale
     */
    public static void drawTextureRegionScaled(SpriteBatch batch, TextureRegion region, float x, float y, float scaled) {
        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                scaled,
                scaled,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }
}
