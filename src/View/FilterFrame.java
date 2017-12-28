package View;

import Model.AppModel;
import acdc.Core.Utils.Filter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterFrame extends JFrame {
    //Filtres sur poids
    private JComboBox<String> unitePoids;
    private JComboBox<String> typeFiltrePoids;
    private JSpinner spinner;

    public FilterFrame(){
        super("Gestion des filtres");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(createWeightPanel());
        this.add(createButtonsPanel());
    }

    private JPanel createWeightPanel(){
        JPanel weightJPanel = new JPanel();
        weightJPanel.setMaximumSize(new Dimension(600,80));
        weightJPanel.setLayout(new GridLayout(1,4));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Filtre sur la taille des fichiers");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        weightJPanel.setBorder(titledBorder);

        String[] typeFiltrePoids = new String[] {"supérieur à", "inférieur à"};
        String[] unitePoidsPattern = new String[]{"o", "Ko", "Mo", "Go"};
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1L);

        JLabel labelPoids = new JLabel("Taille (0 = désactivé) : ");
        this.spinner = new JSpinner(spinnerNumberModel);
        this.typeFiltrePoids = new JComboBox<>(typeFiltrePoids);
        this.unitePoids = new JComboBox<>(unitePoidsPattern);

        weightJPanel.add(labelPoids);
        weightJPanel.add(this.typeFiltrePoids);
        weightJPanel.add(spinner);
        weightJPanel.add(unitePoids);


        return weightJPanel;
    }

    private JPanel createButtonsPanel(){
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JButton cancelButton = new JButton("Annuler");
        JButton acceptButton = new JButton("Ok");
        acceptButton.addActionListener(new AcceptButtonControl());
        buttonsPanel.add(acceptButton);
        buttonsPanel.add(cancelButton);
        return buttonsPanel;
    }

    private class AcceptButtonControl implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Filter appFilter = AppModel.getInstance().getFilter();

            long nbOctet = (long)((double) spinner.getValue());
            nbOctet *= getWeightFactor(unitePoids);


            if(typeFiltrePoids.getSelectedItem().equals("supérieur à"))
                appFilter.GtWeight(nbOctet);
            else
                appFilter.LwWeight(nbOctet);

            setVisible(false);
            dispose();
        }

        private int getWeightFactor(JComboBox<String> unitePoids){
            switch ((String)unitePoids.getSelectedItem()){
                case "o" :
                    return 1;
                case "Ko":
                    return 1024;
                case "Mo" :
                    return 1048576;
                case "Go" :
                    return 1073741824;
                default:
                    return 0;
            }
        }
    }

}
