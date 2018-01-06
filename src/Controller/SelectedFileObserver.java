package Controller;

import Model.AppModel;
import Model.Notifications;
import acdc.TreeDataModel.File1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

public class SelectedFileObserver implements Observer {
    private JTable jTable;
    private SimpleDateFormat dateFormat;

    public SelectedFileObserver(JTable jTable){
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.jTable = jTable;
    }
    @Override
    public void update(Observable o, Object arg) {
        Notifications notif = (Notifications)arg;
        if(notif == Notifications.SelectedFileChange){
            File1 selectedFile = AppModel.getInstance().getSelectedFile();
            updateTable(selectedFile);
        }
    }

    private void updateTable(File1 selectedFile){
        String[] entetes = {"Type", "Nom", "Chemin absolu", "Taille", "Dernière modification"};
        ArrayList<String> currentData;
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        if(selectedFile.isDirectory){
            Enumeration<File1> enumeration = selectedFile.children();
            File1 currentNode;
            while (enumeration.hasMoreElements()){
                currentData = new ArrayList<>();
                currentNode = enumeration.nextElement();
                currentData.add(currentNode.isDirectory ? "Répertoire" : "Fichier");
                currentData.add(currentNode.filename);
                currentData.add(currentNode.absolutePath);
                currentData.add(String.valueOf(currentNode.weight));
                currentData.add(String.valueOf(dateFormat.format(selectedFile.lastModifiedTime.toMillis())));
                data.add(currentData);
            }
        }
        else {
            currentData = new ArrayList<>();
            currentData.add("Fichier");
            currentData.add(selectedFile.filename);
            currentData.add(selectedFile.absolutePath);
            currentData.add(String.valueOf(selectedFile.weight));
            currentData.add(String.valueOf(dateFormat.format(selectedFile.lastModifiedTime.toMillis())));
            data.add(currentData);
        }

        DefaultTableModel tableModel = new DefaultTableModel(entetes, 0);
        data.forEach(row -> tableModel.addRow(row.toArray()));
        this.jTable.setModel(tableModel);
    }
}
