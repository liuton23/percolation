/* *****************************************************************************
 *  Name:              Tong Liu
 *  Coursera User ID:
 *  Last modified:     4/10/2021
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Boolean[][] grid;
    private int size;
    private int numOpened = 0;
    private WeightedQuickUnionUF wquf;


    /**
     * Construct a new percolation object
     *
     * @param n the size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        grid = new Boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        wquf = new WeightedQuickUnionUF(n * n + 2);
    }

    /**
     * open a box given coordinate
     *
     * @param row the row coordinate
     * @param col the col coordinate
     */
    public void open(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1)
            throw new IllegalArgumentException();
        if (!isOpen(row, col)) {
            numOpened++;
        }
        grid[row - 1][col - 1] = true;
        if (row == 1) {
            wquf.union(col, 0);
        }
        if (row == size) {
            wquf.union(getSiteNum(row, col), size * size + 1);
        }
        if (row != 1 && isOpen(row - 1, col)) {
            wquf.union(getSiteNum(row - 1, col), getSiteNum(row, col));
        }
        if (row != size && isOpen(row + 1, col)) {
            wquf.union(getSiteNum(row + 1, col), getSiteNum(row, col));
        }
        if (col != 1 && isOpen(row, col - 1)) {
            wquf.union(getSiteNum(row, col - 1), getSiteNum(row, col));
        }
        if (col != size && isOpen(row, col + 1)) {
            wquf.union(getSiteNum(row, col + 1), getSiteNum(row, col));
        }
    }

    /**
     * return true if it is open at this index
     *
     * @param row row coordinate
     * @param col col coordinate
     * @return true if this position is open
     */
    public boolean isOpen(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1)
            throw new IllegalArgumentException();
        return grid[row - 1][col - 1];
    }

    /**
     * return true if it is full at this index
     *
     * @param row row coordinate
     * @param col col coordinate
     * @return true if this position is full
     */
    public boolean isFull(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1)
            throw new IllegalArgumentException();
        return wquf.find(getSiteNum(row, col)) == wquf.find(0);
    }

    /**
     * return the number of opened sites
     *
     * @return the number of opened sites
     */
    public int numberOfOpenSites() {
        return numOpened;
    }

    /**
     * return whether this grid is percolates or not
     *
     * @return true if this grid percolatess
     */
    public boolean percolates() {
        return wquf.find(0) == wquf.find(size * size + 1);
    }

    /**
     * private helper which get site number given site coordinate.
     *
     * @param row row coordinate
     * @param col col coordinate
     * @return the corresponding site number
     */
    private int getSiteNum(int row, int col) {
        return (row - 1) * size + col;
    }


    public static void main(String[] args) {
    }

}
