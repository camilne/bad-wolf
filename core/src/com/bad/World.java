package com.bad;

import com.bad.objects.BlockObject;
import com.bad.objects.GameObject;
import com.bad.objects.ObjectFactory;
import com.bad.tiles.Tile;
import com.bad.tiles.TileFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import javafx.application.Application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class World implements InputProcessor {

    private static final int FADE_TIME = 2000;

    private int width;
    private int height;
    private Player player;
    private Tile[][] tiles;
    private OrthographicCamera camera;
    private float desPosX;
    private float desPosY;
    private HashMap<Integer, ArrayList<Tile>> connectedTiles;
    private int level;
    private int maxLevels;
    private String avatarImage;
    private static Sound music = Gdx.audio.newSound(Gdx.files.local("sounds/background_music.mp3"));
    private SpriteBatch batch;
    private Texture texture;
    private GameObject[][] objects;
    private BlockObject block;
    private boolean blockAlongX;
    private int lastBlockX;
    private int lastBlockY;
    private Runnable fadeTask;
    private float fadeTime;

    public World(String avatarImage) {
        batch = new SpriteBatch();
        texture = new Texture("images/black.png");
        this.avatarImage = avatarImage;
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        music.play();
        music.loop();
        connectedTiles = new HashMap<Integer, ArrayList<Tile>>();
        block = null;
        blockAlongX = false;
        lastBlockX = -1;
        lastBlockY = -1;
        fadeTime = FADE_TIME / 2.0f;

        level = 1;
        File levelsFolder = new File("levels/");
        if(levelsFolder.exists()) {
            File[] files = levelsFolder.listFiles();
            if(files == null) {
                maxLevels = 0;
            } else {
                maxLevels = files.length;
            }
        } else {
            maxLevels = 0;
        }

        loadLevel("levels/1.txt");
    }

    public void update() {
        player.update();

        centerCamera(player.getCenterX(), player.getCenterY());
        camera.translate((desPosX - camera.position.x) / 10f,
                (desPosY - camera.position.y) / 10f);
        camera.update();
    }

    public void render() {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        renderTiles();

        renderObjects(batch);

        player.render(batch);

        if(fadeTime <= FADE_TIME) {
            if(fadeTime < FADE_TIME / 3) {
                float a = map(0, FADE_TIME / 3, 0, 1, fadeTime);
                batch.setColor(1, 1, 1, a);
            } else if(fadeTime > FADE_TIME * 2.0 / 3) {
                float a = map(2.0f / 3* FADE_TIME, FADE_TIME, 1, 0, fadeTime);
                batch.setColor(1, 1, 1, a);
            } else {
                batch.setColor(1, 1, 1, 1);
                if(fadeTime >= FADE_TIME / 2 - 1000.0/60/2 && fadeTime < FADE_TIME / 2 + 1000.0/60/2 && fadeTask != null) {
                    fadeTask.run();
                }
            }

            batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            fadeTime += 1000.0f/60;
        }
        batch.end();
    }

    // Maps a variable x from a range [a,b] to [c,d]
    private float map(float a, float b, float c, float d, float x) {
        if(b - a == 0)
            return 0;

        return (x - a) / (b - a) * (d - c) + c;
    }

    private void renderTiles() {
        for(int i = -3; i <= 3; i++) {
            for(int j = -3; j <= 3; j++) {
                int ia = Math.abs(i);
                int ja = Math.abs(j);
                if(ia == 2 && ja == 2) {
                    batch.setColor(1, 1, 1, 0.25f);
                } else if((ia == 2 || ja == 2) && (ia != 3) && (ja != 3)) {
                    batch.setColor(1, 1, 1, 0.5f);
                }
                else if((ia == 3 && ja < 2) || (ja == 3 && ia < 2)){
                    batch.setColor(1, 1, 1, 0.25f);
                }
                else if(ia < 2 && ja < 2){
                    batch.setColor(1, 1, 1, 1);
                }
                else{
                    batch.setColor(1,1,1,0);
                }

                int x = i + player.getTileX();
                int y = j + player.getTileY();
                if(x >= 0 && x < width && y >= 0 && y < height)
                    tiles[x][y].render(batch);
            }
        }
        batch.setColor(1, 1, 1, 1);
    }

    private void renderObjects(SpriteBatch batch) {
        for(int i = -3; i <= 3; i++) {
            for(int j = -3; j <= 3; j++) {
                int ia = Math.abs(i);
                int ja = Math.abs(j);
                if(ia == 2 && ja == 2) {
                    batch.setColor(1, 1, 1, 0.25f);
                } else if((ia == 2 || ja == 2) && (ia != 3) && (ja != 3)) {
                    batch.setColor(1, 1, 1, 0.5f);
                }
                else if((ia == 3 && ja < 2) || (ja == 3 && ia < 2)){
                    batch.setColor(1, 1, 1, 0.25f);
                }
                else if(ia < 2 && ja < 2){
                    batch.setColor(1, 1, 1, 1);
                }
                else{
                    batch.setColor(1,1,1,0);
                }

                int x = i + player.getTileX();
                int y = j + player.getTileY();
                if(x >= 0 && x < width && y >= 0 && y < height)
                    if(objects[x][y] != null)
                        objects[x][y].render(batch);
            }
        }
        batch.setColor(1, 1, 1, 1);
    }

    private void centerCamera(float x, float y) {
        desPosX = x;
        desPosY = y;
    }

    private void loadLevel(String name) {
        if(!new File(name).exists()) {
            System.out.println("Level not found: " + name);
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));

            int playerX = 0;
            int playerY = 0;

            width = Integer.parseInt(reader.readLine().split("=")[1].trim());
            height = Integer.parseInt(reader.readLine().split("=")[1].trim());

            tiles = new Tile[width][height];
            objects = new GameObject[width][height];
            connectedTiles.clear();

            for (int j = height - 1; j >= 0; j--) {
                String[] strTiles = reader.readLine().split("\\s+");
                for (int i = 0; i < width; i++) {
                    String strId = strTiles[i].split("=")[0];
                    tiles[i][j] = TileFactory.create(i, j, Integer.parseInt(strId));

                    if (strTiles[i].split("=").length > 1) {
                        String[] networks = strTiles[i].split("=")[1].split(",");
                        for(String network : networks) {
                            int connect = Integer.parseInt(network);
                            if (!connectedTiles.containsKey(connect)) {
                                connectedTiles.put(connect, new ArrayList<Tile>());
                            }
                            connectedTiles.get(connect).add(tiles[i][j]);
                            tiles[i][j].setConnectedNetwork(connect);
                        }
                    }

                    if (tiles[i][j].isSpawn()) {
                        playerX = i * Tile.SIZE;
                        playerY = j * Tile.SIZE;
                    }
                }
            }

            String line;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                if(tokens.length != 3)
                    break;

                int id = Integer.parseInt(tokens[0]);
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                objects[x][y] = ObjectFactory.create(x * Tile.SIZE, y * Tile.SIZE, id);
            }

            reader.close();

            player = new Player(playerX, playerY, avatarImage);
            camera.position.x = player.getCenterX();
            camera.position.y = player.getCenterY();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void fadeToBlack(Runnable fadeTask) {
        this.fadeTask = fadeTask;
        fadeTime = 0;
    }

    private boolean moveBlock(int x, int y) {
        if(block == null)
            return false;

        int newX = block.getTileX() + x;
        int newY = block.getTileY() + y;

        if(newX < 0 || newX >= width || newY < 0 || newY >= height)
            return false;

        if(!tiles[newX][newY].isBoxPlaceable())
            return false;

        objects[newX][newY] = block;
        objects[block.getTileX()][block.getTileY()] = null;
        block.setX(newX * Tile.SIZE);
        block.setY(newY * Tile.SIZE);

        return true;
    }

    private boolean movePlayer(int x, int y) {
        if(block == null) {
            Player.Direction direction = Player.Direction.NONE;
            if (x > 0) {
                direction = Player.Direction.RIGHT;
            }
            if (y > 0) {
                direction = Player.Direction.UP;
            }
            if (x < 0) {
                direction = Player.Direction.LEFT;
            }
            if (y < 0) {
                direction = Player.Direction.DOWN;
            }
            player.rotate(direction);
        } else {
            if(!moveBlock(x, y))
                return false;
        }

        int newX = player.getTileX() + x;
        int newY = player.getTileY() + y;

        if(newX < 0 || newX >= width || newY < 0 || newY >= height) {
            moveBlock(-x, -y);
            return false;
        }

        if(tiles[player.getTileX() + x][player.getTileY() + y].isTravelable() && objects[player.getTileX() + x][player.getTileY() + y] == null) {
            Tile currentTile = tiles[player.getTileX()][player.getTileY()];
            ArrayList<Tile> networkTiles = new ArrayList<Tile>();
            if(connectedTiles.containsKey(currentTile.getConnectedNetwork())) {
                networkTiles = connectedTiles.get(currentTile.getConnectedNetwork());
            }

            if(currentTile.shouldPropogateAction()) {
                for(Tile tile : networkTiles) {
                    tile.onAction();
                }
            }

            tiles[player.getTileX()][player.getTileY()].onPlayerExit(this, player, networkTiles);

            player.move(x, y);

            currentTile = tiles[player.getTileX()][player.getTileY()];

            networkTiles = new ArrayList<Tile>();
            if(connectedTiles.containsKey(currentTile.getConnectedNetwork())) {
                networkTiles = connectedTiles.get(currentTile.getConnectedNetwork());
            }

            if(currentTile.shouldPropogateAction()) {
                for(Tile tile : networkTiles) {
                    tile.onAction();
                }
            }

            currentTile.onPlayerEnter(this, player, networkTiles);

            return true;
        }

        moveBlock(-x, -y);
        return false;
    }

    private void onBlockEnter() {
        if(block == null)
            return;

        int x = block.getTileX();
        int y = block.getTileY();

        ArrayList<Tile> networkTiles = new ArrayList<Tile>();
        Tile currentTile = tiles[x][y];
        if(connectedTiles.containsKey(currentTile.getConnectedNetwork())) {
            networkTiles = connectedTiles.get(currentTile.getConnectedNetwork());
        }

        if(currentTile.shouldPropogateAction()) {
            for(Tile tile : networkTiles) {
                tile.onAction();
            }
        }

        tiles[x][y].onBlockEnter(this, player, networkTiles);
    }

    private void onBlockExit() {
        if(block == null)
            return;

        int x = lastBlockX;
        int y = lastBlockY;

        if(x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        ArrayList<Tile> networkTiles = new ArrayList<Tile>();
        Tile currentTile = tiles[x][y];
        if(connectedTiles.containsKey(currentTile.getConnectedNetwork())) {
            networkTiles = connectedTiles.get(currentTile.getConnectedNetwork());
        }

        if(currentTile.shouldPropogateAction()) {
            for(Tile tile : networkTiles) {
                tile.onAction();
            }
        }

        tiles[x][y].onBlockExit(this, player, networkTiles);

        lastBlockX = block.getTileX();
        lastBlockY = block.getTileY();
    }

    public boolean setPlayerPosition(int x, int y) {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return false;

        if(tiles[x][y].isTravelable()) {
            player.setX(x * Tile.SIZE);
            player.setY(y * Tile.SIZE);
        }

        return true;
    }

    public void nextLevel() {
        if(++level > maxLevels) {
            playCredits();
            return;
        }
        loadLevel("levels/" + (level) + ".txt");
    }

    public void playCredits() {
        fadeToBlack(new Runnable() {
            @Override
            public void run() {
                music.stop();
                Application.launch(Credits.class, String.valueOf(Gdx.graphics.getWidth()), String.valueOf(Gdx.graphics.getHeight()));
                Gdx.app.exit();
            }
        });
    }

    private BlockObject selectBlock() {
        int dx = 0;
        int dy = 0;
        switch(player.getDirection()) {
            case UP:
                blockAlongX = false;
                dy = 1;
                break;
            case RIGHT:
                blockAlongX = true;
                dx = 1;
                break;
            case DOWN:
                blockAlongX = false;
                dy = -1;
                break;
            case LEFT:
                blockAlongX = true;
                dx = -1;
                break;
            default:
                return null;
        }
        int x = player.getTileX() + dx;
        int y = player.getTileY() + dy;
        if(x < 0 || x >= width || y < 0 || y >= height)
            return null;

        if(objects[x][y] == null)
            return null;

        if(objects[x][y] instanceof BlockObject)
            return (BlockObject)objects[x][y];

        return null;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                if(block == null || !blockAlongX)
                    if(movePlayer(0, 1)) {
                        onBlockEnter();
                        onBlockExit();
                    }
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                if(block == null || blockAlongX)
                    if(movePlayer(-1, 0)) {
                        onBlockEnter();
                        onBlockExit();
                    }
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                if(block == null || !blockAlongX)
                    if(movePlayer(0, -1)) {
                        onBlockEnter();
                        onBlockExit();
                    }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                if(block == null || blockAlongX)
                    if(movePlayer(1, 0)) {
                        onBlockEnter();
                        onBlockExit();
                    }
                break;
            case Input.Keys.SPACE:
                block = selectBlock();
                if(block != null) {
                    lastBlockX = block.getTileX();
                    lastBlockY = block.getTileY();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.SPACE:
                block = null;
                break;
        }
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
