package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;
import com.xaviplacidpol.blindbloodblade.utils.Utils;

/**
 * Enemy characters
 */
public class Enemy {
    private Vector2 position;

    private boolean isAlive;

    /**
     * Constructor.
     * Set initial position and alive
     * @param position
     */
    public Enemy(Vector2 position){
        this.position = position;
        this.isAlive = true;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    //TODO Actualitzar quan el samurai tingui que atacar
    public void update(float delta) {
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Render the enemy
     * @param batch
     */
    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.enemyAssets.samuraistatic;
        //Draw the enemy
        Utils.drawTextureRegion(batch, region, position.x - Constants.ENEMY_POSITION.x, position.y - Constants.ENEMY_POSITION.y);
    }


}
