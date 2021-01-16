package OrderTypeGraph;

public class PlanarGraph extends Graph {

  Integer crossingNumber;
  Points points;

  public PlanarGraph(Graph graph, Points points) {
    super(graph.n);
    this.edges = graph.getEdges();
    this.points = points;
  }

  public int crossingNumber() {
    if (crossingNumber == null) {
      crossingNumber = CrossingNumber.CrossingNumberCalc(points, this);
    }
    return crossingNumber;
  }

  public int compareTo(PlanarGraph that) {
    assert that != null;
    if (this.size() == that.size()) {
      return Integer.compare(this.crossingNumber(), that.crossingNumber());
    }
    return Integer.compare(this.size(), that.size());
  }

}
