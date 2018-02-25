/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import com.jme3.scene.Node;
import java.util.HashMap;

/**
 *
 * @author Xavier
 */
public class GridManager {
    final HashMap<Position, Grid> allGrids = new HashMap<>();
    
    final public Node rootNode;
    
    public GridManager(final Node rootNode){
        this.rootNode = rootNode;
    }
    
    public boolean gridIsEmpty(Position pos){
        return !allGrids.containsKey(pos);
    }
    
    public boolean setGrid(Grid grid){
        if (allGrids.containsKey(grid.position)) {
            return false;
        }else{
            allGrids.put(grid.position, grid);
            return true;
        }
    }
}
