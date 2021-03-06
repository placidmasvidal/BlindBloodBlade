package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Utils;

/**
 * BloodSplash object.
 * BloodSplashes are generated by killing enemies
 */
public class BloodSplash {

    private final Vector2 position;

    public BloodSplash(Vector2 position){
        // Save spawn position
        this.position = position;

    }

    //render default blood splash
    public void render(SpriteBatch batch){
        //Get region from Atlas
        final TextureRegion region = Assets.instance.bloodSplashAssets.bloodSplash;

        //Draw blood splash
        Utils.drawTextureRegion(batch, region, position.x, position.y);

    }

    // render scaled blood splash
    public void render(SpriteBatch batch, float scaled){
        //Get region from Atlas
        final TextureRegion region = Assets.instance.bloodSplashAssets.bloodSplash;

        //Draw blood splash
        Utils.drawTextureRegionScaled(batch, region, position.x, position.y, scaled);

    }
}
