package Model;

import acdc.Core.FileTree;
import acdc.Core.Utils.Filter;
import javax.swing.tree.TreeModel;
import java.util.Observable;

public class AppModel extends Observable{
    private static AppModel instance;
    private FileTree fileTree;
    private TreeModel treeModel;
    private Filter filter;
    private int parallelism;

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
        System.out.println("AppModel.setTree(" + rootPath + ")");
        this.setChanged();
        this.notifyObservers(Notifications.TreeModelChange);
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
}
