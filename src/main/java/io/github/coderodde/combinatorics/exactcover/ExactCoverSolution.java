package io.github.coderodde.combinatorics.exactcover;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class implements a list of row indices into a {@link ExactCoverInstance}
 * that constitute its solution.
 */
public final class ExactCoverSolution {
    
    private final List<Integer> selectedRows;
    
    public ExactCoverSolution(List<Integer> selectedRows) {
        this.selectedRows = 
            List.copyOf(
                Objects.requireNonNull(
                    selectedRows, 
                    "The input selected row index list is null."));
    }
    
    public List<Integer> selectedRows() {
        return Collections.unmodifiableList(selectedRows);
    }
    
    @Override
    public String toString() {
        return selectedRows.toString();
    }
}
