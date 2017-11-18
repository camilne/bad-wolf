package com.bad.tiles;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class DoorTile extends Tile{
    private boolean isOpened;

    public DoorTile(int x, int y) {
        super(x, y);
        isOpened = false;
    }

    @Override
    public boolean isTravelable() {
        return isOpened;
    }

    @Override
    public void onAction() {
        isOpened = !isOpened;
    }

    @Override
    protected int getRegionX() {
        return isOpened ? 4 : 3;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }
}
