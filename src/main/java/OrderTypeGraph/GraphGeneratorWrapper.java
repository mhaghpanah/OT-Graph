package OrderTypeGraph;

public class GraphGeneratorWrapper {
  
  public static Tuple<Graph, Proofs> generateGraphWithProofs(Points points, int algoType,
      boolean[] rules) {
    GraphGenerator gg = new GraphGenerator(points, rules);
    Graph graph;
    switch (algoType) {
      case 0:
        graph = gg.runBottomUpRandomAlgorithm();
        break;
      case 1:
        graph = gg.runZeroLevelGreedyAlgorithm();
        break;
      default:
        throw new IllegalArgumentException();
    }

    return new Tuple<>(graph, gg.computeCCClosure(graph).getProofs());
  }

  public static Graph generateGraph(Points points, int algorithmType, boolean[] rules) {
    GraphGenerator gg = new GraphGenerator(points, rules);
    switch (algorithmType) {
      case 0:
        return gg.runBottomUpRandomAlgorithm();
      case 1:
        return gg.runZeroLevelGreedyAlgorithm();
      default:
        throw new IllegalArgumentException();
    }
  }

  public static Graph generateGraph(Points points, int algorithmType, boolean[] rules, int repeatNum) {
    int n = points.size();
    Graph ans = Graph.completeGraph(n);
    for (int i = 0; i < repeatNum; i++) {
      Graph graph = generateGraph(points, algorithmType, rules);
      if (graph.getM() < ans.getM()) {
        ans = graph;
      }
      System.out.printf("Iteration: %d Best so far: %d\n", i, ans.getM());
    }
    return ans;
  }

  public static PlanarGraph generateGraphCrossOrder(Points points, int algorithmType, boolean[] rules,
      int repeatNum) {
    PlanarGraph ans = PlanarGraph.completeGraph(points);
    for (int i = 0; i < repeatNum; i++) {
      Graph graph = generateGraph(points, algorithmType, rules);
      PlanarGraph planarGraph = new PlanarGraph(graph, points);
      if (planarGraph.compareTo(ans) < 0) {
        ans = planarGraph;
      }
      System.out.printf("Iteration: %d Best so far: %d\n", i, ans.getM());
    }
    return ans;
  }

  public static int generateGraphIterationCalc(Points points, int algorithmType, boolean[] rules,
      int desireEdgeNum, int maxRepeatNum) {
    for (int i = 0; i < maxRepeatNum; i++) {
      Graph graph = generateGraph(points, algorithmType, rules);
      if (graph.getM() <= desireEdgeNum) {
        return i + 1;
      }
    }
    return -1;
  }

}
