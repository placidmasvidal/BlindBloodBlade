package com.xaviplacidpol.blindbloodblade.utils;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.xaviplacidpol.blindbloodblade.screens.GameOverScreen;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

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
    public EnemyAssets enemyAssets;
    public ScoreScreenAssets scoreScreenAssets;
    public BloodSplashAssets bloodSplashAssets;
    public BackgroundStageAssets backgroundStageAssets;
//    public SoundAssets soundAssets;
    public GameOverScreenAssets gameOverScreenAssets;

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
        enemyAssets = new EnemyAssets(atlas);
        scoreScreenAssets = new ScoreScreenAssets(atlas);
        bloodSplashAssets = new BloodSplashAssets(atlas);
        backgroundStageAssets = new BackgroundStageAssets(atlas);
  //      soundAssets = new SoundAssets();
        gameOverScreenAssets = new GameOverScreenAssets(atlas);
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
        public final TextureAtlas.AtlasRegion ninjaWalking0;
        public final TextureAtlas.AtlasRegion ninjaWalking;
        public final TextureAtlas.AtlasRegion ninjaWalking2;
        public final TextureAtlas.AtlasRegion ninjaAttacking;
        public final TextureAtlas.AtlasRegion ninjaDead;

        public final Animation ninjaWalkingAnimation;


        public NinjaAssets(TextureAtlas atlas) {
            //Retrieve ninja images from atlas file
            ninjaStatic = atlas.findRegion(Constants.NINJA_STATIC);

            ninjaJumping = atlas.findRegion(Constants.NINJA_JUMPING);

            ninjaWalking0 = atlas.findRegion(Constants.NINJA_WALKING0);

            ninjaWalking = atlas.findRegion(Constants.NINJA_WALKING);

            ninjaWalking2 = atlas.findRegion(Constants.NINJA_WALKING2);

            ninjaAttacking = atlas.findRegion(Constants.NINJA_ATTACKING);

            ninjaDead = atlas.findRegion(Constants.NINJA_DEAD);

            //WALKING ANIMATION
            Array<TextureAtlas.AtlasRegion> ninjaWalkingFrames = new Array<TextureAtlas.AtlasRegion>();
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_STATIC));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING0));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING2)); //modified
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_STATIC));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING0));
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
     * Build enemies
     */
    public class EnemyAssets {
        // Add an AtlasRegion to hold the spikes sprite
        public final TextureAtlas.AtlasRegion samuraistatic;

        public EnemyAssets(TextureAtlas atlas) {
            // Find the samuraistatic atlas region
            samuraistatic = atlas.findRegion(Constants.ENEMY_SPRITE);
        }
    }

    public class ScoreScreenAssets {

        public BitmapFont bbbscorefont;
        public BitmapFont bbbscoresfont;
        public BitmapFont bbbscorehud;
        public TextureAtlas.AtlasRegion backButtonRegion;

        public ScoreScreenAssets(TextureAtlas atlas){

            backButtonRegion = atlas.findRegion(Constants.BACK_BUTTON);

            FileHandle font = Gdx.files.internal(("fonts/bbbscorefontorangeyellowoutline.fnt"));
            bbbscorefont = new BitmapFont(font, false);
            bbbscorefont.getData().setScale(Constants.SCORE_SCREEN_SCALE);

            font = Gdx.files.internal(("fonts/bbbscorefontorangeyellowoutline.fnt"));
            bbbscoresfont = new BitmapFont(font, false);
            bbbscoresfont.getData().setScale(Constants.SCORES_SCREEN_SCALE);

            bbbscorehud = new BitmapFont(font, false);
            bbbscorehud.getData().setScale(0.4f);


        }

    }

    public class BloodSplashAssets{
        // Add an AtlasRegion to hold the bloodSplash sprite
        public final TextureAtlas.AtlasRegion bloodSplash;

        public BloodSplashAssets(TextureAtlas atlas) {
            // Find the blood splash atlas region
            bloodSplash = atlas.findRegion(Constants.BLOOD_SPLASH_SPRITE);
        }
    }

    public class BackgroundStageAssets{

        public final TextureAtlas.AtlasRegion backgroundgamestage;

        public BackgroundStageAssets(TextureAtlas atlas){
            backgroundgamestage = atlas.findRegion(Constants.STAGE_BACKGROUND);
        }

    }

/*    public class SoundAssets{

        public final Sound bloodSplashSound;
        public final Sound swordSlashSound;

        public final Music backgroundMusic;
        public final Music sakuraAmbienceStage;
        public final Music superFastLevel;
        public final Music thrillerStage;


        public SoundAssets(){

            bloodSplashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bloodhitting.ogg"));
            swordSlashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swordslash.ogg"));

            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundmusic.ogg"));
            backgroundMusic.setLooping(true);

            superFastLevel = Gdx.audio.newMusic(Gdx.files.internal("sounds/nessuperfastlevel.ogg"));
            superFastLevel.setLooping(true);

            thrillerStage = Gdx.audio.newMusic(Gdx.files.internal("sounds/thrillerstage.ogg"));
            thrillerStage.setLooping(true);

            sakuraAmbienceStage = Gdx.audio.newMusic(Gdx.files.internal("sounds/sakuraambiencestage.ogg"));
            sakuraAmbienceStage.setLooping(true);
        }

    }
*/

    public class GameOverScreenAssets {

        public BitmapFont bbbgameoverfont;

        public GameOverScreenAssets(TextureAtlas atlas){

            FileHandle font = Gdx.files.internal(("fonts/bbbgameoverresized.fnt"));
            bbbgameoverfont = new BitmapFont(font, false);
            bbbgameoverfont.getData().setScale(Constants.GAMEOVER_SCREEN_SCALE);


        }

    }

}
