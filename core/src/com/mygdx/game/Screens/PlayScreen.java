package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.game.StrongMan;

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



    public PlayScreen(StrongMan game){
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(StrongMan.V_WIDTH,StrongMan.V_HEIGHT,gameCam);
        hud = new Hud(game.batch);
        MapLoad();
        StrongManCharacter character = new StrongManCharacter(world);



    }
    public void MapLoad(){
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("strongManMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shap = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2),(rect.getY() + rect.getHeight()/2));
            body = world.createBody(bodyDef);
            shap.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fixtureDef.shape = shap;
            body.createFixture(fixtureDef);
        }
        for (MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2),(rect.getY() + rect.getHeight()/2));
            body = world.createBody(bodyDef);
            shap.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fixtureDef.shape = shap;
            body.createFixture(fixtureDef);
        }
        for (MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2),(rect.getY() + rect.getHeight()/2));
            body = world.createBody(bodyDef);
            shap.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fixtureDef.shape = shap;
            body.createFixture(fixtureDef);
        }
        for (MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2),(rect.getY() + rect.getHeight()/2));
            body = world.createBody(bodyDef);
            shap.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fixtureDef.shape = shap;
            body.createFixture(fixtureDef);
        }
        for (MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2),(rect.getY() + rect.getHeight()/2));
            body = world.createBody(bodyDef);
            shap.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fixtureDef.shape = shap;
            body.createFixture(fixtureDef);
        }
    }
    public void handInput(float dt){
        if(Gdx.input.isTouched()){
        gameCam.position.x += 100*dt;
        }

    }

    public void update(float dt){
        handInput(dt);
        gameCam.update();
        renderer.setView(gameCam);
        world.step(1/60f,6,2);
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
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


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

    }
}
