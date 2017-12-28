package Model;

import javax.swing.*;

public class LabelsInfo {
    public JLabel jLabelNom;
    public JLabel jLabelAbsolutePath;
    public JLabel jLabelSize;
    public JLabel jLabelIsDirectory;
    public JLabel jLabelLastModification;

    public LabelsInfo(){
        this.jLabelAbsolutePath = new JLabel();
        this.jLabelIsDirectory = new JLabel();
        this.jLabelLastModification = new JLabel();
        this.jLabelNom = new JLabel();
        this.jLabelSize = new JLabel();
    }
}
