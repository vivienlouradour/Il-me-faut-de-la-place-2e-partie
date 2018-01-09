package View.Components;


import Controller.JTableMouseController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JFileTable extends JTable {
    private static String[] entetes = {"Type", "Nom", "Chemin absolu", "Taille (Ko)", "Dernière modification"};

    public JFileTable(){
        super();
        ArrayList<Object[]> data = new ArrayList<>();

        this.changeData(data);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItemCopy = new JMenuItem("Copier la valeur");
        JMenuItem menuItemOpenExplorer = new JMenuItem("Ouvrir le fichier");
        JMenuItem menuItemDelete = new JMenuItem("Supprimer le fichier");
        menuItemDelete.addActionListener( new JTableMouseController());
        menuItemCopy.addActionListener(new JTableMouseController());
        menuItemOpenExplorer.addActionListener((new JTableMouseController()));
        popup.add(menuItemCopy);
        popup.add(menuItemOpenExplorer);
        popup.add(menuItemDelete);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    JTable source = (JTable)e.getSource();
                    int row = source.rowAtPoint( e.getPoint() );
                    int column = source.columnAtPoint( e.getPoint() );

                    if (! source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    JTable source = (JTable)e.getSource();
                    int row = source.rowAtPoint( e.getPoint() );
                    int column = source.columnAtPoint( e.getPoint() );

                    if (! source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public static Object[] getLine(File file, long size){
        Object[] line = {
                file.isDirectory() ? "Répertoire" : "Fichier",
                file.getName(),
                file.getAbsolutePath(),
                new Double(size / 1024.0),
                new Date(file.lastModified())
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

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        this.setModel(tableModel);
        if(data.size() > 0)
            this.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
    }



    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getPreferredSize().width < getParent().getWidth();
    }




}
