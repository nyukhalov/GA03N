package com.github.nyukhalov.ga03n.engine;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameObject extends Entity {

    public static final GameObject EMPTY_PREFAB = new GameObject();

    public static GameObject createEmpty() {
        return instantiate(EMPTY_PREFAB);
    }

    // finds a first matched game object with name `name` or null otherwise
    public static GameObject find(String name) {
        return World.current.find(name);
    }

    public byte layer = 0; // 0+

    private final List<Component> components = new ArrayList<>();

    public final float size = 1f;
    public final Vector2 position = new Vector2();
    public boolean isDead = false;

    public void sendMessage(Consumer<? super Component> action) {
        components.forEach(action);
    }

    public <T extends Component> T addComponent(Class<T> clazz) {
        try {
            T component = clazz.newInstance();
            addExistingComponent(component);
            return component;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    void addExistingComponent(Component component) {
        component.setGameObject(this);
        components.add(component);
    }

    public List<Component> getComponents() {
        return components;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component c: components) {
            if (clazz.isInstance(c)) return (T) c;
        }
        return null;
    }
}
