package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.StrongMan;

public class heart extends InterActiveTileObject{
    public heart(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        serCategoryFilter(StrongMan.default_bit);
    }

    @Override
    public void CollisionDetect() {
        Gdx.app.log("CollisionDetect","heart");
        serCategoryFilter(StrongMan.destroy_bit);
        getCell().setTile(null);
    }
}
