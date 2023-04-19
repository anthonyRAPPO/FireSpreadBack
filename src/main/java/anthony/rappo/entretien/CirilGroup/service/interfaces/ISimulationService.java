package anthony.rappo.entretien.CirilGroup.service.interfaces;

import anthony.rappo.entretien.CirilGroup.model.Forest;

public interface ISimulationService {

    boolean nextStep(Forest forest);

    void allSteps(Forest forest);
    
}
