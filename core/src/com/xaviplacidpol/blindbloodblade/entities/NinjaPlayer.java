package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.scenes.Level;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;

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
    //Attack state (enum)
    AttackState attackState;

    //long number to control jumping time
    long jumpStartTime;

    //long number to control walking time
    long walkStartTime;
    
    //long number to control attacking time
    long attackStartTime;

    //Viewport with the cam view
    Viewport viewport;

    //Vector3 with the touched position
    Vector3 touchPosition;

    //Boolean control if player is alive
    boolean isAlive;

    //Level where ninja is playing
    Level level;

    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    private Integer kills;

    public float getTimeLive() {
        return timeLive;
    }

    public void setTimeLive(float timeLive) {
        this.timeLive = timeLive;
    }

    private float timeLive;

    //Booleans to control player Versus enemies battles
    boolean attackColliding;
    boolean enemyAttackColliding;

    //NinjaPlayer
    public NinjaPlayer(Viewport viewport){
        this.viewport = viewport;
        // Initialize NinjaPlayer position with his height
        position = new Vector2(200, Constants.PLAYER_EYE_HEIGHT + 40);

        // Initialize a new Vector2 for lastFramePosition
        lastFramePosition = new Vector2(position);

        // Initialize velocity (quiet)
        velocity = new Vector2();

        // Initialize jumpState to falling
        jumpState = JumpState.FALLING;

        // Initialize walkState to Standing
        walkState = WalkState.BLOCKED;

        // Initialize attackState to Not Attacking
        attackState = AttackState.NOT_ATTACKING;

        // Initialize touchPosition (empty)
        touchPosition = new Vector3();

        //Player is alive
        isAlive = true;

        timeLive = 0;
        kills = 0;
        score = 0;

    }

    public NinjaPlayer(Viewport viewport, Level level){
        this.viewport = viewport;
        this.level = level;
        // Initialize NinjaPlayer position with his height
        position = new Vector2(200, Constants.PLAYER_EYE_HEIGHT + 40);
//        position = new Vector2(Constants.SCREEN_W/2, Constants.PLAYER_EYE_HEIGHT + 40);
        // Initialize a new Vector2 for lastFramePosition
        lastFramePosition = new Vector2(position);

        // Initialize velocity (quiet)
        velocity = new Vector2();

        // Initialize jumpState to falling
        jumpState = JumpState.FALLING;

        // Initialize walkState to Standing
        walkState = WalkState.BLOCKED;

        // Initialize attackState to Not Attacking
        attackState = AttackState.NOT_ATTACKING;

        // Initialize touchPosition (empty)
        touchPosition = new Vector3();

        //Player is alive
        isAlive = true;

        timeLive = 0;
        kills = 0;
        score = 0;

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

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
        }else{ //ATTACK when touched the right half of the screen
            switch (attackState){
                case ATTACKING:
                    
                    break;
                case NOT_ATTACKING:
                    startAttack();
                    break;
            }
        }

        return true;

    }

    /**
     *  Modify state of ninja to attacking
     *  Modify actual sprite to ninja attack 
     */
    private void startAttack() {
        // Set ninja attackState to ATTACKING
        attackState = AttackState.ATTACKING;

        // Set the attackState start time using TimeUtils.nanoTime()
        attackStartTime = TimeUtils.nanoTime();

        SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_ATTACK).play();
        SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_ATTACKING).play();

        // Call continueAttacking()
        continueAttacking();
    }

    /**
     * Checks time since the attack started, apply corresponding sprite to the ninja depending
     * if it reached the max duration or not
     */
    private void continueAttacking() {

        // First, check if we're ATTACKING
        if(attackState == AttackState.ATTACKING){
            // Find out how long we've been jumping
            float attackDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - attackStartTime);

            // Set corresponding attackState depending if it reached the max attack duration
            if(attackDuration < Constants.MAX_ATTACK_DURATION){
                attackState = AttackState.ATTACKING;
            } else{
                attackState = AttackState.NOT_ATTACKING;
            }
        }//else just return
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
    public void update(float delta, Array<Ground> grounds, Array<Bridge> bridges){

        timeLive += delta;
        if(timeLive > 1) {
            score = score + 10;
            timeLive = 0;
        }

        // Update lastFramePosition
        lastFramePosition.set(position);

        // Apply gravity attraction
        // Multiple delta by the acceleration due to gravity and subtract it from player vertical velocity
        velocity.y -= delta * Constants.GRAVITY;

        // Apply NinjaPlayer velocity
        // Vector2.mulAdd() is very convenient.
        position.mulAdd(velocity, delta);

        //Check if ninja is attacking
        continueAttacking();

        continueJump();

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

        //Collide with spikes
        for(Spikes spikes : level.getSpikes()){
            if(landedOnSpikes(spikes)){ //If collided to spikes, then dies, otherwise return
                SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_SPIKE_DEAD).play();
                isAlive = false;
            }
        }

            //For each bridge, call landedOnBridge()
            for (Bridge bridge : bridges){
                if (landedOnBridge(bridge)) {
                    //  If true, set jumpState to GROUNDED
                    jumpState = JumpState.GROUNDED;

                    // Set zero vertical velocity
                    velocity.y = 0;

                    // Make sure Ninja's feet aren't sticking into the ground
                    position.y = bridge.top + Constants.PLAYER_EYE_HEIGHT;
                }
            }

