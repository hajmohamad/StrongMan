package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class StrongManCharacter extends Sprite {
    public World world;
    public Body b2Body;
    public StrongManCharacter(World world){
        this.world = world;
        makeStrongMan();

    }
    private void makeStrongMan(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32,32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6);
        fdef.shape = shape;
        b2Body.createFixture(fdef);
    }
}
