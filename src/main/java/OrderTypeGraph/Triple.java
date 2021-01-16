package OrderTypeGraph;

public class Triple implements Comparable<Triple> {

  final Integer a;
  final Integer b;
  final Integer c;

  public Triple(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Triple cyclicSymmetry() {
    return new Triple(b, c, a);
  }

  public Triple antiSymmetry() {
    return new Triple(a, c, b);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Triple triple = (Triple) o;

    if (!a.equals(triple.a)) {
      return false;
    }
    if (!b.equals(triple.b)) {
      return false;
    }
    return c.equals(triple.c);
  }

  @Override
  public int hashCode() {
    int result = a.hashCode();
    result = 31 * result + b.hashCode();
    result = 31 * result + c.hashCode();
    return result;
  }

  @Override
  public int compareTo(Triple o) {
    if (a.equals(o.a) && b.equals(o.b)) {
      return c.compareTo(o.c);
    }
    if (a.equals(o.a)) {
      return b.compareTo(o.b);
    }
    return a.compareTo(o.a);
  }

  @Override
  public String toString() {
    return "Triple{" +
        "a=" + a +
        ", b=" + b +
        ", c=" + c +
        '}';
  }

}
