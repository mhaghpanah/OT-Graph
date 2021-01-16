package OrderTypeGraph;

import java.util.ArrayList;
import java.util.List;

public class ExitGraph {

  Edges edges;
  List<Integer> witnesses;

  public ExitGraph(int n) {
    edges = new Edges(n);
    witnesses = new ArrayList<>();
  }

  public static ExitGraph generateGraph(Points points) {
    int n = points.size();
    ExitGraph exitGraph = new ExitGraph(n);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {

        for (int k = 0; k < n; k++) {
          if (k == i || k == j) {
            continue;
          }

          Edge edge = new Edge(i, j);
          Line line1 = new Line(points.get(i), points.get(j));
          if (line1.side(points.get(k)) > 0) {
            edge = new Edge(j, i);
            line1 = new Line(points.get(j), points.get(i));
          }

          boolean found = true;
          for (int p = 0; p < n; p++) {
            if (p == i || p == j || p == k) {
              continue;
            }
            Point point = points.get(p);
            Line line2 = new Line(points.get(edge.getV()), points.get(k));
            Line line3 = new Line(points.get(k), points.get(edge.getU()));

            if ((line1.side(point) < 0 && line2.side(point) > 0 && line3.side(point) > 0) ||
                (line1.side(point) > 0 && line2.side(point) < 0 && line3.side(point) < 0)) {
              continue;
            } else {
              found = false;
              break;
            }
          }

          if (found) {
            exitGraph.add(edge, k);
            break;
          }

        }


      }
    }

    return exitGraph;
  }

  public Edges getExitEdges() {
    return edges;
  }

  public int size() {
    return edges.size();
  }

  public boolean add(Edge e, int w) {
    edges.add(e);
    witnesses.add(w);
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("#");
    sb.append(size());
    sb.append(" ");
    sb.append("{");
    for (int i = 0; i < edges.size(); i++) {
      sb.append(String
          .format("(%d, %d, %d)", edges.get(i).getU(), edges.get(i).getV(), witnesses.get(i)));
      if (i + 1 < edges.size()) {
        sb.append(",");
      }
    }
    sb.append("}");
    return sb.toString();
  }

}
