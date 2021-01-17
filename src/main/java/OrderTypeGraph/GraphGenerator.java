package OrderTypeGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GraphGenerator {

  final PreCCSystem goal;
  Random rand;
  boolean[] rules;

  GraphGenerator(PreCCSystem goal, boolean[] rules) {
    this.goal = goal;
    rand = new Random();
    long seed = rand.nextLong();
//    long seed = -3376504393442772969L;
    rand.setSeed(seed);
//    System.out.printf("seed: %d\n", seed);
    this.rules = rules;
  }

  public static Proofs computeCCClosure(PlanarGraph planarGraph, boolean[] rules) {
    Points points = planarGraph.getPoints();
    PreCCSystem goal = points.computeCCSystem();
    GraphGenerator gg = new GraphGenerator(goal, rules);
    return gg.computeCCClosure(planarGraph).getProofs();
  }

  public static boolean isOTGraph(PlanarGraph planarGraph, boolean[] rules) {
    Points points = planarGraph.getPoints();
    PreCCSystem goal = points.computeCCSystem();
    GraphGenerator gg = new GraphGenerator(goal, rules);
    return gg.computeCCClosure(planarGraph).isComplete();
  }

//  public static boolean isOTGraph(PreCCSystem goal, Graph graph, boolean[] rules) {
//    GraphGenerator gg = new GraphGenerator(goal, rules);
//    return gg.computeCCClosure(graph).isComplete();
//  }

  public int getN() {
    return goal.getN();
  }

  private List<Triple> edgeToRelations(Edge e) {
    List<Triple> triples = new ArrayList<>();
    for (int i = 0; i < getN(); i++) {
      Triple t = new Triple(e.u, e.v, i);
      if (goal.getRelation(t) == +1) {
        triples.add(t);
      } else if (goal.getRelation(t) == -1) {
        triples.add(t.antiSymmetry());
      }
    }
    return triples;
  }

  protected Graph runZeroLevelGreedyAlgorithm() {
    DynamicPreCCSystem current = new DynamicPreCCSystem(getN(), goal, rules);
    Set<Edge> visitedEdges = new HashSet<>();
    Set<Edge> unvisitedEdges = new HashSet<>();

    for (int i = 0; i < getN(); i++) {
      for (int j = i + 1; j < getN(); j++) {
        unvisitedEdges.add(new Edge(i, j));
      }
    }

    Graph graph = new Graph(getN());

    int edgeCount = 0;
    while (!current.isComplete()) {
      assert current.subset(goal);

      List<Edge> candidateEdges = new ArrayList<>();
      int bestValue = 0;
      for (Edge edge : unvisitedEdges) {
        int value = current.zeroLevelValueOf(edge);
        if (value >= bestValue) {
          if (value > bestValue) {
            candidateEdges.clear();
            bestValue = value;
          }
          candidateEdges.add(edge);
        }
      }

      assert bestValue > 0;
      assert candidateEdges.size() > 0;

      int index = rand.nextInt(candidateEdges.size());
      Edge edge = candidateEdges.get(index);

      assert current.zeroLevelValueOf(edge) == bestValue;

      int preKnown = current.getKnown();
      assert current.subset(goal);
      List<Triple> triples = edgeToRelations(edge);
      int increment = current.addRelations(triples);

      assert increment > 0;
      visitedEdges.add(edge);
      unvisitedEdges.remove(edge);
      assert visitedEdges.contains(edge);
      assert !unvisitedEdges.contains(edge);
      graph.addEdge(edge);
      edgeCount++;
      assert current.subset(goal);
      assert preKnown + increment == current.getKnown();
      assert visitedEdges.size() == edgeCount;

    }
    assert current.equals(goal);
    assert current.getProofs().isCorrect();
    assert visitedEdges.size() == edgeCount;

    return graph;
  }


  protected Graph runBottomUpRandomAlgorithm() {
    DynamicPreCCSystem current = new DynamicPreCCSystem(getN(), goal, rules);
    Set<Edge> visitedEdges = new HashSet<>();
    Graph graph = new Graph(getN());

    int edgeCount = 0;
    while (!current.isComplete()) {
      assert current.subset(goal);

      int u = rand.nextInt(getN());
      int v = rand.nextInt(getN());
      Edge edge = new Edge(u, v);
      int preKnown = current.getKnown();
      if (!visitedEdges.contains(edge)) {
        assert current.subset(goal);
        List<Triple> triples = edgeToRelations(edge);
        int increment = current.addRelations(triples);

        if (increment > 0) {
          visitedEdges.add(edge);
          graph.addEdge(edge);
          edgeCount++;
        }
        assert current.subset(goal);
        assert preKnown + increment == current.getKnown();
        assert visitedEdges.size() == edgeCount;

      }
    }
    assert current.equals(goal);
    assert current.getProofs().isCorrect();
    assert visitedEdges.size() == edgeCount;

    return graph;
  }

  protected DynamicPreCCSystem computeCCClosure(Graph graph) {
    DynamicPreCCSystem current = new DynamicPreCCSystem(getN(), goal, rules);
    assert current.subset(goal);

    List<Triple> triples = new ArrayList<>();
    for (Edge edge : graph.getEdges()) {
      triples.addAll(edgeToRelations(edge));
    }
    current.addRelations(triples);

//    assert triples.size() == edges.size() * (getN() - 2);
//    assert current.getProofs().isCorrect();
    assert current.subset(goal);
//    assert current.isComplete();
//    assert current.equals(goal);
//    assert current.getProofs().isCorrect();

    return current;
  }

}
