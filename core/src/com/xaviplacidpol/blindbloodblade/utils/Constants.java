package com.xaviplacidpol.blindbloodblade.utils;




import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    //Screen    16:9 will be extended as required
    public static final float SCREEN_W = 512;
    public static final float SCREEN_H = 288;

    // World
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 480;
    public static final float GRAVITY = 700;
    public static final float CHASE_CAM_MOVE_SPEED = WORLD_SIZE;

    //Restart game position for endless gaming
    public static final float ENDLESS_POSITION = 1000;

    //Kill line when player fall over ground
    public static final float KILL_PLANE = -100;



    public static final String TEXTURE_ATLAS = "images/blindbloodblade.pack.atlas";

    //    public static final String TEXTURE_ATLAS = "images/bbbtextures.atlas";
    //PLAYER
    public static final Vector2 PLAYER_EYE_POSITION = new Vector2(16, 24);
    public static final float PLAYER_EYE_HEIGHT = 16.0f;
    public static final float PLAYER_STANCE_WIDTH = 21.0f;
    public static final float PLAYER_HEAD_HEIGHT = 23.0f;
    public static final float PLAYER_MOVE_SPEED = 200;
    public static final float PLAYER_BLADE_RADIUS = 100f;

    public static final float JUMP_SPEED = 400;
    public static final float MAX_JUMP_DURATION = 0.15f;
    public static final float WALK_LOOP_DURATION = 0.25f;

    public static final float MAX_ATTACK_DURATION = 0.50f;

    //ninja sprites
    public static final String NINJA_STATIC = "ninjastatic";
    public static final String NINJA_JUMPING = "ninjajump";
    public static final String NINJA_WALKING = "ninjawalk";
    public static final String NINJA_ATTACKING = "ninjabloody";
    public static final String NINJA_DEAD = "ninjadead";

    //FLOOR
    // Ground sprite
    public static final String GROUND_SPRITE = "ground";
    // constant holding the size of the stretchable edges in the ground 9 patch
    public static final int GROUND_EDGE = 8;

    //SPIKES
    public static final String SPIKES_SPRITE = "spikes";

    //BRIDGES
    public static final String BRIDGE_SPRITE = "bridge";

    //SPLASH SCREEN
    //Background sprite
    public static final String SPLASH_SPRITE = "background";
    public static final String MAIN_TITLE = "Blind Blood Blade";
    public static final float MAIN_TITLE_SCALE = 1.4f;
    public static final String START_BUTTON = "btstart";
    public static final String SCORE_BUTTON = "btscore";

    // ENEMY
    public static final String ENEMY_SPRITE = "samuraistatic";
    public static final Vector2 ENEMY_POSITION = new Vector2(16, 24);
    public static final float ENEMY_MOVEMENT_SPEED = 0;
    public static final float ENEMY_COLLISION_RADIUS = 15;

    // BLOOD SPLASH
    public static final String BLOOD_SPLASH_SPRITE = "bloodsplash";
    public static final Vector2 BLOOD_SPLASH_CENTER = new Vector2(8, 8);
    public static final float BLOOD_SPLASH_DURATION = 0.50f;
    public static final float BLOOD_SPLASH_OVERLAY_SCALE = 0.2f;

    //SCORE SCREEN
    public static final String SCORE = "SCORE";
    public static final float SCORE_SCREEN_SCALE = 1.0f;
    public static final float SCORES_SCREEN_SCALE = 0.5f;
    public static final String BACK_BUTTON = "backicon";

    public static final String STAGE_BACKGROUND = "backgroundgamestage";

}
