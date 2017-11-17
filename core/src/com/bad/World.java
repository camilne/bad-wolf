package com.bad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class World {

    private int width;
    private int height;
    private Player player;
    private Tile[][] tiles;
    private OrthographicCamera camera;
    private float desPosX;
    private float desPosY;

    public World() {
        player = new Player();
        Gdx.input.setInputProcessor(player);

        width = 20;
        height = 30;
        tiles = new Tile[width][height];
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        desPosX = 0;
        desPosY = 0;
    }

    public void update() {
        player.update();

        centerCamera(player.getCenterX(), player.getCenterY());
        camera.translate((desPosX - camera.position.x) / 10f,
                (desPosY - camera.position.y) / 10f);
        camera.update();
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j].render(batch);
            }
        }

        player.render(batch);
    }

    private void centerCamera(float x, float y) {
        desPosX = x;
        desPosY = y;
    }
}
