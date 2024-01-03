package com.mygdx.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Warfare;

public class LoadingScreen implements Screen {
    private Warfare parent;
    private TextureAtlas atlas;
    private AtlasRegion title;

    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    private int currentLoadingStage = 0;

    // timer for exiting loading screen
    public float countDown = 5f;
    private Stage stage;
    private SpriteBatch sb;


    public LoadingScreen(Warfare mygdx){
        parent = mygdx;
        stage = new Stage(new ScreenViewport());

        loadAssets();
        // initiate queueing of images but don't start loading
        parent.assMan.queueAddImages();
        System.out.println("Loading images....");
    }

    private void loadAssets() {
        // load loading images and wait until finished
        parent.assMan.queueAddLoadingImages();
        parent.assMan.manager.finishLoading();

        // get images used to display loading progress
        atlas = parent.assMan.manager.get("Images/loading.atlas");
        sb = new SpriteBatch();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {
        // load loading images and wait until finished
        parent.assMan.queueAddLoadingImages();
        parent.assMan.manager.finishLoading();

// get images used to display loading progress
        atlas = parent.assMan.manager.get("Images/loading.atlas");
        title = atlas.findRegion("loading.png");
// initiate queueing of images but don't start loading
        parent.assMan.queueAddImages();
        System.out.println("Loading images....");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //  clear the screen
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // start SpriteBatch and draw the logo
        sb.begin();
        sb.draw(title, 135, 250);
        sb.end();

        // check if the asset manager has finished loading
        if (parent.assMan.manager.update()) { // Load some, will return true if done loading
            currentLoadingStage+= 1;
            switch(currentLoadingStage){
                case FONT:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddFonts(); // first load done, now start fonts
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects....");
                    parent.assMan.queueAddParticleEffects(); // fonts are done now do party effects
                    break;
                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddMusic();
                    break;
                case 5:
                    System.out.println("Finished"); // all done
                    break;
            }
            if (currentLoadingStage >5){
                countDown -= delta;  // timer to stay on loading screen for short preiod once done loading
                currentLoadingStage = 5;  // cap loading stage to 5 as will use later to display progress bar anbd more than 5 would go off the screen
                if(countDown < 0){ // countdown is complete
                    parent.changeScreen(Warfare.MENU);  /// go to menu screen
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
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
    }
}