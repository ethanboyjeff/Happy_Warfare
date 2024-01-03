package com.mygdx.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Controller.KeyboardController;
import com.mygdx.game.B2dModel;
import com.mygdx.game.Warfare;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class MainScreen extends InputAdapter implements Screen {

    private B2dModel model;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private AtlasRegion playerTex;
    private AtlasRegion playerTex2;
    private SpriteBatch sb;
    private TextureAtlas atlas;

   private Skin skin;
    float speed = 50.0f;
    float playerx = 0;
    float playery = 0;


    private Warfare parent; // a field to store our orchestrator
    // our constructor with a Box2DTutorial argument

   public MainScreen(Warfare mygdx) {
       parent = mygdx;
       cam = new OrthographicCamera(32,24);
       controller = new KeyboardController();
       model = new B2dModel(controller,cam,parent.assMan);
       debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

       sb = new SpriteBatch();
       sb.setProjectionMatrix(cam.combined);

       atlas = parent.assMan.manager.get("Images/game.atlas");
       playerTex = atlas.findRegion("panda256x256");
       playerTex2 = atlas.findRegion("giraffe");
   }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        /*//Gdx.input.setInputProcessor(controller);
        //player = new Texture("panda256x256.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(controller);
        batch = new SpriteBatch();*/
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(playerTex,model.player.getPosition().x -1,model.player.getPosition().y -1,2,2);
        sb.draw(playerTex2,model.player2.getPosition().x -1,model.player2.getPosition().y -1,2,2);
        sb.end();


        debugRenderer.render(model.world, cam.combined);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("Escape");
            parent.changeScreen(Warfare.PAUSEGAME);
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        //stage.getViewport().update(width, height, true);
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
        //stage.dispose();
        sb.dispose();
    }
}
