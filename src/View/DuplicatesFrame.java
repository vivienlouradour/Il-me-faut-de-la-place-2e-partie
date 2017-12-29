package View;

import Model.AppModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DuplicatesFrame extends JFrame {

    /**
     *
     * @param file répertoire à partir duquel chercher les doublons
     */
    public DuplicatesFrame(File file){
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
        this.setVisible(true);
    }

}
