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
public class ButtonTile extends Tile {
    private boolean isPressed = false;
    private static Sound pressButton = Gdx.audio.newSound(Gdx.files.local("../assets/sounds/press_button.mp3"));

    public ButtonTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isTravelable() {
        return true;
    }

    @Override
    public boolean shouldPropogateAction() {
        return !isPressed;
    }

    @Override
    public void onPlayerEnter(World world, Player player, ArrayList<Tile> networkTiles) {
        pressButton.play();
    }

    @Override
    public void onAction() {
        if (!isPressed) {
            isPressed = true;
        }
    }

    @Override
    protected int getRegionX() {
        return !isPressed ? 2 : 7;
    }

    @Override
    protected int getRegionY() {
        return 0;
    }
}
