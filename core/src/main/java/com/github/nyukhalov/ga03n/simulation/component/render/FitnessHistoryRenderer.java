package com.github.nyukhalov.ga03n.simulation.component.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.World;
import com.github.nyukhalov.ga03n.simulation.component.GeneticAlgorithmSimulation;

public class FitnessHistoryRenderer extends Component {

    private GeneticAlgorithmSimulation simulation;

    @Override
    protected void start() {
        simulation = getComponent(GeneticAlgorithmSimulation.class);
    }

    @Override
    protected void onGUI() {
        World.current.shapeRenderer.setColor(Color.GREEN);
        World.current.shapeRenderer.set(ShapeRenderer.ShapeType.Line);

        int len = simulation.fitnessHistory.size();
        float xOffset = 5f;
        float xStep = 2f;

        float maxFitness = findMaxFitness();
        float yScaleFactor = Math.min(30, Gdx.graphics.getHeight() / (maxFitness + 1));

        for (int i=1; i<len; i++) {
            World.current.shapeRenderer.line(
                    xOffset + (i-1) * xStep,
                    simulation.fitnessHistory.get(i-1) * yScaleFactor,
                    xOffset + i * xStep,
                    simulation.fitnessHistory.get(i) * yScaleFactor
            );
        }
    }

    private float findMaxFitness() {
        float maxFitness = 0;
        for (Float f: simulation.fitnessHistory) {
            maxFitness = Math.max(maxFitness, f);
        }
        return maxFitness;
    }
}
