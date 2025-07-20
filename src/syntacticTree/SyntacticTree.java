package syntacticTree;

import java.util.List;
import java.util.ArrayList;

public class SyntacticTree {
    public String label;
    public List<SyntacticTree> children;

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

    public String getSecondWord() {
        if (this.children.size() >= 2) {
            return this.children.get(1).label;
        }
        return null;
    }

    public String getLastLeaf() {
        if (this.children.isEmpty()) {
            return this.label; 
        }
        return this.children.get(this.children.size() - 1).getLastLeaf();
    }
}
