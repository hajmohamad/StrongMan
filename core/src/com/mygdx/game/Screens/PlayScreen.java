package com.mygdx.game.Screens;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.Scenes.Hud;
import com.mygdx.game.Sprites.StrongManCharacter;
import com.mygdx.game.Sprites.ground;
import com.mygdx.game.StrongMan;

import com.mygdx.game.Tools.WorldCreator;

import java.util.ArrayList;
import java.util.Collection;

public class PlayScreen implements Screen {
    private StrongMan game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private StrongManCharacter player;

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private TextureAtlas atlas;




    public PlayScreen(StrongMan game){
        atlas = new TextureAtlas("atlas/StrongMan.atlas");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport((StrongMan.V_WIDTH)/ (StrongMan.PPM),StrongMan.V_HEIGHT/ StrongMan.PPM,gameCam);
       // hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("strongManMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/ StrongMan.PPM);
        gameCam.position.set(gamePort.getWorldWidth()+100,(gamePort.getWorldHeight()+8.30f)/2,0);
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer(false,false,false,false,false,false);
        new WorldCreator(world,map);
        player = new StrongManCharacter(world, this,gamePort);

    }
    public void update(float dt){
        player.handInput(dt);
        gameCam.update();
        renderer.setView(gameCam);
        player.update(dt);
        world.step(1/60f,6,2);
        if(player.b2body.getPosition().x>2&&player.b2body.getPosition().x<9.2){
        gameCam.position.x = player.b2body.getPosition().x;
        }else if(player.b2body.getPosition().x>9.2){
            gameCam.position.x =9.2f;
        }
        else{
            gameCam.position.x = 2.0f;
        }
        if(player.b2body.getPosition().y>0.98&&player.b2body.getPosition().y<5.20){
        gameCam.position.y = player.b2body.getPosition().y;}
        //hud.setWorldTimer((int) player.b2body.getPosition().x);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        renderer.render();
       b2dr.render(world,gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
