/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // TODO still has:
        // TODO - duplicate / repeat line segments
        // TODO - missing some line segments
        // TODO - too many line segments

        Arrays.sort(points); // sort to easily check for duplicate points
        Point prevPointDupCheck = null;
        final ArrayList<LineSegment> lineSegArray = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            nullCheck(p);
            if (prevPointDupCheck != null) {
                duplicateCheck(p, prevPointDupCheck);
            }

            // clone list to prevent shuffling list while iterating over it
            // and use only a subsection of list, greater than p to prevent
            // duplicate line segments
            Point[] myPoints = cloneSubList(points, 0);

            Arrays.sort(myPoints);

            // sort all points by slope relative to p, only changing order after p
            // Arrays.sort(points, i, points.length, p.slopeOrder());
            Arrays.sort(myPoints, p.slopeOrder());

            int cnt = 1; // start at one because start at p (already 1 point)
            Point start = null;
            for (int j = 0; j < myPoints.length - 1; j++) {
                if (Double.compare(p.slopeTo(myPoints[j]), p.slopeTo(myPoints[j + 1])) == 0) {
                    cnt++;
                    if (cnt == 2) { // store the 2nd point of the line after p
                        start = myPoints[j];
                        cnt++;
                    }
                    // catch final groups (appearing at end of array)
                    else if (cnt >= 4 && j + 1 == myPoints.length - 1) {
                        // confirm start != p
                        if (start != null && start.compareTo(p) > 0) {
                            LineSegment ls = new LineSegment(p, myPoints[j + 1]);
                            // add line seg to array
                            lineSegArray.add(ls);
                        }
                        cnt = 1;
                    }
                }
                else if (cnt >= 4) {
                    // line group found (not including j + 1)
                    if (start != null && start.compareTo(p) > 0) {
                        LineSegment ls = new LineSegment(p, myPoints[j]);
                        // add line seg to array
                        lineSegArray.add(ls);
                    }
                    cnt = 1;
                }
                else {
                    cnt = 1;
                }
            }
            prevPointDupCheck = p;
        }
        // convert arraylist to standard array
        lineSegments = new LineSegment[lineSegArray.size()];
        int cnt = 0;
        for (LineSegment s : lineSegArray) {
            lineSegments[cnt++] = s;
        }
    }


    private Point[] cloneSubList(Point[] a, int start) {
        Point[] sublist = new Point[a.length - start];
        int cnt = 0;
        for (int i = start; i < a.length; i++) {
            sublist[cnt++] = a[i];
        }
        return sublist;
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        LineSegment[] newArr = new LineSegment[lineSegments.length];
        for (int i = 0; i < lineSegments.length; i++) {
            newArr[i] = lineSegments[i];
        }
        return newArr;
    }

    private void nullCheck(Point p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    private void duplicateCheck(Point a, Point b) {
        // prevent duplicates
        if (a.compareTo(b) == 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
