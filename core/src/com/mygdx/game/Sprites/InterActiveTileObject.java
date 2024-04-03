package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.StrongMan;

public abstract class InterActiveTileObject {
    private TiledMap map;
    private TiledMapTile tile;
    private World world;
    private Rectangle bounds;
    private Body body;
    protected Fixture  fixture ;

    public  InterActiveTileObject( World world,TiledMap map, Rectangle bounds) {
        this.map = map;
        this.world = world;
        this.bounds = bounds;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth()/2)/ StrongMan.PPM,(bounds.getY() + bounds.getHeight()/2)/ StrongMan.PPM);
        body = world.createBody(bodyDef);
        PolygonShape shap = new PolygonShape();
        shap.setAsBox(bounds.getWidth()/2/ StrongMan.PPM,bounds.getHeight()/2/ StrongMan.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shap;
        fixture =  body.createFixture(fixtureDef);

    }
    public abstract void CollisionDetect();
    public void serCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer=(TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x*StrongMan.PPM/16),(int) (body.getPosition().y*StrongMan.PPM/16));

    }
}