//	int i = 0;
        // Collide with enemies, kill them or die
        for (Enemy enemy : level.getEnemies()) {
            //Save attackColliding
            attackColliding = ((enemy.getPosition().x - position.x ) < Constants.PLAYER_BLADE_RADIUS) && (enemy.getPosition().x - position.x > 0);
            enemyAttackColliding = ((enemy.getPosition().x - position.x ) < Constants.ENEMY_COLLISION_RADIUS) && (enemy.getPosition().x - position.x > 0);
            //System.out.println("Colliding = " + attackColliding);

            if(enemy.isAlive()) {
                if (attackColliding && (attackState == AttackState.ATTACKING)) {
//                System.out.println("enemy mort");
                    enemy.setAlive(false);
//                level.getEnemies().removeIndex(i);
                    kills++;
                    score = score + 50;

                    //Add a bloodSplash where the enemy died
                    SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_BLOOD_SPLASH).play();
                    level.spawnBloodSplash(new Vector2(enemy.getPosition().x, enemy.getPosition().y));

                    //Add a bloodSplash to the bloodSplashOverlay
                    for (int i = 0; i < 10; i++) {
                        //Add a bloodSplash to the bloodSplashOverlay
                        level.addBloodSplash();
                    }

                    enemy.setAlive(false);
                } else { //Ninja dies if contact with the enemy
                    if (enemyAttackColliding && (attackState == AttackState.NOT_ATTACKING) && enemy.isAlive()) {
                        isAlive = false;
                        //TODO endgame // Que fem quan el ninja mort
                    }
                }
            }
//            i++;
//            System.out.println("Enemies after kill:"+ level.getEnemies().size);
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

        // Moving right automatic
        // check if the left/right arrow keys are pressed
        moveRight(delta);

        // TODO in Android AUTO RUN
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//            moveLeft(delta);
//        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
//            moveRight(delta);
//        } else{
//            walkState = WalkState.BLOCKED;
//        }


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
            float leftFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 5.5f;
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

    /**
     * Checks if ninjaPlayer is landed on bridge or have his foots out the bridge,
     * for example he maybe is falling to the bridge
     * @param bridge
     * @return
     */
    boolean landedOnBridge(Bridge bridge){
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        // First check if Players's feet were above the platform top last frame and below the platform top this frame
        if(lastFramePosition.y - Constants.PLAYER_EYE_HEIGHT >= bridge.top &&
                position.y - Constants.PLAYER_EYE_HEIGHT < bridge.top){
            // If so, find the position of NinjaPlayer left and right toes
            float leftFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 5.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;

            // See if either of ninjaPlayer's toes are on the ground
            leftFootIn = (bridge.left < leftFoot && bridge.right > leftFoot);
            rightFootIn = (bridge.left < rightFoot && bridge.right > rightFoot);

            // See if NinjaPlayer is straddling the platform
            straddle = (bridge.left > leftFoot && bridge.right < rightFoot);

        }
        // Return whether or not NinjaPlayer had landed on the ground
        return leftFootIn || rightFootIn || straddle;
    }


    /**
     * Checks if ninjaPlayer had fall out to a spike
     * @param spikes
     * @return
     */
    boolean landedOnSpikes(Spikes spikes){
//        boolean leftFootIn = false;
//        boolean rightFootIn = false;
        boolean straddle = false;

        // First check if Players's feet were above the spikes top last frame and below the spikes top this frame
        if(lastFramePosition.y - Constants.PLAYER_EYE_HEIGHT >= spikes.position.y + 1 &&
                position.y - Constants.PLAYER_EYE_HEIGHT < spikes.position.y + 40){
            // If so, find the position of NinjaPlayer left and right toes
            float leftFoot = position.x - Constants.PLAYER_STANCE_WIDTH / 2.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;


//            // See if either of ninjaPlayer's toes are on the ground
//            leftFootIn = (ground.left < leftFoot && ground.right > leftFoot);
//            rightFootIn = (ground.left < rightFoot && ground.right > rightFoot);

            // See if NinjaPlayer is straddling the spikes
            straddle = (spikes.position.x < leftFoot && spikes.position.x + 70 > rightFoot);
            //System.out.println(" NinjaPlayer linia 401 left foot: " + leftFoot);


        }
        //return leftFootIn || rightFootIn || straddle;
        // Return if NinjaPlayer had landed or not on the spike
        return straddle;
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

        // Move player right by delta * movement speed if is alive
        if(isAlive){
            position.x += delta * Constants.PLAYER_MOVE_SPEED;
        }else{
            position.x += 0;
        }
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

        SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_JUMP).play();

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

        if(isAlive){
            // Select the correct sprite based on jumpState, and walkState
            if(attackState == AttackState.ATTACKING){
                region = Assets.instance.ninjaAssets.ninjaAttacking;
            } else if(jumpState != JumpState.GROUNDED){
                region = Assets.instance.ninjaAssets.ninjaJumping;
            } else if(walkState == WalkState.BLOCKED){
                region = Assets.instance.ninjaAssets.ninjaStatic;
            } else if(walkState == WalkState.WALKING){

                // Calculate how long we've been walking in seconds
                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);

                // Select the correct frame from the walking  animation
                region = (TextureRegion) Assets.instance.ninjaAssets.ninjaWalkingAnimation.getKeyFrame(walkTimeSeconds);

            }
        }else{
            region = Assets.instance.ninjaAssets.ninjaDead;
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

    /**
     * Enum list, player is attacking or notattacking
     */
    enum AttackState{
        ATTACKING,
        NOT_ATTACKING
    }
}
