package View;

import Model.AppModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {
    private boolean multiThreadEnabled;
    JCheckBox checkBoxMultiThread;
    public SettingsFrame(){
        super("Il me faut de la place - Paramètres");
        this.setSize(new Dimension(600,200));
        this.setResizable(false);

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.multiThreadEnabled = AppModel.getInstance().getParallelism() == 2;

        JPanel multiThreadPanel = createMultiThreadPanel();
        this.add(multiThreadPanel);

        JPanel buttonsPanel = createButtonsPanel();
        this.add(buttonsPanel);
    }

    private JPanel createMultiThreadPanel(){
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(600,50));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("MultiThreading");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(new CompoundBorder(titledBorder, new EmptyBorder(10,10,10,10)));

        this.checkBoxMultiThread = new JCheckBox("MultiThreading (décochez cette case en cas de problème de stabilité)", this.multiThreadEnabled);
        panel.add(checkBoxMultiThread);
        return panel;
    }

    private JPanel createButtonsPanel(){
        JPanel panel = new JPanel();

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        JButton acceptButton = new JButton("Ok");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppModel.getInstance().setParallelism(checkBoxMultiThread.isSelected());
                setVisible(false);
                dispose();
            }
        });
        panel.add(acceptButton);
        panel.add(cancelButton);
        return panel;
    }


}
