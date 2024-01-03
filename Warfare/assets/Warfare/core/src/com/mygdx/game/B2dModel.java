package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Controller.KeyboardController;
import com.mygdx.loader.B2dAssetManager;

import java.lang.reflect.Array;

public class B2dModel {
    public Body player;
    public Body player2;
    public World world;;
    private OrthographicCamera camera;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;
    private KeyboardController controller;
    private B2dAssetManager assMan;

    public B2dModel(KeyboardController cont, OrthographicCamera cam, B2dAssetManager assetManager){
        this.assMan = assetManager;
        camera = cam;
        controller = cont;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new B2dContactListener(this));
        //createFloor();
        //createObject();
        //createMovingObject();

        // get our body factory singleton and store it in bodyFactory
        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        // add a player
        player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,false);
        player2 = bodyFactory.makeBoxPolyBody(4, 4, 2, 2, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,false);
        //bodyFactory.makeCirclePolyBody(-3,5,8,BodyFactory.STEEL, BodyDef.BodyType.DynamicBody,true);
        //bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,false);
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0,0);
        vertices[1] = new Vector2(Gdx.graphics.getWidth(),0);
        vertices[2] = new Vector2(Gdx.graphics.getWidth(),(Gdx.graphics.getHeight()/4));
        bodyFactory.makePolygonShapeBody(vertices,-16,-12,BodyFactory.STEEL, BodyDef.BodyType.StaticBody);

    }

    private void createFloor() {

        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);

        // add it to the world
        bodyd = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyd.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);


        // add it to the world
        bodys = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createMovingObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0,-12);


        // add it to the world
        bodyk = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();

        bodyk.setLinearVelocity(0, 0.75f);
    }

    // our game logic here
    public void logicStep(float delta){

            if(controller.A){
                player.applyForceToCenter(-500, 0,true);
            }else if(controller.D){
                player.applyForceToCenter(500, 0,true);
            }else if(controller.W){
                player.applyForceToCenter(0, 500,true);
            }else if(controller.S){
                player.applyForceToCenter(0, -500,true);
            }

            world.step(delta , 3, 3);
        }
}
