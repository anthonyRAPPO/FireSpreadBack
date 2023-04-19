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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tree other = (Tree) obj;
        if (state != other.state)
            return false;
        return true;
    }

    

}
