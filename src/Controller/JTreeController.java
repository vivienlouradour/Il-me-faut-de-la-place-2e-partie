package Controller;

import Model.AppModel;
import Model.Notifications;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class JTreeController implements ActionListener, Observer {
    JTree jTree;
    JPanel jPanel;

    public JTreeController(JTree jTree, JPanel jPanel){
        this.jTree = jTree;
        this.jPanel = jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog((JMenuItem)e.getSource());

        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            AppModel.getInstance().setTree(file.getAbsolutePath());
        }
    }

    /**
     * Méthode lancée lors du changement du treeModel
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Notifications notif = (Notifications)arg;
        if(notif == Notifications.TreeModelChange){
            this.jTree.setModel(AppModel.getInstance().getTree());
            this.jPanel.revalidate();
        }
    }
}