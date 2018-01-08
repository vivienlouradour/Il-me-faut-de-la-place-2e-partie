import View.MainFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setBounds(0, 0, 800, 600);
        mainFrame.setMinimumSize(new Dimension(500,350));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
    }
}
