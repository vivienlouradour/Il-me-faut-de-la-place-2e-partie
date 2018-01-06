package Controller;

import Model.AppModel;
import acdc.TreeDataModel.File1;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

public class JTreeMouseListener implements MouseListener{
    private JTree jTree;
    private SimpleDateFormat dateFormat;

    public JTreeMouseListener(JTree jTree){
        this.jTree = jTree;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selRow = jTree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
        if (selRow != -1) {
            if (e.getClickCount() == 1) {
                jTree.setSelectionRow(selRow);
                File1 selectedNode = (File1) selPath.getLastPathComponent();
                //changeLabels(selectedNode);
                AppModel.getInstance().setSelectedFile(selectedNode);
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
