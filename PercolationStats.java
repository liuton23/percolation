/* *****************************************************************************
 *  Name:              Tong Liu
 *  Coursera User ID:
 *  Last modified:     4/10/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] stats;
    private int size;
    private int time;

    /**
     * construct a new percolation stats object
     *
     * @param n      size of the grid
     * @param trials number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        stats = new double[trials];
        time = trials;
        size = n;
        for (int i = 1; i < trials + 1; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int num = StdRandom.uniform(n * n) + 1;
                int row = getCoord(num)[0];
                int col = getCoord(num)[1];
                per.open(row, col);
            }
            stats[i - 1] = (double) per.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold

    /**
     * Calculate the mean of the stats.
     *
     * @return the mean of the stats
     */
    public double mean() {
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold

    /**
     * Calculate the standard deviation of the stats.
     *
     * @return the standard deviation of the stats
     */
    public double stddev() {
        return StdStats.stddev(stats);
    }

    // low endpoint of 95% confidence interval

    /**
     * Calculate the lower bound of the 95 % confidence interval
     *
     * @return the lower bound of the 95% CL.
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / (Math.sqrt(time));
    }

    // high endpoint of 95% confidence interval

    /**
     * Calculate the upper bound of the 95 % confidence interval
     *
     * @return the upper bound of the 95 % CL.
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / (Math.sqrt(time));
    }

    /**
     * private helper which get the grid coordinates given it's grid index.
     *
     * @param i the grid index
     * @return the coordinates at this index
     */
    private int[] getCoord(int i) {
        int[] ans = new int[2];
        int col = size;
        if (i % size != 0) {
            col = i % size;
        }
        int row = (i - col) / size + 1;
        ans[0] = row;
        ans[1] = col;
        return ans;
    }

    // test client (see below)
    public static void main(String[] args) {
        StdOut.println("Please type the size of the grid 'n' and number of trials "
                               + "(use space to separate them).");
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("The mean is " + ps.mean());
        StdOut.println("The standard deviation is " + ps.stddev());
        StdOut.println("The 95% confidence interval is [" + ps.confidenceLo()
                               + ", " + ps.confidenceHi() + "]");
    }
}
