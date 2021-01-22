package OrderTypeGraph.Executable;

import OrderTypeGraph.Database;
import OrderTypeGraph.Graph;
import OrderTypeGraph.GraphGeneratorWrapper;
import OrderTypeGraph.PlanarGraph;
import OrderTypeGraph.Points;
import OrderTypeGraph.ResultDatasetIO;
import java.time.Duration;
import java.time.Instant;

public class ExhaustiveSearch {

  Points points;
  boolean[] rules;
  int n;
//  Graph ans;
  PlanarGraph ans;
  public ExhaustiveSearch(Points points, boolean[] rules) {
    this.points = points;
    this.rules = rules;

    n = points.size();
//    ans = Graph.completeGraph(n);
    ans = PlanarGraph.completeGraph(points);
  }

  public Graph bottomUpCompute() {

    boolean notFound = true;
    int m = (n * (n - 1)) / 2;
    int k = 1;

    Graph completeGraph = Graph.completeGraph(n);

    while (notFound) {

      Radon.CombinationIterator it = new Radon.CombinationIterator(m, k++);

      while (it.hasNext()) {
        int[] next = it.next();
        Graph subsetGraph = new Graph(n);
        for (int a : next)
          subsetGraph.addEdge(completeGraph.getEdge(a));

        if (GraphGeneratorWrapper.isOTGraph(subsetGraph, points, rules)) {
          PlanarGraph planarGraph = new PlanarGraph(subsetGraph, points);
          if (planarGraph.compareTo(ans) < 0) ans = planarGraph;
          notFound = false;

//          ans = subsetGraph;
//          break;
        }

      }


    }

    return ans;
  }

  public static void ExhaustiveSearch(int id, Database database, ResultDatasetIO resultDatasetIO, boolean[] rules) {
    Instant start = Instant.now();
    Points points = database.get(id);
    ExhaustiveSearch exhaustiveSearch = new ExhaustiveSearch(points, rules);
    Graph graph = exhaustiveSearch.bottomUpCompute();
    resultDatasetIO.setGraph(id, graph);
    Instant end = Instant.now();
    System.err.printf("Exhustive search for OT-graph id = %d n = %d completed in %d seconds\n",
        id, database.getN(),
        Duration.between(start, end).getSeconds());
    System.err.printf("Summary: %s\n", new Features(id, points, graph, rules));
  }
}
