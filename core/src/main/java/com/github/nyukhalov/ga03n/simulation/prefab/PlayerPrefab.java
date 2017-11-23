package com.github.nyukhalov.ga03n.simulation.prefab;

import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.component.BehaviorComponent;
import com.github.nyukhalov.ga03n.simulation.component.NNController;
import com.github.nyukhalov.ga03n.simulation.component.render.RenderComponent;

public class PlayerPrefab extends GameObject {

    public PlayerPrefab() {
        addComponent(RenderComponent.class);
        addComponent(NNController.class);
        addComponent(BehaviorComponent.class);
    }
}
