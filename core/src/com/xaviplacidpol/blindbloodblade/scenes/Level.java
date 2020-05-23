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
import com.xaviplacidpol.blindbloodblade.entities.Player;
import com.xaviplacidpol.blindbloodblade.entities.PlayerFactory;
import com.xaviplacidpol.blindbloodblade.entities.Spikes;
import com.xaviplacidpol.blindbloodblade.entities.Background;
import com.xaviplacidpol.blindbloodblade.screens.GameOverScreen;
import com.xaviplacidpol.blindbloodblade.utils.Constants;


public class Level implements Disposable {

    private BlindBloodBlade game;

//    private Set<Integer> scoresSet;

    // Add a ninjaPlayer member variable
    Player player;

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

    private PlayerFactory playerFactory;

    public Level(Viewport viewport, BlindBloodBlade game){

        this.game = game;
//        scoresSet = new HashSet<>();
 /*       scoresSet.add(19286);
        scoresSet.add(17388);
        scoresSet.add(19281);
        scoresSet.add(21990);
        scoresSet.add(26722);
*/

        playerFactory = PlayerFactory.getInstance();

        // Initialize NinjaPlayer
        double random = Math.random();
       /* if(random < 0.33) {
            player = playerFactory.getPlayer("NINJA");
        } else if (random > 0.33 && random < 0.66){
            player = playerFactory.getPlayer("RONIN");
        }else if (random > 0.66){
            player = playerFactory.getPlayer("AUTOMATA");
        }
        */
        if(random < 0.50) {
            player = playerFactory.getPlayer("NINJA");
        } else if (random > 0.50){
            player = playerFactory.getPlayer("RONIN");
        }

//        player = playerFactory.getPlayer("RONIN");
        player.setViewport(viewport);
        player.setLevel(this);

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

        //Initialize both arrays of BloodSplashes
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

        //Set input touch screen for ninjaPlayer
        Gdx.input.setInputProcessor(player);

        //Add bridges
        addBridges();

        //Add enemies
        addEnemies();

        //Initialize level end to false
        levelEnd = false;

        this.viewport = viewport;

    }

