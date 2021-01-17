package OrderTypeGraph;

public class PlanarGraph extends Graph {

  Integer crossingNumber;
  Points points;

  public PlanarGraph(Points points) {
    super(points.size());
    this.points = points;
  }

  public PlanarGraph(Graph graph, Points points) {
    super(graph);
    assert points.size() == graph.getN();
    this.points = points;
  }

  public static PlanarGraph completeGraph(Points points) {
    int n = points.size();
    Graph completeGraph = Graph.completeGraph(n);
    PlanarGraph planarGraph = new PlanarGraph(completeGraph, points);
    return planarGraph;
  }

  public Points getPoints() {
    return points;
  }

  public int crossingNumber() {
    if (crossingNumber == null) {
      crossingNumber = CrossingNumber.CrossingNumberCalc(points, this);
    }
    return crossingNumber;
  }

  public int compareTo(PlanarGraph that) {
    assert that != null;
    if (this.getM() == that.getM()) {
      return Integer.compare(this.crossingNumber(), that.crossingNumber());
    }
    return Integer.compare(this.getM(), that.getM());
  }

}
