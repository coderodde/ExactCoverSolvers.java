package io.github.coderodde.combinatorics.exactcover;

import static io.github.coderodde.combinatorics.exactcover.benchmark.Benchmark.getRandomInstance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class BenchmarkTest {
    
    @Test
    public void testBenchmark() {
        ExactCoverSolver solver1 = new BruteForceExactCoverSolver();
        ExactCoverSolver solver2 = new AlgorithmXExactCoverSolver();
        
        var instance  = getRandomInstance();
        var solution1 = solver1.solve(instance);
        var solution2 = solver2.solve(instance);
        
        assertEquals(solution1.selectedRows(), solution2.selectedRows());
    }
}
