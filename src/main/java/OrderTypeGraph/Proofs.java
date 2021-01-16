package OrderTypeGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Proofs {

  final PreCCSystem goal;
  final List<Edge> edges;

  List<Proof> proofs;

  public Proofs(PreCCSystem goal, List<Edge> edges) {
    this.goal = goal;
    this.edges = edges;

    proofs = new ArrayList<>();
  }

  public int getN() {
    return goal.getN();
  }

  public int size() {
    return proofs.size();
  }

  public List<Proof> getProofs() {
    return proofs;
  }

  public int getProofsComplexity() {
    int ans = 0;
    for (Proof p : getProofs()) {
      if (p.type == 4 || p.type == 5) {
        ans++;
      }
    }
    return ans;
  }

  public void add(Proof proof) {
    proofs.add(proof);
  }

  public int countContainsEquivalents(Triple t, Set<Triple> set) {
    int ans = 0;
    for (int i = 0; i < 3; i++) {
      if (set.contains(t)) {
        ans++;
      }
      t = t.cyclicSymmetry();
    }
    return ans;
  }

  private int countContainsCyclicSymmetry(Triple t, Set<Triple> set) {
    return countContainsEquivalents(t, set);
  }

  private int countContainsAntiSymmetry(Triple t, Set<Triple> set) {
    return countContainsEquivalents(t.antiSymmetry(), set);
  }

  public boolean isCorrect() {
    Set<Triple> knownTriples = new HashSet<>();
    for (Proof p : proofs) {
      knownTriples.add(p.t);
    }

    for (int i = 0; i < getN(); i++) {
      for (int j = 0; j < getN(); j++) {
        for (int k = 0; k < getN(); k++) {
          Triple t = new Triple(i, j, k);
          if (goal.isKnown(t)) {
            if (goal.getRelation(t) == 1
                && !(countContainsCyclicSymmetry(t, knownTriples) == 1
                && countContainsAntiSymmetry(t, knownTriples) == 0)) {
              assert false;
              return false;
            } else if (goal.getRelation(t) == -1
                && !(countContainsCyclicSymmetry(t, knownTriples) == 0
                && countContainsAntiSymmetry(t, knownTriples) == 1)) {
              assert false;
              return false;
            }
          } else {
            if (!(countContainsCyclicSymmetry(t, knownTriples) == 0
                && countContainsAntiSymmetry(t, knownTriples) == 0)) {
              assert false;
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Proofs)) {
      return false;
    }

    Proofs proofs1 = (Proofs) o;

    return Objects.equals(proofs, proofs1.proofs);
  }

  @Override
  public int hashCode() {
    return proofs != null ? proofs.hashCode() : 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Proof p : getProofs()) {
      sb.append(p);
      sb.append("\n");
    }
    return sb.toString();
  }
}
