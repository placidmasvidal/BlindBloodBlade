package com.xaviplacidpol.blindbloodblade.utils;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 *  This utility class holds onto all the assets used in BlindBloodBlade Game Screen. It's a singleton, so the constructor
 *  is private, and a single instance is created when the class is loaded. That way all
 *  the entities that make up the game can access their sprites in the same place, and no work loading
 *  up textures is repeated.
 *
 *  Each entity gets its own inner class to hold its assets.
 */
public class Assets implements Disposable, AssetErrorListener {


    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    public NinjaAssets ninjaAssets;
    public GroundAssets groundAssets;
    public SpikesAssets spikesAssets;
    public BridgeAssets bridgeAssets;
    public BackgroundAssets backgroundAssets;
    public GroundWithSpikesAssets groundSpikesAssets;
    public SplashScreenAssets splashScreenAssets;
    private AssetManager assetManager;

    private Assets() {
    }

    /**
     * Initialize assetManager
     * Load and initialize assets
     *
     * @param assetManager
     */
    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);

        //Load texture pack
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        //Initialize ninjaAssets, floorAssets, spikesAssets
        ninjaAssets = new NinjaAssets(atlas);
        groundAssets = new GroundAssets(atlas);
        spikesAssets = new SpikesAssets(atlas);
        bridgeAssets = new BridgeAssets(atlas);
        backgroundAssets = new BackgroundAssets(atlas);
        groundSpikesAssets = new GroundWithSpikesAssets(atlas);
        splashScreenAssets = new SplashScreenAssets(atlas);

    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    /**
     * Build ninja textures and walking animation
     */
    public class NinjaAssets {

        public final TextureAtlas.AtlasRegion ninjaStatic;
        public final TextureAtlas.AtlasRegion ninjaJumping;
        public final TextureAtlas.AtlasRegion ninjaWalking;

        public final Animation ninjaWalkingAnimation;


        public NinjaAssets(TextureAtlas atlas) {
            //Retrieve ninja images from atlas file
            ninjaStatic = atlas.findRegion(Constants.NINJA_STATIC);

            ninjaJumping = atlas.findRegion(Constants.NINJA_JUMPING);

            ninjaWalking = atlas.findRegion(Constants.NINJA_WALKING);

            //WALKING ANIMATION
            Array<TextureAtlas.AtlasRegion> ninjaWalkingFrames = new Array<TextureAtlas.AtlasRegion>();
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_STATIC));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_STATIC));
            ninjaWalkingAnimation = new Animation(Constants.WALK_LOOP_DURATION, ninjaWalkingFrames, Animation.PlayMode.LOOP);

        }

    }

    /**
     * Build ground with a NinePatch
     */
    public class GroundAssets {
        // Build NinePatch member for the ground
        public final NinePatch groundNinePatch;

        public GroundAssets(TextureAtlas atlas) {
            // Find the AtlasRegion holding the platform
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.GROUND_SPRITE);
            // Turn that AtlasRegion into a NinePatch using the edge constant you defined
            int edge = Constants.GROUND_EDGE;
            groundNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    /**
     * Build spikes
     */
    public class SpikesAssets {
        // Add an AtlasRegion to hold the spikes sprite
        public final TextureAtlas.AtlasRegion spike;

        public SpikesAssets(TextureAtlas atlas) {
            // Find the spikes atlas region
            spike = atlas.findRegion(Constants.SPIKES_SPRITE);
        }
    }

    /**
     * Build spikes
     */
    public class BackgroundAssets {
        // Build NinePatch member for the ground
        public final NinePatch backNinePatch;

        public BackgroundAssets(TextureAtlas atlas) {
            // Find the AtlasRegion holding the platform
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.BACKGROUND_SPRITE);
            // Turn that AtlasRegion into a NinePatch using the edge constant you defined
            int edge = Constants.BACKGROUND_EDGE;
            backNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }


    /**
     *Build bridges with a NinePatch
     */
    public class BridgeAssets {
        // Build NinePatch member for the ground
        public final NinePatch groundNinePatch;

        public BridgeAssets(TextureAtlas atlas) {
            // Find the AtlasRegion holding the platform
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.BRIDGE_SPRITE);
            // Turn that AtlasRegion into a NinePatch using the edge constant you defined
            int edge = Constants.BRIDGE_EDGE;
            groundNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    /**
     *Build groundSpikes with a NinePatch
     */
    public class GroundWithSpikesAssets {
        // Build NinePatch member for the ground
        public final NinePatch groundNinePatch;

        public GroundWithSpikesAssets (TextureAtlas atlas) {
            // Find the AtlasRegion holding the platform
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.BLOCK_SPRITE);
            // Turn that AtlasRegion into a NinePatch using the edge constant you defined
            int edge = Constants.BLOCK_EDGE;
            groundNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class SplashScreenAssets {

        public BitmapFont bbbattackfont;
        public TextureAtlas.AtlasRegion backgroundRegion;
        public TextureAtlas.AtlasRegion startButtonRegion;
        public TextureAtlas.AtlasRegion scoreButtonRegion;

//        public Texture bgTexture;

        public SplashScreenAssets(TextureAtlas atlas) {

            FileHandle fontFile = Gdx.files.internal("fonts/bbbattack.fnt");
            bbbattackfont = new BitmapFont(fontFile, true);
            bbbattackfont.getData().setScale(Constants.MAIN_TITLE_SCALE);
            backgroundRegion = atlas.findRegion(Constants.SPLASH_SPRITE);
            startButtonRegion = atlas.findRegion((Constants.START_BUTTON));
            scoreButtonRegion = atlas.findRegion((Constants.SCORE_BUTTON));

        }
    }
}