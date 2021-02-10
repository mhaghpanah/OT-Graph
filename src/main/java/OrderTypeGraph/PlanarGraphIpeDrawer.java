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
  long minX;
  long maxX;
  long minY;
  long maxY;
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
    minX = Long.MAX_VALUE;
    minY = Long.MAX_VALUE;
    maxX = Long.MIN_VALUE;
    maxY = Long.MIN_VALUE;
    for (int i = 0; i < points.size(); i++) {
      Point p = points.get(i);
      minX = Math.min(minX, p.getX());
      minY = Math.min(minY, p.getY());

      maxX = Math.max(maxX, p.getX());
      maxY = Math.max(maxY, p.getY());
    }

    double deltaX = maxX - minX;
    double deltaY = maxY - minY;
    return drawSize / Math.max(deltaX, deltaY);

//    return Math.min(1.0, (double) drawSize / (maxCoordinate - minCoordinate));
  }

  private int scaleX(long t) {
    return (int) (((t - minX) * scaleFactor) + o);
//    return (int) (t / 3 + o);
  }

  private int scaleY(long t) {
    return (int) (((t - minY) * scaleFactor) + o);
  }

  private void drawEdges() {
    for (Edge edge : graph.getEdges()) {
      Point u = points.get(edge.getU());
      Point v = points.get(edge.getV());

      int ux = scaleX(u.getX());
      int uy = scaleY(u.getY());

      int vx = scaleX(v.getX());
      int vy = scaleY(v.getY());

      output.append(drawIpePath(new int[]{ux, vx}, new int[]{uy, vy}));
    }
  }

  private void drawPoints(boolean addText) {
    int eps = 3;
    for (int i = 0; i < points.size(); i++) {
      Point point = points.get(i);
      int x = scaleX(point.getX());
      int y = scaleY(point.getY());

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
