package dyf;

public class TreeNode {
    private String key;
    private float splitValue;
    private TreeNode lessThanSubTree;
    private TreeNode greatThanSubTree;
    private int leafIndex = -1;
    private float weight;

    public TreeNode() {
        super();
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getLeafIndex() {
        return leafIndex;
    }

    public void setLeafIndex(int leafIndex) {
        this.leafIndex = leafIndex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(float splitValue) {
        this.splitValue = splitValue;
    }

    public TreeNode getLessThanSubTree() {
        return lessThanSubTree;
    }

    public void setLessThanSubTree(TreeNode lessThanSubTree) {
        this.lessThanSubTree = lessThanSubTree;
    }

    public TreeNode getGreatThanSubTree() {
        return greatThanSubTree;
    }

    public void setGreatThanSubTree(TreeNode greatThanSubTree) {
        this.greatThanSubTree = greatThanSubTree;
    }

    public void printTree(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(key + "," + splitValue + "," + weight);
        if (null != lessThanSubTree) {
            lessThanSubTree.printTree(level + 1);
        }
        if (null != greatThanSubTree) {
            greatThanSubTree.printTree(level + 1);
        }
    }
}
