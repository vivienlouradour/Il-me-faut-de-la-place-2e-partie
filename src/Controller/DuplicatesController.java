package Controller;

import View.DuplicatesFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DuplicatesController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            int filterResult = JOptionPane.showConfirmDialog(null, "Voulez vous appliquer les filtres (définis dans le menu \"filtre\") à la recherche de doublons ?", "Filtres",  JOptionPane.YES_NO_CANCEL_OPTION);
            boolean useFilter = true;
            switch (filterResult){
                case JOptionPane.YES_OPTION:
                    useFilter = true;
                    break;
                case JOptionPane.NO_OPTION:
                    useFilter = false;
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
            DuplicatesFrame duplicatesFrame = new DuplicatesFrame(file, useFilter);
        }
    }
}
