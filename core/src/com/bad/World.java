package com.bad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class World {

    public static final int TILE_SIZE = 32;

    private Player player;

    public World() {
        player = new Player();
        Gdx.input.setInputProcessor(player);
    }

    public void update() {
        player.update();
    }

    public void render(SpriteBatch batch) {
        player.render(batch);
    }
}
