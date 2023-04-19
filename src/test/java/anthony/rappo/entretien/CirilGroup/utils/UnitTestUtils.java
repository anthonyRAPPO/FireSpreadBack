package anthony.rappo.entretien.CirilGroup.utils;

import anthony.rappo.entretien.CirilGroup.model.Forest;
import anthony.rappo.entretien.CirilGroup.model.Tree;
import anthony.rappo.entretien.CirilGroup.model.enumerations.TreeState;

public class UnitTestUtils {
    public static Tree[][] create2dTreeArray(){
        Tree[][] trees = new Tree[3][3];
        for(int y=0;y<trees.length;y++){
            for(int x=0;x<trees[y].length;x++){
                if(x==1 && y==1){
                    trees[y][x] = new Tree(TreeState.FIRE);
                }else{
                    trees[y][x] = new Tree(TreeState.INITIAL);
                }
            }
        }
        return trees;
    }

    public static boolean areTreeArraysSameLength(Tree[][] oldTrees,Tree[][] newTrees){
        if(oldTrees.length == newTrees.length){
            for(int y=0;y<oldTrees.length;y++){
                if(oldTrees[y].length != newTrees[y].length){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }  
    }

}
