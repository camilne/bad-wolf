package com.bad.tiles;

import com.bad.Player;
import com.bad.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class StairTile extends Tile {
    private static Sound stairSound = Gdx.audio.newSound(Gdx.files.local("sounds/stairs2.mp3"));

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
        stairSound.play();
        world.nextLevel();

    }
}
