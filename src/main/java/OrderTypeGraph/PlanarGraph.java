package OrderTypeGraph;

public class PlanarGraph extends Graph {

  Integer crossingNumber;
  Points points;

  public PlanarGraph(Points points) {
    super(points.size());
    this.points = points;
  }

  public PlanarGraph(Graph graph, Points points) {
    super(points.size());
    assert points.size() == graph.getN();
    this.points = points;
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
