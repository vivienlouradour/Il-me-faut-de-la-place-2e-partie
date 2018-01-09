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
import java.net.URI;

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
        this.jPanelBas = new JPanel(new BorderLayout());
        //this.jPanelBas.setLayout(new BoxLayout(this.jPanelBas, BoxLayout.X_AXIS));

        this.jTable = new JFileTable();
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
        JPanel panelBtnGauche = new JPanel();
        panelBtnGauche.setLayout(new BoxLayout(panelBtnGauche, BoxLayout.X_AXIS));

        JButton buttonScan = new JButton("Scan répertoire");
        JTreeController jTreeController = new JTreeController(this.jTree, this.jPanelGauche);
        buttonScan.addActionListener(jTreeController);

        JButton buttonFilter = new JButton("Filtres");
        ShowFilterController showFilterController = new ShowFilterController();
        buttonFilter.addActionListener(showFilterController);

        JButton buttonDuplicates = new JButton("Doublons");
        DuplicatesController duplicatesController = new DuplicatesController();
        buttonDuplicates.addActionListener(duplicatesController);

        JButton buttonErrors = new JButton("Log erreurs");
        buttonErrors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErrorsFrame errorsFrame = new ErrorsFrame();
                errorsFrame.setVisible(true);
            }
        });


        panelBtnGauche.add(buttonScan);
        panelBtnGauche.add(buttonFilter);
        panelBtnGauche.add(buttonDuplicates);
        //panelBtnGauche.add(buttonErrors);


        JPanel panelBtnDroit = new JPanel();
        panelBtnDroit.setLayout(new BoxLayout(panelBtnDroit, BoxLayout.X_AXIS));

        JButton buttonZoomIn = new JButton("+");
        buttonZoomIn.addActionListener(new JTableZoomController(this.jTable, true));
        JButton buttonZoomOut = new JButton("-");
        buttonZoomOut.addActionListener(new JTableZoomController(this.jTable, false));
        panelBtnDroit.add(buttonZoomIn);
        panelBtnDroit.add(buttonZoomOut);

        this.jPanelBas.add(panelBtnGauche, BorderLayout.WEST);
        this.jPanelBas.add(panelBtnDroit, BorderLayout.EAST);

    }


    private void creerPanneauDroite() {
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
        JMenu mHelp = new JMenu("Aide");
        jmb.add(mfile);
        jmb.add(mHelp);

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

        mfile.addSeparator();
        JMenuItem quitter = new JMenuItem("Quitter");
        mfile.add(quitter);
        quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem githubMenuItem = new JMenuItem("GitHub Repository");
        mHelp.add(githubMenuItem);
        githubMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String githubLink = "https://github.com/vivienlouradour/Il-me-faut-de-la-place-2e-partie";
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(githubLink));
                    }
                    catch (Exception ex){}
                }
            }
        });
    }
}