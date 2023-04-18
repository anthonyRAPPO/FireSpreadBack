package anthony.rappo.entretien.CirilGroup.model.enumerations;

public enum TreeState {
    FIRE{
        @Override
        public TreeState nextState() {
            return EXTINCT;
        }
    },
    INIT_FIRE{
        @Override
        public TreeState nextState() {
            return FIRE;
        }
    },
    EXTINCT{
        @Override
        public TreeState nextState() {
            return EXTINCT;
        }
    },
    INITIAL{
        @Override
        public TreeState nextState() {
            return INIT_FIRE;
        }
    };  

    public abstract TreeState nextState(); 
}
