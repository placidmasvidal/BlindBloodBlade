package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

public class NinjaPlayer extends Actor {
    public final static String TAG = NinjaPlayer.class.getName();

    //Attributes
    //Vector 2 with x and y position
    Vector2 position;

    //Vector 2 with x and y velocity
    Vector2 velocity;

    //Jump and walking states (enums)
    JumpState jumpState;
    WalkState walkState;

    //long number to control jumping time
    long jumpStartTime;

    //long number to control walking time
    long walkStartTime;

    public NinjaPlayer(){
        // Initialize NinjaPlayer position with his height
        position = new Vector2(20, Constants.PLAYER_EYE_HEIGHT);

        // Initialize velocity (quiet)
        velocity = new Vector2();

        // Initialize jumpState to falling
        jumpState = JumpState.FALLING;

        // Initialize walkState to Standing
        walkState = WalkState.BLOCKED;
    }

    /**
     * Update our ninja every frame
     *
     * It will apply gravity, update all movement, speed, and act if the player do anything with the ninja like jump
     * and attack
     * @param delta
     */
    public void update(float delta){
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
        }


        // Jumping
        if(Gdx.input.justTouched()){

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

        }else {
            // If the jump key wasn't pressed, endJump()
            endJump();
        }

        // Moving left, right or standing quiet
        // check if the left/right arrow keys are pressed
        // TODO in Android
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveLeft(delta);
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveRight(delta);
        } else{
            walkState = WalkState.BLOCKED;
        }


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
            //Before 2.2.10 (NO ANIMATION)
            //region = Assets.instance.gigaGalAssets.walkingRight;

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
