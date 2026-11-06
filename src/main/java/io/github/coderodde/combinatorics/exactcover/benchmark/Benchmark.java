package io.github.coderodde.combinatorics.exactcover.benchmark;

import io.github.coderodde.combinatorics.exactcover.AlgorithmXExactCoverSolver;
import io.github.coderodde.combinatorics.exactcover.BruteForceExactCoverSolver;
import io.github.coderodde.combinatorics.exactcover.ExactCoverInstance;
import io.github.coderodde.combinatorics.exactcover.ExactCoverSolver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class Benchmark {

    public static void main(String[] args) {
        ExactCoverSolver solver1 = new BruteForceExactCoverSolver();
        ExactCoverSolver solver2 = new AlgorithmXExactCoverSolver();
        
        var instance  = getRandomInstance();
        var ta        = System.nanoTime();
        var solution1 = solver1.solve(instance);
        var tb        = System.nanoTime();
        var bruteForceSolverDuration = tb - ta;
        
        ta            = System.nanoTime();
        var solution2 = solver2.solve(instance);
        tb            = System.nanoTime();
        
        var algorithmXSolverDuration = tb - ta;
        
        var maxDuration = Math.max(bruteForceSolverDuration, 
                                   algorithmXSolverDuration);
        
        var durationLength = Long.toString(maxDuration).length();

        System.out.printf("%s found solution %s in %" 
                                + durationLength 
                                + "d nanoseconds.%n",
                          solver1.getClass().getSimpleName(),
                          solution1.selectedRows(),
                          bruteForceSolverDuration);

        System.out.printf("%s found solution %s in %" 
                                + durationLength
                                + "d nanoseconds.%n",
                          solver2.getClass().getSimpleName(),
                          solution2.selectedRows(),
                          algorithmXSolverDuration);
        
        System.out.printf(
                "Algorithms agree: %b.%n",
                solution1.selectedRows().equals(solution2.selectedRows()));
        
        double ratio = (double) algorithmXSolverDuration 
                     / bruteForceSolverDuration;
        
        double percent = 100.0 * ratio;
        
        System.out.printf(
            "Algorithm X took only %.1f percent of time of the " + 
            "bruteforce solver.", 
            percent);
    }
    
    public static ExactCoverInstance getRandomInstance() {
        final int width  = 20;
        final int height = 35;
        
        Random random = new Random(13L);
        ExactCoverInstance instance = new ExactCoverInstance(width, height);
        List<Integer> columnIndices = new ArrayList<>(width);
        
        // Begin: build a solvable data.
        for (int i = 0; i < width; ++i) {
            columnIndices.add(i);
        }
        
        Collections.shuffle(columnIndices, random);
        
        for (int y = 0; y < columnIndices.size(); ++y) {
            int x = columnIndices.get(y);
            instance.set(x, y);
        }
        
        // End: build a solvable data.
        
        // Add "noise":
        final int randomCellCount = 4;
        
        for (int i = 0; i < randomCellCount; ++i) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            
            instance.set(x, y);
        }
        
        return instance;
    }
}
