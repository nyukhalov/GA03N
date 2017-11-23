package com.github.nyukhalov.ga03n.simulation.prefab;

import com.badlogic.gdx.graphics.Color;
import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.component.MovingWallController;
import com.github.nyukhalov.ga03n.simulation.component.render.RenderComponent;

public class MovingWallPrefab extends GameObject {

    public static String NAME = "MovingWall";

    public MovingWallPrefab() {
        name = NAME;
        RenderComponent render = addComponent(RenderComponent.class);
        render.color = Color.GRAY;

        addComponent(MovingWallController.class);
    }
}
