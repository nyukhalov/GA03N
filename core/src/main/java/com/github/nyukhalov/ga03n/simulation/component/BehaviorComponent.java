package com.github.nyukhalov.ga03n.simulation.component;

import com.badlogic.gdx.math.Vector2;
import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.prefab.MovingWallPrefab;

public class BehaviorComponent extends Component {
    private final float maxLifetimeSec = 30f;
    private float lifetime = 0f;

    public float minX;
    public float maxX;

    private Vector2 movingWallPos;

    @Override
    protected void start() {
        movingWallPos = GameObject.find(MovingWallPrefab.NAME).position;
    }

    @Override
    public void update(float delta) {
        lifetime += delta;

        if (isLifetimeExceeded() || isCollisionWithWall(gameObject)) {
            gameObject.isDead = true;
        }
    }

    public void moveRight() {
        if (gameObject.position.x < maxX) gameObject.position.x++;
    }

    public void moveLeft() {
        if (gameObject.position.x > minX) gameObject.position.x--;
    }

    private boolean isLifetimeExceeded() {
        return lifetime >= maxLifetimeSec;
    }

    private boolean isCollisionWithWall(GameObject gameObject) {
        return Math.abs(movingWallPos.y - gameObject.position.y) < gameObject.size
                && Math.abs(movingWallPos.x - gameObject.position.x) < gameObject.size;
    }

    public float getLifetime() {
        return lifetime;
    }
}
