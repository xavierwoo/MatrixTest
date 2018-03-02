/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Xavier
 */
public class GridManager {
    public final HashMap<Position, Grid> allGrids = new HashMap<>();
    
    final public Node rootNode;
    
    public GridManager(final Node rootNode){
        this.rootNode = rootNode;
    }
    
    public boolean gridIsEmpty(Position pos){
        return !allGrids.containsKey(pos);
    }
    
    public boolean setGrid(Grid grid){
        if (allGrids.containsKey(grid.position)) {
            System.err.println("The grid is already occupied");
            return false;
        }else{
            allGrids.put(grid.position, grid);
            return true;
        }
    }
    
    public ArrayList<NaviPath> AStar(Grid start, Grid goal){
        HashSet<Grid> closedSet = new HashSet<>();
        HashSet<Grid> openSet = new HashSet<>();
        openSet.add(start);
        HashMap<Grid, NaviPath> cameFrom = new HashMap<>();
        HashMap<Grid, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);
        HashMap<Grid, Double> fScore = new HashMap<>();
        fScore.put(start, heuristicCostEstimate(start, goal));
        
        while (!openSet.isEmpty()) {
            Grid current = findMinGrid(fScore, openSet);
            if (current == goal) {
                return retrivePaths(cameFrom, current);
            }
            
            openSet.remove(current);
            closedSet.add(current);
            
            for(Map.Entry<Grid, NaviPath> entry : current.edges.entrySet()){
                Grid neighbour = entry.getKey();
                NaviPath naviPath = entry.getValue();
                if (closedSet.contains(neighbour)) {
                    continue;
                }
                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                }
                
                double tenativeGScore = gScore.get(current) + naviPath.maxTime;
                if (!gScore.containsKey(neighbour)
                        || tenativeGScore < gScore.get(neighbour)) {
                    cameFrom.put(neighbour, naviPath);
                    gScore.put(neighbour, tenativeGScore);
                    fScore.put(neighbour, tenativeGScore + heuristicCostEstimate(neighbour, goal));
                }
            }
        }
        return null;
    }
    
    private ArrayList<NaviPath> retrivePaths(HashMap<Grid, NaviPath> cameFrom, Grid current){
        ArrayList<NaviPath> route = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            NaviPath naviPath = cameFrom.get(current);
            route.add(naviPath);
            current = naviPath.source;
        }
        Collections.reverse(route);
        return route;
    }
    
    private Grid findMinGrid(HashMap<Grid, Double> fScore, HashSet<Grid> openSet){
        Grid minGrid = null;
        double minScore = Double.MAX_VALUE;
        for (Grid grid: openSet){
            double score = fScore.get(grid);
            if (score < minScore) {
                minGrid = grid;
                minScore = score;
            }
        }
        return minGrid;
    }
    
    private double heuristicCostEstimate(Grid start, Grid goal){
        return (FastMath.abs(goal.position.x - start.position.x)
                + FastMath.abs(goal.position.z - start.position.z)) * 0.001;
    }
}
