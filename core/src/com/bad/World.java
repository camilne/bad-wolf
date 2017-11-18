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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class World implements InputProcessor {

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
    private GameObject[][] objects;
    private BlockObject block;
    private boolean blockAlongX;


    public World(String avatarImage) {
        this.avatarImage = avatarImage;
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        music.play();
        music.loop();
        connectedTiles = new HashMap<Integer, ArrayList<Tile>>();
        block = null;
        blockAlongX = false;

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

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        renderTiles(batch);

        renderObjects(batch);

        player.render(batch);
    }

    private void renderTiles(SpriteBatch batch) {
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
                        int connect = Integer.parseInt(strTiles[i].split("=")[1]);
                        if (!connectedTiles.containsKey(connect)) {
                            connectedTiles.put(connect, new ArrayList<Tile>());
                        }
                        connectedTiles.get(connect).add(tiles[i][j]);
                        tiles[i][j].setConnectedNetwork(connect);
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

    private boolean moveBlock(int x, int y) {
        if(block == null)
            return false;

        int newX = block.getTileX() + x;
        int newY = block.getTileY() + y;

        if(newX < 0 || newX >= width || newY < 0 || newY >= height)
            return false;

        if(!tiles[newX][newY].isTravelable())
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
            player.move(x, y);
            Tile currentTile = tiles[player.getTileX()][player.getTileY()];

            ArrayList<Tile> networkTiles = new ArrayList<Tile>();
            if(connectedTiles.containsKey(currentTile.getConnectedNetwork())) {
                networkTiles = connectedTiles.get(currentTile.getConnectedNetwork());
            }

            currentTile.onPlayerEnter(this, player, networkTiles);

            if(currentTile.shouldPropogateAction()) {
                for(Tile tile : networkTiles) {
                    tile.onAction();
                }
            }
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
        tiles[x][y].onBlockEnter(this, player, networkTiles);

        if(currentTile.shouldPropogateAction()) {
            for(Tile tile : networkTiles) {
                tile.onAction();
            }
        }
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
            level = 1;
        }
        loadLevel("levels/" + (level) + ".txt");
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
                    if(movePlayer(0, 1))
                        onBlockEnter();
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                if(block == null || blockAlongX)
                    if(movePlayer(-1, 0))
                        onBlockEnter();
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                if(block == null || !blockAlongX)
                    if(movePlayer(0, -1))
                        onBlockEnter();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                if(block == null || blockAlongX)
                    if(movePlayer(1, 0))
                        onBlockEnter();
                break;
            case Input.Keys.SPACE:
                block = selectBlock();
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
