package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.StrongMan;

public class StrongManCharacter extends Sprite {
    public enum state {standing, run, walking, shoot, hurt}

    public World world;
    public Body b2body;
    public static boolean health = true;
    private state currentState;
    private state previousState;
    private TextureRegion stand;
    private TextureRegion dead;
    private Animation runAnimation;
    private Animation shootAnimation;
    private Animation hurtAnimation;

    private float stateTimer;
    private boolean runingRight;
    public  int shouting = 0;
    private Viewport viewport;


    public StrongManCharacter(World world, PlayScreen screen, Viewport vp) {
        super(screen.getAtlas().findRegion("Walk"));
        stateTimer = 0;
        viewport = vp;
        this.world = world;
        runingRight = true;
        currentState = state.walking;
        previousState = state.walking;
        makeStrongMan();
        makeAnimation();


    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getWidth())+2.35f  / 2);

        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region = stand;
        switch (currentState) {
            case standing:
                region = stand;
                break;
            case run:
                region = (TextureRegion) runAnimation.getKeyFrame(stateTimer, true);
                break;
            case shoot:
                region = (TextureRegion) shootAnimation.getKeyFrame(stateTimer, true);
                break;
            case hurt:
                region = ((TextureRegion) hurtAnimation.getKeyFrame(stateTimer));
                break;
        }


        if ( !runingRight && !region.isFlipX()) {
            region.flip(true, false);
        }else if (runingRight && region.isFlipX()){
            region.flip(true, false);
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        if (stateTimer > 1.0f && currentState == state.hurt) {
            currentState = state.standing;
            return stand;
        }
        return region;
    }

    private state getState() {
        if (shouting > 0) {
            shouting--;
            health = true;
            return state.shoot;
        }
        if (!health) {
            return state.hurt;
        }
        if (b2body.getLinearVelocity().x != 0) {
            return state.run;
        }
        return state.standing;

    }

    private void makeStrongMan() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/StrongMan.PPM,(viewport.getWorldHeight()+8.6f)/2);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1 / StrongMan.PPM);
        fdef.shape = shape;
        /// add body rectangle
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(0f  , 0.2f  );
        vertices[1] = new Vector2(0f , 0.6f  );
        vertices[2] = new Vector2(0.01f , 0.6f);
        vertices[3] = new Vector2(0.01f , 0.2f);
        PolygonShape shape2 = new PolygonShape();
        shape2.set(vertices);
        fdef.shape = shape2;
        fdef.filter.categoryBits = StrongMan.Man_bit;
        fdef.filter.maskBits = StrongMan.default_bit;
        ///// add sensor shap
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.set(vertices);
        fdef.shape = sensorShape;
        b2body.createFixture(fdef).setUserData("strongMan");
        fdef.isSensor = true;



    }

    private void makeAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 128, 262, 128, 128));
        }
        runAnimation = new Animation(0.3f, frames);
        frames.clear();
        for (int i = 0; i <= 3; i++) {
            frames.add(new TextureRegion(getTexture(), 1156 + (i * 128), 652, 128, 128));
        }
        shootAnimation = new Animation(0.2f, frames);
        frames.clear();
        for (int i = 0; i <= 4; i++) {
            frames.add(new TextureRegion(getTexture(), 2 + (i * 128), 2, 128, 128));
        }
        hurtAnimation = new Animation(0.3f, frames);
        dead = new TextureRegion(getTexture(), 2 + (3 * 128), 2, 128, 128);

        stand = new TextureRegion(getTexture(), 2, 262, 128, 128);
        setBounds(0, 10/2.0f, 1.0f, 1.0f);
        setRegion(stand);
    }
    public void handInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)&&b2body.getLinearVelocity().y==0)
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2body.getLinearVelocity().x <= 2){
            runingRight = true;
            b2body.applyLinearImpulse(new Vector2(0.1f, 0), b2body.getWorldCenter(), true);}
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2body.getLinearVelocity().x >= -2){
            runingRight = false;
            b2body.applyLinearImpulse(new Vector2(-0.1f, 0), b2body.getWorldCenter(), true);}
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if ((button == Input.Buttons.LEFT)||(Gdx.input.isKeyPressed(Input.Keys.E))) {
                   shouting = 40;

                    return true;
                }
                return false;
            }
        });
        if(Gdx.input.isKeyJustPressed(Input.Keys.valueOf("Q"))){
            StrongManCharacter.health=false;

        }

    }
}
