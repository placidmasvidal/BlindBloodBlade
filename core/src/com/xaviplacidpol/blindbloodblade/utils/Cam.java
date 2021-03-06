package com.xaviplacidpol.blindbloodblade.utils;

import com.badlogic.gdx.graphics.Camera;
import com.xaviplacidpol.blindbloodblade.entities.Player;

public class Cam {
    public Camera camera;
    public Player target;

    public Cam(Camera camera, Player target) {
        this.camera = camera;
        this.target = target;
    }
    public Cam() {
        camera = new Camera() {
            @Override
            public void update() {
            }

            @Override
            public void update(boolean updateFrustum) {
            }
        };
    }

    /**
     * Update the current position of the ninja
     * @param delta the time span between the current frame and the last frame in seconds.
     */
    public void update(float delta) {
        if(target.isAlive()){
            camera.position.x += delta * Constants.PLAYER_MOVE_SPEED;
        }else {
            //If ninja is dead, don't move the cam
            camera.position.x += 0;
        }

    }
}
