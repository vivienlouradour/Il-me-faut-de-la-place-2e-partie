package View;

import Controller.*;
import Model.AppModel;
import View.Components.JFileTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private JTree jTree;
    private JPanel jPanelGauche;
    private JPanel jPanelDroite;
    private JPanel jPanelPrincipal;
    private JPanel jPanelBas;
    private JFileTable jTable;
    private JSplitPane jSplitPanePrincipal;


    public MainFrame(){
        super("Il me faut de la place");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        //Init
        this.jPanelPrincipal = new JPanel(new GridLayout(1,2));
        this.jPanelBas = new JPanel();
        this.jPanelBas.setLayout(new BoxLayout(this.jPanelBas, BoxLayout.X_AXIS));

        this.jTable = new JFileTable();
        this.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.creerPanneauGauche();
        this.creerPanneauDroite();
        SelectedFileObserver selectedFileObserver = new SelectedFileObserver(this.jTable);
        AppModel.getInstance().addObserver(selectedFileObserver);
        this.jSplitPanePrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.jPanelGauche, this.jPanelDroite);
        this.jSplitPanePrincipal.setOneTouchExpandable(true);
        this.jSplitPanePrincipal.setResizeWeight(0.5);
        this.jPanelPrincipal.add(jSplitPanePrincipal);
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
        ShowFilterController showFilterController = new ShowFilterController();
        buttonFilter.addActionListener(showFilterController);

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
        this.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        JScrollPane jScrollPane = new JScrollPane(this.jTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.jPanelDroite = new JPanel();
        this.jPanelDroite.setLayout(new BorderLayout());
        jPanelDroite.add(jScrollPane, BorderLayout.CENTER);
        //this.jPanelPrincipal.add(jScrollPane);
        //this.jSplitPanePrincipal.add(jScrollPane);
    }


    void creerPanneauGauche(){
        this.jPanelGauche = new JPanel();
        this.jPanelGauche.setLayout(new BorderLayout());
        this.jTree = new JTree();
        JTreeMouseListener jTreeMouseListener = new JTreeMouseListener(this.jTree);
        this.jTree.addMouseListener(jTreeMouseListener);

        this.jTree.setModel(null);
        JScrollPane treeView = new JScrollPane(this.jTree);
        jPanelGauche.add(treeView, BorderLayout.CENTER);
        jPanelGauche.setBorder(new EmptyBorder(10,10,10,10));
        //this.jPanelPrincipal.add(jPanelGauche);
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
        ShowFilterController showFilterController = new ShowFilterController();
        filterMenuItem.addActionListener(showFilterController);

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