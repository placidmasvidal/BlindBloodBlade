package com.xaviplacidpol.blindbloodblade.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

public class Enemy {
    private Vector2 position;

    public Enemy(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    //TODO Actualitzar quan el samurai tingui que atacar
    public void update(float delta) {
    }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.enemyAssets.samuraistatic;
        //Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER);
        batch.draw(
                region.getTexture(),
                position.x - Constants.ENEMY_POSITION.x,
                position.y - Constants.ENEMY_POSITION.y,
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


}
