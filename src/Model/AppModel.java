package Model;

import acdc.Core.FileTree;
import acdc.Core.Utils.Filter;
import acdc.TreeDataModel.File1;

import javax.swing.tree.TreeModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Observable;

public class AppModel extends Observable{
    private static AppModel instance;
    private FileTree fileTree;
    private TreeModel treeModel;
    private Filter filter;
    private int parallelism;
    private File1 selectedFile;

    private AppModel(){
        this.fileTree = FileTree.creerFileTree();
        this.filter = Filter.createFilter();
        this.parallelism = 2;
    }

    public static AppModel getInstance(){
        if(instance == null)
            instance = new AppModel();
        return instance;
    }

    public void setTree(String rootPath) {
        this.treeModel = this.fileTree.tree(rootPath, this.filter, this.parallelism);
        this.setChanged();
        this.notifyObservers(Notifications.TreeModelChange);
    }

    public File1 getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File1 selectedNode){
        this.selectedFile = selectedNode;
        this.setChanged();
        this.notifyObservers(Notifications.SelectedFileChange);
    }

    public FileTree getFileTree() {
        return fileTree;
    }

    public int getParallelism(){
        return this.parallelism;
    }

    public TreeModel getTree() {
        return this.treeModel;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void deleteFile(Path path) throws IOException{
        Files.delete(path);
        this.setChanged();
        this.notifyObservers(Notifications.FileDeleted);
    }
}
