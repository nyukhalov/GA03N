package com.github.nyukhalov.ga03n.engine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class World {

    public static World current = null;

    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> notInitializedGO = new ArrayList<>();
    private final List<GameObject> toInitGO = new ArrayList<>();

    public final Random random = new Random(System.currentTimeMillis());
    public final ShapeRenderer shapeRenderer;
    public final SpriteBatch batch;
    public final BitmapFont font;

    public World(ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font) {
        if (current == null) current = this;
        this.shapeRenderer = shapeRenderer;
        this.batch = batch;
        this.font = font;
    }

    public void addGO(GameObject go) {
        go.sendMessage(Component::awake);
        notInitializedGO.add(go);
    }

    public void update(float delta) {
        initNewGO();

        gameObjects.forEach(o -> o.sendMessage(c -> c.update(delta)));
        gameObjects.forEach(o -> o.sendMessage(c -> c.lateUpdate(delta)));
        gameObjects.removeIf(o -> o.isDead);
    }

    private void initNewGO() {
        while(!notInitializedGO.isEmpty()) {
            gameObjects.addAll(notInitializedGO);
            toInitGO.addAll(notInitializedGO);
            notInitializedGO.clear();

            toInitGO.forEach(go -> go.sendMessage(Component::start));
            toInitGO.clear();
        }
    }

    public void render() {
        gameObjects.forEach(e -> e.sendMessage(Component::onRender));
    }

    public void renderUI() {
        gameObjects.forEach(e -> e.sendMessage(Component::onGUI));
    }

    // finds a first matched game object with name `name` or null otherwise
    public GameObject find(String name) {
        Objects.requireNonNull(name);

        return gameObjects.stream()
                .filter(go -> name.equals(go.name))
                .findFirst()
                .orElse(null);
    }
}
