package OrderTypeGraph;

public class PlanarGraph extends Graph {

  Integer crossingNumber;
  Points points;

  public PlanarGraph(Points points) {
    super(points.size());
    this.points = points;
  }

  public PlanarGraph(Graph graph, Points points) {
    super(graph.edgesNumber());
    assert points.size() == graph.verticesNumber();
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
    if (this.edgesNumber() == that.edgesNumber()) {
      return Integer.compare(this.crossingNumber(), that.crossingNumber());
    }
    return Integer.compare(this.edgesNumber(), that.edgesNumber());
  }

}
