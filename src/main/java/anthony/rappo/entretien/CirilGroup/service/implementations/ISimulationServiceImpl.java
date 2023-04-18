package anthony.rappo.entretien.CirilGroup.service.implementations;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

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
    public void nextStep(Forest forest) {
        Tree[][] trees = forest.getTrees();
        burningTreesTreatment(trees,forest.getProbability());
        setInitFireTreeToFire(trees);
    }

    private void burningTreesTreatment(Tree[][] trees,float probability) {
        for(int y=0;y<trees.length;y++){
            for(int x=0;x<trees[y].length;x++){
                Tree tree = trees[y][x];
                if(Objects.isNull(tree)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, invalid parameters");
                }
                if(tree.getState().equals(TreeState.FIRE)){
                    tree.nextState();
                    spreadFire(x,y,trees,probability);
                }
                
            }
        }  
    }

    private void setInitFireTreeToFire(Tree[][] trees) {
        Arrays.stream(trees)
        .flatMap(treeRows->Arrays.stream(treeRows))
        .filter(tree->tree.getState().equals(TreeState.INIT_FIRE))
        .forEach(tree->{
            tree.nextState();
        });
    }

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
