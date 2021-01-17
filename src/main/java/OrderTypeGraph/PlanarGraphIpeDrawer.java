package OrderTypeGraph;

import static OrderTypeGraph.IpeDraw.drawIpeMark;
import static OrderTypeGraph.IpeDraw.drawIpePath;
import static OrderTypeGraph.IpeDraw.getIpeConf;
import static OrderTypeGraph.IpeDraw.getIpeEnd;
import static OrderTypeGraph.IpeDraw.getIpePreamble;
import static OrderTypeGraph.IpeDraw.writeIpeText;

public class PlanarGraphIpeDrawer {

  final long drawSize = 300;
  final long o = 200;
  StringBuilder output;
  Graph graph;
  Points points;
  double scaleFactor;
  long minCoordinate;
  long maxCoordinate;
  boolean addText;

  public PlanarGraphIpeDrawer(Graph graph, Points points, boolean addText) {
    output = new StringBuilder();
    this.graph = graph;
    this.points = points;

    scaleFactor = computeScaleFactor();

    this.addText = addText;

    output.append(getIpePreamble());
    output.append(getIpeConf());
    drawPoints(addText);
    drawEdges();
    output.append(getIpeEnd());
  }

  public PlanarGraphIpeDrawer(PlanarGraph planarGraph, boolean addText) {
    this(planarGraph, planarGraph.getPoints(), addText);
  }

  public static String draw(Graph graph, Points points, boolean addText) {
    PlanarGraphIpeDrawer pgid = new PlanarGraphIpeDrawer(graph, points, addText);
    return pgid.output();
  }

  public static void drawAndWrite(PlanarGraph planarGraph, String suffixPathOutput, boolean addText) {
    drawAndWrite(planarGraph, planarGraph.getPoints(), suffixPathOutput, addText);
  }

  public static void drawAndWrite(Graph graph, Points points, String suffixPathOutput, boolean addText) {
    MyFileWriter
        .write(suffixPathOutput + ".ipe", PlanarGraphIpeDrawer.draw(graph, points, addText));
  }

  private double computeScaleFactor() {
    maxCoordinate = Integer.MIN_VALUE;
    minCoordinate = Integer.MAX_VALUE;
    for (int i = 0; i < points.size(); i++) {
      Point p = points.get(i);
      minCoordinate = Math.min(minCoordinate, p.getX());
      minCoordinate = Math.min(minCoordinate, p.getY());

      maxCoordinate = Math.max(maxCoordinate, p.getX());
      maxCoordinate = Math.max(maxCoordinate, p.getY());
    }

    return Math.min(1.0, (double) drawSize / (maxCoordinate - minCoordinate));
  }

  private int scale(long t) {
    return (int) (((t - minCoordinate) * scaleFactor) + o);
//    return (int) (t / 3 + o);
  }

  private void drawEdges() {
    for (Edge edge : graph.getEdges()) {
      Point u = points.get(edge.getU());
      Point v = points.get(edge.getV());

      int ux = scale(u.getX());
      int uy = scale(u.getY());

      int vx = scale(v.getX());
      int vy = scale(v.getY());

      output.append(drawIpePath(new int[]{ux, vx}, new int[]{uy, vy}));
    }
  }

  private void drawPoints(boolean addText) {
    int eps = 3;
    for (int i = 0; i < points.size(); i++) {
      Point point = points.get(i);
      int x = scale(point.getX());
      int y = scale(point.getY());

      output.append(drawIpeMark(x, y));
      if (addText) {
        output.append(writeIpeText(Integer.toString(i + 1), x - eps, y + eps));
      }
    }
  }

  public String output() {
    return output.toString();
  }

}
