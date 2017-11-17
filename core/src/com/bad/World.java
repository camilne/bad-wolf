package com.bad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class World implements InputProcessor{

    private int width;
    private int height;
    private Player player;
    private Tile[][] tiles;
    private OrthographicCamera camera;
    private float desPosX;
    private float desPosY;

    public World() {
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        try {
            loadLevel("levels/1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        int playerX = 0;
        int playerY = 0;

        width = Integer.parseInt(reader.readLine().split("=")[1].trim());
        height = Integer.parseInt(reader.readLine().split("=")[1].trim());

        tiles = new Tile[width][height];
        for(int j = height - 1; j >= 0; j--) {
            String[] strTiles = reader.readLine().split("\\s+");
            for (int i = 0; i < width; i++) {
                tiles[i][j] = new Tile(i, j, Integer.parseInt(strTiles[i]));
                if(tiles[i][j].isSpawn()) {
                    playerX = i * Tile.SIZE;
                    playerY = j * Tile.SIZE;
                }
            }
        }

        reader.close();

        player = new Player(playerX, playerY);
        camera.position.x = player.getCenterX();
        camera.position.y = player.getCenterY();
    }

    private boolean movePlayer(int x, int y) {
        int newX = player.getTileX() + x;
        int newY = player.getTileY() + y;

        if(newX < 0 || newX >= width || newY < 0 || newY >= height)
            return false;

        if(tiles[player.getTileX() + x][player.getTileY() + y].canTravel()) {
            player.move(x, y);
            return true;
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                movePlayer(0, 1);
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                movePlayer(-1, 0);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                movePlayer(0, -1);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                movePlayer(1, 0);
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
