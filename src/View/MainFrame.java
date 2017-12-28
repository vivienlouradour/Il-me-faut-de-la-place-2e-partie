package View;

import Controller.*;
import Model.AppModel;
import Model.LabelsInfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private JTree jTree;
    private JPanel jPanelGauche;
    private LabelsInfo labelsInfo;
    private JPanel jPanelPrincipal;
    private JPanel jPanelBas;


    public MainFrame(){
        super("Il me faut de la place");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        //Init
        this.labelsInfo = new LabelsInfo();
        this.jPanelPrincipal = new JPanel(new GridLayout(1,2));
        this.jPanelBas = new JPanel();
        this.jPanelBas.setLayout(new BoxLayout(this.jPanelBas, BoxLayout.X_AXIS));

        this.creerPanneauGauche();
        this.creerPanneauDroite();
        this.creerMenu();
        this.creerPanneauBas();

        this.getContentPane().add(this.jPanelPrincipal, BorderLayout.CENTER);
        this.getContentPane().add(this.jPanelBas, BorderLayout.SOUTH);

        this.pack();
    }

    private void creerPanneauBas() {
        JButton buttonScan = new JButton("Scan répertoire");
        JTreeController jTreeController = new JTreeController(this.jTree, this.jPanelGauche);
        buttonScan.addActionListener(jTreeController);

        JButton buttonFilter = new JButton("Filtres");
        FilterController filterController = new FilterController();
        buttonFilter.addActionListener(filterController);

        JButton buttonDuplicates = new JButton("Doublons");
        DuplicatesController duplicatesController = new DuplicatesController();
        buttonDuplicates.addActionListener(duplicatesController);

        JButton buttonSettings = new JButton("Paramètres");
        SettingsController settingsController = new SettingsController();
        buttonSettings.addActionListener(settingsController);

        this.jPanelBas.add(buttonScan);
        this.jPanelBas.add(buttonFilter);
        this.jPanelBas.add(buttonDuplicates);
        this.jPanelBas.add(buttonSettings);
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
        this.jPanelPrincipal.add(jScrollPane);
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
        this.jPanelPrincipal.add(jPanelGauche);
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

        JMenuItem filterMenuItem = new JMenuItem("Filtres");
        mfile.add(filterMenuItem);
        FilterController filterController = new FilterController();
        filterMenuItem.addActionListener(filterController);

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