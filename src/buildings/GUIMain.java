package buildings;

import buildings.dwelling.Dwelling;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;
import util.Buildings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GUIMain {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}

class GUI extends JFrame {

    private Building building;

    public GUI() {
        setTitle("Building Browser");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(800/2, 600/4);
        setMinimumSize(new Dimension(800, 600));
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem dwellingItem = new JMenuItem("Choose dwelling...");
        JMenuItem officeItem = new JMenuItem("Choose office building...");
        JMenu menuLookFeel = new JMenu("Look&Feel");

        //LOOK AND FEEL
        ButtonGroup buttonGroup = new ButtonGroup();
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo installedLookAndFeel : installedLookAndFeels) {
            JRadioButtonMenuItem radioButton = new JRadioButtonMenuItem(installedLookAndFeel.getName());
            if (installedLookAndFeel.getName().equals(UIManager.getLookAndFeel().getName())) {
                radioButton.setSelected(true);
            }
            radioButton.addActionListener(e -> {
                try {
                    UIManager.setLookAndFeel(installedLookAndFeel.getClassName());
                    SwingUtilities.updateComponentTreeUI(getContentPane());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            });
            buttonGroup.add(radioButton);
            menuLookFeel.add(radioButton);
        }

        dwellingItem.addActionListener(e -> loadBuilding(Dwelling.class.getName()));
        officeItem.addActionListener(e -> loadBuilding(OfficeBuilding.class.getName()));

        menuFile.add(dwellingItem);
        menuFile.add(officeItem);

        menuBar.add(menuFile);
        menuBar.add(menuLookFeel);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        setLayout(layout);

      //layout.setVerticalGroup(layout.createSequentialGroup()                .);


    }

    private File showFileOpenDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File result = fileChooser.getSelectedFile();
        return result;
    }

    private void loadBuilding(String name) {

        File file = showFileOpenDialog();
        Buildings.setBuildingFactory(Buildings.getFactoryFromBuildingClassName(name));

        if (file != null) {
            try {
                Scanner in = new Scanner(file);
                //FileReader reader = new FileReader(file);
                try {
                    building = Buildings.readBuilding(in);
                    System.out.println(building.toString());
                } catch (NoSuchElementException e) {
                    JOptionPane.showMessageDialog(getContentPane(), "Bulding parse error! Select correct file", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
