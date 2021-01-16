package OrderTypeGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DynamicPreCCSystem extends PreCCSystem {

  final PreCCSystem goal;
  final boolean[] rules;
  Queue<Triple> q;
  int increment;
  Proofs proofs;

  public DynamicPreCCSystem(int n, PreCCSystem goal, boolean[] rules) {
    super(n);
    proofs = new Proofs(goal, new ArrayList<>());
    this.goal = goal;
    this.rules = rules;
  }

  public int zeroLevelValueOf(Edge edge) {
    int value = 0;
    int u = edge.getU();
    int v = edge.getV();

    assert 0 <= u && u < getN();
    assert 0 <= v && v < getN();
    assert u != v;

    for (int i = 0; i < getN(); i++) {
      if (!isKnown(u, v, i)) {
        value++;
      }
    }
    return value;
  }

  public int addRelations(List<Triple> triples) {
    increment = 0;
    q = new LinkedList<>();

    for (Triple t : triples) {
      assert goal.isCCRelation(t);
      if (!isKnown(t) && !isKnown(t.cyclicSymmetry()) && !isKnown(
          t.cyclicSymmetry().cyclicSymmetry())) {
        update(t, 1);
        proofs.add(new Proof(t, 0, null));
      }
    }

    while (!q.isEmpty()) {
      Triple top = q.remove();

      assert isKnown(top) && isCCRelation(top);
      assert goal.isCCRelation(top);

      axiom1(top);
      axiom2(top);
      if (rules.length > 0 && rules[0]) {
        axiom4(top);
      }
      if (rules.length > 1 && rules[1]) {
        axiom5(top);
      }
      if (rules.length > 2 && rules[2]) {
        axiom5prime(top);
      }

    }

    return increment;
  }

  private boolean update(Triple t, int v) {
    assert !isKnown(t) || getRelation(t) == v;
    assert goal.getRelation(t) == v;

    if (!isKnown(t)) {
      setRelation(t, v);
      if (v == 1) {
        q.add(t);
      }
      increment += 1;
      update(t.cyclicSymmetry(), v);
      return true;
    }
    return false;
  }

  private void axiom1(Triple t) {
    assert isKnown(t) && isCCRelation(t);
    update(t.cyclicSymmetry(), 1);
  }

  private void axiom2(Triple t) {
    assert isKnown(t) && isCCRelation(t);
    update(t.antiSymmetry(), -1);
  }

  private void axiom4(Triple triple) {
    assert isKnown(triple) && isCCRelation(triple);

    int p, q, r, t;
    t = triple.a;
    q = triple.b;
    r = triple.c;
    for (p = 0; p < getN(); p++) {
      axiom4(p, q, r, t);
    }
    p = triple.a;
    t = triple.b;
    r = triple.c;
    for (q = 0; q < getN(); q++) {
      axiom4(p, q, r, t);
    }
    p = triple.a;
    q = triple.b;
    t = triple.c;
    for (r = 0; r < getN(); r++) {
      axiom4(p, q, r, t);
    }
  }

  private void axiom4(int p, int q, int r, int t) {
    if (isCCRelation(t, q, r) && isCCRelation(p, t, r) && isCCRelation(p, q, t)
        && !isKnown(p, q, r)) {

      assert goal.isCCRelation(t, q, r) && goal.isCCRelation(p, t, r) && goal.isCCRelation(p, q, t);
      assert goal.isKnown(p, q, r);
      assert goal.isCCRelation(p, q, r);

      Triple triple = new Triple(p, q, r);
      if (update(triple, 1)) {
        proofs.add(new Proof(triple, 4, Arrays.asList(p, q, r, t)));
      }
    }
  }

  private void axiom5(Triple triple) {
    assert isKnown(triple) && isCCRelation(triple);

    int p, q, r, s, t;
    t = triple.a;

    s = triple.b;
    p = triple.c;
    for (q = 0; q < getN(); q++) {
      for (r = 0; r < getN(); r++) {
        axiom5(p, q, r, s, t);
      }
    }

    s = triple.b;
    q = triple.c;
    for (p = 0; p < getN(); p++) {
      for (r = 0; r < getN(); r++) {
        axiom5(p, q, r, s, t);
      }
    }

    s = triple.b;
    r = triple.c;
    for (p = 0; p < getN(); p++) {
      for (q = 0; q < getN(); q++) {
        axiom5(p, q, r, s, t);
      }
    }

    p = triple.b;
    q = triple.c;
    for (r = 0; r < getN(); r++) {
      for (s = 0; s < getN(); s++) {
        axiom5(p, q, r, s, t);
      }
    }

    q = triple.b;
    r = triple.c;
    for (p = 0; p < getN(); p++) {
      for (s = 0; s < getN(); s++) {
        axiom5(p, q, r, s, t);
      }
    }

  }

  private void axiom5(int p, int q, int r, int s, int t) {
    if (isCCRelation(t, s, p) && isCCRelation(t, s, q) && isCCRelation(t, s, r)
        && isCCRelation(t, p, q) && isCCRelation(t, q, r) && !isKnown(t, p, r)) {

      assert goal.isCCRelation(t, s, p) && goal.isCCRelation(t, s, q) && goal.isCCRelation(t, s, r)
          && goal.isCCRelation(t, p, q) && goal.isCCRelation(t, q, r);
      assert goal.isKnown(t, p, r);
      assert goal.getRelation(t, p, r) == 1;
      assert goal.isCCRelation(t, p, r);

      Triple triple = new Triple(t, p, r);
      if (update(triple, 1)) {
        proofs.add(new Proof(triple, 5, Arrays.asList(p, q, r, s, t)));
      }
    }
  }


  private void axiom5prime(Triple triple) {
    assert isKnown(triple) && isCCRelation(triple);

    int p, q, r, s, t;

    s = triple.a;
    t = triple.b;
    p = triple.c;
    for (q = 0; q < getN(); q++) {
      for (r = 0; r < getN(); r++) {
        axiom5prime(p, q, r, s, t);
      }
    }

    s = triple.a;
    t = triple.b;
    q = triple.c;
    for (p = 0; p < getN(); p++) {
      for (r = 0; r < getN(); r++) {
        axiom5prime(p, q, r, s, t);
      }
    }

    s = triple.a;
    t = triple.b;
    r = triple.c;
    for (p = 0; p < getN(); p++) {
      for (q = 0; q < getN(); q++) {
        axiom5prime(p, q, r, s, t);
      }
    }

    t = triple.a;
    p = triple.b;
    q = triple.c;
    for (r = 0; r < getN(); r++) {
      for (s = 0; s < getN(); s++) {
        axiom5prime(p, q, r, s, t);
      }
    }

    t = triple.a;
    q = triple.b;
    r = triple.c;
    for (p = 0; p < getN(); p++) {
      for (s = 0; s < getN(); s++) {
        axiom5prime(p, q, r, s, t);
      }
    }

  }

  private void axiom5prime(int p, int q, int r, int s, int t) {
    if (isCCRelation(s, t, p) && isCCRelation(s, t, q) && isCCRelation(s, t, r)
        && isCCRelation(t, p, q) && isCCRelation(t, q, r) && !isKnown(t, p, r)) {

      assert goal.isCCRelation(s, t, p) && goal.isCCRelation(s, t, q) && goal.isCCRelation(s, t, r)
          && goal.isCCRelation(t, p, q) && goal.isCCRelation(t, q, r);
      assert goal.isKnown(t, p, r);
      assert goal.getRelation(t, p, r) == 1;
      assert goal.isCCRelation(t, p, r);

      Triple triple = new Triple(t, p, r);
      if (update(triple, 1)) {
        proofs.add(new Proof(triple, 6, Arrays.asList(p, q, r, s, t)));
      }
    }
  }

  public Proofs getProofs() {
    return proofs;
  }
}
