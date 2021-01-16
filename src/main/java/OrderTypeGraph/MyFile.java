package OrderTypeGraph;

import java.io.File;
import java.io.IOException;

public class MyFile {

  String pathname;
  File file;

  public MyFile(String suffixPath, boolean rootPath) {
    if (rootPath) {
      pathname = suffixPath;
    } else {
      pathname = String.join(File.separator,
          System.getProperty("user.dir"), "results", suffixPath);
    }
    System.err.printf("MyFile class is processing %s\n", pathname);
    createFile();
  }

  public static File getInstance(String suffixPath, boolean rootPath) {
    MyFile myFile = new MyFile(suffixPath, rootPath);
    return myFile.getFile();
  }

  public static boolean isExists(String suffixPath, boolean rootPath) {
    String pathname;
    if (rootPath) {
      pathname = suffixPath;
    } else {
      pathname = String.join(File.separator,
          System.getProperty("user.dir"), "results", suffixPath);
    }
    System.err.printf("MyFile class is processing %s\n", pathname);
    File file = new File(pathname);
    return file.exists();
  }

  public File getFile() {
    return file;
  }

  private void createFile() {
    file = new File(pathname);
    try {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      } else {
        System.err.println("Folder already exists.");
      }
      if (file.createNewFile()) {
        System.err.println("File created: " + file.getName());
      } else {
        System.err.println("File already exists.");
      }
    } catch (IOException e) {
      System.err.println("An error occurred.");
      e.printStackTrace();
      System.exit(1);
    }
  }

}
