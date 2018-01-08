package Controller;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class JTableZoomController implements ActionListener {
    private JTable table;
    private boolean zoomIn;

    public JTableZoomController(JTable jTable, boolean zoomIn){
        this.table = jTable;
        this.zoomIn = zoomIn;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Font oldFont = table.getFont();
        float size = oldFont.getSize() + (this.zoomIn ? +2 : -2);
        table.setFont(oldFont.deriveFont(size));

        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight(row);

            for (int col = 0; col < table.getColumnCount(); col++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                TableColumn column = table.getColumnModel().getColumn(col);
                int colWidth = column.getWidth();

                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                colWidth = comp.getPreferredSize().width;

                column.setPreferredWidth(colWidth);
            }
            table.setRowHeight(row, rowHeight);
        }
    }
}
