package com.xaviplacidpol.blindbloodblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;

public class MenuScreen extends BaseScreen {

    private int screenWidth = BaseScreen.getScreenWidth();
    private int screenHeight = BaseScreen.getScreenHeight();

    private SpriteBatch batch;
    private Texture btStart;
    private int btStartPositionX;
    private int btStartPositionY;
    private Texture btScore;
    private int btScorePositionX;
    private int btScorePositionY;

    public MenuScreen(BlindBloodBlade game){
        super(game);
        batch = new SpriteBatch();
        btStart = new Texture("btstart.png");
        btStartPositionX = screenWidth/3;
        btStartPositionY = screenHeight/3;
        btScore = new Texture("btscore.png");
        btScorePositionX = screenWidth/3;
        btScorePositionY = screenHeight / 3 - (btStart.getHeight()/2)-(btStart.getHeight()/3);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //set background color and transparency
        Gdx.gl.glClearColor(1,0,0,1);
        //clean previous residual content from the bit buffer of the graphic card
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //batch start point, SpriteBatch will draw (or any other thing) to everything from begin to end
        batch.begin();

        //draw our textures using the most simple way
        batch.draw(btStart, btStartPositionX, btStartPositionY);
        batch.draw(btScore, btScorePositionX, btScorePositionY);

        //detect if any kind of input occurred over button zone
        if(Gdx.input.getX() < btStartPositionX + btStart.getWidth() && (BaseScreen.getScreenHeight() -
        Gdx.input.getY()) < btStartPositionY + btStart.getHeight() && BaseScreen.getScreenHeight()-Gdx.input.getY() > btStartPositionY){

            //detect if an input (finger click) occurred, we're now inside button zone
            if(Gdx.input.isTouched()){
  //              batch.draw(btStart, btStartPositionX, btStartPositionY - (btStart.getHeight()/5), btStart.getWidth(), btStart.getHeight()+(btStart.getHeight()/4));

                //destruct current screen
                this.dispose();
                //load a GameScreen instance

                //game.setScreen(new GameScreen(game));
                game.setScreen(new GameScreen());
            }

        }

        if(Gdx.input.getX() < btScorePositionX + btScore.getWidth() && (BaseScreen.getScreenHeight() -
                Gdx.input.getY()) < btScorePositionY + btScore.getHeight() && BaseScreen.getScreenHeight()-Gdx.input.getY() > btScorePositionY) {
            Gdx.app.exit();
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        btScore.dispose();
        btStart.dispose();
        batch.dispose();
    }
}
