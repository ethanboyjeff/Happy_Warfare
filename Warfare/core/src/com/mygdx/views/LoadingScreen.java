package com.mygdx.views;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Warfare;

public class LoadingScreen implements Screen {

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        parent.changeScreen(Warfare.MENU);
        // TODO Auto-generated method stub
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
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
    }
    private Warfare parent; // a field to store our orchestrator
    // our constructor with a Box2DTutorial argument
    public LoadingScreen(Warfare mygdx) {
        parent = mygdx;     // setting the argument to our field.
    }
}