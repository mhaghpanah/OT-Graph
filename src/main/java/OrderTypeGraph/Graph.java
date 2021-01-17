package OrderTypeGraph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

  private final int n;
  private final List<Edge> edges;

  public Graph(int n) {
    this.n = n;
    edges = new ArrayList<>();
  }

  public Graph(int n, List<Edge> edges) {
    this(n);
    for (Edge e : edges) {
      this.edges.add(new Edge(e));
    }
  }

  public Graph(Graph graph) {
    this(graph.getN(), graph.getEdges());
  }

  public static Graph completeGraph(int n) {
    Graph graph = new Graph(n);
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        Edge edge = new Edge(i, j);
        graph.addEdge(edge);
      }
    }
    return graph;
  }

  public static int bitFormatSize(int n) {
    return (n * n + 7) / 8;
  }

  public static Graph toGraph(byte[] bytes, int n) {
    Graph graph = new Graph(n);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int t = i * n + j;
        if ((bytes[t / 8] & (1 << (t % 8))) != 0) {
          graph.addEdge(new Edge(i, j));
        }
      }
    }
    return graph;
  }

  public Edge getEdge(int i) {
    return edges.get(i);
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public int getN() {
    return n;
  }

  public int getM() {
    return edges.size();
  }

  public void addEdge(Edge e) {
    edges.add(e);
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
    if (!(o instanceof Graph)) {
      return false;
    }

    Graph graph = (Graph) o;

    if (n != graph.n) {
      return false;
    }
    return edges.equals(graph.edges);
  }

  @Override
  public int hashCode() {
    int result = n;
    result = 31 * result + edges.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Graph{" +
        "n=" + n +
        ", edges=" + edges +
        '}';
  }
}
