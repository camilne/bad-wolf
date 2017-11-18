package com.bad;

import com.bad.tiles.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class Player {

    private float x;
    private float y;
    private float size;
    private Texture texture;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        size = Tile.SIZE;
        texture = new Texture("images/badlogic.jpg");
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, size, size);
    }

    public void move(int x, int y) {
        this.x += x * Tile.SIZE;
        this.y += y * Tile.SIZE;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getCenterX() {
        return x + size / 2;
    }

    public float getCenterY() {
        return y + size / 2;
    }

    public int getTileX() {
        return (int)(getCenterX() / Tile.SIZE);
    }

    public int getTileY() {
        return (int)(getCenterY() / Tile.SIZE);
    }
}
