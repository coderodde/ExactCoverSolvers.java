package io.github.coderodde.combinatorics.exactcover;

/**
 * This class encodes a raw binary matrix representing an instance of the exact
 * cover problem.
 */
public final class ExactCoverInstance {
    
    private static final int MINIMUM_WIDTH  = 1;
    private static final int MINIMUM_HEIGHT = 1;
    
    private final boolean[][] binaryMatrix;
    private final int height;
    private final int width;
    
    public ExactCoverInstance(int width, int height) {
        this.binaryMatrix = new boolean[this.height = validateHeight(height)]
                                       [this.width  = validateWidth(width)];
    }
    
    public int width() {
        return width;
    }
    
    public int height() {
        return height;
    }
    
    public boolean get(int x, int y) {
        return binaryMatrix[validateY(y)][validateX(x)];
    }
    
    public void set(int x, int y) {
        binaryMatrix[validateY(y)][validateX(x)] = true;
    }
    
    public void unset(int x, int y) {
        binaryMatrix[validateY(y)][validateX(x)] = false;
    }
    
    private static int validateHeight(int height) {
        if (height < MINIMUM_HEIGHT) {
            throw new IllegalArgumentException(
                "height(%d) < MINIMUM_HEIGHT(%d)".formatted(height,
                                                            MINIMUM_HEIGHT));
        }
        
        return height;
    }
    
    private static int validateWidth(int width) {
        if (width < MINIMUM_WIDTH) {
            throw new IllegalArgumentException(
                "width(%d) < MINIMUM_WIDTH(%d)".formatted(width,
                                                         MINIMUM_WIDTH));
        }
        
        return width;
    }
    
    private int validateX(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("x(%d) < 0".formatted(x));
        }
        
        if (x >= width) {
            throw new IllegalArgumentException(
                "x(%d) >= width(%d)".formatted(x, width)
            );
        }
        
        return x;
    }
    
    private int validateY(int y) {
        if (y < 0) {
            throw new IllegalArgumentException("y(%d) < 0".formatted(y));
        }
        
        if (y >= height) {
            throw new IllegalArgumentException(
                "y(%d) >= height(%d)".formatted(y, height)
            );
        }
        
        return y;
    }
}
