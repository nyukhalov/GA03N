package com.github.nyukhalov.ga03n.simulation.prefab;

import com.badlogic.gdx.graphics.Color;
import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.component.render.RenderComponent;

public class WallPrefab extends GameObject {

    public WallPrefab() {
        RenderComponent render = addComponent(RenderComponent.class);
        render.color = Color.GRAY;
    }
}
