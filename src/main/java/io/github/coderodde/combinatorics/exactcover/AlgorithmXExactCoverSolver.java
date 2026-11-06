package io.github.coderodde.combinatorics.exactcover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the 
 * <a href="https://en.wikipedia.org/wiki/Donald_Knuth">Donald Knuth's</a>
 * <a href="https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X">Algorithm X</a> 
 * with 
 * <a href="https://en.wikipedia.org/wiki/Dancing_links">dancing links (DLX)</a>.
 */
public final class AlgorithmXExactCoverSolver implements ExactCoverSolver {

    /**
     * {@inheritDoc }
     */
    @Override
    public ExactCoverSolution solve(ExactCoverInstance instance) {
        Objects.requireNonNull(instance, "The input problem instance is null.");
        
        Search search = new Search(instance);
        
        if (search.findFirst()) {
            Collections.sort(search.getSelected());
            return new ExactCoverSolution(search.getSelected());
        }
        
        throw new NoSolutionFoundException();
    }
    
    private static class Node {
        Node left;
        Node right;
        Node up;
        Node down;
        
        ColumnNode column;
        int rowIndex;
        
        Node() {
            left  = this;
            right = this;
            up    = this;
            down  = this;
        }
    }
    
    private static final class ColumnNode extends Node {
        
        int size;
        
        ColumnNode() {
            this.column = this;
        }
    }
    
    private static final class Search {
        
        private final ColumnNode root = new ColumnNode();
        
        private final List<Integer> selected = new ArrayList<>();
        
        Search(ExactCoverInstance instance) {
            int width  = instance.width();
            int height = instance.height();
            
            ColumnNode[] columns = new ColumnNode[width];
            
            // Build the column headers:
            for (int x = 0; x < width; ++x) {
                ColumnNode column = new ColumnNode();
                columns[x]        = column;
                
                // Insert immediately before root:
                column.left  = root.left;
                column.right = root;
                
                root.left.right = column;
                root.left       = column;
            }
            
            // Build the matrix nodes:
            for (int y = 0; y < height; ++y) {
                Node first = null;
                
                for (int x = 0; x < width; ++x) {
                    if (!instance.get(x, y)) {
                        continue;
                    }
                    
                    ColumnNode column = columns[x];
                    
                    Node node     = new Node();
                    node.column   = column;
                    node.rowIndex = y;
                    
                    // Insert into the column vertically:
                    node.up   = column.up;
                    node.down = column;
                    
                    column.up.down = node;
                    column.up      = node;
                    
                    ++column.size;
                    
                    // Insert into the row horizontally:
                    if (first == null) {
                        first = node;
                    } else {
                        node.left  = first.left;
                        node.right = first;
                        
                        first.left.right = node;
                        first.left       = node;
                    }
                }
            }
        }
        
        private boolean findFirst() {
            if (root.right == root) {
                // All columns have been covered:
                return true;
            }
            
            ColumnNode column = chooseColumn();
            
            if (column.size == 0) {
                return false;
            }
            
            cover(column);
            
            for (Node row = column.down; row != column; row = row.down) {
                
                selected.add(row.rowIndex);
                
                for (Node node = row.right; node != row; node = node.right) {
                    
                    cover(node.column);
                }
                
                if (findFirst()) {
                    return true;
                }
                
                for (Node node = row.left; node != row; node = node.left) {
                    
                    uncover(node.column);
                }
                
                selected.removeLast();
            }
            
            uncover(column);
            return false;
        }
        
        private ColumnNode chooseColumn() {
            ColumnNode best = null;
            int minimumSize = Integer.MAX_VALUE;
            
            for (Node node = root.right; node != root; node = node.right) {
                
                ColumnNode column = (ColumnNode) node;
                
                if (minimumSize > column.size) {
                    minimumSize = column.size;
                    best        = column;
                    
                    if (minimumSize == 0) {
                        break;
                    }
                }
            }
            
            return best;
        }
        
        private void cover(ColumnNode column) {
            // Remove the column header horizontally:
            column.right.left = column.left;
            column.left.right = column.right;
            
            for (Node row = column.down; row != column; row = row.down) {
                
                for (Node node = row.right; node != row; node = node.right) {
                    
                    // Remove node vertically:
                    node.down.up = node.up;
                    node.up.down = node.down;
                    node.column.size--;
                }
            }
        }
        
        private void uncover(ColumnNode column) {
            for (Node row = column.up; row != column; row = row.up) {
                
                for (Node node = row.left; node != row; node = node.left) {
                    
                    node.column.size++;
                    node.down.up = node;
                    node.up.down = node;
                }
            }
            
            column.right.left = column;
            column.left.right = column;
        }
        
        private List<Integer> getSelected() {
            return selected;
        }
    }
}
