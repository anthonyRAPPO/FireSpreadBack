package anthony.rappo.entretien.CirilGroup.service;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import anthony.rappo.entretien.CirilGroup.model.Forest;
import anthony.rappo.entretien.CirilGroup.model.Tree;
import anthony.rappo.entretien.CirilGroup.model.enumerations.TreeState;
import anthony.rappo.entretien.CirilGroup.service.interfaces.ISimulationService;
import anthony.rappo.entretien.CirilGroup.utils.UnitTestUtils;

@SpringBootTest
@ActiveProfiles("dev")
public class ISimulationServiceImplTest {
    @Autowired
    private ISimulationService simulationService;

    @Test
    void nextStep_probability0(){
        Forest forest = new Forest();
        forest.setProbability(0);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        simulationService.nextStep(forest);
        Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        Assertions.assertFalse(isThereNewTreesOnFire(oldTrees, forest.getTrees()));
    }

    @Test
    void nextStep_probability1(){
        Forest forest = new Forest();
        forest.setProbability(1);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        simulationService.nextStep(forest);
        Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        Assertions.assertTrue(doFireSpreadtoAllNeighbors(oldTrees, forest.getTrees()));
    }

    @Test
    void allSteps_probability0(){
        Forest forest = new Forest();
        forest.setProbability(0);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        simulationService.allSteps(forest);
        Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        Assertions.assertFalse(isThereNewTreesOnFire(oldTrees, forest.getTrees()));
    }

    @Test
    void allSteps_probability1(){
        Forest forest = new Forest();
        forest.setProbability(1);
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        simulationService.allSteps(forest);
        Assertions.assertTrue(areAllTreesBurned(forest.getTrees()));
    }

    private boolean doFireSpreadtoAllNeighbors(Tree[][] oldTrees,Tree[][] newTrees){
        for(int y=0;y<oldTrees.length;y++){
            for(int x=0;x<oldTrees[y].length;x++){
                if(oldTrees[y][x].getState().equals(TreeState.FIRE)){
                    if(isIndexCorrect(oldTrees,x,y+1) && oldTrees[y+1][x].getState().equals(TreeState.INITIAL) && !newTrees[y+1][x].getState().equals(TreeState.FIRE)){
                        return false;
                    }
                    if(isIndexCorrect(oldTrees,x,y-1) && oldTrees[y-1][x].getState().equals(TreeState.INITIAL) && !newTrees[y-1][x].getState().equals(TreeState.FIRE)){
                        return false;
                    }
                    if(isIndexCorrect(oldTrees,x+1,y) && oldTrees[y][x+1].getState().equals(TreeState.INITIAL) && !newTrees[y][x+1].getState().equals(TreeState.FIRE)){
                        return false;
                    }
                    if(isIndexCorrect(oldTrees,x-1,y) && oldTrees[y][x-1].getState().equals(TreeState.INITIAL) && !newTrees[y-1][x].getState().equals(TreeState.FIRE)){
                        return false;
                    }
                }
            } 
        } 
        return true;
    }

    private boolean isThereNewTreesOnFire(Tree[][] oldTrees,Tree[][] newTrees){
            for(int y=0;y<oldTrees.length;y++){
                for(int x=0;x<oldTrees[y].length;x++){
                    if(!oldTrees[y][x].equals(newTrees[y][x]) && !newTrees[y][x].getState().equals(TreeState.EXTINCT)){
                        return true;
                    }
                } 
            } 
            return false;
    }
    
    private boolean isIndexCorrect(Tree[][] trees,int x, int y){
        return  (y>=0 && y < trees.length && x>=0 && x < trees[y].length);
    }

    private boolean areAllTreesBurned(Tree[][] trees){
        return Arrays.stream(trees)
        .flatMap(treeRows->Arrays.stream(treeRows))
        .filter(tree->!tree.getState().equals(TreeState.EXTINCT))
        .count()==0;
    }
}
