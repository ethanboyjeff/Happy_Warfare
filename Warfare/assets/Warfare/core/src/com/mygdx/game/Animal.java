/*
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import java.util.HashSet;
import java.util.Set;
import com.mygdx.game.B2dModel;

public class Player extends Actor {
    public float speed = 5 * Resources.TILE_SIZE;
    private Vector2 currentVelocity = new Vector2(0, 0);

    public Player(Vector2 startPosition) {
        super();
        Texture texture = new Texture(Gdx.files.internal("panda256x256.png"));
        Sprite sprite = new Sprite(texture, 20, 20, 50, 50);

        // Set the actor's x and y coordinates, as well as width and height.
        setBounds(startPosition.x, startPosition.y, sprite.getWidth(), sprite.getHeight());

        // Create the Box2D body for the Player
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        // Create our body in the world using our body definition
        Body physicalBody = world.createBody(bodyDef);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = physicalBody.createFixture(fixtureDef);


        // connect Actor with Box2D body
        physicalBody.setUserData(this);

        addListener(FreeRoamingMovementListener(this));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        trackMovement(delta);
    }

    private void trackMovement(float delta) {
        // calculate how far the body has moved by multiplying the constant speed by how much time has elapsed
        float movement = delta * speed;

        // move the physical body by multiplying current velocity with the movement
        physicalBody.setLinearVelocity(currentVelocity.cpy().scl(movement));

        // move sprite by moving actor to the same coordinates as the physical body
        this.setPosition(physicalBody.getPosition().x - Resources.WORLD_TILE_SIZE / 2f, physicalBody.getPosition().y - Resources.WORLD_TILE_SIZE / 2f);
    }

    void setStateAndVelocity(PlayerState newState, Vector2 newVelocity) {
        // Update our velocity and enact it on our physical body.
        currentVelocity = newVelocity;
        physicalBody.setLinearVelocity(currentVelocity);

        // Update our state and let the sprite animator know of our new state.

        // Trigger hooks upon a state change.
    }
}
//FreeRoamingMovementListener.java
class FreeRoamingMovementListener extends InputListener {
    private final Player player;
    private final Set<Integer> pressedKeyCodes = new HashSet<>();

    public FreeRoamingMovementListener(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        pressedKeyCodes.add(keycode);

        // Step 1: Determining the state.
        PlayerState state = getPlayerStateBasedOnCurrentlyPressedKeys();
        if (state == null) {
            pressedKeyCodes.remove(keycode);
            return false;
        }

        // Step 2: Translate the direction the player is facing into velocity.
        Vector2 newVelocity = state.calculateDirectionVector();

        // Step 3: Update velocity & state in `Player`.
        player.setStateAndVelocity(state, newVelocity);

        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        pressedKeyCodes.remove(keycode);

        // Step 1: Determining the state.
        PlayerState state = getPlayerStateBasedOnCurrentlyPressedKeys();

        // Step 2: Translate the state of player into velocity or default to Vector2.Zero.
        Vector2 newVelocity = Vector2.Zero;
        if (state != null) {
            newVelocity = state.calculateDirectionVector();
        }

        // Step 3: Update velocity & state in `Player`.
        updatePlayerState(state, newVelocity);

        return true;
    }

    private PlayerState getPlayerStateBasedOnCurrentlyPressedKeys() {
        // get sprite based on walking direction
        if (pressedKeyCodes.contains(Input.Keys.UP)) {
            if (pressedKeyCodes.contains(Input.Keys.RIGHT)) {
                return PlayerState.WALKING_NE;
            } else if (pressedKeyCodes.contains(Input.Keys.LEFT)) {
                return PlayerState.WALKING_NW;
            } else {
                return PlayerState.WALKING_N;
            }
        } else if (pressedKeyCodes.contains(Input.Keys.DOWN)) {
            if (pressedKeyCodes.contains(Input.Keys.RIGHT)) {
                return PlayerState.WALKING_SE;
            } else if (pressedKeyCodes.contains(Input.Keys.LEFT)) {
                return PlayerState.WALKING_SW;
            } else {
                return PlayerState.WALKING_S;
            }
        } else if (pressedKeyCodes.contains(Input.Keys.RIGHT)) {
            return PlayerState.WALKING_E;
        } else if (pressedKeyCodes.contains(Input.Keys.LEFT)) {
            return PlayerState.WALKING_W;
        } else {
            return null;
        }
    }
}
//PlayerState.java
public enum PlayerState {
    STANDING_S, STANDING_N, STANDING_E, STANDING_W,
    WALKING_S, WALKING_N, WALKING_E, WALKING_W, WALKING_NE, WALKING_SE, WALKING_SW, WALKING_NW,
    SITTING_W,
    PICKUP_S, PICKUP_N, PICKUP_E, PICKUP_W,
    HOLD_S,
    ;

    private static final float ONE_ON_ROOT_TWO = (float) (1.0 / Math.sqrt(2));

    public Vector2 calculateDirectionVector() {
        return switch (this) {
            case WALKING_N, STANDING_N -> new Vector2(0, 1);
            case WALKING_S, STANDING_S -> new Vector2(0, -1);
            case WALKING_E, STANDING_E -> new Vector2(1, 0);
            case WALKING_W, STANDING_W -> new Vector2(-1, 0);
            case WALKING_NE -> new Vector2(ONE_ON_ROOT_TWO, ONE_ON_ROOT_TWO);
            case WALKING_NW -> new Vector2(-ONE_ON_ROOT_TWO, ONE_ON_ROOT_TWO);
            case WALKING_SE -> new Vector2(ONE_ON_ROOT_TWO, -ONE_ON_ROOT_TWO);
            case WALKING_SW -> new Vector2(-ONE_ON_ROOT_TWO, -ONE_ON_ROOT_TWO);
            case SITTING_W -> new Vector2(0, 0);
            case PICKUP_S, PICKUP_E, PICKUP_W, PICKUP_N -> new Vector2(0, 0);
            case HOLD_S -> new Vector2(0, 0);
        };
    }
*/