    /**
     * Update level and all components
     * @param delta
     */
    public void update(float delta){
        // Update NinjaPlayer
        player.update(delta, grounds, bridges);    //CRITIC

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
        if(player.getPosition().x > Constants.ENDLESS_POSITION){
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
        player.render(batch);

//        batch.end();
        if(!player.isAlive()) {
//            dispose();
            game.setScreen(new GameOverScreen(game, player.getScore()));
        }
    }

    /**
     *  Add here all the grounds, grounds are necessary for the ninja to not fall out,
     *  then is important to be rendered in order
     *
     */
    private void addDebugGrounds(){
        grounds.add(new Ground(0, 40, 400, 40));
        grounds.add(new Ground(400, 40, 400, 40));
        grounds.add(new Ground(541, 40, 250, 40));
        grounds.add(new Ground(2090, 40, 400, 40));
        grounds.add(new Ground(2491, 40, 400, 40));
        grounds.add(new Ground(3730, 40, 400, 40));
        grounds.add(new Ground(4130, 40, 400, 40));
        grounds.add(new Ground(4530, 40, 400, 40));
        grounds.add(new Ground(4930, 40, 400, 40));
        grounds.add(new Ground(7221, 40, 400, 40));
        grounds.add(new Ground(7621, 40, 400, 40));
        grounds.add(new Ground(8021, 40, 400, 40));
        grounds.add(new Ground(8421, 40, 400, 40));
        grounds.add(new Ground(8821, 40, 400, 40));
        grounds.add(new Ground(9221, 40, 400, 40));
        grounds.add(new Ground(10741, 40, 400, 40));
        grounds.add(new Ground(11121, 40, 400, 40));
        grounds.add(new Ground(11521, 40, 400, 40));
        grounds.add(new Ground(11921, 40, 400, 40));
        grounds.add(new Ground(12321, 40, 400, 40));
        grounds.add(new Ground(12721, 40, 400, 40));
        grounds.add(new Ground(13121, 40, 400, 40));
        grounds.add(new Ground(13521, 40, 400, 40));
        grounds.add(new Ground(13921, 40, 400, 40));
    }

    private void addSpikes() {
        spikes.add(new Spikes(new Vector2(800, 0)));
        spikes.add(new Spikes(new Vector2(870, 0)));
        spikes.add(new Spikes(new Vector2(940, 0)));
        spikes.add(new Spikes(new Vector2(955, 0)));
        spikes.add(new Spikes(new Vector2(1010, 0)));
        spikes.add(new Spikes(new Vector2(1015, 0)));
        spikes.add(new Spikes(new Vector2(1080, 0)));
        spikes.add(new Spikes(new Vector2(1100, 0)));
        spikes.add(new Spikes(new Vector2(1150, 0)));
        spikes.add(new Spikes(new Vector2(1165, 0)));
        spikes.add(new Spikes(new Vector2(1220, 0)));
        spikes.add(new Spikes(new Vector2(1290, 0)));
        spikes.add(new Spikes(new Vector2(1360, 0)));
        spikes.add(new Spikes(new Vector2(1430, 0)));
        spikes.add(new Spikes(new Vector2(1500, 0)));
        spikes.add(new Spikes(new Vector2(1570, 0)));
        spikes.add(new Spikes(new Vector2(1640, 0)));
        spikes.add(new Spikes(new Vector2(1655, 0)));
        spikes.add(new Spikes(new Vector2(1710, 0)));
        spikes.add(new Spikes(new Vector2(1780, 0)));
        spikes.add(new Spikes(new Vector2(1850, 0)));
        spikes.add(new Spikes(new Vector2(1875, 0)));
        spikes.add(new Spikes(new Vector2(1920, 0)));
        spikes.add(new Spikes(new Vector2(1990, 0)));
        spikes.add(new Spikes(new Vector2(2020, 0)));
        spikes.add(new Spikes(new Vector2(2890, 0)));
        spikes.add(new Spikes(new Vector2(2960, 0)));
        spikes.add(new Spikes(new Vector2(3030, 0)));
        spikes.add(new Spikes(new Vector2(3100, 0)));
        spikes.add(new Spikes(new Vector2(3135, 0)));
        spikes.add(new Spikes(new Vector2(3170, 0)));
        spikes.add(new Spikes(new Vector2(3240, 0)));
        spikes.add(new Spikes(new Vector2(3275, 0)));
        spikes.add(new Spikes(new Vector2(3310, 0)));
        spikes.add(new Spikes(new Vector2(3380, 0)));
        spikes.add(new Spikes(new Vector2(3450, 0)));
        spikes.add(new Spikes(new Vector2(3520, 0)));
        spikes.add(new Spikes(new Vector2(3590, 0)));
        spikes.add(new Spikes(new Vector2(3660, 0)));
        spikes.add(new Spikes(new Vector2(5330, 0)));
        spikes.add(new Spikes(new Vector2(5390, 0)));
        spikes.add(new Spikes(new Vector2(5450, 0)));
        spikes.add(new Spikes(new Vector2(5560, 0)));
        spikes.add(new Spikes(new Vector2(5530, 0)));
        spikes.add(new Spikes(new Vector2(5400, 0)));
        spikes.add(new Spikes(new Vector2(5430, 0)));
        spikes.add(new Spikes(new Vector2(5470, 0)));
        spikes.add(new Spikes(new Vector2(5540, 0)));
        spikes.add(new Spikes(new Vector2(5610, 0)));
        spikes.add(new Spikes(new Vector2(5650, 0)));
        spikes.add(new Spikes(new Vector2(5680, 0)));
        spikes.add(new Spikes(new Vector2(5750, 0)));
        spikes.add(new Spikes(new Vector2(5820, 0)));
        spikes.add(new Spikes(new Vector2(5845, 0)));
        spikes.add(new Spikes(new Vector2(5890, 0)));
        spikes.add(new Spikes(new Vector2(5960, 0)));
        spikes.add(new Spikes(new Vector2(6030, 0)));
        spikes.add(new Spikes(new Vector2(6070, 0)));
        spikes.add(new Spikes(new Vector2(6100, 0)));
        spikes.add(new Spikes(new Vector2(6170, 0)));
        spikes.add(new Spikes(new Vector2(6240, 0)));
        spikes.add(new Spikes(new Vector2(6290, 0)));
        spikes.add(new Spikes(new Vector2(6310, 0)));
        spikes.add(new Spikes(new Vector2(6380, 0)));
        spikes.add(new Spikes(new Vector2(6450, 0)));
        spikes.add(new Spikes(new Vector2(6490, 0)));
        spikes.add(new Spikes(new Vector2(6520, 0)));
        spikes.add(new Spikes(new Vector2(6590, 0)));
        spikes.add(new Spikes(new Vector2(6660, 0)));
        spikes.add(new Spikes(new Vector2(6780, 0)));
        spikes.add(new Spikes(new Vector2(6730, 0)));
        spikes.add(new Spikes(new Vector2(6800, 0)));
        spikes.add(new Spikes(new Vector2(6870, 0)));
        spikes.add(new Spikes(new Vector2(6890, 0)));
        spikes.add(new Spikes(new Vector2(6940, 0)));
        spikes.add(new Spikes(new Vector2(7010, 0)));
        spikes.add(new Spikes(new Vector2(7080, 0)));
        spikes.add(new Spikes(new Vector2(7150, 0)));
        spikes.add(new Spikes(new Vector2(9620, 0)));
        spikes.add(new Spikes(new Vector2(9690, 0)));
        spikes.add(new Spikes(new Vector2(9760, 0)));
        spikes.add(new Spikes(new Vector2(9830, 0)));
        spikes.add(new Spikes(new Vector2(9900, 0)));
        spikes.add(new Spikes(new Vector2(9970, 0)));
        spikes.add(new Spikes(new Vector2(10040, 0)));
        spikes.add(new Spikes(new Vector2(10110, 0)));
        spikes.add(new Spikes(new Vector2(10180, 0)));
        spikes.add(new Spikes(new Vector2(10250, 0)));
        spikes.add(new Spikes(new Vector2(10320, 0)));
        spikes.add(new Spikes(new Vector2(10390, 0)));
        spikes.add(new Spikes(new Vector2(10460, 0)));
        spikes.add(new Spikes(new Vector2(10530, 0)));
        spikes.add(new Spikes(new Vector2(10600, 0)));
        spikes.add(new Spikes(new Vector2(10670, 0)));
        spikes.add(new Spikes(new Vector2(11500, 250)));
        spikes.add(new Spikes(new Vector2(11570, 250)));
        spikes.add(new Spikes(new Vector2(11640, 250)));
        spikes.add(new Spikes(new Vector2(11710, 250)));
        spikes.add(new Spikes(new Vector2(11780, 250)));
        spikes.add(new Spikes(new Vector2(11850, 250)));
        spikes.add(new Spikes(new Vector2(11920, 250)));
        spikes.add(new Spikes(new Vector2(11990, 250)));
        spikes.add(new Spikes(new Vector2(12060, 250)));
        spikes.add(new Spikes(new Vector2(12130, 250)));

    }

    private void addBridges() {
        bridges.add(new Bridge(1100, 100, 330, 180));
        bridges.add(new Bridge(1660, 100, 330, 180));
        bridges.add(new Bridge(2250, 200, 150, 280));
        bridges.add(new Bridge(2570, 300, 425, 380));
        bridges.add(new Bridge(2950, 300, 425, 380));
        bridges.add(new Bridge(5550, 100, 150, 180));
        bridges.add(new Bridge(5900, 200, 150, 280));
        bridges.add(new Bridge(6250, 300, 150, 380));
        bridges.add(new Bridge(6600, 200, 150, 280));
        bridges.add(new Bridge(6950, 100, 150, 180));
        bridges.add(new Bridge(8821, 150, 150, 210));
        bridges.add(new Bridge(9171, 250, 150, 330));
        bridges.add(new Bridge(9751, 250, 150, 330));
        bridges.add(new Bridge(10221, 150, 150, 210));
        bridges.add(new Bridge(10571, 250, 150, 330));
        bridges.add(new Bridge(10921, 300, 150, 380));
        bridges.add(new Bridge(11051, 300, 150, 380));
        bridges.add(new Bridge(11201, 300, 150, 380));
        bridges.add(new Bridge(11351, 300, 150, 380));
        bridges.add(new Bridge(11501, 250, 150, 330));
        bridges.add(new Bridge(11651, 250, 150, 330));
        bridges.add(new Bridge(11801, 250, 150, 330));
        bridges.add(new Bridge(11951, 250, 150, 330));
        bridges.add(new Bridge(12101, 250, 100, 330));
    }

    private void addEnemies(){
        enemies.add(new Enemy(new Vector2(750, 60)));
        enemies.add(new Enemy(new Vector2(1150, 120)));
        enemies.add(new Enemy(new Vector2(1850, 120)));
        enemies.add(new Enemy(new Vector2(2950, 300)));
        enemies.add(new Enemy(new Vector2(4500, 60)));
        enemies.add(new Enemy(new Vector2(4600, 60)));
        enemies.add(new Enemy(new Vector2(4700, 60)));
        enemies.add(new Enemy(new Vector2(4800, 60)));
        enemies.add(new Enemy(new Vector2(6300, 300)));
        enemies.add(new Enemy(new Vector2(7400, 60)));
        enemies.add(new Enemy(new Vector2(7490, 60)));
        enemies.add(new Enemy(new Vector2(8838, 150)));
        enemies.add(new Enemy(new Vector2(10231, 150)));
        enemies.add(new Enemy(new Vector2(10931, 300)));
        enemies.add(new Enemy(new Vector2(11211, 300)));
        enemies.add(new Enemy(new Vector2(11511, 60)));
        enemies.add(new Enemy(new Vector2(11961, 60)));

    }


    public Player getPlayer() {
        return player;
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
                MathUtils.random(viewport.getWorldWidth()) - 30,
                MathUtils.random(viewport.getWorldHeight()) - 20
        )));
    }


    @Override
    public void dispose() {


    }
}
