package com.github.nyukhalov.ga03n.simulation.component;

import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class DebugInfo extends Component {
    public final List<String> info = new ArrayList<>();

    @Override
    protected void update(float delta) {
        info.clear();

        GeneticAlgorithmSimulation simulation = getComponent(GeneticAlgorithmSimulation.class);

        info.add("Generation: " + simulation.generation);

        List<GameObject> liveEntities = simulation.liveEntities();

        float lifetime = 0f;
        float input0 = 0f;
        float input1 = 0f;
        float input2 = 0f;
        if (!liveEntities.isEmpty()) {
            GameObject gameObject = liveEntities.get(0);
            BehaviorComponent behaviorComponent = gameObject.getComponent(BehaviorComponent.class);
            lifetime = behaviorComponent.getLifetime();

            NNController controller = gameObject.getComponent(NNController.class);
            input0 = (float) controller.input[0];
            input1 = (float) controller.input[1];
            input2 = (float) controller.input[2];
        }
        info.add("Lifetime: " + lifetime);
        info.add("Input 0: " + input0);
        info.add("Input 1: " + input1);
        info.add("Input 2: " + input2);

        info.add("Max fitness: " + simulation.maxFitness);
        info.add("Live objects: " + liveEntities.size());
    }
}
