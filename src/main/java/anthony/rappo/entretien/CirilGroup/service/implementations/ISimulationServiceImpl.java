package anthony.rappo.entretien.CirilGroup.service.implementations;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import anthony.rappo.entretien.CirilGroup.model.Forest;
import anthony.rappo.entretien.CirilGroup.model.Tree;
import anthony.rappo.entretien.CirilGroup.model.enumerations.TreeState;
import anthony.rappo.entretien.CirilGroup.service.interfaces.ISimulationService;

@Service
public class ISimulationServiceImpl implements ISimulationService{

    @Override
    public boolean nextStep(Forest forest) {
        Tree[][] trees = forest.getTrees();
        boolean isStillBurning = burningTreesTreatment(trees,forest.getProbability());
        if(isStillBurning) setInitFireTreeToFire(trees);
        return isStillBurning;
    }

    @Override
    public void allSteps(Forest forest) {
        boolean stillBuring = true;
        do{
            stillBuring = nextStep(forest);
        }while(stillBuring);
    }

    /**
     * For all trees in fire in trees 2D Array parameter, set to EXTINCT and spread fire to neighbors with the probability passed. 
     * @param trees
     * @param probability
     * @return
     */
    private boolean burningTreesTreatment(Tree[][] trees,float probability) {
        boolean isStillBurning = false;
        for(int y=0;y<trees.length;y++){
            for(int x=0;x<trees[y].length;x++){
                Tree tree = trees[y][x];
                if(Objects.isNull(tree)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, invalid parameters");
                }
                if(tree.getState().equals(TreeState.FIRE)){
                    if(!isStillBurning) isStillBurning = true;
                    tree.nextState();
                    spreadFire(x,y,trees,probability);
                }
                
            }
        } 
        return isStillBurning;
    }

    /**
     * Set all trees in INIT_FIRE state to next state (FIRE).
     * @param trees
     */
    private void setInitFireTreeToFire(Tree[][] trees) {
        Arrays.stream(trees)
        .flatMap(treeRows->Arrays.stream(treeRows))
        .filter(tree->tree.getState().equals(TreeState.INIT_FIRE))
        .forEach(tree->{
            tree.nextState();
        });
    }

    /**
     * Set neighbors of coordinates (x,y) to next state (INIT_FIRE) with probability passed in parameters.
     * @param x
     * @param y
     * @param trees
     * @param probability
     */
    private void spreadFire(int x, int y, Tree[][] trees, float probability) {
        if(isIndexCorrect(trees,x,y+1) 
            && trees[y+1][x].getState().equals(TreeState.INITIAL) 
            && isProbabilityCheck(probability)){
                trees[y+1][x].nextState();
        }
        if(isIndexCorrect(trees,x,y-1) 
            && trees[y-1][x].getState().equals(TreeState.INITIAL) 
            && isProbabilityCheck(probability)){
                trees[y-1][x].nextState();
        }
        if(isIndexCorrect(trees,x+1,y) 
            && trees[y][x+1].getState().equals(TreeState.INITIAL) 
            && isProbabilityCheck(probability)){
                trees[y][x+1].nextState();
        }
        if(isIndexCorrect(trees,x-1,y) 
            && trees[y][x-1].getState().equals(TreeState.INITIAL) 
            && isProbabilityCheck(probability)){
                trees[y][x-1].nextState();
        }
    }

    private boolean isIndexCorrect(Tree[][] trees,int x, int y){
        return  (y>=0 && y < trees.length && x>=0 && x < trees[y].length);
    }

    private boolean isProbabilityCheck(float probability){
        return (new Random().nextFloat() <= probability);
    }

    

    

    
    
}
