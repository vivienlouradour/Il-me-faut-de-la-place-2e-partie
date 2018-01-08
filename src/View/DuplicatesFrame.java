package View;

import Model.AppModel;
import View.Components.JFileTable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DuplicatesFrame extends JFrame {
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<File>> duplicates;
    private SimpleDateFormat dateFormat;
    /**
     *
     * @param file répertoire à partir duquel chercher les doublons
     */
    public DuplicatesFrame(File file){
        AppModel appModel = AppModel.getInstance();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            duplicates = appModel.getFileTree().collectDuplicates(file.getAbsolutePath(), appModel.getFilter(), appModel.getParallelism());
            JFileTable duplicatesTable = createDuplicatesTable();
            JScrollPane jScrollPane = new JScrollPane(duplicatesTable);
            this.getContentPane().add(jScrollPane);
        }
        catch (IOException ex){
            System.err.println(ex.getMessage());
        }
        this.setMinimumSize(new Dimension(400,350));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JFileTable createDuplicatesTable(){
        JFileTable duplicatesTable = new JFileTable();

        ArrayList<Object[]> data = new ArrayList<>();

        for (ConcurrentLinkedQueue<File> files : duplicates.values()) {
            for (File duplicate  : files) {
                data.add(JFileTable.getLine(duplicate, duplicate.length()));
            }
            data.add(JFileTable.getEmptyLine());
        }
        duplicatesTable.changeData(data);
        return duplicatesTable;
    }

}
