package com.bad.tiles;

import com.bad.Player;
import com.bad.World;

import java.util.ArrayList;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class StairTile extends Tile {
    public StairTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isTravelable() {
        return true;
    }

    @Override
    protected int getRegionX() {
        return 5;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }

    @Override
    public void onPlayerEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        world.nextLevel();
    }
}
