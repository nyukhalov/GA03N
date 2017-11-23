package com.github.nyukhalov.ga03n.simulation.prefab;

import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.component.DebugInfo;
import com.github.nyukhalov.ga03n.simulation.component.GeneticAlgorithmSimulation;
import com.github.nyukhalov.ga03n.simulation.component.render.DebugInfoRenderer;
import com.github.nyukhalov.ga03n.simulation.component.render.FitnessHistoryRenderer;

public class SimulationPrefab extends GameObject {

    public SimulationPrefab() {
        addComponent(GeneticAlgorithmSimulation.class);
        addComponent(DebugInfo.class);
        addComponent(DebugInfoRenderer.class);
        addComponent(FitnessHistoryRenderer.class);
    }
}
