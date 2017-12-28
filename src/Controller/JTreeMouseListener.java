package Controller;

import Model.LabelsInfo;
import acdc.TreeDataModel.File1;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

public class JTreeMouseListener implements MouseListener{
    private JTree jTree;
    private LabelsInfo labelsInfo;
    private SimpleDateFormat dateFormat;

    public JTreeMouseListener(JTree jTree, LabelsInfo labelsInfo){
        this.jTree = jTree;
        this.labelsInfo = labelsInfo;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selRow = jTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {
                        jTree.setSelectionRow(selRow);
                        File1 selectedNode = (File1) selPath.getLastPathComponent();
                        this.labelsInfo.jLabelIsDirectory.setText("Type : " + (selectedNode.isDirectory ? "répertoire" : "fichier"));
                        this.labelsInfo.jLabelNom.setText("Nom : " + selectedNode.filename);
                        this.labelsInfo.jLabelAbsolutePath.setText("Chemin complet : " + selectedNode.absolutePath);
                        this.labelsInfo.jLabelSize.setText("Taille : " + selectedNode.weight/1000000.0 + " Mo");
                        this.labelsInfo.jLabelLastModification.setText("Dernière modification : " + dateFormat.format(selectedNode.lastModifiedTime.toMillis()));
                    }
                }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
