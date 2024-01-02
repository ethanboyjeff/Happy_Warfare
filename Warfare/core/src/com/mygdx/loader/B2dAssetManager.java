package com.mygdx.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class B2dAssetManager {
    public final AssetManager manager = new AssetManager();
    public final String panda = "Input/Game/panda256x256.png";
    public final String giraffe = "Input/Game/giraffe.png";
    //Music
    public final String music = "Audio/Monkeys-Spinning-Monkeys(chosic.com).mp3";
    public final String buttonClick = "Audio/buttonClick.wav";
    // Skin
    public final String skin = "Skin/glassy-ui.json";

    public void queueAddImages(){
        manager.load(panda, Texture.class);
        manager.load(giraffe, Texture.class);
    }
    public void queueAddMusic() {
        manager.load(music, Music.class);
    }
    public void queueAddSound() {
        manager.load(buttonClick, Sound.class);
    }

    public void queueAddSkin(){
        SkinParameter params = new SkinParameter("Skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, params);
    }
}
