package com.github.nyukhalov.ga03n.simulation.component;

import com.badlogic.gdx.math.Vector2;
import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.simulation.DNA;
import com.github.nyukhalov.ga03n.simulation.prefab.MovingWallPrefab;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationStep;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

public class NNController extends Component {

    public static final int NN_INPUT_COUNT = 3;
    public static final int NN_HIDDEN_COUNT = 6;
    public static final int NN_OUTPUT_COUNT = 2;
    public static final int NN_TOTAL = (NN_INPUT_COUNT + 1) * NN_HIDDEN_COUNT + (NN_HIDDEN_COUNT + 1) * NN_OUTPUT_COUNT;

    public final double[] input = new double[NN_INPUT_COUNT];
    private final double[] output = new double[NN_OUTPUT_COUNT];

    public DNA dna;

    private BasicNetwork nn = null;
    private float fitness = 0;
    private Vector2 movingWallPos;
    private BehaviorComponent behaviorComponent;

    @Override
    protected void start() {
        behaviorComponent = getComponent(BehaviorComponent.class);
        movingWallPos = GameObject.find(MovingWallPrefab.NAME).position;
    }

    @Override
    public void update(float delta) {
        if (nn == null) nn = createNN();

        fillInput();
        compute();

        if (isMoveRight(output)) {
            behaviorComponent.moveRight();
        }
        if (isMoveLeft(output)) {
            behaviorComponent.moveLeft();
        }

        updateFitness(delta);
    }

    private BasicNetwork createNN() {
        BasicNetwork network = new BasicNetwork();

        network.addLayer(new BasicLayer(null,true, NN_INPUT_COUNT));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, NN_HIDDEN_COUNT));
        network.addLayer(new BasicLayer(new ActivationStep(), true, NN_OUTPUT_COUNT));
        network.getStructure().finalizeStructure();
        network.reset();

        int offset = createNNLayer(network, 0, 0);
        createNNLayer(network, 1, offset);

        return network;
    }

    private int createNNLayer(BasicNetwork network, int fromLayer, int offset) {
        final int fromCount = network.getLayerTotalNeuronCount(fromLayer);
        final int toCount = network.getLayerNeuronCount(fromLayer + 1);

        for (int fromNeuron = 0; fromNeuron < fromCount; fromNeuron++) {
            for (int toNeuron = 0; toNeuron < toCount; toNeuron++) {
                double weight = dna.data[offset + fromNeuron * toCount + toNeuron];
                network.setWeight(fromLayer, fromNeuron, toNeuron, weight);
            }
        }

        return fromCount * toCount;
    }

    public float getFitness() {
        return fitness;
    }

    private void fillInput() {
        input[0] = movingWallPos.y - gameObject.position.y; // distance to the closest moving wall (Y)
        input[1] = movingWallPos.x - gameObject.position.x; // horizontal offset of moving wall (X)
        input[2] = gameObject.position.x - behaviorComponent.minX;          // horizontal offset of left wall
    }

    private void compute() {
        nn.compute(input, output);
    }

    private boolean isMoveRight(double[] output) {
        return output[1] >= 0.5f;
    }

    private boolean isMoveLeft(double[] output) {
        return output[0] >= 0.5f;
    }

    private void updateFitness(float delta) {
        fitness += delta;
    }
}
