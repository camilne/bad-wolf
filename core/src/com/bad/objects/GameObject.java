package com.bad.objects;

import com.bad.tiles.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public abstract class GameObject {
    private static TextureRegion[][] images;

    static {
        Texture image = new Texture("images/objects.png");
        final int IMG_OBJECT_SIZE = 32;

        int width = image.getWidth() / IMG_OBJECT_SIZE;
        int height = image.getHeight() / IMG_OBJECT_SIZE;
        images = new TextureRegion[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                images[i][j] = new TextureRegion(image, i * IMG_OBJECT_SIZE, j * IMG_OBJECT_SIZE, IMG_OBJECT_SIZE, IMG_OBJECT_SIZE);
            }
        }
    }

    private float x;
    private float y;

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(images[getRegionX()][getRegionY()], x, y, Tile.SIZE, Tile.SIZE);
    }

    protected abstract int getRegionX();

    protected abstract int getRegionY();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getTileX() {
        return (int)(x / Tile.SIZE);
    }

    public int getTileY() {
        return (int)(y / Tile.SIZE);
    }
}
