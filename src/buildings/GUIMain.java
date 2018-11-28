package buildings;

import javax.swing.*;
import java.awt.*;

public class GUIMain {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}

class GUI extends JFrame {

    private JMenuBar menuBar;

    public GUI() {
        setTitle("Building Browser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem dwellingItem = new JMenuItem("Choose dwelling...");
        JMenuItem officeItem = new JMenuItem("Choose office building...");
        menuFile.add(dwellingItem);
        menuFile.add(officeItem);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);


    }

}
