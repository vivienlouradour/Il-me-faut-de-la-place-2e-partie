package Controller;

import Model.AppModel;
import acdc.TreeDataModel.File1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class JTableMouseController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem c = (JMenuItem)e.getSource();
        JPopupMenu popup = (JPopupMenu)c.getParent();
        JTable table = (JTable)popup.getInvoker();

        switch (c.getText()){
            case "Copier la valeur":
                copySelectedValue(table);
                break;
            case "Supprimer le fichier":
                deleteSelectedFile(table);
                break;
            case "Ouvrir le fichier":
                openExplorerOnSelectedFile(table);
                break;
            default:break;
        }
    }

    private void openExplorerOnSelectedFile(JTable jTable){
        String selectedFilePath = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
        try {
            Desktop.getDesktop().open(new File(selectedFilePath));
        }
        catch (IOException iae) {
            System.err.println("File To Open Not Found");
        }
    }
    private void copySelectedValue(JTable table){
        String value = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void deleteSelectedFile(JTable jTable){
        AppModel appModel = AppModel.getInstance();
        String selectedFilePath = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
        appModel.getFileTree().deleteFile(Paths.get(selectedFilePath));
        ((DefaultTableModel)jTable.getModel()).removeRow(jTable.getSelectedRow());
        TreeModel treeModel = appModel.getTree();
        if(treeModel != null) {
            String rootFilePath = ((File1)(treeModel.getRoot())).absolutePath;
            appModel.setTree(rootFilePath);
        }
    }
}
