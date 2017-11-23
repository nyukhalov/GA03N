package com.github.nyukhalov.ga03n.simulation;

import com.badlogic.gdx.graphics.Color;
import com.github.nyukhalov.ga03n.simulation.component.NNController;

import java.util.Random;

public class DNA {
    public final Color color;
    public final double[] data = new double[NNController.NN_TOTAL];

    private final float mutateProbability = 0.01f;

    public DNA(Color color) {
        this.color = color;
    }

    public static DNA randomDNA(Random random) {
        Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1f);
        DNA dna = new DNA(color);
        for (int i=0; i<dna.data.length; i++) {
            dna.data[i] = randomGene(random);
        }
        return dna;
    }

    private static double randomGene(Random random) {
        return 2 * random.nextDouble() - 1;
    }

    public DNA mutate(Random random) {
        int cIdx = random.nextInt(3);
        Color newColor = new Color(
                cIdx == 0? color.r: random.nextFloat(),
                cIdx == 1? color.g: random.nextFloat(),
                cIdx == 2? color.b: random.nextFloat(),
                color.a
        );
        DNA dna = new DNA(newColor);
        System.arraycopy(this.data, 0, dna.data, 0, this.data.length);
        for (int i=0; i<dna.data.length; i++) {
            if (random.nextFloat() < mutateProbability) {
                dna.data[i] = randomGene(random);
            }
        }
        return dna;
    }

    public DNA crossover(DNA anotherDNA, Random random) {
        int cutPoint = 1 + random.nextInt(data.length - 2);

        DNA dna = new DNA(new Color(
                random.nextBoolean()? color.r: anotherDNA.color.r,
                random.nextBoolean()? color.g: anotherDNA.color.g,
                random.nextBoolean()? color.b: anotherDNA.color.b,
                1
        ));

        boolean b = random.nextBoolean();
        double[] src1 = b? data: anotherDNA.data;
        double[] src2 = b? anotherDNA.data: data;

        System.arraycopy(src1, 0, dna.data, 0, cutPoint);
        System.arraycopy(src2, cutPoint, dna.data, cutPoint, data.length - cutPoint);

        return dna;
    }
}
