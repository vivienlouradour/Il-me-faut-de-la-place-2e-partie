package Controller;

import Model.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DuplicatesController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog((JMenuItem)e.getSource());

        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            AppModel appModel = AppModel.getInstance();
            ConcurrentHashMap<String, ConcurrentLinkedQueue<File>> duplicates;
            try {
                duplicates = appModel.getFileTree().collectDuplicates(file.getAbsolutePath(), appModel.getFilter(), appModel.getParallelism());
                for (ConcurrentLinkedQueue<File> files : duplicates.values()) {
                    System.out.println("*************");
                    for (File duplicate  : files) {
                        System.out.println("duplicate = " + duplicate);
                    }
                }
            }
            catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
