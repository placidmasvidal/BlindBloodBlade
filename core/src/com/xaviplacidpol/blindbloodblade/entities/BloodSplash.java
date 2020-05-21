package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.Utils;

public class BloodSplash {

    private final Vector2 position;

    public BloodSplash(Vector2 position){
        // Save spawn position
        this.position = position;

    }

    //render default blood splash
    public void render(SpriteBatch batch){
        // render function
        final TextureRegion region = Assets.instance.bloodSplashAssets.bloodSplash;

        Utils.drawTextureRegion(batch, region, position.x, position.y);

    }

    // render scaled blood splash
    public void render(SpriteBatch batch, float scaled){
        final TextureRegion region = Assets.instance.bloodSplashAssets.bloodSplash;

        Utils.drawTextureRegionScaled(batch, region, position.x, position.y, scaled);

    }
}
