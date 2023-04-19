package anthony.rappo.entretien.CirilGroup.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import anthony.rappo.entretien.CirilGroup.model.Forest;
import anthony.rappo.entretien.CirilGroup.model.Tree;
import anthony.rappo.entretien.CirilGroup.model.enumerations.TreeState;
import anthony.rappo.entretien.CirilGroup.utils.UnitTestUtils;

@SpringBootTest
@ActiveProfiles("dev")
public class SimulationControllerTest {
    @Autowired
    private SimulationController simulationController;


    @Test
    void nextStep_nullForest(){
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.nextStep(null));
    }

    @Test
    void nextStep_nullTrees(){
        Forest forest = new Forest();
        forest.setProbability(0.2f);
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.nextStep(forest));
    }

    @Test
    void nextStep_negativeProbability(){
        Forest forest = new Forest();
        forest.setProbability(-0.2f);
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.nextStep(forest));
    }

    @Test
    void nextStep_probabilitySuperior1(){
        Forest forest = new Forest();
        forest.setProbability(1.1f);
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.nextStep(forest));
    }

    @Test
    void nextStep_probabilityEqual1(){
        Forest forest = new Forest();
        forest.setProbability(1f);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertDoesNotThrow(()->{
            simulationController.nextStep(forest);
            Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        });
    }

    @Test
    void nextStep_probabilityEqual0(){
        Forest forest = new Forest();
        forest.setProbability(0f);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertDoesNotThrow(()->{
            simulationController.nextStep(forest);
            Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        });
    }

    @Test
    void allSteps_nullForest(){
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.allSteps(null));
    }

    @Test
    void allSteps_nullTrees(){
        Forest forest = new Forest();
        forest.setProbability(0.2f);
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.allSteps(forest));
    }

    @Test
    void allSteps_negativeProbability(){
        Forest forest = new Forest();
        forest.setProbability(-0.2f);
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.allSteps(forest));
    }

    @Test
    void allSteps_probabilitySuperior1(){
        Forest forest = new Forest();
        forest.setProbability(1.1f);
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertThrows(ResponseStatusException.class,()->simulationController.allSteps(forest));
    }

    @Test
    void allSteps_probabilityEqual1(){
        Forest forest = new Forest();
        forest.setProbability(1f);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertDoesNotThrow(()->{
            simulationController.allSteps(forest);
            Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        });
    }

    @Test
    void allSteps_probabilityEqual0(){
        Forest forest = new Forest();
        forest.setProbability(0f);
        Tree[][] oldTrees = UnitTestUtils.create2dTreeArray();
        forest.setTrees(UnitTestUtils.create2dTreeArray());
        Assertions.assertDoesNotThrow(()->{
            simulationController.allSteps(forest);
            Assertions.assertTrue(UnitTestUtils.areTreeArraysSameLength(oldTrees, forest.getTrees()));
        });
    }


    
}
