package View;

import acdc.Services.ErrorLogging;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class ErrorsFrame extends JFrame {
    public ErrorsFrame(){
        ArrayList<String> logs = ErrorLogging.getInstance().getLogs();
        Object[] columns = {"Description"};
        Object[][] data = new Object[logs.size()][];
        Object[] currentData = new Object[1];
        for (int cpt = 0; cpt < logs.size(); cpt++) {
            currentData[0] = logs.get(cpt);
            data[cpt] = currentData;
        }
        TableModel tableModel = new DefaultTableModel(data, columns);
        JTable jTable = new JTable(tableModel);
        this.add(new JScrollPane(jTable));
    }
}
