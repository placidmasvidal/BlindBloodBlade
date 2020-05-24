package com.xaviplacidpol.blindbloodblade.entities;

import   com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.scenes.Level;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.SetupValues;
import com.xaviplacidpol.blindbloodblade.utils.SoundAssetsManager;
import com.xaviplacidpol.blindbloodblade.utils.Utils;

/**
 * Main Player
 */
public class AutomataPlayer extends InputAdapter implements Player {

    //ATTRIBUTES
    //Vector 2 with x and y position
    private Vector2 position;

    //Vector 2 with x and y velocity
    private Vector2 velocity;

    // Vector2 to hold ninja's position last frame (useful to verify if is GROUNDED or not)
    private Vector2 lastFramePosition;

    //Different states of the ninja (enums)
    private JumpState jumpState;
    private WalkState walkState;
    private AttackState attackState;

    //long number to control jumping time
    private long jumpStartTime;

    //long number to control walking time
    private long walkStartTime;

    //long number to control attacking time
    private long attackStartTime;

    //Viewport with the cam view
    private Viewport viewport;

    //Boolean control if player is alive
    private boolean isAlive;

    //Level where ninja is playing
    private Level level;

    private Integer score;


    private Integer kills;

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public Integer getKills() {
        return kills;
    }

    @Override
    public void setKills(Integer kills) {
        this.kills = kills;
    }

    @Override
    public float getTimeLive() {
        return timeLive;
    }

    @Override
    public void setTimeLive(float timeLive) {
        this.timeLive = timeLive;
    }

    private float timeLive;

    //Booleans to control player Versus enemies battles
    boolean attackColliding;
    boolean enemyAttackColliding;

    //AutomataPlayer Constructor
    public AutomataPlayer(/*Viewport viewport, Level level*/){
//        this.viewport = viewport;
//        this.level = level;
        // Initialize AutomataPlayer position with his height
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

        //Player is alive
        isAlive = true;

        timeLive = 0;
        kills = 0;
        score = 0;

    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    @Override
    public Vector2 getVelocity() {
        return velocity;
    }
    @Override
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    @Override
    public boolean isAlive() {
        return isAlive;
    }
    @Override
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

    @Override
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     *  Modify state of ninja to attacking
     *  Modify actual sprite to ninja attack
     */
    @Override
    public void startAttack() {
        // Set ninja attackState to ATTACKING
        attackState = AttackState.ATTACKING;

        // Set the attackState start time using TimeUtils.nanoTime()
        attackStartTime = TimeUtils.nanoTime();

        if(SetupValues.sound) {
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_ATTACK).play();
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_ATTACKING).play();
        }/* else {
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_ATTACK).stop();
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_ATTACKING).stop();
        }*/




