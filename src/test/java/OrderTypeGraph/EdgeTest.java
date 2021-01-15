package OrderTypeGraph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EdgeTest {

  @Test
  public void test() {
    Edge edge = new Edge(0, 1);
    assertEquals(0, edge.getU());
    assertEquals(1, edge.getV());
  }

}
