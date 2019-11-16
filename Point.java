/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /* *
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        // return slope between this & that, using: (that.y - this.y)/(that.x - this.x)
        double yDiff = (that.y - this.y);
        double xDiff = (that.x - this.x);

        // slope: degenerate line (between point and itself) == - infinity
        if (yDiff == 0 && xDiff == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        // slope: horiz line == + 0
        else if (yDiff == 0) {
            return 0.0;
        }
        // slope: vert. line == + infinity
        else if (xDiff == 0) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            return yDiff / xDiff;
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        // compare points by y-coords, break ties with x-coords
        // formally: invoking point (this) is LESS THAN (that) IFF this.y < that.y OR this.y == that.y && this.x < that.x
        if (this.y < that.y) {
            return -1;
        }
        else if (this.y > that.y) {
            return 1;
        }
        // y values are tied, break tie with x values
        else if (this.x < that.x) {
            return -1;
        }
        else if (this.x > that.x) {
            return 1;
        }
        // full tie, points are equal
        else {
            return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        // YOUR CODE HERE
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            return Double.compare(slopeA, slopeB);
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        // test compareTo()
        StdOut.println("");
        Point a = new Point(1, 1);
        Point b = new Point(0, 0);
        assert (a.compareTo(b) > 0);
        assert (b.compareTo(a) < 0);
        Point c = new Point(1, 3);
        Point d = new Point(1, 2);
        assert (c.compareTo(d) > 0);
        assert (d.compareTo(c) < 0);
        Point e = new Point(2, 2);
        Point f = new Point(2, 2);
        assert (e.compareTo(f) == 0);
        assert (f.compareTo(e) == 0);

        // test slopeTo()
        assert (e.slopeTo(f) == Double.NEGATIVE_INFINITY); // identical points
        assert (b.slopeTo(e) == 0.0); // horiz. slope
        assert (a.slopeTo(c) == Double.POSITIVE_INFINITY); // vert. slope
        assert (b.slopeTo(a) == 1.0);
        assert (c.slopeTo(f) == -1.0);

        StdOut.println("Point tests passed");
    }
}
