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
public class TunnelTile extends Tile {
    private static Sound tunnelSound = Gdx.audio.newSound(Gdx.files.local("sounds/tunnel.mp3"));

    public TunnelTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isTravelable() {
        return true;
    }

    @Override
    protected int getRegionX() {
        return 6;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }

    @Override
    public void onPlayerEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        tunnelSound.play();
        for(Tile tile : networkTiles) {
            if(tile instanceof TunnelSpawnTile) {
                world.setPlayerPosition(tile.getX(), tile.getY());
                break;
            }
        }
    }
}
