package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.scenes.Level;

public class RoninPlayer extends InputAdapter implements Player {
    @Override
    public void setViewport(Viewport viewport) {

    }

    @Override
    public void setLevel(Level level) {

    }

    @Override
    public Integer getScore() {
        return null;
    }

    @Override
    public void setScore(Integer score) {

    }

    @Override
    public Integer getKills() {
        return null;
    }

    @Override
    public void setKills(Integer kills) {

    }

    @Override
    public float getTimeLive() {
        return 0;
    }

    @Override
    public void setTimeLive(float timeLive) {

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public void setPosition(Vector2 position) {

    }

    @Override
    public Vector2 getVelocity() {
        return null;
    }

    @Override
    public void setVelocity(Vector2 velocity) {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void setAlive(boolean alive) {

    }

    @Override
    public void startAttack() {

    }

    @Override
    public void continueAttacking() {

    }

    @Override
    public void update(float delta, Array<Ground> grounds, Array<Bridge> bridges) {

    }

    @Override
    public boolean landedOnGround(Ground ground) {
        return false;
    }

    @Override
    public boolean landedOnBridge(Bridge bridge) {
        return false;
    }

    @Override
    public boolean landedOnSpikes(Spikes spikes) {
        return false;
    }

    @Override
    public void moveRight(float delta) {

    }

    @Override
    public void startJump() {

    }

    @Override
    public void continueJump() {

    }

    @Override
    public void endJump() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
