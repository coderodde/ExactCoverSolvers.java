package io.github.coderodde.combinatorics.exactcover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements a brute-force algorithm for solving the exact cover 
 * problem. 
 */
public final class BruteForceExactCoverSolver implements ExactCoverSolver {

    /**
     * {@inheritDoc } 
     */
    @Override
    public ExactCoverSolution solve(ExactCoverInstance instance) {
        Objects.requireNonNull(instance, "The input instance is null.");
        
        boolean[] covered          = new boolean[instance.width()];
        List<Integer> selectedRows = new ArrayList<>();
        
        if (search(instance,
                   0,
                   0,
                   covered,
                   selectedRows)) {
            
            return new ExactCoverSolution(selectedRows);
        }
        
        throw new NoSolutionFoundException();
    }
    
    private boolean search(ExactCoverInstance instance,
                           int row,
                           int coveredCount,
                           boolean[] covered,
                           List<Integer> selectedRows) {
        
        final int width = instance.width();
        
        if (coveredCount == width) {
            return true;
        }
        
        if (row == instance.height()) {
            // No candidate rows available:
            return false;
        }
        
        boolean compatible = true;
        int newlyCovered   = 0;
        
        for (int x = 0; x < width; ++x) {
            if (instance.get(x, row)) {
                if (covered[x]) {
                    compatible = false;
                    break;
                }
                
                ++newlyCovered;
            }
        }
        
        if (compatible) {
            for (int x = 0; x < width; ++x) {
                if (instance.get(x, row)) {
                    covered[x] = true;
                }
            }
            
            selectedRows.add(row);
            
            if (search(instance,
                       row + 1,
                       coveredCount + newlyCovered,
                       covered,
                       selectedRows)) {
                
                return true;
            }
            
            selectedRows.removeLast();
            
            for (int x = 0; x < width; ++x) {
                if (instance.get(x, row)) {
                    covered[x] = false;
                }
            }
        }
        
        return search(instance,
                      row + 1,
                      coveredCount,
                      covered,
                      selectedRows);
    }
}
