package View;

import Controller.DuplicatesController;
import Controller.JTreeController;
import Controller.JTreeMouseListener;
import Controller.SettingsController;
import Model.AppModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private JTree jTree;
    private JPanel jPanelGauche;
    private GridBagConstraints gridBagConstraints;
    private LabelsInfo labelsInfo;


    public MainFrame(){
        super("Il me faut de la place");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(1,2));
        this.gridBagConstraints = new GridBagConstraints();

        //Init
        this.labelsInfo = new LabelsInfo();

        this.creerPanneauGauche();
        this.creerPanneauDroite();
        this.creerMenu();

        this.pack();
    }

    private void creerPanneauDroite() {
        JPanel jPanelDroite = new JPanel();
        jPanelDroite.setLayout(new BoxLayout(jPanelDroite, BoxLayout.Y_AXIS));
        //jPanelDroite.setPreferredSize(new Dimension(400,80));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Détails du fichier sélectionné");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jPanelDroite.setBorder(titledBorder);
        this.labelsInfo.jLabelNom.setText("Effectuez un scan d'un répertoire.");

        jPanelDroite.add(this.labelsInfo.jLabelNom);
        jPanelDroite.add(this.labelsInfo.jLabelAbsolutePath);
        jPanelDroite.add(this.labelsInfo.jLabelSize);
        jPanelDroite.add(this.labelsInfo.jLabelIsDirectory);
        jPanelDroite.add(this.labelsInfo.jLabelLastModification);
        JScrollPane jScrollPane = new JScrollPane(jPanelDroite);
        this.add(jScrollPane);
    }

    void creerPanneauGauche(){
        this.jPanelGauche = new JPanel();
        this.jPanelGauche.setLayout(new BorderLayout());
        this.jTree = new JTree();
        JTreeMouseListener jTreeMouseListener = new JTreeMouseListener(this.jTree, this.labelsInfo);
        this.jTree.addMouseListener(jTreeMouseListener);

        this.jTree.setModel(null);
        JScrollPane treeView = new JScrollPane(this.jTree);
        jPanelGauche.add(treeView, BorderLayout.CENTER);
        this.getContentPane().add(jPanelGauche);
    }

    void creerMenu() {
        JMenuBar jmb = new JMenuBar();
        this.setJMenuBar(jmb);
        JMenu mfile = new JMenu("Fichier");
        jmb.add(mfile);

        JMenuItem scanTreeMenuItem = new JMenuItem("Scanner un répertoire");
        mfile.add(scanTreeMenuItem);
        JTreeController jTreeController = new JTreeController(this.jTree, this.jPanelGauche);
        scanTreeMenuItem.addActionListener(jTreeController);
        AppModel.getInstance().addObserver(jTreeController);

        JMenuItem searchDuplicatesMenuItem = new JMenuItem("Rechercher les doublons");
        mfile.add(searchDuplicatesMenuItem);
        DuplicatesController duplicatesController = new DuplicatesController();
        searchDuplicatesMenuItem.addActionListener(duplicatesController);

        JMenuItem settingsMenuItem = new JMenuItem("Paramètres");
        mfile.add(settingsMenuItem);
        SettingsController settingsController = new SettingsController();
        settingsMenuItem.addActionListener(settingsController);

        mfile.addSeparator();
        JMenuItem quitter = new JMenuItem("Quitter");
        mfile.add(quitter);
        quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}