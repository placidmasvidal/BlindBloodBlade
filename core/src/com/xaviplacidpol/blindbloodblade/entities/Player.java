package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.scenes.Level;

public interface Player extends InputProcessor {

    public void setViewport(Viewport viewport);
    public void setLevel(Level level);

    Integer getPlayerId();

    public Integer getScore();
    public void setScore(Integer score);
    public Integer getKills();
    public void setKills(Integer kills);
    public float getTimeLive();
    public void setTimeLive(float timeLive);
    public Vector2 getPosition();
    public void setPosition(Vector2 position);
    public Vector2 getVelocity();
    public void setVelocity(Vector2 velocity);
    public boolean isAlive();
    public void setAlive(boolean alive);

    /**
     * Modify state of ninja to attacking
     * Modify actual sprite to ninja attack
     */
    public void startAttack();


    /**
     * Checks time since the attack started, apply corresponding sprite to the ninja depending
     * if it reached the max duration or not
     */
    public void continueAttacking();


    /**
     * Update our ninja every frame
     * <p>
     * It will apply gravity, update all movement, speed, and act if the player do anything with the ninja like jump
     * and attack
     * Check if player is grounded
     *
     * @param delta
     * @param grounds Array with all the ground in the level
     */
    public void update(float delta, Array<Ground> grounds, Array<Bridge> bridges);

    /**
     * Checks if ninjaPlayer is landed on ground or have his foots out the ground,
     * for example he maybe is falling to the spikes
     *
     * @param ground piece of ground where to check if player is landing
     * @return true if player is landed on the ground, false otherwise
     */
    boolean landedOnGround(Ground ground);

    /**
     * Checks if ninjaPlayer is landed on bridge or have his foots out the bridge
     *
     * @param bridge where to check if player is landing
     * @return
     */
    boolean landedOnBridge(Bridge bridge);


    /**
     * Checks if ninjaPlayer had fall out to a spike
     *
     * @param spikes where to check if player are crossing them
     * @return true if ninjaPlayer had fall to the spike,
     * false otherwise
     */
    boolean landedOnSpikes(Spikes spikes);

    /**
     * Move constantly to right if player don't collide with any obstacle
     *
     * @param delta
     */
    public void moveRight(float delta);

    /**
     * startJump()
     * set jumpState to JUMPING
     * Set the jump start time
     * Call continueJump
     */
    public void startJump();

    /**
     * Check if we're JUMPING
     * If we are, find out how long we've been jumping
     * If we have been jumping for less than the max jump duration, set player's vertical speed to the jump speed constant
     * Else, call endJump()
     */
    public void continueJump();

    /**
     * If we're JUMPING and ended the jump, now we're FALLING
     */
    public void endJump();

    /**
     * Renderize our ninjaPlayer
     *
     * @param batch
     */
    public void render(SpriteBatch batch);

    @Override
    boolean keyDown(int keycode);

    @Override
    boolean keyUp(int keycode);

    @Override
    boolean keyTyped(char character);

    @Override
    boolean touchDown(int screenX, int screenY, int pointer, int button);

    @Override
    boolean touchUp(int screenX, int screenY, int pointer, int button);

    @Override
    boolean touchDragged(int screenX, int screenY, int pointer);

    @Override
    boolean mouseMoved(int screenX, int screenY);

    @Override
    boolean scrolled(int amount);

    void setGame(BlindBloodBlade game);
}