package com.bad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;

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

        try {
            loadLevel("levels/1.txt");
        } catch (IOException e) {
            e.printStackTrace();
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

        renderTiles(batch);

        player.render(batch);
    }

    private void renderTiles(SpriteBatch batch) {
        for(int i = -2; i <= 2; i++) {
            for(int j = -2; j <= 2; j++) {
                if(Math.abs(i) == 2 && Math.abs(j) == 2) {
                    batch.setColor(1, 1, 1, 0.25f);
                } else if(Math.abs(i) == 2 || Math.abs(j) == 2) {
                    batch.setColor(1, 1, 1, 0.5f);
                } else {
                    batch.setColor(1, 1, 1, 1);
                }

                int x = i + player.getTileX();
                int y = j + player.getTileY();
                if(x >= 0 && x < width && y >= 0 && y < height)
                    tiles[x][y].render(batch);
            }
        }
        batch.setColor(1, 1, 1, 1);
    }

    private void centerCamera(float x, float y) {
        desPosX = x;
        desPosY = y;
    }

    private void loadLevel(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));

        width = Integer.parseInt(reader.readLine().split("=")[1].trim());
        height = Integer.parseInt(reader.readLine().split("=")[1].trim());

        tiles = new Tile[width][height];
        for(int j = 0; j < height; j++) {
            String[] strTiles = reader.readLine().split("\\s+");
            for (int i = 0; i < width; i++) {
                tiles[i][j] = new Tile(i, j, Integer.parseInt(strTiles[i]));
            }
        }

        reader.close();
    }
}
