package anthony.rappo.entretien.CirilGroup.model;

import anthony.rappo.entretien.CirilGroup.model.enumerations.TreeState;

public class Tree {

    private TreeState state;

    public Tree() {
    }

    public Tree(TreeState state) {
        this.state = state;
    }

    public TreeState getState() {
        return state;
    }

    public void nextState() {
        this.state = state.nextState();
    }

}
