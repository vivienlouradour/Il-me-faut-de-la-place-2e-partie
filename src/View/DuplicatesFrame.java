package View;

import Model.AppModel;
import View.Components.JFileTable;
import acdc.Core.Utils.Filter;
import acdc.Services.ErrorLogging;

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
    private boolean useFilters;
    /**
     *
     * @param file répertoire à partir duquel chercher les doublons
     */
    public DuplicatesFrame(File file, boolean useFilters){
        super("Il me faut de la place - Recherche de doublons en cours...");
        AppModel appModel = AppModel.getInstance();
        this.useFilters = useFilters;
        this.setMinimumSize(new Dimension(850,350));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            duplicates = appModel.getFileTree().collectDuplicates(file.getAbsolutePath(), useFilters ? appModel.getFilter() : Filter.createFilter(), appModel.getParallelism());
            this.setTitle("Il me faut de la place - Liste des doublons");
            this.getContentPane().add(new JScrollPane(createPanel()));
        }
        catch (IOException ex){
            ErrorLogging.getInstance().addLog("Erreur recherche doublons : " + ex.getMessage());
        }

    }

    private JPanel createPanel(){
        JPanel jPanel = new JPanel();
        if(duplicates.isEmpty()){
            jPanel.add(new JLabel("Aucun doublon détecté dans le répertoire indiqué" + (this.useFilters ? ", avec les filtres sélectionnés." : ".")));
            return jPanel;
        }
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
