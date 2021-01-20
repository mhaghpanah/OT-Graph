package OrderTypeGraph;

public class ResultDatasetIO {

  final int n;
  final int datasetSize;
  final int resultSize;
  MyBinaryFileIO myBinaryFileIO;

  public ResultDatasetIO(int n, int datasetSize, int resultSize, String suffixPath) {
    assert Database.databaseSize(n) == datasetSize;

    this.n = n;
    this.datasetSize = datasetSize;
    this.resultSize = resultSize;

    myBinaryFileIO = new MyBinaryFileIO(String.format("%sdataset_results%02d.bin", suffixPath, n));

    assert
        myBinaryFileIO.length() == 0 || myBinaryFileIO.length() == (long) datasetSize * resultSize;

    if (myBinaryFileIO.length() == 0) {
      Graph completeGraph = Graph.completeGraph(n);
      byte[] completeGraphBytes = completeGraph.toBytes();
      assert completeGraphBytes.length == resultSize;
      byte[] bytes = new byte[datasetSize * resultSize];
      for (int i = 0; i < datasetSize; i++) {
        for (int j = 0; j < resultSize; j++) {
          bytes[i * resultSize + j] = completeGraphBytes[j];
        }
      }
//      myBinaryFileIO.writeFile(new byte[datasetSize * resultSize]);
      myBinaryFileIO.writeFile(bytes);
    }

  }

  public ResultDatasetIO(int n, String suffixPath) {
    this(n, Database.read(n).size(), Graph.bitFormatSize(n), suffixPath);
  }

  public int getN() {
    return n;
  }

  public int getDatasetSize() {
    return datasetSize;
  }

  public byte[] getBytes(int index) {
    return myBinaryFileIO.readFile(index * resultSize, resultSize);
  }

  public Graph getGraph(int index) {
    byte[] bytes = getBytes(index);
    return Graph.toGraph(bytes, getN());
  }

  public void setBytes(int index, byte[] bytes) {
    assert bytes.length == resultSize;
    myBinaryFileIO.writeFile(index * resultSize, bytes);
  }

  public void setGraph(int index, Graph graph) {
    setBytes(index, graph.toBytes());
  }

}
