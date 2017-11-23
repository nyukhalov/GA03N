package com.github.nyukhalov.ga03n.engine;

import java.util.Objects;

public abstract class Component extends Entity implements Cloneable {
    protected GameObject gameObject;

    void setGameObject(GameObject gameObject) {
        Objects.requireNonNull(gameObject);
        this.gameObject = gameObject;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        return gameObject.getComponent(clazz);
    }

    // the method is called before start, and immediately after prefab has instantiated
    protected void awake() {}

    // the method is called before the first frame for all components of all objects
    protected void start() {}

    protected void update(float delta) {}

    // the method is called once per frame, after update of all objects has finished;
    protected void lateUpdate(float delta) {}

    protected void onRender() {}

    protected void onGUI() {}

    @Override
    protected Component clone() {
        try {
            return (Component) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
