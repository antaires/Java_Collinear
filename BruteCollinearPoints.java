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

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private final ArrayList<LineSegment> lineSegArray = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // duplicate the array
        Point[] myPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            myPoints[i] = points[i];
        }

        Arrays.sort(myPoints); // sort to easily check for duplicate points
        for (int i = 0; i < myPoints.length; i++) {
            for (int j = i + 1; j < myPoints.length; j++) {
                duplicateCheck(myPoints[i], myPoints[j]);
                for (int k = j + 1; k < myPoints.length; k++) {
                    duplicateCheck(myPoints[i], myPoints[k]);
                    for (int s = k + 1; s < myPoints.length; s++) {
                        duplicateCheck(myPoints[i], myPoints[s]);
                        // check if 4 points are collinear
                        double slope1 = myPoints[i].slopeTo(myPoints[j]);
                        double slope2 = myPoints[j].slopeTo(myPoints[k]);
                        double slope3 = myPoints[k].slopeTo(myPoints[s]);

                        if (Double.compare(slope1, slope2) == 0
                                && Double.compare(slope2, slope3) == 0
                                && Double.compare(slope1, slope3) == 0) {
                            // save index pairs
                            LineSegment ls = new LineSegment(myPoints[i], myPoints[s]);
                            lineSegArray.add(ls);
                        }
                    }
                }
            }
        }
        // convert arraylist to standard array
        lineSegments = new LineSegment[lineSegArray.size()];
        int cnt = 0;
        for (LineSegment s : lineSegArray) {
            lineSegments[cnt++] = s;
        }
    }

    private void duplicateCheck(Point a, Point b) {
        // prevent duplicates
        if (a.compareTo(b) == 0) {
            throw new IllegalArgumentException();
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] newArr = new LineSegment[lineSegments.length];
        for (int i = 0; i < lineSegments.length; i++) {
            newArr[i] = lineSegments[i];
        }
        return newArr;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
