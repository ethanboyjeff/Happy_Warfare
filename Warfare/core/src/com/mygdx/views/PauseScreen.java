package com.mygdx.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Warfare;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.loader.B2dAssetManager;


public class PauseScreen extends InputAdapter implements Screen {
    Stage stage;
    SpriteBatch batch;
    private Skin skin;
    private Warfare parent;
    private Sound buttonClick;
    public B2dAssetManager assMan = new B2dAssetManager();
    public PauseScreen(Warfare mygdx){
        parent = mygdx;
        parent.assMan.queueAddSkin();
        parent.assMan.manager.finishLoading();
        skin = parent.assMan.manager.get("Skin/glassy-ui.json"); // new
        assMan.queueAddSound();
        assMan.manager.finishLoading();
        buttonClick = assMan.manager.get("Audio/buttonClick.wav");
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        final Table pause = new Table();
        stage.addActor(pause);
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                System.out.println("Close Pause");
                parent.changeScreen(Warfare.APPLICATION);
            }
        });
        TextButton menuButton = new TextButton("Menu",skin);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                parent.changeScreen(Warfare.MENU);
            }
        });

        pause.padTop(64);
        pause.add(resumeButton).row();
        pause.row().pad(10,0,10,0);
        pause.add(menuButton).row();
        pause.row().pad(10,0,10,0);
        pause.setSize(stage.getWidth() /1.5f , stage.getHeight() /1.5f );
        pause.setPosition(stage.getWidth() /2 - pause.getWidth() /2, stage.getHeight() /2 - pause.getHeight() /2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        buttonClick.dispose();
    }
}
