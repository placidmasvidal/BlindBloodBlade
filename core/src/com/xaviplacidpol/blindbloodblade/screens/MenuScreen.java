package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xaviplacidpol.blindbloodblade.utils.Constants;


public class MenuScreen extends ScreenAdapter {


    private SpriteBatch batch;
    private Texture btStart;
    private int btStartPositionX;
    private int btStartPositionY;
    private Texture btScore;
//    private int btScorePositionX;
//    private int btScorePositionY;
    private float btScorePositionX;
    private float btScorePositionY;

    private ExtendViewport viewport;



    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        btStart = new Texture("btstart.png");


        btStartPositionX = 10;
        btStartPositionY = 10;

        btScore = new Texture("btscore.png");

    }

    @Override
    public void render(float delta) {
        viewport.apply();
        //set background color and transparency
        Gdx.gl.glClearColor(1,0,0,1);
        //clean previous residual content from the bit buffer of the graphic card
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(viewport.getCamera().combined);

        //batch start point, SpriteBatch will draw (or any other thing) to everything from begin to end
        batch.begin();

        batch.draw(btStart, 30, 60, 120, 30);
        batch.draw(btScore, 30, 30, 120, 30);


//        //detect if any kind of input occurred over button zone
//        if(Gdx.input.getX() < btStartPositionX + btStart.getWidth() && (BaseScreen.getScreenHeight() -
//        Gdx.input.getY()) < btStartPositionY + btStart.getHeight() && BaseScreen.getScreenHeight()-Gdx.input.getY() > btStartPositionY){
//
//            //detect if an input (finger click) occurred, we're now inside button zone
//            if(Gdx.input.isTouched()){
//  //              batch.draw(btStart, btStartPositionX, btStartPositionY - (btStart.getHeight()/5), btStart.getWidth(), btStart.getHeight()+(btStart.getHeight()/4));
//
//                //destruct current screen
//                this.dispose();
//                //load a GameScreen instance
//                game.setScreen(new GameScreen(game));
//            }
//
//        }
//
//        if(Gdx.input.getX() < btScorePositionX + btScore.getWidth() && (BaseScreen.getScreenHeight() -
//                Gdx.input.getY()) < btScorePositionY + btScore.getHeight() && BaseScreen.getScreenHeight()-Gdx.input.getY() > btScorePositionY) {
//            Gdx.app.exit();
//        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }



    @Override
    public void dispose() {
        btScore.dispose();
        btStart.dispose();
        batch.dispose();
    }
}
