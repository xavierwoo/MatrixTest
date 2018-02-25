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
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;
import mygame.grid.Grid;
import mygame.grid.GridContainer;
import mygame.grid.GridManager;
import mygame.grid.NaviPath;
import mygame.grid.Position;

/**
 *
 * @author Xavier
 */
public class RoadManager {

    Node roadsNode = new Node("roads");
    AssetManager assetManager;
    GridManager gridManager;

    private Mesh twoLaneRoadMesh;

    public RoadManager(final AssetManager am, final GridManager gm) {
        assetManager = am;
        gridManager = gm;
        genTwoLaneRoadMesh();
        gm.rootNode.attachChild(roadsNode);
        setGridPoint();
    }

    private void genTwoLaneRoadMesh() {
        twoLaneRoadMesh = new Mesh();
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(-0.5f, 0, -0.5f);
        vertices[1] = new Vector3f(-0.5f, 0, 1.5f);
        vertices[2] = new Vector3f(0.5f, 0, 1.5f);
        vertices[3] = new Vector3f(0.5f, 0, -0.5f);

        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(0, 1);
        texCoord[2] = new Vector2f(1, 1);
        texCoord[3] = new Vector2f(1, 0);

        int[] indexes = {0, 1, 2, 0, 2, 3};
        twoLaneRoadMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        twoLaneRoadMesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        twoLaneRoadMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indexes));

    }

    /***
     * Create a two way one lane road tile
     * @param x location
     * @param z location
     * @param isZdir if the road is facing to z direction
     * @return 
     */
    private Spatial twoWayOneLaneRoad(int x, int z, boolean isZdir) {
        Spatial road = new Geometry("twoWayOneLaneRoad", twoLaneRoadMesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Gray);
        road.setMaterial(mat);

        road.setLocalTranslation(x, 0, z);

        Grid grid0 = new Grid(new Position(x, z), road);
        Grid grid1;
        if (isZdir) {
            road.setLocalRotation(new Quaternion().fromAngles(0, FastMath.PI / 2, 0));
            grid1 = new Grid(new Position(x+1, z), road);
        } else {
            grid1 = new Grid(new Position(x, z+1), road);
        }

        GridContainer gridContainer = new GridContainer();
        gridContainer.grids.add(grid0);
        gridContainer.grids.add(grid1);
        
        road.setUserData("GridContainer", gridContainer);
        
        return road;
    }

    private void setGridPoint() {
        int num = 10;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Geometry zeroPoint = new Geometry("zero", new Box(0.1f, 0.1f, 0.1f));
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat.setColor("Color", ColorRGBA.Red);
                zeroPoint.setMaterial(mat);
                zeroPoint.setLocalTranslation(new Vector3f(i, 0, j));
                roadsNode.attachChild(zeroPoint);
            }
        }
    }
    
    /***
     * Build a two way one lane road from (fromX, z) to (toX, z)
     * @param fromX location
     * @param toX location
     * @param z location
     * @return 
     */
    public boolean setTwoWayOneLaneRoadX(int fromX, int toX, int z){
        Spatial[] roadSpatials = new Spatial[toX - fromX + 1];
        for(int x=fromX; x<=toX; ++x){
            roadSpatials[x - fromX] = twoWayOneLaneRoad(x, z, false);
        }
        
        //set navipaths
        for (int i=0; i<roadSpatials.length - 1; ++i){
            ArrayList<Grid> gridsA = ((GridContainer)roadSpatials[i].getUserData("GridContainer")).grids;
            ArrayList<Grid> gridsB = ((GridContainer)roadSpatials[i+1].getUserData("GridContainer")).grids;
            
            Vector3f[] wayPoints0 = new Vector3f[2];
            Grid gridA0 = gridsA.get(0);
            Grid gridB0 = gridsB.get(0);
            wayPoints0[0] = new Vector3f(gridB0.position.x, 0, gridB0.position.z);
            wayPoints0[1] = new Vector3f(gridA0.position.x, 0, gridA0.position.z);
            NaviPath path0 = new NaviPath(gridB0, gridA0, wayPoints0, 3);
            gridB0.edges.add(path0);
            
            Vector3f[] wayPoints1 = new Vector3f[2];
            Grid gridA1 = gridsA.get(1);
            Grid gridB1 = gridsB.get(1);
            wayPoints1[0] = new Vector3f(gridA1.position.x, 0, gridA1.position.z);
            wayPoints1[1] = new Vector3f(gridB1.position.x, 0, gridB1.position.z);
            NaviPath path1 = new NaviPath(gridA1, gridB1, wayPoints1, 3);
            gridA1.edges.add(path1);
        }
        
        //set end points naviPath
        ArrayList<Grid> grids = ((GridContainer)roadSpatials[1].getUserData("GridContainer")).grids;
        Vector3f[] wayPoints = new Vector3f[4];
        wayPoints[0] = new Vector3f(grids.get(0).position.x, 0, grids.get(0).position.z);
        wayPoints[1] = new Vector3f(grids.get(0).position.x - 1, 0, grids.get(0).position.z);
        wayPoints[2] = new Vector3f(grids.get(1).position.x - 1, 0, grids.get(1).position.z);
        wayPoints[3] = new Vector3f(grids.get(1).position.x, 0, grids.get(1).position.z);
        NaviPath path = new NaviPath(grids.get(0), grids.get(1), wayPoints, 0.5f);
        grids.get(0).edges.add(path);
        
        grids = ((GridContainer)roadSpatials[roadSpatials.length-2].getUserData("GridContainer")).grids;
        wayPoints = new Vector3f[4];
        wayPoints[0] = new Vector3f(grids.get(1).position.x, 0, grids.get(1).position.z);
        wayPoints[1] = new Vector3f(grids.get(1).position.x + 1, 0, grids.get(1).position.z);
        wayPoints[2] = new Vector3f(grids.get(0).position.x + 1, 0, grids.get(0).position.z);
        wayPoints[3] = new Vector3f(grids.get(0).position.x, 0, grids.get(0).position.z);
        path = new NaviPath(grids.get(1), grids.get(0), wayPoints, 0.5f);
        grids.get(1).edges.add(path);
        
        for (Spatial road : roadSpatials) {
            roadsNode.attachChild(road);
            GridContainer gridContainer = road.getUserData("GridContainer");
            for (Grid grid : gridContainer.grids){
                gridManager.setGrid(grid);
            }
        }
        return true;
    }

    public void setTestRoads() {
        setTwoWayOneLaneRoadX(0, 10, 4);
    }
}
