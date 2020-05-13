package com.xaviplacidpol.blindbloodblade.utils;




import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    // World
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 460;
    public static final float GRAVITY = 100;

    //Kill line when player fall over ground
    public static final float KILL_PLANE = -100;



    public static final String TEXTURE_ATLAS = "images/blindbloodblade.pack.atlas";

    //PLAYER
    public static final Vector2 PLAYER_EYE_POSITION = new Vector2(16, 24);
    public static final float PLAYER_EYE_HEIGHT = 16.0f;
    public static final float PLAYER_STANCE_WIDTH = 21.0f;
    public static final float PLAYER_HEAD_HEIGHT = 23.0f;
    public static final float PLAYER_MOVE_SPEED = 100;

    public static final float JUMP_SPEED = 200;
    public static final float MAX_JUMP_DURATION = 0.1f;
    public static final float WALK_LOOP_DURATION = 0.25f;

    //ninja sprites
    public static final String NINJA_STATIC = "ninjastatic";
    public static final String NINJA_JUMPING = "ninjajump";
    public static final String NINJA_WALKING = "ninjamove";



}
