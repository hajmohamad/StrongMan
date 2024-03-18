package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.StrongMan;

public abstract class InterActiveTileObject {
    private TiledMap map;
    private TiledMapTile tile;
    private World world;
    private Rectangle bounds;
    private Body body;

    public InterActiveTileObject( World world,TiledMap map, Rectangle bounds) {
        this.map = map;
        this.world = world;
        this.bounds = bounds;
        BodyDef bodyDef = new BodyDef();
        PolygonShape shap = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth()/2)/ StrongMan.PPM,(bounds.getY() + bounds.getHeight()/2)/ StrongMan.PPM);
        body = world.createBody(bodyDef);
        shap.setAsBox(bounds.getWidth()/2/ StrongMan.PPM,bounds.getHeight()/2/ StrongMan.PPM);
        fixtureDef.shape = shap;
        body.createFixture(fixtureDef);
    }
}
