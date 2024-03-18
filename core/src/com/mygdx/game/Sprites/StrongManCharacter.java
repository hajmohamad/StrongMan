package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.StrongMan;

public class StrongManCharacter extends Sprite {
    public World world;
    public Body b2body;

    public StrongManCharacter(World world){
        this.world = world;
        makeStrongMan();

    }
    private void makeStrongMan(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ StrongMan.PPM,32/StrongMan.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/StrongMan.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
