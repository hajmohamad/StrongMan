package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.StrongMan;

public class keys extends InterActiveTileObject{
    public keys(World world, TiledMap map, Rectangle bounds) {

        super(world, map, bounds);

    }
    @Override
    public void CollisionDetect() {
        Gdx.app.log("CollisionDetect","keys");
    }

}
