package com.mygdx.game.Sprites;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.StrongMan;

public class StrongManCharacter extends Sprite {
    public enum state{standing,run,walking,shoot,hurt}
    public World world;
    public Body b2body;
    public static boolean health = true;
    private state currentState;
    private state previousState;
    private TextureRegion stand;
        private Animation runAnimation;
    private Animation shootAnimation;
    private Animation hurtAnimation;

        private float stateTimer;
        private boolean runingRight;
        public static int shouting=0;



    public StrongManCharacter(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Walk"));
        stateTimer = 0;
        this.world = world;
        runingRight= true;
        currentState = state.walking;
        previousState = state.walking;
        makeStrongMan();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 128, 262, 128, 128));
        }
        runAnimation = new Animation(0.3f, frames);
        frames.clear();
        for (int i = 0; i <= 4; i++) {
            frames.add(new TextureRegion(getTexture(), 1156 +(i*128),652, 128, 128));
        }
        shootAnimation = new Animation(0.3f, frames);
        frames.clear();
        for (int i = 0; i <= 4; i++) {
            frames.add(new TextureRegion(getTexture(), 2 +(i*128) ,2, 128, 128));
        }
        hurtAnimation = new Animation(0.3f, frames);

        stand = new TextureRegion(getTexture(), 2,262,128,128);
       setBounds(0,0,1/2.0f,1/2.0f);
        setRegion(stand);

    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, (b2body.getPosition().y- getWidth())+0.85f /2);
        setRegion(getFrame(dt));
    }
    private TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region = stand ;
        if(currentState == state.standing){
            return stand;
        }
        if(currentState == state.run){
            region = (TextureRegion) runAnimation.getKeyFrame(stateTimer,true);
        }
        if(currentState == state.shoot){
            region = (TextureRegion) shootAnimation.getKeyFrame(stateTimer,true);
        }
        if(currentState == state.hurt){

            region = ((TextureRegion) hurtAnimation.getKeyFrame(stateTimer));

        }


        if((b2body.getLinearVelocity().x<0 || !runingRight)&& !region.isFlipX()){
            region.flip(true,false);
            runingRight = false;
        }
        else if((b2body.getLinearVelocity().x>0 || runingRight) && region.isFlipX()){
            region.flip(true,false);
            runingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        if(stateTimer>1.0f && currentState == state.hurt){
            currentState = state.standing;
            return stand;
        }
        return region;
    }
    private state getState(){
        if(shouting>0){
            shouting--;
            health = true;
            return state.shoot;
        }
        if(!health){
            return state.hurt;
        }
        if(b2body.getLinearVelocity().x!=0){
            return state.run;
        }
        return state.standing;

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
