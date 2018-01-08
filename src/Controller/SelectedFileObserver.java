package Controller;

import Model.AppModel;
import Model.Notifications;
import View.Components.JFileTable;
import acdc.TreeDataModel.File1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

public class SelectedFileObserver implements Observer {
    private JFileTable jTable;
    private SimpleDateFormat dateFormat;

    public SelectedFileObserver(JFileTable jTable){
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
        ArrayList<Object[]> data = new ArrayList<>();
        if(selectedFile.isDirectory){
            Enumeration<File1> enumeration = selectedFile.children();
            File1 currentNode;
            while (enumeration.hasMoreElements()){
                currentNode = enumeration.nextElement();
                data.add(JFileTable.getLine(new File(currentNode.absolutePath), currentNode.weight));
            }
        }
        else {
            data.add(JFileTable.getLine(new File((selectedFile.absolutePath)), selectedFile.weight));
        }
        this.jTable.changeData(data);
    }
}
