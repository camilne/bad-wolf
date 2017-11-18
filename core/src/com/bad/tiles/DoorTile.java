package com.bad.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class DoorTile extends Tile{
    protected boolean isOpened;
    private static Sound openDoor = Gdx.audio.newSound(Gdx.files.local("sounds/open_door.mp3"));
    private static Sound closeDoor = Gdx.audio.newSound(Gdx.files.local("sounds/door_close.mp3"));

    public DoorTile(int x, int y) {
        super(x, y);
        isOpened = false;
    }

    @Override
    public boolean isTravelable() {
        return isOpened;
    }

    @Override
    public boolean isBoxPlaceable() {
        return isTravelable();
    }

    @Override
    public void onAction() {
        isOpened = !isOpened;
        if (isOpened) {
            openDoor.play();
        } else {
            closeDoor.play();
        }
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
