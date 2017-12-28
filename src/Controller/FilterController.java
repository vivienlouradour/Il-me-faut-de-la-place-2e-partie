package Controller;

import View.FilterFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        FilterFrame filterFrame = new FilterFrame();
        filterFrame.setSize(new Dimension(600,800));
        filterFrame.setVisible(true);
    }
}
