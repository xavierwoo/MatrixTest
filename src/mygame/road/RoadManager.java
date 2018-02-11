/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.road;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import mygame.grid.Grid;
import mygame.grid.GridManager;
import mygame.grid.Position;

/**
 *
 * @author Xavier
 */
public class RoadManager {
    Node roads = new Node("roads");
    AssetManager assetManager;
    GridManager gridManager;
    
    public RoadManager(final AssetManager am, final GridManager gm){
        assetManager = am;
        gridManager = gm;
        
        gm.rootNode.attachChild(roads);
        setGridPoint();
    }
    
    
    private Grid laneTile(final int x, final int z){
        Spatial lane = new Geometry("Lane", new Quad(1,1));
        lane.setLocalRotation(new Quaternion().fromAngleAxis(- FastMath.PI / 2, Vector3f.UNIT_X));
        lane.setLocalTranslation(x - 0.5f, 0, z+0.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Gray);
        lane.setMaterial(mat);
        
        Grid grid = new Grid(new Position(x, z), lane);
        lane.setUserData("x 1", x);
        lane.setUserData("z 1", z);
        lane.setUserData("xMax", 1);
        lane.setUserData("zMax", 1);
        return grid;
    }
    
    private void setGridPoint(){
        int num = 10;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Geometry zeroPoint = new Geometry("zero", new Box(0.1f,0.1f,0.1f));
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat.setColor("Color", ColorRGBA.Red);
                zeroPoint.setMaterial(mat);
                zeroPoint.setLocalTranslation(new Vector3f(i,0,j));
                roads.attachChild(zeroPoint);
            }
        }
    }
    
    
    public boolean buildTwoLaneRoadZ(final Position from, final Position to){
        if (from.x != to.x) {
            throw new Error("Not supported!");
        }
        
        Position source;
        Position target;
        if (from.z < to.z) {
            source = from;
            target = to;
        }else{
            source = to;
            target = from;
        }
        
        //check whether grids are empty
        for (int x = source.x; x < source.x+2; ++x) {
            for (int z = source.z; z <= target.z; ++z) {
                if (!gridManager.gridIsEmpty(new Position(x, z))) {
                    return false;
                }
            }
        }
        
        Grid[][] grids = new Grid[2][target.z - source.z+1];
        for (int x = source.x; x < source.x+2; ++x){
            for (int z = source.z; z <= target.z; ++z) {
                Grid laneGrid = laneTile(x, z);
                gridManager.setGrid(laneGrid);
                grids[x - source.x][z - source.z] = laneGrid;
            }
        }
        
//        for (int z = 1; z < grids[0].length - 2; ++z) {
//            Grid gridA 
//        }
        
        return true;
    }
    
    public void setTestRoads(){
        buildTwoLaneRoadZ(new Position(1,1), new Position(1, 5));
    }
}
