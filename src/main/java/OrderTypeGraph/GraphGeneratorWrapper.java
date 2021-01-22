package OrderTypeGraph;

public class GraphGeneratorWrapper {

  public static Proofs computeCCClosure(PlanarGraph planarGraph, boolean[] rules) {
    return computeCCClosure(planarGraph, planarGraph.getPoints(), rules);
  }

  public static Proofs computeCCClosure(Graph graph, Points points, boolean[] rules) {
    GraphGenerator gg = new GraphGenerator(points, rules);
    return gg.computeCCClosure(graph).getProofs();
  }

  public static boolean isOTGraph(PlanarGraph planarGraph, boolean[] rules) {
    return isOTGraph(planarGraph, planarGraph.getPoints(), rules);
  }

  public static boolean isOTGraph(Graph graph, Points points, boolean[] rules) {
    GraphGenerator gg = new GraphGenerator(points, rules);
    boolean ans = gg.computeCCClosure(graph).isComplete();
    assert ans == Verifier.verify(graph, points, rules);
    return ans;
  }

  public static Tuple<Graph, Proofs> generateGraphWithProofs(Points points,
      AlgorithmParameters algorithmParameters) {
    GraphGenerator gg = new GraphGenerator(points, algorithmParameters.rules);
    Graph graph;
    switch (algorithmParameters.algorithmType) {
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

  public static Graph generateGraph(Points points, AlgorithmParameters algorithmParameters) {
    GraphGenerator gg = new GraphGenerator(points, algorithmParameters.rules);
    switch (algorithmParameters.algorithmType) {
      case 0:
        return gg.runBottomUpRandomAlgorithm();
      case 1:
        return gg.runZeroLevelGreedyAlgorithm();
      default:
        throw new IllegalArgumentException();
    }
  }

  public static Graph generateGraph(Points points, AlgorithmParameters algorithmParameters,
      int repeatNum) {
    int n = points.size();
    Graph ans = Graph.completeGraph(n);
    for (int i = 0; i < repeatNum; i++) {
      Graph graph = generateGraph(points, algorithmParameters);
      if (graph.getM() < ans.getM()) {
        ans = graph;
      }
      System.out.printf("Iteration: %d Best so far: %d\n", i, ans.getM());
    }
    return ans;
  }

  public static PlanarGraph generateGraphCrossOrder(Points points,
      AlgorithmParameters algorithmParameters, int repeatNum) {
    PlanarGraph ans = PlanarGraph.completeGraph(points);
    for (int i = 0; i < repeatNum; i++) {
      Graph graph = generateGraph(points, algorithmParameters);
      PlanarGraph planarGraph = new PlanarGraph(graph, points);
      if (planarGraph.compareTo(ans) < 0) {
        ans = planarGraph;
      }
      System.out.printf("Iteration: %d Best so far: %d\n", i, ans.getM());
    }
    return ans;
  }

  public static int generateGraphIterationCalc(Points points,
      AlgorithmParameters algorithmParameters,
      int desireEdgeNum, int maxRepeatNum) {
    for (int i = 0; i < maxRepeatNum; i++) {
      Graph graph = generateGraph(points, algorithmParameters);
      if (graph.getM() <= desireEdgeNum) {
        return i + 1;
      }
    }
    return -1;
  }

  public static class AlgorithmParameters {

    int algorithmType;
    boolean[] rules;

    public AlgorithmParameters(int algorithmType, boolean[] rules) {
      this.algorithmType = algorithmType;
      this.rules = rules;
    }

    public boolean[] getRules() {
      return rules;
    }
  }

}
