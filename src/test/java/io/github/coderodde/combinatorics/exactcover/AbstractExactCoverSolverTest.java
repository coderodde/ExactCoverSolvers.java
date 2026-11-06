package io.github.coderodde.combinatorics.exactcover;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * This abstract test class defines some shared code between the solvers.
 */
public abstract class AbstractExactCoverSolverTest {
    
    private static final int[][] DATA1 = {
        {0, 1},      
        {0, 2},
        {1, 3},
        {1, 2, 3} 
    };
    
    private static final int[][] DATA2 = {
        {1, 2},
        {0, 1},
        {0, 2}
    };
    
    private final ExactCoverSolver solver;
    
    public AbstractExactCoverSolverTest(ExactCoverSolver solver) {
        this.solver = solver;
    }
    
    @Test
    public void touchUncover() {
        ExactCoverInstance instance = instance1();
        ExactCoverSolution solution = solver.solve(instance);
        
        assertEquals(List.of(1, 2), solution.selectedRows());
    }
    
    @Test
    public void throwsOnNoSolution() {
        assertThrows(NoSolutionFoundException.class, () -> {
            ExactCoverInstance instance = instance2();
            solver.solve(instance);
        });
    }
    
    public static ExactCoverInstance convert(int[][] data, 
                                             int width, 
                                             int height) {
        
        ExactCoverInstance instance = new ExactCoverInstance(width, height);
        
        for (int y = 0; y < data.length; ++y) {
            for (int x : data[y]) {
                instance.set(x, y);
            }
        }
        
        return instance;
    }
    
    public static ExactCoverInstance instance1() {
        return convert(DATA1, 4, 4);
    }
    
    public static ExactCoverInstance instance2() {
        return convert(DATA2, 3, 3);
    }
}
