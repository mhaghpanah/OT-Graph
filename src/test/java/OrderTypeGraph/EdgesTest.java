package OrderTypeGraph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class EdgesTest {

  int n;

  Edges edges0;
  Edges edges1;

  Edge e0 = new Edge(0, 1);
  Edge e1 = new Edge(2, 9);
  Edge e2 = new Edge(4, 2);
  Edge e3 = new Edge(5, 9);

  List<Edge> edgeList0 = Arrays.asList(e0, e1, e2, e3);
  List<Edge> edgeList1 = Arrays.asList(e0, e1);

  public EdgesTest() {
    n = 100;
    edges0 = new Edges(n, edgeList0);
    edges1 = new Edges(n, edgeList1);
  }

  @Test
  public void toEdge() {
    byte[] bytes0 = edges0.toBytes();
    Edges ans0 = Edges.toEdge(bytes0, n);

    byte[] bytes1 = edges1.toBytes();
    Edges ans1 = Edges.toEdge(bytes1, n);

    assertEquals(edges0, ans0);
    assertNotEquals(edges1, ans0);

    assertEquals(edges1, ans1);
    assertNotEquals(edges0, ans1);
  }

  @Test
  public void get() {
    assertEquals(edges0.get(0), edges1.get(0));
    assertEquals(edges0.get(1), edges1.get(1));
    assertNotEquals(edges0.get(2), edges1.get(0));
    assertNotEquals(edges0.get(3), edges1.get(1));
  }

  @Test
  public void getEdges() {
    assertEquals(edgeList0, edges0.getEdges());
    assertEquals(edgeList1, edges1.getEdges());
    assertNotEquals(edgeList0, edges1.getEdges());
  }

  @Test
  public void size() {
    assertEquals(4, edges0.size());
    assertEquals(2, edges1.size());
  }

  @Test
  public void add() {
    Edges edges = new Edges(n);
    edges.add(e0);
    assertNotEquals(edges, edges1);
    edges.add(e1);
    assertEquals(edges, edges1);
  }

  @Test
  public void bitFormatSize() {
    byte[] bytes = edges0.toBytes();
    assertEquals((n * n + 7) / 8, bytes.length);
    assertEquals((n * n + 7) / 8, Edges.bitFormatSize(n));
  }
}
