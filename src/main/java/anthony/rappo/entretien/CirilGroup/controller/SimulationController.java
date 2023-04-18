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
    
    @GetMapping("/test")
    public void test() {
        Forest f = new Forest();
        f.setProbability(0.5f);
        Tree[][] trees = new Tree[5][5];
        for(int y=0;y<5;y++){
            for(int x=0;x<5;x++){
                trees[y][x] = new Tree(TreeState.INITIAL);
            }
        }
        trees[1][3] = new Tree(TreeState.FIRE);
        f.setTrees(trees);
        displayTrees(f.getTrees());
        nextStep(f);
        displayTrees(f.getTrees());
        nextStep(f);
        displayTrees(f.getTrees());
        nextStep(f);
        displayTrees(f.getTrees());
        nextStep(f);
        displayTrees(f.getTrees());
       
    }

    private void displayTrees(Tree[][] trees) {
        for(int y=0;y<5;y++){
            for(int x=0;x<5;x++){
                System.out.print(trees[y][x].getState().name().charAt(0)+" ");
            }
            System.out.println("");
        }
        System.out.println("//////////////////////////////");
    }

    @PostMapping("/step")
    public void nextStep(@RequestBody Forest forest){
        if(!isForestOk(forest)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, invalid parameters");
        }
        simulationService.nextStep(forest);
    }

    private boolean isForestOk(Forest forest){
        return (Objects.nonNull(forest.getTrees()) && forest.getProbability()>=0 && forest.getProbability()<=1);
    }

}
