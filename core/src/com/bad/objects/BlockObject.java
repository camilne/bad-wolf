package com.bad.objects;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class BlockObject extends GameObject {
    public BlockObject(float x, float y) {
        super(x, y);
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
