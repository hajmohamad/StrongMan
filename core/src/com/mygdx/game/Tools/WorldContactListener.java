package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.InterActiveTileObject;

import java.lang.reflect.Field;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
       Fixture a = contact.getFixtureA();
       Fixture b = contact.getFixtureB();
       if(a.getUserData()== "strongMan"||b.getUserData()== "strongMan") {
           Fixture character = a.getUserData()=="strongMan"? a:b;
           Fixture object = character == a?b:a;
           if(object.getUserData()!= null&& InterActiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
              ( (InterActiveTileObject) object.getUserData()).CollisionDetect();
           }
       }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
