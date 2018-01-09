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
        int result = fileChooser.showOpenDialog((Component) e.getSource());

        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            DuplicatesFrame duplicatesFrame = new DuplicatesFrame(file);
        }
    }
}
