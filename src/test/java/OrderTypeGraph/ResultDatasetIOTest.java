package OrderTypeGraph;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ResultDatasetIOTest {

  @Test
  public void test0() {
    int n = 7;

    Database database = Database.read(n);
    ResultDatasetIO resultDatasetIO2 =
        new ResultDatasetIO(n, database.size(), Graph.bitFormatSize(n), "z");

    for (int i = 0; i < 6; i++) {
      System.out.println(resultDatasetIO2.getEdges(i));
      System.out.println(Graph.toGraph(resultDatasetIO2.getBytes(i), n));
    }

    ResultDatasetIO resultDatasetIO = new ResultDatasetIO(n, "z");
    System.out.println(resultDatasetIO.myBinaryFileIO.length());
    Edge e0 = new Edge(0, 1);
    Edge e1 = new Edge(2, 6);
    Edge e2 = new Edge(4, 2);
    Edge e3 = new Edge(5, 6);

    Graph graph0 = new Graph(n, Arrays.asList(e0, e2));
    Graph graph1 = new Graph(n, Arrays.asList(e1, e3));
    Graph graph2 = new Graph(n, Arrays.asList(e0, e1, e2, e3));
    List<Graph> list = Arrays.asList(graph0, graph1, graph2);

    for (int i = 0; i < list.size(); i++) {
      resultDatasetIO.setEdges(i, list.get(i));
    }

    for (int i = 0; i < 2 * list.size(); i++) {
      System.out.println(resultDatasetIO.getEdges(i));
      System.out.println(Graph.toGraph(resultDatasetIO.getBytes(i), n));
    }
  }

}
