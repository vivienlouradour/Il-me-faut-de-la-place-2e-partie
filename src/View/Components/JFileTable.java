package View.Components;


import acdc.TreeDataModel.File1;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

public class JFileTable extends JTable {
    private static String[] entetes = {"Type", "Nom", "Chemin absolu", "Taille", "Dernière modification"};

    public JFileTable(){
        super();
        ArrayList<Object[]> data = new ArrayList<>();

        this.changeData(data);
        this.setEnabled(false);
    }

    public static Object[] getLine(File file, long size){
        Object[] line = {
                file.isDirectory() ? "Répertoire" : "Fichier",
                file.getName(),
                file.getAbsolutePath(),
                new Long(size),
                new Date(file.lastModified())
        };
        return line;
    }

    public static Object[] getEmptyLine(){
        Object[] line = {
                "",
                "",
                "",
                new Long(0),
                new Date()
        };
        return line;
    }

    public void changeData(ArrayList<Object[]> data){

        Object[][] dataArray = new Object[data.size()][];
        for(int cpt = 0; cpt < data.size(); cpt++)
            dataArray[cpt] = data.get(cpt);
        DefaultTableModel tableModel = new DefaultTableModel(dataArray, entetes){
            @Override
            public Class getColumnClass(int column) {
                if(this.getRowCount() == 0)
                    return String.class;
                return getValueAt(0, column).getClass();
            }
        };

        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                if( value instanceof Date) {
                    value = f.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
            }
        };

        this.setAutoCreateRowSorter(true);
        this.setEnabled(false);
        this.setModel(tableModel);
        if(!data.isEmpty()) {
            this.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
//            this.tweakColumns(this);
            this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);// AUTO_RESIZE_ALL_COLUMNS);
        }
    }

    private void tweakColumns(JTable table){
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();

        int required = 0;
        while(columns.hasMoreElements()){
            TableColumn column = columns.nextElement();
            int width = (int)table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, column.getIdentifier()
                            , false, false, -1, column.getModelIndex()).getPreferredSize().getWidth();
            required += width;
        }

        JViewport viewport = (JViewport)SwingUtilities.getAncestorOfClass(JViewport.class, table);
        int viewportWidth = viewport.getWidth();
        table.setAutoResizeMode(required<viewportWidth ? JTable.AUTO_RESIZE_ALL_COLUMNS : JTable.AUTO_RESIZE_OFF);
    }




}
