import View.MainFrame;
import acdc.Services.ErrorLogging;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            ErrorLogging.getInstance().addLog("Impossible de charger le thème graphique : " + e.getMessage());
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.setBounds(0, 0, 800, 600);
        mainFrame.setMinimumSize(new Dimension(500,350));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
    }
}
