package com.mygdx.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Controller.KeyboardController;
import com.mygdx.game.B2dModel;
import com.mygdx.game.Warfare;

public class MainScreen extends InputAdapter implements Screen {

    private final Texture playerTex;
    private final Texture playerTex2;
    Stage stage;
    SpriteBatch batch;
    Texture player;

   private Skin skin;
    float speed = 50.0f;
    float playerx = 0;
    float playery = 0;


    private Warfare parent; // a field to store our orchestrator
    // our constructor with a Box2DTutorial argument
    private B2dModel model;
    private OrthographicCamera cam;

    private KeyboardController controller;
    private Box2DDebugRenderer debugRenderer;
    private PolygonSprite polygon;
    private SpriteBatch sb;

   public MainScreen(Warfare mygdx) {
       parent = mygdx;// setting the argument to our field.
       sb = new SpriteBatch();
       cam = new OrthographicCamera(32,24);
       sb.setProjectionMatrix(cam.combined);
       controller = new KeyboardController();
       model = new B2dModel(controller);
       debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
       parent.assMan.queueAddSkin();
       // tells our asset manger that we want to load the images set in loadImages method
       parent.assMan.queueAddImages();
       // tells the asset manager to load the images and wait until finsihed loading.
       parent.assMan.manager.finishLoading();
       // gets the images as a texture
       playerTex = parent.assMan.manager.get("Input/Game/panda256x256.png");
       playerTex2 = parent.assMan.manager.get("Input/Game/giraffe.png");

       skin = parent.assMan.manager.get("Skin/glassy-ui.json"); // new
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        //Gdx.input.setInputProcessor(controller);
        //player = new Texture("panda256x256.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        model.logicStep(delta);
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        debugRenderer.render(model.world, cam.combined);
        debugRenderer.setDrawAABBs(false);
       /* batch.begin();
        stage.draw();
        batch.draw(player,playerx,playery);
        */
        sb.begin();
        sb.draw(playerTex,model.player.getPosition().x -1,model.player.getPosition().y -1,4,4);
        sb.draw(playerTex2,model.player2.getPosition().x -1,model.player2.getPosition().y -1,4,4);
        sb.end();
        //polygon = new PolygonSprite();

        /*if(Gdx.input.isKeyPressed(Input.Keys.W)){
            System.out.println("W");
            playery += Gdx.graphics.getDeltaTime()*speed*2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            System.out.println("S");
            playery -= Gdx.graphics.getDeltaTime()*speed*2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            System.out.println("A");
            playerx -= Gdx.graphics.getDeltaTime()*speed*2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            System.out.println("D");
            playerx += Gdx.graphics.getDeltaTime()*speed*2;
        }*/
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("Escape");
            parent.changeScreen(Warfare.PAUSEGAME);
        }
        //batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        stage.dispose();
        sb.dispose();
    }
}
