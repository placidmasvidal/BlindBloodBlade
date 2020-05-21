package com.xaviplacidpol.blindbloodblade.overlays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.entities.BloodSplash;
import com.xaviplacidpol.blindbloodblade.scenes.Level;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

public class BloodSplashOverlay {
    public final static String TAG = BloodSplashOverlay.class.getName();
    public final Viewport viewport;

    private Level level;

    Array<BloodSplash> bloodSplashes;

    public BloodSplashOverlay(){
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

    }
    public BloodSplashOverlay(Level level){
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        this.level = level;

    }

    public void init( ){
        bloodSplashes = new Array<BloodSplash>();

    }


    public void render(SpriteBatch batch){
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        bloodSplashes = level.getBloodSplashesScreen();

        // Render the blood splashes

        for(BloodSplash bloodSplash : bloodSplashes){
            bloodSplash.render(batch, Constants.BLOOD_SPLASH_OVERLAY_SCALE);
        }

        // Test bloodsplash
//        bloodSplashes.add(new BloodSplash(new Vector2(
//                MathUtils.random(viewport.getWorldWidth()),
//                MathUtils.random(viewport.getWorldHeight())
//        )));

    }
}