        // Call continueAttacking()
        continueAttacking();
    }

    /**
     * Checks time since the attack started, apply corresponding sprite to the ninja depending
     * if it reached the max duration or not
     */
    @Override
    public void continueAttacking() {

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
    @Override
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

        // Apply AutomataPlayer velocity
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

                }
            }
        }

        //Collide with spikes
        for(Spikes spikes : level.getSpikes()){
            if(landedOnSpikes(spikes)){ //If collided to spikes, then dies, otherwise return
                if(SetupValues.sound) {
                    SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_SPIKE_DEAD).play();
                }

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
            attackColliding = ((enemy.getPosition().x - position.x ) < Constants.PLAYER_BLADE_RADIUS)
                    && (enemy.getPosition().x - position.x > 0)
                    && (enemy.getPosition().y - position.y < Constants.PLAYER_HEAD_HEIGHT) //Control if player is under the enemy
                    && (position.y - enemy.getPosition().y < Constants.PLAYER_HEAD_HEIGHT); // Control if player is over the enemy
            enemyAttackColliding = ((enemy.getPosition().x - position.x ) < Constants.ENEMY_COLLISION_RADIUS)
                    && (enemy.getPosition().x - position.x > 0) //Control if player passed the X position of the enemy
                    && (enemy.getPosition().y - position.y < Constants.PLAYER_HEAD_HEIGHT) //Control if player is under the enemy
                    && (position.y - enemy.getPosition().y < Constants.PLAYER_HEAD_HEIGHT); // Control if player is over the enemy

            //System.out.println("Colliding = " + attackColliding);

            if(enemy.isAlive()) {
                if (attackColliding && (attackState == AttackState.ATTACKING)) {
//                System.out.println("enemy mort");
                    enemy.setAlive(false);
//                level.getEnemies().removeIndex(i);
                    kills++;
                    score = score + 50;

                    //Add a bloodSplash where the enemy died
                    if(SetupValues.sound) {
                        SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_BLOOD_SPLASH).play();
                    }

                    level.spawnBloodSplash(new Vector2(enemy.getPosition().x, enemy.getPosition().y));

//TODO
                    //Add a bloodSplash to the bloodSplashOverlay
                    for (int i = 0; i < Constants.BLOOD_SPLASHES_PER_KILL_A; i++) {
                        //Add a bloodSplash to the bloodSplashOverlay
                        level.addBloodSplash();
                    }

                    enemy.setAlive(false);
                } else { //Ninja dies if contact with the enemy
                    if (enemyAttackColliding && (attackState == AttackState.NOT_ATTACKING) && enemy.isAlive()) {
                        isAlive = false;
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

    }

    /**
     * Checks if ninjaPlayer is landed on ground or have his foots out the ground,
     * for example he maybe is falling to the spikes
     * @param ground piece of ground where to check if player is landing
     * @return true if player is landed on the ground, false otherwise
     */
    @Override
    public boolean landedOnGround(Ground ground){
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        // First check if Players's feet were above the platform top last frame and below the platform top this frame
        if(lastFramePosition.y - Constants.PLAYER_EYE_HEIGHT >= ground.top &&
                position.y - Constants.PLAYER_EYE_HEIGHT < ground.top){
            // If so, find the position of AutomataPlayer left and right toes
            float leftFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 5.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;


            // See if either of ninjaPlayer's toes are on the ground
            leftFootIn = (ground.left < leftFoot && ground.right > leftFoot);
            rightFootIn = (ground.left < rightFoot && ground.right > rightFoot);

            // See if AutomataPlayer is straddling the platform
            straddle = (ground.left > leftFoot && ground.right < rightFoot);

        }
        // Return whether or not AutomataPlayer had landed on the ground
        return leftFootIn || rightFootIn || straddle;

    }

    /**
     * Checks if ninjaPlayer is landed on bridge or have his foots out the bridge
     * @param bridge where to check if player is landing
     * @return
     */
    @Override
    public boolean landedOnBridge(Bridge bridge){
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        // First check if Players's feet were above the platform top last frame and below the platform top this frame
        if(lastFramePosition.y - Constants.PLAYER_EYE_HEIGHT >= bridge.top &&
                position.y - Constants.PLAYER_EYE_HEIGHT < bridge.top){
            // If so, find the position of AutomataPlayer left and right toes
            float leftFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 5.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;

            // See if either of ninjaPlayer's toes are on the ground
            leftFootIn = (bridge.left < leftFoot && bridge.right > leftFoot);
            rightFootIn = (bridge.left < rightFoot && bridge.right > rightFoot);

            // See if AutomataPlayer is straddling the platform
            straddle = (bridge.left > leftFoot && bridge.right < rightFoot);

        }
        // Return whether or not AutomataPlayer had landed on the ground
        return leftFootIn || rightFootIn || straddle;
    }


    /**
     * Checks if ninjaPlayer had fall out to a spike
     * @param spikes where to check if player are crossing them
     * @return true if ninjaPlayer had fall to the spike,
     *          false otherwise
     */
    @Override
    public boolean landedOnSpikes(Spikes spikes){

        boolean straddle = false;

        // First check if Players is above the spikes top last frame and below the spikes bottom frame
        if(lastFramePosition.y - Constants.PLAYER_EYE_HEIGHT >= spikes.position.y
                && position.y - Constants.PLAYER_EYE_HEIGHT < spikes.position.y + 40){
            // If so, find the position of AutomataPlayer left and right toes
            float leftFoot = position.x - Constants.PLAYER_STANCE_WIDTH / 2.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;

            // See if AutomataPlayer is straddling the spikes
            straddle = (spikes.position.x < leftFoot && spikes.position.x + 70 > rightFoot);

        }
        // Return if AutomataPlayer had landed or not on the spike
        return straddle;
    }

    /**
     * Move constantly to right if player don't collide with any obstacle
     * @param delta
     */
    @Override
    public void moveRight(float delta){
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
     * startJump()
     * set jumpState to JUMPING
     * Set the jump start time
     * Call continueJump
     */
    @Override
    public void startJump(){
        // Set jumpState to JUMPING
        jumpState = JumpState.JUMPING;

        // Set the jump start time
        // Using TimeUtils.nanoTime()
        jumpStartTime = TimeUtils.nanoTime();

        if(SetupValues.sound) {
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_JUMP_AUTOMATA).play();
        }


        // Call continueJump()
        continueJump();
    }

    /**
     * Check if we're JUMPING
     * If we are, find out how long we've been jumping
     *  If we have been jumping for less than the max jump duration, set player's vertical speed to the jump speed constant
     *  Else, call endJump()
     */
    @Override
    public void continueJump(){
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
    @Override
    public void endJump(){
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
    @Override
    public void render(SpriteBatch batch){

        // Render ninja standing static
        TextureRegion region = Assets.instance.automataAssets.automataStatic;
        if(isAlive){
            // Select the correct sprite based on jumpState, and walkState
            if(attackState == AttackState.ATTACKING){
                region = Assets.instance.automataAssets.automataAttacking;
            } else if(jumpState != JumpState.GROUNDED){
                region = Assets.instance.automataAssets.automataJumping;
            } else if(walkState == WalkState.BLOCKED){
                region = Assets.instance.automataAssets.automataStatic;
            } else if(walkState == WalkState.WALKING){

                // Calculate how long we've been walking in seconds
                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);

                // Select the correct frame from the walking  animation
                region = (TextureRegion) Assets.instance.automataAssets.ninjaWalkingAnimation.getKeyFrame(walkTimeSeconds);

            }
        }else{
            region = Assets.instance.automataAssets.automataDead;
        }

        Utils.drawTextureRegion(batch, region, position.x - Constants.PLAYER_EYE_POSITION.x, position.y - Constants.PLAYER_EYE_POSITION.y );

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
