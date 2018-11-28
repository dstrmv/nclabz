package buildings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JMenu menuLookFeel = new JMenu("Look&Feel");

        //LOOK AND FEEL
        ButtonGroup buttonGroup = new ButtonGroup();
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for (int i = 0; i < installedLookAndFeels.length; i++) {
            UIManager.LookAndFeelInfo installedLookAndFeel = installedLookAndFeels[i];
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

        dwellingItem.addActionListener(e -> System.out.println("test"));

        menuFile.add(dwellingItem);
        menuFile.add(officeItem);

        menuBar.add(menuFile);
        menuBar.add(menuLookFeel);

        setJMenuBar(menuBar);


    }

}
