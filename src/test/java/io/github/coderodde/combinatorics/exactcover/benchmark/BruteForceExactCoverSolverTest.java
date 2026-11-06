package io.github.coderodde.combinatorics.exactcover.benchmark;

import io.github.coderodde.combinatorics.exactcover.AbstractExactCoverSolverTest;
import io.github.coderodde.combinatorics.exactcover.BruteForceExactCoverSolver;

public final class BruteForceExactCoverSolverTest 
             extends AbstractExactCoverSolverTest {

    public BruteForceExactCoverSolverTest() {
        super(new BruteForceExactCoverSolver());
    }
}
