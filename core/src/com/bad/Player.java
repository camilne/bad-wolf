package com.bad;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class Player implements InputProcessor {

    private float x;
    private float y;
    private Texture texture;

    public Player() {
        this.x = 0;
        this.y = 0;
        texture = new Texture("badlogic.jpg");
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, World.TILE_SIZE, World.TILE_SIZE);
    }

    private void move(int x, int y) {
        this.x += x * World.TILE_SIZE;
        this.y += y * World.TILE_SIZE;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                move(0, 1);
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                move(-1, 0);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                move(0, -1);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                move(1, 0);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
