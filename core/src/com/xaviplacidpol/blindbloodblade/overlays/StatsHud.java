package com.xaviplacidpol.blindbloodblade.overlays;

import  com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xaviplacidpol.blindbloodblade.entities.Player;
import com.xaviplacidpol.blindbloodblade.utils.Assets;
import com.xaviplacidpol.blindbloodblade.utils.Constants;

public class StatsHud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private float timer;
    private static Integer score;
    private Integer deads;

    private Label lblTimeHeader;
    private Label lblScoreHeader;
    private Label lblTime;
    private static Label lblScore;

    private Player player;

    private Label lblKillsHeader;
    private Label lblKills;
    private Integer kills;

    public StatsHud(SpriteBatch sb, Player player){

        this.player = player;

        timer = 0.0f;
        deads = 0;
        score = 0;

        initComponents(sb);

        Table table = initStageContent();

        stage.addActor(table);

    }

    /**
     * Initializs the content to shown
     */
    private Table initStageContent() {
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        lblTimeHeader = new Label("TIME", new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorehud, null));
        lblTime = new Label(String.format("%.0f", timer), new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorehud, null));
        lblKillsHeader = new Label("KILLS", new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorehud, null));
        lblScoreHeader = new Label("SCORE", new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorehud, null));
        lblScore = new Label(String.format("%05d", score), new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorehud, null));
        lblKills = new Label(String.format("%02d", deads), new Label.LabelStyle(Assets.instance.scoreScreenAssets.bbbscorehud, null));

        table.add(lblTimeHeader).expandX().padLeft(20);
        table.add(lblTime).expandX();
        table.add(lblKillsHeader).expandX();
        table.add(lblKills).expandX();
        table.add(lblScoreHeader).expandX();
        table.add(lblScore).expandX().padRight(20);
        return table;
    }

    /**
     * Initializes those components used to show screen content
     */
    private void initComponents(SpriteBatch sb) {
        viewport = new FitViewport(Constants.SCREEN_W, Constants.SCREEN_H, new OrthographicCamera());
        stage = new Stage(viewport, sb);
    }

    public void update(float delta){
        timer += delta;
            lblTime.setText(String.format("%.0f", timer));
        kills = player.getKills();
        lblKills.setText(String.format("%02d", kills));
        score = player.getScore();
        lblScore.setText(String.format("%06d", score));
    }

    /**
     * Show method inherited from ScreenAdapter draw elements on the screen at every frame
     * given by delta time
     */
    public void render(){

        stage.draw();

    }

    /**
     * loads player's score to the label
     * @param value score to load
     */
    public static void addScore(int value){
        score += value;
        lblScore.setText(String.format("%06d", score));
    }

    /**
     * free resources
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    public static Integer getScore() {
        return score;
    }

}
