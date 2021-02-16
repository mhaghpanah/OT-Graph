package OrderTypeGraph;

import java.util.ArrayList;
import java.util.List;

public class CrossingNumber {

  public Points points;
  public Graph graph;
  public List<Cross> crosses;

  public CrossingNumber(Points points, Graph graph) {
    this.points = points;
    this.graph = graph;
    crosses = new ArrayList<>();
    computeCrossingNumber();
  }

  public CrossingNumber(PlaneGraph planeGraph) {
    this(planeGraph.getPoints(), planeGraph);
  }

  public static int CrossingNumberCalc(Points points, Graph graph) {
    CrossingNumber cn = new CrossingNumber(points, graph);
    return cn.size();
  }

  private void computeCrossingNumber() {
    int m = graph.getM();
    for (int i = 0; i < m; i++) {
      for (int j = i + 1; j < m; j++) {
        Point a = points.get(graph.getEdge(i).getU());
        Point b = points.get(graph.getEdge(i).getV());
        Point c = points.get(graph.getEdge(j).getU());
        Point d = points.get(graph.getEdge(j).getV());

        if (checkIntersection2(a, b, c, d)) {
          Cross cross = new Cross(i, j);
          crosses.add(cross);
        }
      }
    }
  }

  private boolean inter1(long a, long b, long c, long d) {
    if (a > b) {
      long t = a;
      a = b;
      b = t;
    }

    if (c > d) {
      long t = c;
      c = d;
      d = t;
    }
    return Math.max(a, c) <= Math.min(b, d);
  }

  private int sign(long a) {
    return a > 0 ? +1 : (a == 0 ? 0 : -1);
  }

  private boolean checkIntersection(Point a, Point b, Point c, Point d) {
    if (c.cross(a, d) == 0 && c.cross(b, d) == 0) {
      return inter1(a.getX(), b.getX(), c.getX(), d.getX()) && inter1(a.getY(), b.getY(), c.getY(),
          d.getY());
    }
    return sign(a.cross(b, c)) != sign(a.cross(b, d)) && sign(c.cross(d, a)) != sign(c.cross(d, b));
  }

  private boolean checkIntersection2(Point a, Point b, Point c, Point d) {
    if (a.equals(c) || a.equals(d) || b.equals(c) || b.equals(d)) {
      return false;
    }
    return checkIntersection(a, b, c, d);
  }

  public int size() {
    return crosses.size();
  }

  static class Cross {

    int e1, e2;

    public Cross(int e1, int e2) {
      this.e1 = e1;
      this.e2 = e2;
    }

    @Override
    public String toString() {
      return "Cross{" +
          "e1=" + e1 +
          ", e2=" + e2 +
          '}';
    }
  }
}
