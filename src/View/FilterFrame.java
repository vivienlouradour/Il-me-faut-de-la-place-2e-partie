package View;

import Model.AppModel;
import View.Components.JListExtension;
import acdc.Core.Utils.Filter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FilterFrame extends JFrame {
    private Filter appFilter;
    private int separatorHeight = 10;

    //Filtres sur poids
    private JComboBox<String> unitePoids;
    private JComboBox<String> typeFiltrePoids;
    private JSpinner spinnerPoids;

    //Filtres sur Extentions
    private JListExtension acceptedExtensionsPanel;
    private JListExtension refusedExtensionsPanel;

    //Filtre sur regex
    private JTextField regexTxtField;

    public FilterFrame(){
        super("Gestion des filtres");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.appFilter = AppModel.getInstance().getFilter();
        this.add(Box.createVerticalStrut(this.separatorHeight));
        this.add(createWeightPanel());
        this.add(Box.createVerticalStrut(this.separatorHeight));
        this.add(createAcceptedExtension());
        this.add(Box.createVerticalStrut(this.separatorHeight));
        this.add(createRefusedExtension());
        this.add(Box.createVerticalStrut(this.separatorHeight));
        this.add(createRegexPanel());
        this.add(Box.createVerticalStrut(this.separatorHeight));
        this.add(createButtonsPanel());
    }

    private JPanel createRegexPanel() {
        JPanel regexPanel = new JPanel(new BorderLayout());
        regexPanel.setMaximumSize(new Dimension(600,80));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Expression régulière");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        regexPanel.setBorder(new CompoundBorder(titledBorder, new EmptyBorder(10,10,10,10)));

        JLabel label = new JLabel("Expression : ");
        regexPanel.add(label, BorderLayout.WEST);
        this.regexTxtField = new JTextField();
        this.regexTxtField.setText(appFilter.getPattern());
        regexPanel.add(this.regexTxtField, BorderLayout.CENTER);

        return regexPanel;
    }

    private JPanel createAcceptedExtension() {
        this.acceptedExtensionsPanel = new JListExtension(appFilter.getAcceptedExtensions());
        acceptedExtensionsPanel.setMaximumSize(new Dimension(600,80));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Extensions autorisées");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        this.acceptedExtensionsPanel.setBorder(new CompoundBorder(titledBorder, new EmptyBorder(10,10,10,10)));

        return this.acceptedExtensionsPanel;
    }

    private JPanel createRefusedExtension() {
        this.refusedExtensionsPanel = new JListExtension(appFilter.getRefusedExtensions());
        this.refusedExtensionsPanel.setMaximumSize(new Dimension(600,80));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Extensions refusées");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        this.refusedExtensionsPanel.setBorder(new CompoundBorder(titledBorder, new EmptyBorder(10,10,10,10)));

        return refusedExtensionsPanel;
    }

    private JPanel createWeightPanel(){
        JPanel weightJPanel = new JPanel();
        weightJPanel.setMaximumSize(new Dimension(600,80));
        weightJPanel.setLayout(new GridLayout(1,4));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Taille des fichiers");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        weightJPanel.setBorder(new CompoundBorder(titledBorder, new EmptyBorder(10,10,10,10)));

        String[] typeFiltrePoids = new String[] {"supérieur à", "inférieur à"};
        String[] unitePoidsPattern = new String[]{"o", "Ko", "Mo", "Go"};
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(0L, 0L, Long.MAX_VALUE, 1L);

        JLabel labelPoids = new JLabel("Taille (0 = désactivé) : ");
        this.spinnerPoids = new JSpinner(spinnerNumberModel);
        this.typeFiltrePoids = new JComboBox<>(typeFiltrePoids);
        this.unitePoids = new JComboBox<>(unitePoidsPattern);
        if(appFilter.getWeight() != null)
            this.spinnerPoids.setValue(appFilter.getWeight());

        weightJPanel.add(labelPoids);
        weightJPanel.add(this.typeFiltrePoids);
        weightJPanel.add(spinnerPoids);
        weightJPanel.add(unitePoids);

        return weightJPanel;
    }

    private JPanel createButtonsPanel(){
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        JButton acceptButton = new JButton("Ok");
        acceptButton.addActionListener(new AcceptButtonControl());
        buttonsPanel.add(acceptButton);
        buttonsPanel.add(cancelButton);
        return buttonsPanel;
    }

    private class AcceptButtonControl implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // weight
            long nbOctet;
            try {
                nbOctet = (long) ((double) spinnerPoids.getValue());
            }
            catch (ClassCastException ex){
                nbOctet = (long)spinnerPoids.getValue();
            }
            nbOctet *= getWeightFactor(unitePoids);

            if(nbOctet == 0)
                appFilter.setWeight(null);

            if(typeFiltrePoids.getSelectedItem().equals("supérieur à"))
                appFilter.GtWeight(nbOctet);
            else
                appFilter.LwWeight(nbOctet);

            //Accepted extensions
            ArrayList<String> acceptedExtensions = acceptedExtensionsPanel.getExtensions();
            appFilter.setAcceptedExtensions(acceptedExtensions);

            //Refused extensions
            appFilter.setRefusedExtensions(refusedExtensionsPanel.getExtensions());

            //Regex
            appFilter.setPattern(regexTxtField.getText());

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
