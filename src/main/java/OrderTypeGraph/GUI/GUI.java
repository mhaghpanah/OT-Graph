package OrderTypeGraph;

import OrderTypeGraph.Exp.StaticConventions;
import OrderTypeGraph.MyFile.Address;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame {

  private JButton button1;
  private JPanel panelMain;
  private JSpinner spinnerN;
  private JSpinner spinnerID;
  private JComboBox comboBoxType;
  private JButton info;

//  public void setSpinnerNumberModelID(JSpinner jSpinner, int n) {
//    int[] size = new int[]{0, 0, 0,
//        1, 2, 3,
//        16, 135, 3_315,
//        158_817, 14_309_547, 0};
//    int id = Integer.valueOf(jSpinner.getValue().toString());
//    int newId = id < n ? id : id - 1;
//    SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(newId,0, size[n] - 1, 1);
//    jSpinner.setModel(spinnerNumberModel);
//  }

  public void Error(String str) {
    JOptionPane.showMessageDialog(null, str);
  }

  public void saveFile(int n, int id, String filenamePrefix, String str) {
    JFileChooser jFileChooser = new JFileChooser();

    String defaultFileName = String.format("%s_n_%d_id_%d.ipe", filenamePrefix, n, id);
    jFileChooser.setSelectedFile(new File(defaultFileName));

    String pathnamePrefix = null;
    String fileName = defaultFileName;
    // Demonstrate "Save" dialog:
    int rVal = jFileChooser.showSaveDialog(GUI.this);
    if (rVal == JFileChooser.APPROVE_OPTION) {
      fileName = jFileChooser.getSelectedFile().getName();
      pathnamePrefix = jFileChooser.getCurrentDirectory().toString();
      String pathname = String.format("%s%s%s", pathnamePrefix, File.separator, fileName);
      System.out.println(pathnamePrefix);
      System.out.println(fileName);
      System.out.println(pathname);
      MyFileWriter.write(pathname, str, Address.ROOT);
    } else if (rVal == JFileChooser.CANCEL_OPTION) {
      return;
    }
  }

  public GUI() {
    int defaultN = 5;
    spinnerN.setModel(new SpinnerNumberModel(defaultN, 3, 9, 1));
    String[] comboBoxValues = new String[]{"OT-Graph (Minimal Edge)", "OT-Graph (Cross Free)",
        "Exit-Graph", "Order-Types"};
    comboBoxType.setModel(new DefaultComboBoxModel(comboBoxValues));

    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String spinnerNValue = spinnerN.getValue().toString();
        int n = Integer.valueOf(spinnerNValue);
        String spinnerIDValue = spinnerID.getValue().toString();
        int id = Integer.valueOf(spinnerIDValue);
        String comboBoxTypeValue = comboBoxType.getSelectedItem().toString();

        int caseNum = 0;
        if (id >= Database.databaseSize(n)) {
          Error(String
              .format("For n = %d, there are %d order-types and our program is 0-indexed.", n,
                  Database.databaseSize(n)));
        } else {

//          String ans = ResultInterface.readResult(n, id, caseNum);
          String ans = null;
          Database database = Database.read(n);
          Points points = database.get(id);
          Graph graph = null;
          boolean addText = true;

          if (comboBoxTypeValue.equalsIgnoreCase("OT-Graph (Minimal Edge)") || comboBoxTypeValue
              .equalsIgnoreCase("OT-Graph (Cross Free)")) {

            String prefixPath = null;
            if (comboBoxTypeValue.equalsIgnoreCase("OT-Graph (Minimal Edge)")) {
              prefixPath = StaticConventions.caseNumber2PrefixPath(6);
            } else {
              prefixPath = StaticConventions.caseNumber2PrefixPath(0);
            }

            ResultDatasetIO resultDatasetIO = new ResultDatasetIO(n, prefixPath);
            graph = resultDatasetIO.getGraph(id);
          } else if (comboBoxTypeValue.equalsIgnoreCase("Exit-Graph")) {
            graph = ExitGraph.generateGraph(points);
          } else if (comboBoxTypeValue.equalsIgnoreCase("Order-Types")) {
            graph = new Graph(n);
          }

          ans = PlaneGraphIpeDrawer.draw(graph, points, addText);
          saveFile(n, id, comboBoxTypeValue, ans);
        }

      }
    });
    info.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        Error(
            "This program is written by \nMohammadreza Haghpanah (Mohammadreza.Haghpanah@utdallas.edu) under supervision of Dr. Sergey Bereg (besp@utdallas.edu) \nas part of NSF award CCF-1718994 project.");
      }
    });

//    spinnerN.addChangeListener(new ChangeListener() {
//      @Override
//      public void stateChanged(ChangeEvent changeEvent) {
//        int n = Integer.valueOf(spinnerN.getValue().toString());
//        setSpinnerNumberModelID(spinnerID, n);
//      }
//    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("OT-graph");

    frame.setContentPane(new GUI().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }


  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    panelMain = new JPanel();
    panelMain.setLayout(
        new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    panelMain.setEnabled(false);
    final JLabel label1 = new JLabel();
    label1.setText("Number of Points");
    panelMain.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label2 = new JLabel();
    label2.setText("Order Type ID");
    panelMain.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    info = new JButton();
    info.setText("Info");
    panelMain.add(info, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    button1 = new JButton();
    button1.setText("Get");
    panelMain.add(button1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    spinnerN = new JSpinner();
    panelMain.add(spinnerN, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    spinnerID = new JSpinner();
    panelMain.add(spinnerID, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label3 = new JLabel();
    label3.setText("Type");
    panelMain.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    comboBoxType = new JComboBox();
    panelMain.add(comboBoxType, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1,
        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return panelMain;
  }

}
