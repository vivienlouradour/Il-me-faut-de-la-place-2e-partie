package View;

import Model.AppModel;
import View.Components.JFileTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
            this.getContentPane().add(new JScrollPane(createPanel()));
        }
        catch (IOException ex){
            System.err.println(ex.getMessage());
        }
        this.setMinimumSize(new Dimension(850,350));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ArrayList<JFileTable> fileTables = new ArrayList<>();

        for (ConcurrentLinkedQueue<File> files : duplicates.values()) {
            JPanel jPanel1 = new JPanel(new BorderLayout());
            JScrollPane jScrollPane = new JScrollPane(createDuplicatesTable(files));
            jPanel1.add(jScrollPane, BorderLayout.CENTER);
            jPanel1.setPreferredSize(new Dimension(800, 100));
            jPanel1.setMinimumSize(new Dimension(800,100));
            jPanel.add(jPanel1);
        }
        return jPanel;
    }

    private JFileTable createDuplicatesTable(ConcurrentLinkedQueue<File> files){
        JFileTable duplicatesTable = new JFileTable();
        ArrayList<Object[]> data = new ArrayList<>();

        for (File duplicate  : files) {
            data.add(JFileTable.getLine(duplicate, duplicate.length()));
        }

        duplicatesTable.changeData(data);
        return duplicatesTable;
    }

}
