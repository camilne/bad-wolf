package com.bad;

import com.bad.tiles.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class Player {

    public enum Direction {
        UP,LEFT,RIGHT,DOWN,NONE
    }

    private float x;
    private float y;
    private int rotation;
    private float size;
    private static Texture texture;
    private Direction direction;

    public Player(float x, float y, String avatarImage) {
        this.x = x;
        this.y = y;

        size = Tile.SIZE;
        direction = Direction.NONE;
        if(texture == null)
            texture = new Texture("images/" + avatarImage);
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, size/2, size/2, size, size,1,1,rotation,0,0,32,32,false,false);
    }

    public void move(int x, int y) {
        this.x += x * Tile.SIZE;
        this.y += y * Tile.SIZE;
    }

    public void rotate(Direction direction){
        this.direction = direction;

        if(direction == Direction.UP){
            rotation = 0;
        }
        if(direction == Direction.DOWN){
            rotation = 180;
        }
        if(direction == Direction.RIGHT){
            rotation = -90;
        }
        if(direction == Direction.LEFT){
            rotation = 90;
        }
    }

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

    public float getCenterX() {
        return x + size / 2;
    }

    public float getCenterY() {
        return y + size / 2;
    }

    public int getTileX() {
        return (int)(getCenterX() / Tile.SIZE);
    }

    public int getTileY() {
        return (int)(getCenterY() / Tile.SIZE);
    }

    public Direction getDirection() {
        return direction;
    }
}
