package OrderTypeGraph;

import java.util.ArrayList;
import java.util.List;

public class Edges {

  protected int n;
  protected List<Edge> edges;

  public Edges(int n) {
    this.n = n;
    edges = new ArrayList<>();
  }

  public Edges(int n, List<Edge> edges) {
    this(n);
    for (Edge e : edges) {
      this.edges.add(new Edge(e));
    }
  }

  public static int bitFormatSize(int n) {
    return (n * n + 7) / 8;
  }

  public static Edges toEdge(byte[] bytes, int n) {
    Edges edges = new Edges(n);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int t = i * n + j;
        if ((bytes[t / 8] & (1 << (t % 8))) != 0) {
          edges.add(new Edge(i, j));
        }
      }
    }
    return edges;
  }

  public Edge get(int i) {
    return edges.get(i);
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public int size() {
    return edges.size();
  }

  public boolean add(Edge e) {
    edges.add(e);
    return true;
  }

  public byte[] toBytes() {
    byte[] ans = new byte[bitFormatSize(n)];
    for (Edge e : getEdges()) {
      int t = e.getU() * n + e.getV();
      ans[t / 8] |= (1 << (t % 8));
    }
    return ans;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Edges edges1 = (Edges) o;

    if (n != edges1.n) {
      return false;
    }
    return edges.equals(edges1.edges);
  }

  @Override
  public int hashCode() {
    int result = n;
    result = 31 * result + edges.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Edges{" +
        "edges=" + edges +
        '}';
  }
}
