package com.bad.objects;

import com.bad.tiles.Tile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class ObjectFactory {

    private static HashMap<Integer, Class<? extends GameObject>> objects;

    static {
        objects = new HashMap<Integer, Class<? extends GameObject>>();
        objects.put(0, BlockObject.class);
    }

    public static GameObject create(int x, int y, int id) {
        try {
            Constructor<? extends GameObject> construct;

            if (objects.containsKey(id)) {
                construct = objects.get(id).getConstructor(float.class, float.class);
            } else {
                System.out.println("Invalid id: " + id);
                construct = objects.get(0).getConstructor(float.class, float.class);
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
