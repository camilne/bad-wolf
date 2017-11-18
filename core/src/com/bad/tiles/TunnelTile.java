package com.bad.tiles;

import com.bad.Player;
import com.bad.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
        final World finalWorld = world;
        final ArrayList<Tile> finalNetworkTiles = networkTiles;
        world.fadeToBlack(new Runnable() {
            @Override
            public void run() {
                for(Tile tile : finalNetworkTiles) {
                    if(tile instanceof TunnelSpawnTile) {
                        finalWorld.setPlayerPosition(tile.getX(), tile.getY());
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean isBoxPlaceable() {
        return false;
    }
}
