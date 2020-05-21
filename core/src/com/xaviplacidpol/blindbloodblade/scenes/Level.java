package com.xaviplacidpol.blindbloodblade.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.BlindBloodBlade;
import com.xaviplacidpol.blindbloodblade.entities.BloodSplash;
import com.xaviplacidpol.blindbloodblade.entities.Bridge;
import com.xaviplacidpol.blindbloodblade.entities.Enemy;
import com.xaviplacidpol.blindbloodblade.entities.Ground;
import com.xaviplacidpol.blindbloodblade.entities.NinjaPlayer;
import com.xaviplacidpol.blindbloodblade.entities.Spikes;
import com.xaviplacidpol.blindbloodblade.entities.Background;
import com.xaviplacidpol.blindbloodblade.screens.GameOverScreen;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Level implements Disposable {

    private BlindBloodBlade game;

//    private Set<Integer> scoresSet;

    // Add a ninjaPlayer member variable
    NinjaPlayer ninjaPlayer;

    // Add an Array of Grounds
    Array<Ground> grounds;

    // Add an Array of Spikes
    Array<Spikes> spikes;

    public Viewport viewport;

    // Add an Array of Bridges
    Array<Bridge> bridges;

    // Add an Array of Enemies
    Array<Enemy> enemies;

    //Add blood splash to the enemy position when this enemy is killed
    private Array<BloodSplash> bloodSplashes;

    // Add an Array of backgrounds
    Array<Background> backgrounds;

    //Array with the random fixed blood splashes to the screen
    private Array<BloodSplash> bloodSplashesScreen;

    //Boolean to control if reached end of level
    public boolean levelEnd;

//    private Integer score;

    public Level(Viewport viewport, BlindBloodBlade game){

        this.game = game;
//        scoresSet = new HashSet<>();
 /*       scoresSet.add(19286);
        scoresSet.add(17388);
        scoresSet.add(19281);
        scoresSet.add(21990);
        scoresSet.add(26722);
*/
        // Initialize NinjaPlayer
        ninjaPlayer = new NinjaPlayer(viewport, this);

        // Initialize the bridges array
        bridges = new Array<Bridge>();

        // Initialize the ground array
        grounds = new Array<Ground>();

        // Initialize the spikes array
        spikes = new Array<Spikes>();

        // Initialize the bridges array
        bridges = new Array<Bridge>();

        //Initialize the enemyes array
        enemies = new Array<Enemy>();

        bloodSplashes = new Array<BloodSplash>();
        bloodSplashesScreen = new Array<BloodSplash>();

        // Initialize the backgrounds array
        backgrounds = new Array<Background>();

        //Add bridges
        addBridges();

        // Add addDebugPlatforms
        addDebugGrounds();

        //Add spikes
        addSpikes();

        //Add backgrounds
        addBackgrounds();

        //Set input touch screen for ninjaPlayer
        Gdx.input.setInputProcessor(ninjaPlayer);

        //Add bridges
        addBridges();

        //Add enemies
        addEnemies();

        //Initialize level end to false
        levelEnd = false;

        //TODO POL revisar viewport + cam en resize
        this.viewport = viewport;

    }

    /**
     * Update level and all components
     * @param delta
     */
    public void update(float delta){
        // Update NinjaPlayer
        ninjaPlayer.update(delta, grounds, bridges);    //CRITIC

        endlessGame();
    }

    /**
     * Restart player position for endless game when player reached end point
     * Clean all blood splashes in enemies position
     * Relive all enemies
     *
     */
    private void endlessGame() {
        // Restart player position for endless game if player reached end point, otherwise just return
        if(ninjaPlayer.getPosition().x > Constants.ENDLESS_POSITION){
            levelEnd = true;
            //Clean old blood splashes in the enemies position
            bloodSplashes.clear();
            //Relive all enemies
            for(Enemy enemy : enemies){
                enemy.setAlive(true);
            }
        }
    }

    /**
     * Renderize level and all components
     * @param batch
     */
    public void render(SpriteBatch batch){

   //     batch.begin();

        //Render all backgrounds
        for (Background b : backgrounds) {
            b.render(batch);
        }

        // Render all grounds in the grounds array
        for(Ground ground : grounds){
            ground.render(batch);
        }

        // Render all spikes
        for(Spikes spike : spikes){
            spike.render(batch);
        }

        // Render all bridges
        for(Bridge bridge : bridges){
            bridge.render(batch);
        }

        // Render all enemiew
        for(Enemy enemy : enemies){
            enemy.render(batch);
        }

        // Render bloodSplashes
        for(BloodSplash bloodSplash : bloodSplashes){
            bloodSplash.render(batch);
        }

        // Render NinjaPlayer
        ninjaPlayer.render(batch);

//        batch.end();
        if(!ninjaPlayer.isAlive()) {
//            dispose();
            game.setScreen(new GameOverScreen(game, ninjaPlayer.getScore()));
        }
    }

    /**
     *  Add here all the grounds, grounds are necessary for the ninja to not fall out,
     *  then is important to be rendered in order
     *
     */
    private void addDebugGrounds(){
        // TODO:  Add here all grounds in the level
        grounds.add(new Ground(0, 40, 400, 40));
        grounds.add(new Ground(541, 40, 400, 40));
        grounds.add(new Ground(2090, 40, 400, 40));
        grounds.add(new Ground(2491, 40, 400, 40));
        grounds.add(new Ground(3730, 40, 400, 40));
        grounds.add(new Ground(4130, 40, 400, 40));
        grounds.add(new Ground(4530, 40, 400, 40));
        grounds.add(new Ground(4930, 40, 400, 40));
    }

    private void addSpikes() {
        spikes.add(new Spikes(new Vector2(401, 0)));
        spikes.add(new Spikes(new Vector2(471, 0)));
        spikes.add(new Spikes(new Vector2(941, 0)));
        spikes.add(new Spikes(new Vector2(1011, 0)));
        spikes.add(new Spikes(new Vector2(1081, 0)));
        spikes.add(new Spikes(new Vector2(1151, 0)));
        spikes.add(new Spikes(new Vector2(1221, 0)));
        spikes.add(new Spikes(new Vector2(1291, 0)));
        spikes.add(new Spikes(new Vector2(1361, 0)));
        spikes.add(new Spikes(new Vector2(1431, 0)));
        spikes.add(new Spikes(new Vector2(1501, 0)));
        spikes.add(new Spikes(new Vector2(1571, 0)));
        spikes.add(new Spikes(new Vector2(1641, 0)));
        spikes.add(new Spikes(new Vector2(1711, 0)));
        spikes.add(new Spikes(new Vector2(1781, 0)));
        spikes.add(new Spikes(new Vector2(1851, 0)));
        spikes.add(new Spikes(new Vector2(1921, 0)));
        spikes.add(new Spikes(new Vector2(1991, 0)));
        spikes.add(new Spikes(new Vector2(2720, 40)));
        spikes.add(new Spikes(new Vector2(2790, 40)));
        spikes.add(new Spikes(new Vector2(2821, 40)));
        spikes.add(new Spikes(new Vector2(2891, 0)));
        spikes.add(new Spikes(new Vector2(2961, 0)));
        spikes.add(new Spikes(new Vector2(3031, 0)));
        spikes.add(new Spikes(new Vector2(3101, 0)));
        spikes.add(new Spikes(new Vector2(3171, 0)));
        spikes.add(new Spikes(new Vector2(3241, 0)));
        spikes.add(new Spikes(new Vector2(3311, 0)));
        spikes.add(new Spikes(new Vector2(3381, 0)));
        spikes.add(new Spikes(new Vector2(3451, 0)));
        spikes.add(new Spikes(new Vector2(3521, 0)));
        spikes.add(new Spikes(new Vector2(3591, 0)));
        spikes.add(new Spikes(new Vector2(3661, 0)));

    }

    private void addBridges() {
        bridges.add(new Bridge(1100, 100, 990, 180));
        bridges.add(new Bridge(2250, 200, 150, 280));
        bridges.add(new Bridge(2570, 300, 850, 380));
    }

    private void addEnemies(){
        enemies.add(new Enemy(new Vector2(550, 60)));
        enemies.add(new Enemy(new Vector2(1150, 120)));
        enemies.add(new Enemy(new Vector2(2000, 120)));
        enemies.add(new Enemy(new Vector2(2300, 225)));
        enemies.add(new Enemy(new Vector2(4500, 60)));
        enemies.add(new Enemy(new Vector2(4600, 60)));
        enemies.add(new Enemy(new Vector2(4700, 60)));
        enemies.add(new Enemy(new Vector2(4800, 60)));
    }

    private void addBackgrounds() {
        backgrounds.add(new Background(0, 503, 640, 500));
    }

    public NinjaPlayer getNinjaPlayer() {
        return ninjaPlayer;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<Spikes> getSpikes() {
        return spikes;
    }

    public BlindBloodBlade getGame() {
        return game;
    }

    public Array<BloodSplash> getBloodSplashesScreen() {
        return bloodSplashesScreen;
    }

    /**
     * Generate a blood Splash to the enemy position where died
     * @param position
     */
    public void spawnBloodSplash(Vector2 position){
        bloodSplashes.add(new BloodSplash(position));
    }

    /**
     * Generate a random blood splash for the BloodSplashOverlay
     */
    public void addBloodSplash(){
        bloodSplashesScreen.add(new BloodSplash(new Vector2(
                MathUtils.random(viewport.getWorldWidth()),
                MathUtils.random(viewport.getWorldHeight())
        )));
    }

//    public void gameOver(Integer score){
  //      this.score = score;
  //      dispose();
//        game.setScreen(new GameOverScreen(game));
//    }

    @Override
    public void dispose() {

 /*       int i = 1;
        scoresSet.add(score);

        List<Integer> scores = new ArrayList<>();

        for(Integer score : scoresSet){
            scores.add(score);
        }

        java.util.Collections.sort(scores, Collections.reverseOrder());

        for(Integer score : scores){
            game.gameData.putInteger("score"+i, score);
            i++;
        }
*/
/*        for(Integer score : scoresSet){
            game.gameData.putInteger("score"+i, score);
            i++;
        }
        scoresSet.remove(0);
*/
//        game.gameData.putInteger("score5", ninjaPlayer.getScore());
  //      game.gameData.flush();
    }
}
