package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Cam;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import java.awt.Event;

public class NinjaPlayer extends InputAdapter {


    public final static String TAG = NinjaPlayer.class.getName();

    //Attributes
    //Vector 2 with x and y position
    Vector2 position;

    //Vector 2 with x and y velocity
    Vector2 velocity;

    // Vector2 to hold ninja's position last frame (useful to verify if is GROUNDED or not)
    Vector2 lastFramePosition;

    //Jump and walking states (enums)
    JumpState jumpState;
    WalkState walkState;

    //long number to control jumping time
    long jumpStartTime;

    //long number to control walking time
    long walkStartTime;

    //Viewport with the cam view
    Viewport viewport;

    //Vector3 with the touched position
    Vector3 touchPosition;

    public NinjaPlayer(){ //Contructor no utilitzat
        // Initialize NinjaPlayer position with his height
        position = new Vector2(20, Constants.PLAYER_EYE_HEIGHT + 40);

        // Initialize a new Vector2 for lastFramePosition
        lastFramePosition = new Vector2(position);

        // Initialize velocity (quiet)
        velocity = new Vector2();

        // Initialize jumpState to falling
        jumpState = JumpState.FALLING;

        // Initialize walkState to Standing
        walkState = WalkState.BLOCKED;
    }

    //NinjaPlayer Constructor with viewport
    public NinjaPlayer(Viewport viewport){
        this.viewport = viewport;
        // Initialize NinjaPlayer position with his height
        position = new Vector2(20, Constants.PLAYER_EYE_HEIGHT + 40);

        // Initialize a new Vector2 for lastFramePosition
        lastFramePosition = new Vector2(position);

        // Initialize velocity (quiet)
        velocity = new Vector2();

        // Initialize jumpState to falling
        jumpState = JumpState.FALLING;

        // Initialize walkState to Standing
        walkState = WalkState.BLOCKED;

        // Initialize touchPosition (empty)
        touchPosition = new Vector3();

    }


