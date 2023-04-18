package anthony.rappo.entretien.CirilGroup.model;

public class Forest {
    private Tree[][] trees;
    private float probability;

    public Forest() {
    }

    public Tree[][] getTrees() {
        return trees;
    }
    public void setTrees(Tree[][] trees) {
        this.trees = trees;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    

    
}
