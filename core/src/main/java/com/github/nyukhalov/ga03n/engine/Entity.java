package com.github.nyukhalov.ga03n.engine;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Entity {

    public String name = UUID.randomUUID().toString();

    public static <T extends Entity> T instantiate(T original) {
        return instantiate(original, null);
    }

    public static <T extends Entity> T instantiate(T original, Vector2 position) {
        boolean isComponent = original instanceof Component;

        GameObject go = getGameObject(original); // original game object
        GameObject newGameObject = instantiateGO(go);

        if (position != null) {
            newGameObject.position.set(position);
        }
        if (isComponent) {
            Component c = (Component) original;
            return (T) getComponent(newGameObject, c);
        }
        else return (T) newGameObject;
    }

    private static <T extends Component> T getComponent(GameObject go, T component) {
        return go.getComponent((Class<T>) component.getClass());
    }

    private static <T extends Entity> GameObject getGameObject(T entity) {
        if (entity instanceof Component) {
            return ((Component) entity).gameObject;
        }
        return (GameObject) entity;
    }

    private static GameObject instantiateGO(GameObject original) {
        try {
            GameObject newGO = original.getClass().newInstance();

            newGO.layer = original.layer;
            newGO.position.set(original.position);

            World.current.addGO(newGO);

            return newGO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
