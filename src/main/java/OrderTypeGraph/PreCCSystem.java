package OrderTypeGraph;

import java.util.Arrays;

public class PreCCSystem {

  final int n;
  final int target;

  Integer[][][] relation;
  int known;

  public PreCCSystem(int n) {
    this.n = n;
    target = n * n * n;

    relation = new Integer[n][n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        relation[i][i][j] = 0;
        relation[i][j][i] = 0;
        relation[j][i][i] = 0;
      }
    }

    known = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          if (isKnown(i, j, k)) {
            known++;
          }
        }
      }
    }

  }

  public int getN() {
    return n;
  }

  public int getKnown() {
    return known;
  }

  public int getTarget() {
    return target;
  }

  public boolean isComplete() {
    return getKnown() == getTarget();
  }

  public Integer getRelation(Triple t) {
    return getRelation(t.a, t.b, t.c);
  }

  public Integer getRelation(int a, int b, int c) {
    return relation[a][b][c];
  }

  public void setRelation(Triple t, int v) {
    setRelation(t.a, t.b, t.c, v);
  }

  public void setRelation(int a, int b, int c, int v) {
    if (relation[a][b][c] == null) {
      known += 1;
    }
    assert relation[a][b][c] == null || relation[a][b][c] == v;
    relation[a][b][c] = v;
  }

  public boolean isKnown(Triple t) {
    return isKnown(t.a, t.b, t.c);
  }

  public boolean isKnown(int a, int b, int c) {
    return relation[a][b][c] != null;
  }

  public boolean isCCRelation(Triple t) {
    return isCCRelation(t.a, t.b, t.c);
  }

  public boolean isCCRelation(int a, int b, int c) {
    return isKnown(a, b, c) && getRelation(a, b, c) == 1;
  }

  public boolean subset(PreCCSystem o) {
    if (getN() != o.getN()) {
      return false;
    }
    for (int i = 0; i < getN(); i++) {
      for (int j = 0; j < getN(); j++) {
        for (int k = 0; k < getN(); k++) {
          if (isKnown(i, j, k) && !getRelation(i, j, k).equals(o.getRelation(i, j, k))) {
            System.out.printf("Not equal: %s = %d %d",
                new Triple(i, j, k), getRelation(i, j, k), o.getRelation(i, j, k));
            return false;
          }
        }
      }
    }
    return true;
  }

  public boolean KnuthAxiomChecker() {

    for (int p = 0; p < getN(); p++) {
      for (int q = 0; q < getN(); q++) {
        for (int r = 0; r < getN(); r++) {
          if (isCCRelation(p, q, r) && !isCCRelation(q, r, p)) {
            return false;
          }
          if (isCCRelation(p, q, r) && isCCRelation(p, r, q)) {
            return false;
          }
          if (p != q && q != r && p != r
              && !(isCCRelation(p, q, r) || isCCRelation(p, r, q))) {
            return false;
          }
        }
      }
    }

    for (int p = 0; p < getN(); p++) {
      for (int q = 0; q < getN(); q++) {
        for (int r = 0; r < getN(); r++) {
          for (int t = 0; t < getN(); t++) {
            if (isCCRelation(t, q, r) && isCCRelation(p, t, r) && isCCRelation(p, q, t)
                && !isCCRelation(p, q, r)) {
              return false;
            }
          }
        }
      }
    }

    for (int p = 0; p < getN(); p++) {
      for (int q = 0; q < getN(); q++) {
        for (int r = 0; r < getN(); r++) {
          for (int s = 0; s < getN(); s++) {
            for (int t = 0; t < getN(); t++) {
              if (isCCRelation(t, s, p) && isCCRelation(t, s, q) && isCCRelation(t, s, r)
                  && isCCRelation(t, p, q) && isCCRelation(t, q, r) && !isCCRelation(t, p, r)) {
                return false;
              }
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
    if (!(o instanceof PreCCSystem)) {
      return false;
    }

    PreCCSystem that = (PreCCSystem) o;

    return Arrays.deepEquals(relation, that.relation);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(relation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("CCSystem{ n=")
        .append(n)
        .append(", relation=")
        .append(Arrays.deepToString(relation))
        .append("}\n");

    for (int i = 0; i < n; i++) {
      sb.append(String.format("i = %d ", i))
          .append(Arrays.deepToString(relation[i]))
          .append("\n");
    }

    return sb.toString();
  }

}
