package com.bad.tiles;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class ButtonTile extends Tile {
    public ButtonTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isTravelable() {
        return true;
    }

    @Override
    public boolean shouldPropogateAction() {
        return true;
    }

    @Override
    protected int getRegionX() {
        return 2;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }
}
