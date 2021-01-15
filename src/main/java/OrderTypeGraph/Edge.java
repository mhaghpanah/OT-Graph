package OrderTypeGraph;

public class Edge {

  Integer u;
  Integer v;

  public Edge(int u, int v) {
    this.u = u;
    this.v = v;
  }

  public Edge(Edge e) {
    u = e.getU();
    v = e.getV();
  }

  public int getU() {
    return u;
  }

  public int getV() {
    return v;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Edge edge = (Edge) o;

    if (!u.equals(edge.u)) {
      return false;
    }
    return v.equals(edge.v);
  }

  @Override
  public int hashCode() {
    int result = u.hashCode();
    result = 31 * result + v.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Edge{" +
        "u=" + u +
        ", v=" + v +
        '}';
  }
}
