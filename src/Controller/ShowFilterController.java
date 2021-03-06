package Controller;

import View.FilterFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowFilterController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        FilterFrame filterFrame = new FilterFrame();
        filterFrame.setSize(new Dimension(600,700));
        filterFrame.setMaximumSize(new Dimension(600,700));
        filterFrame.setLocationRelativeTo(null);
        filterFrame.setVisible(true);
        filterFrame.setResizable(false);
    }
}
