package io.github.coderodde.combinatorics.exactcover;

/**
 * This interface defines the common API for all exact cover solvers.
 */
public sealed interface ExactCoverSolver permits BruteForceExactCoverSolver,
                                                 AlgorithmXExactCoverSolver {

    /**
     * Returns a solution to the exact cover problem.
     * 
     * @param instance the problem instance to solve.
     * 
     * @return the solution object.
     * 
     * @throws NoSolutionFoundException thrown if there is not viable solution.
     */
    public ExactCoverSolution solve(ExactCoverInstance instance);
}
