package com.github.nyukhalov.ga03n.simulation.component;

import com.badlogic.gdx.math.Vector2;
import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.Entity;
import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.DNA;
import com.github.nyukhalov.ga03n.simulation.prefab.MovingWallPrefab;
import com.github.nyukhalov.ga03n.simulation.prefab.PlayerPrefab;
import com.github.nyukhalov.ga03n.simulation.prefab.WallPrefab;
import com.github.nyukhalov.ga03n.simulation.component.render.RenderComponent;

import java.util.Objects;

public class GameScript extends Component {
    private final WallPrefab wallPrefab = new WallPrefab();
    private final MovingWallPrefab movingWallPrefabPrefab = new MovingWallPrefab();
    private final PlayerPrefab playerPrefab = new PlayerPrefab();

    private final int leftLaneX = 24;
    private final int laneStartY = 10;
    public int nrOfLanes = 3;
    public int laneLength = 10;
    public GameObject movingWall;
    public Vector2 movingWallPos;

    @Override
    protected void awake() {
        for (int x = leftLaneX; x < leftLaneX + nrOfLanes; x++) {
            addWallAt(x, laneStartY - 1);
            addWallAt(x, laneStartY + laneLength + 1);
        }
        for (int y = laneStartY - 1; y < laneStartY + laneLength + 2; y++) {
            addWallAt(leftLaneX - 1, y);
            addWallAt(leftLaneX + nrOfLanes, y);
        }

        movingWall = createMovingWall();
        movingWallPos = movingWall.position;
    }

    private void addWallAt(int x, int y) {
        Entity.instantiate(wallPrefab, new Vector2(x, y));
    }

    private MovingWallPrefab createMovingWall() {
        int movingWallStartY = laneStartY + laneLength + 1;

        MovingWallPrefab movingWall = Entity.instantiate(movingWallPrefabPrefab, new Vector2(leftLaneX, movingWallStartY));

        MovingWallController movingWallController = movingWall.getComponent(MovingWallController.class);
        movingWallController.startY = movingWallStartY;
        movingWallController.minY = laneStartY - 1;
        movingWallController.firstLaneOffset = leftLaneX;
        movingWallController.nrOfLanes = nrOfLanes;

        return movingWall;
    }

    public GameObject addNewPlayer(DNA dna) {
        Objects.requireNonNull(dna);

        PlayerPrefab player = Entity.instantiate(playerPrefab, new Vector2(leftLaneX + 1, laneStartY));

        RenderComponent renderer = player.getComponent(RenderComponent.class);
        renderer.color = dna.color;

        NNController nnController = player.getComponent(NNController.class);
        nnController.dna = dna;

        BehaviorComponent behavior = player.getComponent(BehaviorComponent.class);
        behavior.minX = leftLaneX;
        behavior.maxX = leftLaneX + nrOfLanes - 1;

        return player;
    }
}
