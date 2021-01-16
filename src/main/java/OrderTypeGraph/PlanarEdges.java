package OrderTypeGraph;

public class PlanarEdges extends Edges {

  Integer crossingNumber;
  Points points;

  public PlanarEdges(Edges edges, Points points) {
    super(edges.n);
    this.edges = edges.getEdges();
    this.points = points;
  }

  public int crossingNumber() {
    if (crossingNumber == null) {
      crossingNumber = CrossingNumber.CrossingNumberCalc(points, this);
    }
    return crossingNumber;
  }

  public int compareTo(PlanarEdges that) {
    assert that != null;
    if (this.size() == that.size()) {
      return Integer.compare(this.crossingNumber(), that.crossingNumber());
    }
    return Integer.compare(this.size(), that.size());
  }

}
