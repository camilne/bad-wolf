package com.bad.tiles;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class GroundTile extends Tile {
    public GroundTile(int x, int y) {
        super(x, y);
    }

    @Override
    protected int getRegionX() {
        return 0;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }

    @Override
    public boolean isTravelable() {
        return true;
    }
}
