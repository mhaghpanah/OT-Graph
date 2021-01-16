package OrderTypeGraph;

import java.util.List;
import java.util.Objects;

public class Proof {

  Triple t;
  int type;
  List<Integer> arg;

  public Proof(Triple t, int type, List<Integer> arg) {
    this.t = t;
    this.type = type;
    this.arg = arg;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Proof)) {
      return false;
    }

    Proof proof = (Proof) o;

    if (type != proof.type) {
      return false;
    }
    if (!t.equals(proof.t)) {
      return false;
    }
    return Objects.equals(arg, proof.arg);
  }

  @Override
  public int hashCode() {
    int result = t.hashCode();
    result = 31 * result + type;
    result = 31 * result + (arg != null ? arg.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Proof{" +
        "t=" + t +
        ", type=" + type +
        ", arg=" + arg +
        '}';
  }
}
