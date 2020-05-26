package com.xaviplacidpol.blindbloodblade.utils;

import   com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
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
    public RoninAssets roninAssets;
    public AutomataAssets automataAssets;
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
    public GameOverScreenAssets gameOverScreenAssets;
    public SetupScreenAssets setupScreenAssets;

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
        assetManager.load(Constants.TEXTURE_ATLAS_R, TextureAtlas.class);
        assetManager.load(Constants.TEXTURE_ATLAS_A, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        TextureAtlas atlasR = assetManager.get(Constants.TEXTURE_ATLAS_R);
        TextureAtlas atlasA = assetManager.get(Constants.TEXTURE_ATLAS_A);

        //Initialize ninjaAssets, floorAssets, spikesAssets
        ninjaAssets = new NinjaAssets(atlas);
        roninAssets = new RoninAssets(atlasR);
        automataAssets = new AutomataAssets(atlasA);
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
        gameOverScreenAssets = new GameOverScreenAssets(atlas);
        setupScreenAssets = new SetupScreenAssets(atlas);
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

        public final Texture ninjaAvatar;

        public final TextureAtlas.AtlasRegion ninjaStatic;
        public final TextureAtlas.AtlasRegion ninjaJumping;
        public final TextureAtlas.AtlasRegion ninjaWalking0;
        public final TextureAtlas.AtlasRegion ninjaWalking;
        public final TextureAtlas.AtlasRegion ninjaWalking2;
        public final TextureAtlas.AtlasRegion ninjaAttacking;
        public final TextureAtlas.AtlasRegion ninjaDead;

        public final Animation ninjaWalkingAnimation;


        public NinjaAssets(TextureAtlas atlas) {

            ninjaAvatar = new Texture(Gdx.files.internal(Constants.NINJA_AVATAR_FILE));

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
            ninjaWalkingFrames.add(atlas.findRegion(Constants.NINJA_WALKING2));
            ninjaWalkingAnimation = new Animation(Constants.WALK_LOOP_DURATION, ninjaWalkingFrames, Animation.PlayMode.LOOP);

        }

    }

    /**
     * Build ninja textures and walking animation
     */
    public class RoninAssets {

        public final Texture roninAvatar;

        public final TextureAtlas.AtlasRegion roninStatic;
        public final TextureAtlas.AtlasRegion roninJumping;
        public final TextureAtlas.AtlasRegion roninWalking0;
        public final TextureAtlas.AtlasRegion roninWalking;
        public final TextureAtlas.AtlasRegion roninWalking2;
        public final TextureAtlas.AtlasRegion roninAttacking;
        public final TextureAtlas.AtlasRegion roninDead;

        public final Animation roninWalkingAnimation;


        public RoninAssets(TextureAtlas atlas) {

            roninAvatar = new Texture(Gdx.files.internal(Constants.RONIN_AVATAR_FILE));

            //Retrieve ninja images from atlas file
            roninStatic = atlas.findRegion(Constants.RONIN_STATIC);

            roninJumping = atlas.findRegion(Constants.RONIN_JUMPING);

            roninWalking0 = atlas.findRegion(Constants.RONIN_WALKING0);

            roninWalking = atlas.findRegion(Constants.RONIN_WALKING);

            roninWalking2 = atlas.findRegion(Constants.RONIN_WALKING2);

            roninAttacking = atlas.findRegion(Constants.RONIN_ATTACKING);

            roninDead = atlas.findRegion(Constants.RONIN_DEAD);

            //WALKING ANIMATION
            Array<TextureAtlas.AtlasRegion> ninjaWalkingFrames = new Array<TextureAtlas.AtlasRegion>();
            ninjaWalkingFrames.add(atlas.findRegion(Constants.RONIN_STATIC));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.RONIN_WALKING0));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.RONIN_WALKING));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.RONIN_WALKING2));

            roninWalkingAnimation = new Animation(Constants.WALK_LOOP_DURATION, ninjaWalkingFrames, Animation.PlayMode.LOOP);

        }

    }

    /**
     * Build ninja textures and walking animation
     */
    public class AutomataAssets {

        public final Texture automataAvatar;

        public final TextureAtlas.AtlasRegion automataStatic;
        public final TextureAtlas.AtlasRegion automataJumping;
        public final TextureAtlas.AtlasRegion automataWalking0;
        public final TextureAtlas.AtlasRegion automataWalking;
        public final TextureAtlas.AtlasRegion automataWalking2;
        public final TextureAtlas.AtlasRegion automataAttacking;
        public final TextureAtlas.AtlasRegion automataDead;

        public final Animation ninjaWalkingAnimation;


        public AutomataAssets(TextureAtlas atlas) {

            automataAvatar = new Texture(Gdx.files.internal(Constants.AUTOMATA_AVATAR_FILE));

            //Retrieve ninja images from atlas file
            automataStatic = atlas.findRegion(Constants.AUTOMATA_STATIC);

            automataJumping = atlas.findRegion(Constants.AUTOMATA_JUMPING);

            automataWalking0 = atlas.findRegion(Constants.AUTOMATA_WALKING0);

            automataWalking = atlas.findRegion(Constants.AUTOMATA_WALKING);

            automataWalking2 = atlas.findRegion(Constants.AUTOMATA_WALKING2);

            automataAttacking = atlas.findRegion(Constants.AUTOMATA_ATTACKING);

            automataDead = atlas.findRegion(Constants.AUTOMATA_DEAD);

            //WALKING ANIMATION
            Array<TextureAtlas.AtlasRegion> ninjaWalkingFrames = new Array<TextureAtlas.AtlasRegion>();
            ninjaWalkingFrames.add(atlas.findRegion(Constants.AUTOMATA_STATIC));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.AUTOMATA_WALKING0));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.AUTOMATA_WALKING));
            ninjaWalkingFrames.add(atlas.findRegion(Constants.AUTOMATA_WALKING2));
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
        public TextureAtlas.AtlasRegion setupButtonRegion;

        public SplashScreenAssets(TextureAtlas atlas) {

            FileHandle fontFile = Gdx.files.internal("fonts/bbbattack.fnt");
            bbbattackfont = new BitmapFont(fontFile, true);
            bbbattackfont.getData().setScale(Constants.MAIN_TITLE_SCALE);

            backgroundRegion = atlas.findRegion(Constants.SPLASH_SPRITE);
            startButtonRegion = atlas.findRegion((Constants.START_BUTTON));
            scoreButtonRegion = atlas.findRegion((Constants.SCORE_BUTTON));
            setupButtonRegion = atlas.findRegion((Constants.SETUP_BUTTON));


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

    /**
     * Assets for the Score Screen
     */
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

    /**
     * Assets to paint bloodsplahes in an overlay
     */
    public class BloodSplashAssets{
        // Add an AtlasRegion to hold the bloodSplash sprite
        public final TextureAtlas.AtlasRegion bloodSplash;

        public BloodSplashAssets(TextureAtlas atlas) {
            // Find the blood splash atlas region
            bloodSplash = atlas.findRegion(Constants.BLOOD_SPLASH_SPRITE);
        }
    }

    public class BackgroundStageAssets{

        public final Texture backgroundgamestage;

        public BackgroundStageAssets(TextureAtlas atlas){
            backgroundgamestage = new Texture(Gdx.files.internal(Constants.BACKGROUND_FILE));
        }

    }

    /**
     * Assets for the Game Over Screen
     */
    public class GameOverScreenAssets {

        public BitmapFont bbbgameoverfont;

        public GameOverScreenAssets(TextureAtlas atlas){

            FileHandle font = Gdx.files.internal(("fonts/bbbgameoverresized.fnt"));
            bbbgameoverfont = new BitmapFont(font, false);
            bbbgameoverfont.getData().setScale(Constants.GAMEOVER_SCREEN_SCALE);


        }

    }

    /**
     * Assets for the Setup Screen
     */
    public class SetupScreenAssets {

        public TextureAtlas.AtlasRegion musicButtonRegion;
        public TextureAtlas.AtlasRegion soundButtonRegion;
        public TextureAtlas.AtlasRegion controlsButtonRegion;
        public TextureAtlas.AtlasRegion onButtonRegion;
        public TextureAtlas.AtlasRegion offButtonRegion;
        public TextureAtlas.AtlasRegion controlsRegion;

        public SetupScreenAssets(TextureAtlas atlas){

            musicButtonRegion = atlas.findRegion(Constants.MUSIC_BUTTON);
            soundButtonRegion = atlas.findRegion(Constants.SOUND_BUTTON);
            controlsButtonRegion = atlas.findRegion(Constants.CONTROLS_BUTTON);
            controlsRegion = atlas.findRegion(Constants.CONTROLS);
            onButtonRegion = atlas.findRegion(Constants.ON_BUTTON);
            offButtonRegion = atlas.findRegion(Constants.OFF_BUTTON);

        }

    }

}
