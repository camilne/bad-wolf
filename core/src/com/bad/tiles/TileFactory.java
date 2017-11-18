package com.bad.tiles;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class TileFactory {

    private static HashMap<Integer, Class<? extends Tile>> tiles;

    static {
        tiles = new HashMap<Integer, Class<? extends Tile>>();
        tiles.put(0, GroundTile.class);
        tiles.put(1, WallTile.class);
        tiles.put(2, SpawnTile.class);
        tiles.put(3, ButtonTile.class);
        tiles.put(4, DoorTile.class);
        tiles.put(6, StairTile.class);
        tiles.put(7, TunnelTile.class);
        tiles.put(8, TunnelSpawnTile.class);
    }

    public static Tile create(int x, int y, int id) {
        try {
            Constructor<? extends Tile> construct;

            if (tiles.containsKey(id)) {
                construct = tiles.get(id).getConstructor(int.class, int.class);
            } else {
                System.out.println("Invalid id: " + id);
                construct = tiles.get(0).getConstructor(int.class, int.class);
            }

            return construct.newInstance(x, y);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
