package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class boxes extends InterActiveTileObject {
    public boxes(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
    @Override
    public void CollisionDetect() {
        Gdx.app.log("CollisionDetect","Boxes");
    }
}