    //TODO provisional touch meitat pantalla esquerra per saltar, meitat dreta per atacar, modificar quan tinguem camera
    @Override
    /**
     * Get touchPosition, verify what side of the screen was touched,
     * if left side touched then execute jump(),
     * if right side touched then execute atack()
     */
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2){ //Half left of the screen touched
            // Add a switch statement. If the jump key is pressed and player is GROUNDED, then startJump()
            // If she's JUMPING, then continueJump()
            // If she's falling, then don't do anything
            switch (jumpState){
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                    break;
            }
        }else{
            //TODO ATTACK when touched the right half of the screen
        }

        return true;

    }



    /**
     * Update our ninja every frame
     *
     * It will apply gravity, update all movement, speed, and act if the player do anything with the ninja like jump
     * and attack
     * Check if player is grounded
     * @param delta
     * @param grounds Array with all the ground in the level
     */
    public void update(float delta, Array<Ground> grounds){
        // Update lastFramePosition
        lastFramePosition.set(position);

        // Apply gravity attraction
        // Multiple delta by the acceleration due to gravity and subtract it from player vertical velocity
        velocity.y -= delta * Constants.GRAVITY;

        // Apply NinjaPlayer velocity
        // Vector2.mulAdd() is very convenient.
        position.mulAdd(velocity, delta);

        // If ninjaPlayer isn't JUMPING, make her now FALLING
        if(jumpState != JumpState.JUMPING){
            jumpState = JumpState.FALLING;

            // Check if the ninja has landed on the ground
            // Position keeps track of player eye position, not her feet.
            // If she has indeed landed, change her jumpState to GROUNDED, set her vertical velocity to 0,
            // and make sure her feet aren't sticking into the floor.
            if((position.y - Constants.PLAYER_EYE_HEIGHT) < 0){
                jumpState = JumpState.GROUNDED;
                position.y = Constants.PLAYER_EYE_HEIGHT;
                velocity.y = 0;
            }
            //For each ground, call landedOnGround()
            for (Ground ground : grounds){
                if (landedOnGround(ground)) {
                    //  If true, set jumpState to GROUNDED
                    jumpState = JumpState.GROUNDED;

                    // Set zero vertical velocity
                    velocity.y = 0;

                    // Make sure Ninja's feet aren't sticking into the ground
                    position.y = ground.top + Constants.PLAYER_EYE_HEIGHT;

                    // TODO moure automatic
                    //moveRight(delta);
                }
            }
        }



        // Jumping
        if(Gdx.input.justTouched()){

            // Add a switch statement. If the jump key is pressed and player is GROUNDED, then startJump()
            // If she's JUMPING, then continueJump()
            // If she's falling, then don't do anything
            switch (jumpState){
//                case GROUNDED:
//                    startJump();
//                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                    break;
            }

        }else {
            // If the jump key wasn't pressed, endJump()
            endJump();
        }

        // Moving left, right or standing quiet
        // check if the left/right arrow keys are pressed
        // TODO in Android AUTO RUN
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveLeft(delta);
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
            moveRight(delta);
        } else{
            walkState = WalkState.BLOCKED;
        }


    }

    /**
     * Checks if ninjaPlayer is landed on ground or have his foots out the ground,
     * for example he maybe is falling to the spikes
     * @param ground
     * @return
     */
    boolean landedOnGround(Ground ground){
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        // First check if Players's feet were above the platform top last frame and below the platform top this frame
        if(lastFramePosition.y - Constants.PLAYER_EYE_HEIGHT >= ground.top &&
                position.y - Constants.PLAYER_EYE_HEIGHT < ground.top){
            // If so, find the position of NinjaPlayer left and right toes
            float leftFoot = position.x - Constants.PLAYER_STANCE_WIDTH / 2.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;


            // See if either of ninjaPlayer's toes are on the ground
            leftFootIn = (ground.left < leftFoot && ground.right > leftFoot);
            rightFootIn = (ground.left < rightFoot && ground.right > rightFoot);

            // See if NinjaPlayer is straddling the platform
            straddle = (ground.left > leftFoot && ground.right < rightFoot);

        }
        // Return whether or not NinjaPlayer had landed on the ground
        return leftFootIn || rightFootIn || straddle;

    }

    //TODO
    /**
     * Move constantly to right if player don't collide with any obstacle
     * @param delta
     */
    private void moveRight(float delta){
        // If we are GROUNDED and not BLOCKED, save the walkStartTime
        if(jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING){
            walkStartTime = TimeUtils.nanoTime();
        }

        //Set walkState to WALKING
        walkState = WalkState.WALKING;

        // Move player right by delta * movement speed
        position.x += delta * Constants.PLAYER_MOVE_SPEED;
    }

    /**
     * TODO Delete this method when automatic runner implemented
     * @param delta
     */
    private void moveLeft(float delta){
        if(jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING){
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = WalkState.WALKING;

        position.x -= delta * Constants.PLAYER_MOVE_SPEED;

    }



    /**
     * startJump()
     * set jumpstate to JUMPING
     * Set the jump start time
     * Call continueJump
     */
    private void startJump(){
        // Set jumpState to JUMPING
        jumpState = JumpState.JUMPING;

        // Set the jump start time
        // Using TimeUtils.nanoTime()
        jumpStartTime = TimeUtils.nanoTime();

        // Call continueJump()
        continueJump();
    }

    /**
     * Check if we're JUMPING
     * If we are, find out how long we've been jumping
     *  If we have been jumping for less than the max jump duration, set player's vertical speed to the jump speed constant
     *  Else, call endJump()
     */
    private void continueJump(){
        // First, check if we're JUMPING, if not, just return
        if(jumpState == JumpState.JUMPING){
            // Find out how long we've been jumping
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);

            // If we have been jumping for less than the max jump duration, set player's vertical speed to the jump
            // speed constant
            // Else, call endJump()
            if(jumpDuration < Constants.MAX_JUMP_DURATION){
                velocity.y = Constants.JUMP_SPEED;
            } else{
                endJump();
            }
        }
    }

    /**
     * If we're JUMPING and ended the jump, now we're FALLING
     */
    private void endJump(){
        // If we're JUMPING, now we're FALLING
        if(jumpState == JumpState.JUMPING){
            jumpState = JumpState.FALLING;
        }
    }

    /**
     * Renderize our ninjaPlayer
     *
     * @param batch
     */
    public void render(SpriteBatch batch){

        // Render ninja standing static
        TextureRegion region = Assets.instance.ninjaAssets.ninjaStatic;

        // Select the correct sprite based on jumpState, and walkState

        if(jumpState != JumpState.GROUNDED){
            region = Assets.instance.ninjaAssets.ninjaJumping;
        } else if(walkState == WalkState.BLOCKED){
            region = Assets.instance.ninjaAssets.ninjaStatic;
        } else if(walkState == WalkState.WALKING){

            // Calculate how long we've been walking in seconds
            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);

            // Select the correct frame from the walking  animation
            region = (TextureRegion) Assets.instance.ninjaAssets.ninjaWalkingAnimation.getKeyFrame(walkTimeSeconds);

        }

        batch.draw(
                region.getTexture(),
                position.x - Constants.PLAYER_EYE_POSITION.x,
                position.y - Constants.PLAYER_EYE_POSITION.y,
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
                false
        );

    }

    /**
     * enum list containing jumping states of ninja player
     */
    enum JumpState{
        JUMPING,
        FALLING,
        GROUNDED
    }


    /**
     * Enum list, player is walking or blocked
     */
    enum WalkState{
        BLOCKED,
        WALKING
    }
}