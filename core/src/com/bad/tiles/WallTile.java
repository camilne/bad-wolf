package com.bad.tiles;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class WallTile extends Tile {
    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isTravelable() {
        return false;
    }

    @Override
    protected int getRegionX() {
        return 1;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }
}
