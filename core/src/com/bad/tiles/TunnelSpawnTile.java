package com.bad.tiles;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class TunnelSpawnTile extends Tile{
    public TunnelSpawnTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isTravelable() {
        return true;
    }

    @Override
    protected int getRegionX() {
        return 0;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }
}
