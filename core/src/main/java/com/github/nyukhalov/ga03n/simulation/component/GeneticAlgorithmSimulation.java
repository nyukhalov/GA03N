package com.github.nyukhalov.ga03n.simulation.component;

import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.GameObject;
import com.github.nyukhalov.ga03n.engine.World;
import com.github.nyukhalov.ga03n.simulation.DNA;
import com.github.nyukhalov.ga03n.simulation.prefab.GamePrefab;

import java.util.*;
import java.util.stream.Collectors;

public class GeneticAlgorithmSimulation extends Component {

    private final GamePrefab gamePrefab = new GamePrefab();

    private GameObject game;
    private MovingWallController movingWallController;
    private GameScript gameScript;
    private Random random;

    private final int populationCount = 10;
    public final List<GameObject> currentGeneration = new ArrayList<>();
    public int generation = -1;
    public float maxFitness = 0f;
    public final List<Float> fitnessHistory = new ArrayList<>();

    @Override
    protected void awake() {
        random = World.current.random;
        game = instantiate(gamePrefab);
        gameScript = game.getComponent(GameScript.class);
        movingWallController = gameScript.movingWall.getComponent(MovingWallController.class);

        instantiateNewGeneration(Collections.emptyList());
    }

    @Override
    protected void lateUpdate(float delta) {
        if (currentGeneration.stream().allMatch(o -> o.isDead)) {
            List<NNController> bestObjects = currentGeneration.stream()
                    .map(o -> o.getComponent(NNController.class))
                    .sorted(Comparator.comparingDouble(o -> -o.getFitness()))
                    .limit(4)
                    .collect(Collectors.toList());

            maxFitness = bestObjects.get(0).getFitness();

            List<DNA> topDNAs = bestObjects.stream()
                    .map(o -> o.dna)
                    .collect(Collectors.toList());

            fitnessHistory.add(maxFitness);

            movingWallController.resetPosition();
            instantiateNewGeneration(topDNAs);
        }
    }

    private void instantiateNewGeneration(List<DNA> topDNAs) {
        generation++;
        currentGeneration.clear();

        for (int i = 0; i < populationCount; i++) {
            DNA dna = newDNA(topDNAs, i);
            GameObject player = gameScript.addNewPlayer(dna);
            currentGeneration.add(player);
        }
    }

    private DNA newDNA(List<DNA> topDNAs, int objectNumber) {
        if (topDNAs.isEmpty()) return DNA.randomDNA(random);

        if (objectNumber == 0) {
            return topDNAs.get(0).mutate(random);
        }

        if (objectNumber == 1) {
            return topDNAs.get(0).crossover(topDNAs.get(1), random).mutate(random);
        }

        int size = topDNAs.size();
        int firstIdx = random.nextInt(size);
        int secondIdx = firstIdx;
        while (firstIdx == secondIdx) {
            secondIdx = random.nextInt(size);
        }
        DNA dna = topDNAs.get(firstIdx).crossover(topDNAs.get(secondIdx), random);
        return dna.mutate(random);
    }

    public List<GameObject> liveEntities() {
        return currentGeneration.stream()
                .filter(o -> !o.isDead)
                .collect(Collectors.toList());
    }
}
