package com.bad;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class Tile {
    private enum TileType {
        GROUND(0, true),
        WALL(1, false),
        SPAWN(0, true);

        private int imageId;
        private boolean isTravelable;

        TileType(int imageId, boolean isTravelable) {
            this.imageId = imageId;
            this.isTravelable = isTravelable;
        }

        public int getImageId() {
            return imageId;
        }

        public boolean isTravelable() {
            return isTravelable;
        }
    }

    public static final int SIZE = 32;

    private static Texture[] images = new Texture[] {
            new Texture("images/tile.png"),
            new Texture("images/tile2.png")
    };

    private int x;
    private int y;
    private int id;
    private TileType type;

    public Tile(int x, int y, int id) {
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.id = id;

        type = TileType.values()[id];
    }

    public void render(SpriteBatch batch) {
        batch.draw(images[type.getImageId()], x, y, SIZE, SIZE);
    }

    public boolean canTravel() {
        return type.isTravelable();
    }

    public boolean isSpawn() {
        return type == TileType.SPAWN;
    }
}
