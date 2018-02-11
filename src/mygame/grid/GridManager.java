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
    HashMap<Position, Grid> allGrids = new HashMap<>();
    
    public Node rootNode;
    public Node roads = new Node("Roads");
    
    public GridManager(final Node rootNode){
        this.rootNode = rootNode;
        rootNode.attachChild(roads);
    }
    
    public boolean gridIsEmpty(Position pos){
        return !allGrids.containsKey(pos);
    }
    
    public boolean setGrid(Grid grid){
        if (allGrids.containsKey(grid.position)) {
            return false;
        }else{
            allGrids.put(grid.position, grid);
            
            switch (grid.spatial.getName()){
                case "Lane":
                    roads.attachChild(grid.spatial);
                    break;
                default:
                    throw new Error("Not supported yet!");
            }
            return true;
        }
    }
}
