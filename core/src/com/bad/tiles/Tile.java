package com.bad.tiles;

import com.bad.Player;
import com.bad.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public abstract class Tile {

    public static final int SIZE = 32;

    private static TextureRegion[][] images;

    static {
        Texture image = new Texture("images/tiles.png");
        final int IMG_TILE_SIZE = 32;

        int width = image.getWidth() / IMG_TILE_SIZE;
        int height = image.getHeight() / IMG_TILE_SIZE;
        images = new TextureRegion[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                images[i][j] = new TextureRegion(image, i * IMG_TILE_SIZE, j * IMG_TILE_SIZE, IMG_TILE_SIZE, IMG_TILE_SIZE);
            }
        }
    }

    private int x;
    private int y;
    private int connected;

    public Tile(int x, int y) {
        this.x = x * SIZE;
        this.y = y * SIZE;
        connected = -1;
    }

    public void render(SpriteBatch batch) {
        batch.draw(images[getRegionX()][getRegionY()], x, y, SIZE, SIZE);
    }

    public abstract boolean isTravelable();

    public boolean isSpawn() {
        return false;
    }

    public void onAction() { }

    public void onPlayerEnter(World world, Player player) { }

    public boolean shouldPropogateAction() {
        return false;
    }

    public void setConnectedNetwork(int connected) {
        this.connected = connected;
    }

    public int getConnectedNetwork() {
        return connected;
    }

    protected abstract int getRegionX();

    protected abstract int getRegionY();
}
