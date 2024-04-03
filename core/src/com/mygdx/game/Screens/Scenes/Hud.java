package com.mygdx.game.Screens.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.StrongMan;

/**
 * Created by brentaureli on 8/17/15.
 */
public class Hud implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;
    //Mario score/time Tracking Variables
    private Integer worldTimer;
    public void setWorldTimer(Integer worldTimer){
        this.worldTimer = worldTimer;
    }
    private float timeCount;
    private Integer score;
    //Scene2D widgets
    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label health;
    private Label ScoreTextLable;
    private Image HeartImage;
    private Table MainTable;
    private void addValue(){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        HeartImage = new Image(new Texture(Gdx.files.internal("craftpix-net-965049-free-industrial-zone-tileset-pixel-art/1_Tiles2/tile_0044.png")));
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        health = new Label("Health", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ScoreTextLable = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        MainTable.add(ScoreTextLable).expandX().padTop(10);
        MainTable.add(health).expandX().padTop(10);
        MainTable.add(timeLabel).expandX().padTop(10);
        MainTable.row();
        MainTable.add(scoreLabel).expandX();
        //add health image to table
        HeartImage.setWidth(16/StrongMan.PPM);
        HeartImage.setHeight(16/StrongMan.PPM);
        Table outerTable = new Table();
        outerTable.setFillParent(true);
        for(int i = 0;i<3;i++){
            outerTable.add(new Image(new Texture(Gdx.files.internal("craftpix-net-965049-free-industrial-zone-tileset-pixel-art/1_Tiles2/tile_0044.png"))));
        }

        MainTable.add(outerTable).colspan(2).expand().fill(); // Add the nested table to the first row and make it span two columns


        //MainTable.add(healthTable).expandX().padTop(10).fill();
       MainTable.debug();






        ///// addCountDown
        MainTable.add(countdownLabel).expandX();

    }
    public Hud(SpriteBatch sb){
        //define our tracking variables
        MainTable = new Table();
        MainTable.top();
        MainTable.setFillParent(true);
        viewport = new FitViewport(StrongMan.V_WIDTH, StrongMan.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        addValue();
        stage.addActor(MainTable);

    }
    public void update(float dt){
        timeCount+=dt;
        if(timeCount>=1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount=0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}