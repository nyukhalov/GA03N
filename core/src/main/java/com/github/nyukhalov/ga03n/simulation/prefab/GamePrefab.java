package com.github.nyukhalov.ga03n.simulation.prefab;

import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.component.GameScript;

public class GamePrefab extends GameObject {

    public static final String NAME = "Game";

    public GamePrefab() {
        name = NAME;
        addComponent(GameScript.class);
    }
}
