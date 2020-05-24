package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.Gdx;
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
public class RoninPlayer extends AbstractPlayer implements Player {

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

    //RoninPlayer Constructor
    public RoninPlayer(/*Viewport viewport, Level level*/){
//        this.viewport = viewport;
//        this.level = level;
        // Initialize RoninPlayer position with his height
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

        // Apply RoninPlayer velocity
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
                    for (int i = 0; i < Constants.BLOOD_SPLASHES_PER_KILL_R; i++) {
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
            SoundAssetsManager.bbbsounds.get(SoundAssetsManager.S_JUMP_RONIN).play();
        }


        // Call continueJump()
        continueJump();
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
            // If so, find the position of RoninPlayer left and right toes
            float leftFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 5.5f;
            float rightFoot = position.x + Constants.PLAYER_STANCE_WIDTH / 0.7f;


            // See if either of ninjaPlayer's toes are on the ground
            leftFootIn = (ground.left < leftFoot && ground.right > leftFoot);
            rightFootIn = (ground.left < rightFoot && ground.right > rightFoot);

            // See if RoninPlayer is straddling the platform
            straddle = (ground.left > leftFoot && ground.right < rightFoot);

        }
        // Return whether or not RoninPlayer had landed on the ground
        return leftFootIn || rightFootIn || straddle;

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
     * Renderize our ninjaPlayer
     *
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch){

        // Render ninja standing static
        TextureRegion region = Assets.instance.roninAssets.roninStatic;

        if(isAlive){
            // Select the correct sprite based on jumpState, and walkState
            if(attackState == AttackState.ATTACKING){
                region = Assets.instance.roninAssets.roninAttacking;
            } else if(jumpState != JumpState.GROUNDED){
                region = Assets.instance.roninAssets.roninJumping;
            } else if(walkState == WalkState.BLOCKED){
                region = Assets.instance.roninAssets.roninStatic;
            } else if(walkState == WalkState.WALKING){

                // Calculate how long we've been walking in seconds
                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);

                // Select the correct frame from the walking  animation
                region = (TextureRegion) Assets.instance.roninAssets.roninWalkingAnimation.getKeyFrame(walkTimeSeconds);

            }
        }else{
            region = Assets.instance.roninAssets.roninDead;
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
