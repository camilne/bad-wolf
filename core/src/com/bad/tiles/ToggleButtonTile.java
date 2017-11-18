package com.bad.tiles;

import com.bad.Player;
import com.bad.World;

import java.util.ArrayList;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class ToggleButtonTile extends ButtonTile {
    public ToggleButtonTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void onPlayerEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
        isPressed = !isPressed;
    }

    @Override
    public void onBlockEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
        isPressed = !isPressed;
    }
}
