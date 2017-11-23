package com.github.nyukhalov.ga03n.simulation.component;

import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.World;

import java.util.Random;

public class MovingWallController extends Component {

    private final float speed = 15f; // per second

    private Random random;

    public float startY;
    public float minY;
    public float firstLaneOffset;
    public int nrOfLanes;

    @Override
    protected void awake() {
        random = World.current.random;
    }

    @Override
    public void update(float delta) {
        gameObject.position.y -= speed * delta;
        if (gameObject.position.y <= minY) {
           resetPosition();
        }
    }

    public void resetPosition() {
        int curLane = (int) (gameObject.position.x - firstLaneOffset);
        int nextLane = curLane;
        while(curLane == nextLane) {
            nextLane = random.nextInt(nrOfLanes);
        }
        gameObject.position.x = firstLaneOffset + nextLane;
        gameObject.position.y = startY;
    }
}
