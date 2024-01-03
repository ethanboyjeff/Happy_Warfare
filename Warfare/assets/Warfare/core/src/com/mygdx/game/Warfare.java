package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.loader.B2dAssetManager;
import com.mygdx.views.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;


public class Warfare extends Game {
    SpriteBatch batch;
    Texture img;

    private LoadingScreen loadingScreen;
    private SettingsScreen settingsScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;
    private PauseScreen pauseScreen;


    private AppPreferences settings;
    public B2dAssetManager assMan = new B2dAssetManager();
    private Music music;

    public final static int MENU = 0;
    public final static int SETTINGS = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    public final static int PAUSEGAME = 4;


    @Override
    public void create() {
        LoadingScreen loadingScreen = new LoadingScreen(this);
        settings = new AppPreferences();
        setScreen(loadingScreen);

        // tells our asset manger that we want to load the images set in loadImages method
        assMan.queueAddMusic();
        // tells the asset manager to load the images and wait until finished loading.
        assMan.manager.finishLoading();
        // loads the 2 sounds we use
        music = assMan.manager.get("Audio/Monkeys-Spinning-Monkeys(chosic.com).mp3");
        music.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU:
                if (menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case SETTINGS:
                if (settingsScreen == null) settingsScreen = new SettingsScreen(this);
                this.setScreen(settingsScreen);
                break;
            case APPLICATION:
                if (mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if (endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
            case PAUSEGAME:
                if (pauseScreen == null) pauseScreen = new PauseScreen(this);
                this.setScreen(pauseScreen);
                break;
        }
    }

    public AppPreferences getPreferences() {
        return this.settings;
    }

    @Override
    public void dispose() {
        music.dispose();
        assMan.manager.dispose();
    }
}