package anthony.rappo.entretien.CirilGroup.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import anthony.rappo.entretien.CirilGroup.model.Forest;
import anthony.rappo.entretien.CirilGroup.model.Tree;
import anthony.rappo.entretien.CirilGroup.model.enumerations.TreeState;
import anthony.rappo.entretien.CirilGroup.service.interfaces.ISimulationService;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    @Autowired
    private ISimulationService simulationService;
    
    /**
     * Return a Tree[][] corresponding to the next step of the fire propagation.
     * Same Tree[][] is returned if the no Trees are in fire.
     * @param forest With non null trees and 0<=probability<=1
     */
    @PostMapping("/step")
    public Tree[][] nextStep(@RequestBody Forest forest){
        if(!isForestOk(forest)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, invalid parameters");
        }
        simulationService.nextStep(forest);
        return forest.getTrees();
    }

    /**
     * Return a Tree[][] corresponding to the last step of the fire propagation.
     * Same Tree[][] is returned if the no Trees are in fire.
     * @param forest With non null trees and 0<=probability<=1
     */
    @PostMapping("/steps")
    public Tree[][] allSteps(@RequestBody Forest forest){
        if(!isForestOk(forest)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, invalid parameters");
        }
        simulationService.allSteps(forest);
        return forest.getTrees();
    }

    private boolean isForestOk(Forest forest){
        return (Objects.nonNull(forest) && Objects.nonNull(forest.getTrees()) && forest.getProbability()>=0 && forest.getProbability()<=1);
    }

}
