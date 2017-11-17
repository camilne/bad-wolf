package com.bad;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class Tile {
    public static final int SIZE = 32;

    private static Texture[] images = new Texture[] {
            new Texture("images/tile.png"),
            new Texture("images/tile2.png")
    };

    private int x;
    private int y;
    private Texture image;

    public Tile(int x, int y) {
        this.x = x * SIZE;
        this.y = y * SIZE;

        int imageNum = new Random().nextInt(2);
        image = images[imageNum];
    }

    public void render(SpriteBatch batch) {
        batch.draw(image, x, y, SIZE, SIZE);
    }
}
