package buildings;

import buildings.dwelling.Dwelling;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.OfficeBuilding;
import util.Buildings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
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
    private JPanel container;
    private JLabel floorInfoLabel, spaceInfo;

    public GUI() {
        Locale.setDefault(Locale.US);
        setTitle("Building Browser");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(800 / 2, 600 / 4);
        setMinimumSize(new Dimension(800, 600));
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem dwellingItem = new JMenuItem("Choose dwelling...");
        JMenuItem officeItem = new JMenuItem("Choose office building...");
        JMenu menuLookFeel = new JMenu("Look&Feel");


        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(container);

        this.add(scrollPane);
        this.add(container);

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

        dwellingItem.addActionListener(e -> {
            loadBuilding(Dwelling.class.getName());
            showBuildingUI(building);
        });
        officeItem.addActionListener(e -> {
            loadBuilding(OfficeBuilding.class.getName());
            showBuildingUI(building);
        });

        menuFile.add(dwellingItem);
        menuFile.add(officeItem);

        menuBar.add(menuFile);
        menuBar.add(menuLookFeel);

        setJMenuBar(menuBar);

        //layout.setVerticalGroup(layout.createSequentialGroup()                .);


    }

    private String getBuildingInfo(Building building) {
        StringBuilder str = new StringBuilder();
        String[] splitName = building.getClass().getName().split("\\.");
        str.append("<html>").append(splitName[splitName.length-1]).append("<br/>Floor amount: ").append(building.floorsAmount()).
                append("<br/>Spaces amount: ").append(building.spacesAmount()).append("<br/>")
                .append("Total area: ").append(building.totalArea()).append("</html>");
        return str.toString();
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

    private File showFileOpenDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File result = fileChooser.getSelectedFile();
        return result;
    }

    private MouseListener getFloorML(Floor floor) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { floorInfoLabel.setText(getFloorInfo(floor)); }

            @Override
            public void mouseExited(MouseEvent e) { floorInfoLabel.setText(""); }
        };
    }

    private MouseListener getSpaceButtonML (Space space) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { spaceInfo.setText(space.toString()); }

            @Override
            public void mouseExited(MouseEvent e) { spaceInfo.setText(""); }
        };
    }

    private String getFloorInfo (Floor floor) {
        StringBuilder str = new StringBuilder("<html>");
        String name = floor.getClass().getSimpleName();
        str.append(name).append("<br/>Space amount: ").append(floor.spacesAmount())
                .append("<br/>Floor area: ").append(floor.totalArea()).append("</html>");
        return str.toString();
    }

    private void showBuildingUI (Building building) {
        container.removeAll();
        JPanel floor, buildingInfo,floorInfo, infoContainer;
        JButton button;
        infoContainer = new JPanel();
        infoContainer.setLayout(new BoxLayout(infoContainer,BoxLayout.X_AXIS));
        infoContainer.setBorder(new EmptyBorder(5,10,5,10));

        buildingInfo = new JPanel();
        JLabel buildingInfoLabel = new JLabel(getBuildingInfo(building));
        buildingInfo.add(buildingInfoLabel);
        infoContainer.add(buildingInfo);

        floorInfo = new JPanel();
        floorInfoLabel = new JLabel("");
        floorInfo.add(floorInfoLabel);
        infoContainer.add(floorInfo);

        spaceInfo = new JLabel("");
        infoContainer.add(spaceInfo);

        Floor currentFloor;
        for (int i = 0; i < building.floorsAmount(); i++) {
            floor = new JPanel();
            floor.setMinimumSize(new Dimension(50,10));
            floor.setBorder(new LineBorder(Color.BLACK,1));
            currentFloor = building.getFloor(i);
            floor.addMouseListener(getFloorML(currentFloor));
            for (Space space : currentFloor) {
                button = new JButton(space.toString());
                //button.setBorder(new LineBorder(Color.BLACK,1,true));
                button.addMouseListener(getFloorML(currentFloor));
                button.addMouseListener(getSpaceButtonML(space));
                floor.add(button);
            }
            container.add(floor, i);
        }
        container.add(infoContainer);
        this.validate();
        this.doLayout();
    }


}
