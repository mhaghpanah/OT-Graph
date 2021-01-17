package OrderTypeGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class GraphTest {

  int n;

  Graph graph0;
  Graph graph1;

  Edge e0 = new Edge(0, 1);
  Edge e1 = new Edge(2, 9);
  Edge e2 = new Edge(4, 2);
  Edge e3 = new Edge(5, 9);

  List<Edge> edgeList0 = Arrays.asList(e0, e1, e2, e3);
  List<Edge> edgeList1 = Arrays.asList(e0, e1);

  public GraphTest() {
    n = 100;
    graph0 = new Graph(n, edgeList0);
    graph1 = new Graph(n, edgeList1);
  }

  @Test
  public void toGraph() {
    byte[] bytes0 = graph0.toBytes();
    Graph ans0 = Graph.toGraph(bytes0, n);

    byte[] bytes1 = graph1.toBytes();
    Graph ans1 = Graph.toGraph(bytes1, n);

    assertEquals(graph0, ans0);
    assertNotEquals(graph1, ans0);

    assertEquals(graph1, ans1);
    assertNotEquals(graph0, ans1);
  }

  @Test
  public void getEdge() {
    assertEquals(graph0.getEdge(0), graph1.getEdge(0));
    assertEquals(graph0.getEdge(1), graph1.getEdge(1));
    assertNotEquals(graph0.getEdge(2), graph1.getEdge(0));
    assertNotEquals(graph0.getEdge(3), graph1.getEdge(1));
  }

  @Test
  public void getEdges() {
    assertEquals(edgeList0, graph0.getEdges());
    assertEquals(edgeList1, graph1.getEdges());
    assertNotEquals(edgeList0, graph1.getEdges());
  }

  @Test
  public void size() {
    assertEquals(4, graph0.getM());
    assertEquals(2, graph1.getM());
  }

  @Test
  public void addEdge() {
    Graph graph = new Graph(n);
    graph.addEdge(e0);
    assertNotEquals(graph, graph1);
    graph.addEdge(e1);
    assertEquals(graph, graph1);
  }

  @Test
  public void bitFormatSize() {
    byte[] bytes = graph0.toBytes();
    assertEquals((n * n + 7) / 8, bytes.length);
    assertEquals((n * n + 7) / 8, Graph.bitFormatSize(n));
  }

  @Test
  public void completeGraph() {
    for (int n : new int[]{1, 2, 3, 4, 5}) {
      Graph completeGraph = Graph.completeGraph(n);
      assertEquals((n * (n - 1)) / 2, completeGraph.getM());
    }
  }
}
