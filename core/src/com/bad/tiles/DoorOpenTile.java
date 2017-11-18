package com.bad.tiles;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class DoorOpenTile extends DoorTile {
    public DoorOpenTile(int x, int y) {
        super(x, y);
        isOpened = true;
    }
}
