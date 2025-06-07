package syntacticTree;

import java.util.List;
import java.util.ArrayList;

public class SyntacticTree {
    String label;
    List<SyntacticTree> children;

    public SyntacticTree(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    public void addChild(SyntacticTree child) {
        children.add(child);
    }

    public void print(String prefix) {
        System.out.println(prefix + label);
        for (SyntacticTree child : children) {
            child.print(prefix + "  ");
        }
    }
}