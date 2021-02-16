package OrderTypeGraph;

public class PlaneGraph extends Graph {

  Integer crossingNumber;
  private final Points points;

  public PlaneGraph(Points points) {
    super(points.size());
    this.points = points;
  }

  public PlaneGraph(Graph graph, Points points) {
    super(graph);
    assert points.size() == graph.getN();
    this.points = points;
  }

  public static PlaneGraph completeGraph(Points points) {
    int n = points.size();
    Graph completeGraph = Graph.completeGraph(n);
    return new PlaneGraph(completeGraph, points);
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

  public int compareTo(PlaneGraph that) {
    assert that != null;
    if (this.getM() == that.getM()) {
      return Integer.compare(this.crossingNumber(), that.crossingNumber());
    }
    return Integer.compare(this.getM(), that.getM());
  }

}
