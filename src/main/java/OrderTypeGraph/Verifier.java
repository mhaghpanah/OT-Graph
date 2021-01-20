package OrderTypeGraph;

public class Verifier {

  public static boolean isCCW(Integer[][][] orientations, int a, int b, int c) {
    Integer v = orientations[a][b][c];
    return v != null && v == 1;
  }

  public static boolean verify(Graph graph, Points points, boolean[] rules) {
    PreCCSystem goal = points.computeCCSystem();
    int n = points.size();
    Integer[][][] orientations = new Integer[n][n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          if (i == j || j == k || i == k) {
            orientations[i][j][k] = 0;
          }
        }
      }
    }

    for (Edge edge : graph.getEdges()) {
      for (int i = 0; i < n; i++) {
        int u = edge.getU();
        int v = edge.getV();
        if (u == v) {
          System.err.println("Error Edge");
          return false;
        }
        if (i == u || i == v) continue;
        orientations[i][u][v] = goal.getRelation(i, u, v);
        orientations[i][v][u] = goal.getRelation(i, v, u);

        orientations[u][i][v] = goal.getRelation(u, i, v);
        orientations[u][v][i] = goal.getRelation(u, v, i);

        orientations[v][i][u] = goal.getRelation(v, i, u);
        orientations[v][u][i] = goal.getRelation(v, u, i);
      }
    }

    int count = 0;
    boolean changed = true;
    while (changed) {
      changed = false;

      System.err.println(count);
      count++;

      // rule 1
      for (int p = 0; p < n; p++) {
        for (int q = 0; q < n; q++) {
          if (q == p) continue;
          for (int r = 0; r < n; r++) {
            if (r == p || r == q) continue;
            if (isCCW(orientations, p, q, r) && orientations[q][r][p] == null) {
              changed = true;
              orientations[q][r][p] = 1;
              if (!orientations[q][r][p].equals(goal.getRelation(q, r, p))) {
                System.err.println("Rule 1 Error");
                return false;
              }
            }
          }
        }
      }

      // rule 2
      for (int p = 0; p < n; p++) {
        for (int q = 0; q < n; q++) {
          if (q == p) continue;
          for (int r = 0; r < n; r++) {
            if (r == p || r == q) continue;
            if (isCCW(orientations, p, q, r) && orientations[p][r][q] == null) {
              changed = true;
              orientations[p][r][q] = -1;
              if (!orientations[p][r][q].equals(goal.getRelation(p, r, q))) {
                System.err.println("Rule 2 Error");
                return false;
              }
            }
          }
        }
      }

      // rule 3

      // rule 4
      if (rules[0]) {
        for (int t = 0; t < n; t++) {
          for (int p = 0; p < n; p++) {
            if (p == t)
              continue;
            for (int q = 0; q < n; q++) {
              if (q == t || q == p)
                continue;
              for (int r = 0; r < n; r++) {
                if (r == t || r == p || r == q)
                  continue;

                if (isCCW(orientations, t, q, r) && isCCW(orientations, p, t, r) &&
                    isCCW(orientations, p, q, t) && orientations[p][q][r] == null) {
                  changed = true;
                  orientations[p][q][r] = 1;
                  if (!orientations[p][q][r].equals(goal.getRelation(p, q, r))) {
                    System.err.println("Rule 4 Error");
                    return false;
                  }
                }
              }
            }
          }
        }
      }

      // rule 5
      if (rules[1]) {
        for (int t = 0; t < n; t++) {
          for (int p = 0; p < n; p++) {
            if (p == t)
              continue;
            for (int q = 0; q < n; q++) {
              if (q == t || q == p)
                continue;
              for (int r = 0; r < n; r++) {
                if (r == t || r == p || r == q)
                  continue;
                for (int s = 0; s < n; s++) {
                  if (s == t || s == p || s == q || s == r)
                    continue;
                  if (isCCW(orientations, t, s, p) && isCCW(orientations, t, s, q) &&
                      isCCW(orientations, t, s, r) && isCCW(orientations, t, p, q) &&
                      isCCW(orientations, t, q, r) && orientations[t][p][r] == null) {

                    changed = true;
                    orientations[t][p][r] = 1;
                    if (!orientations[t][p][r].equals(goal.getRelation(t, p, r))) {
                      System.err.println("Rule 5 Error");
                      return false;
                    }
                  }
                }
              }
            }
          }
        }
      }

      // rule 6
      if (rules[2]) {
        for (int t = 0; t < n; t++) {
          for (int p = 0; p < n; p++) {
            if (p == t)
              continue;
            for (int q = 0; q < n; q++) {
              if (q == t || q == p)
                continue;
              for (int r = 0; r < n; r++) {
                if (r == t || r == p || r == q)
                  continue;
                for (int s = 0; s < n; s++) {
                  if (s == t || s == p || s == q || s == r)
                    continue;
                  if (isCCW(orientations, s, t, p) && isCCW(orientations, s, t, q) &&
                      isCCW(orientations, s, t, r) && isCCW(orientations, t, p, q) &&
                      isCCW(orientations, t, q, r) && orientations[t][p][r] == null) {

                    changed = true;
                    orientations[t][p][r] = 1;
                    if (!orientations[t][p][r].equals(goal.getRelation(t, p, r))) {
                      System.err.println("Rule 6 Error");
                      return false;
                    }
                  }
                }
              }
            }
          }
        }
      }

    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          if (orientations[i][j][k] == null || !orientations[i][j][k]
              .equals(goal.getRelation(i, j, k))) {
            System.err.println("Error Not Complete");
            return false;
          }
        }
      }
    }

    return true;
  }
}
