package com.bad.tiles;

import com.bad.Player;
import com.bad.World;

import java.util.ArrayList;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class ReleasableButtonTile extends ButtonTile {
    public ReleasableButtonTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean shouldPropogateAction() {
        return true;
    }

    @Override
    public void onPlayerEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
        isPressed = !isPressed;
    }

    @Override
    public void onPlayerExit(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
        isPressed = !isPressed;
    }

    @Override
    public void onBlockEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
        isPressed = !isPressed;
    }

    @Override
    public void onBlockExit(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
        isPressed = !isPressed;
    }

    @Override
    public int getRegionX() {
        return 0;
    }

    @Override
    public int getRegionY() {
        return 1;
    }
}
