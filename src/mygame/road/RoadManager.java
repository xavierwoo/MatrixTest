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
import com.jme3.util.BufferUtils;
import java.util.ArrayList;
import mygame.grid.Grid;
import mygame.grid.GridContainer;
import mygame.grid.GridManager;
import mygame.grid.NaviPath;
import mygame.grid.Position;
import mygame.terrain.TerrainManager;

/**
 *
 * @author Xavier
 */
public class RoadManager {

    final private float U_TURN_MAX_VELOCITY = 0.5f;
    final private float TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY = 2f;
    final private Node roadsNode = new Node("roads");
    final private AssetManager assetManager;
    final private GridManager gridManager;
    final private TerrainManager terrainManager;
    
    private Mesh twoLaneRoadMesh;
    private Mesh twoLaneCrossMesh;

    public RoadManager(final AssetManager am, final GridManager gm, final TerrainManager tm) {
        assetManager = am;
        gridManager = gm;
        terrainManager = tm;
        genTwoLaneRoadMesh();
        genTwoLaneCrossMesh();
        gm.rootNode.attachChild(roadsNode);
    }

    private void genTwoLaneRoadMesh() {
        twoLaneRoadMesh = new Mesh();
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(-0.5f, 0.01f, -0.5f);
        vertices[1] = new Vector3f(-0.5f, 0.01f, 1.5f);
        vertices[2] = new Vector3f(0.5f, 0.01f, 1.5f);
        vertices[3] = new Vector3f(0.5f, 0.01f, -0.5f);

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

    private void genTwoLaneCrossMesh() {
        twoLaneCrossMesh = new Mesh();
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(-0.5f, 0.01f, -0.5f);
        vertices[1] = new Vector3f(-0.5f, 0.01f, 1.5f);
        vertices[2] = new Vector3f(1.5f, 0.01f, 1.5f);
        vertices[3] = new Vector3f(1.5f, 0.01f, -0.5f);

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

    /**
     * *
     * Create a two way one lane road tile
     *
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

        road.setLocalTranslation(x, terrainManager.getHeight(x, z), z);

        Grid grid0 = new Grid(new Position(x, z), road);
        Grid grid1;
        if (isZdir) {
            road.setLocalRotation(new Quaternion().fromAngles(0, -FastMath.PI / 2, 0));
            grid1 = new Grid(new Position(x - 1, z), road);
            setUTurn(grid0, grid1, 0, -1);
            setUTurn(grid1, grid0, 0, 1);
        } else {
            grid1 = new Grid(new Position(x, z + 1), road);
            setUTurn(grid0, grid1, -1, 0);
            setUTurn(grid1, grid0, 1, 0);
        }

        GridContainer gridContainer = new GridContainer(2);
        gridContainer.grids[0] = grid0;
        gridContainer.grids[1] = grid1;

        road.setUserData("GridContainer", gridContainer);

        return road;
    }
    
    private Spatial twoWayOneLaneCross(int x, int z){
        Spatial cross = new Geometry("twoLaneCross", twoLaneCrossMesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        cross.setMaterial(mat);
        cross.setLocalTranslation(x, terrainManager.getHeight(x, z), z);
        
        Grid grid0 = new Grid(new Position(x, z), cross);
        Grid grid1 = new Grid(new Position(x+1, z), cross);
        Grid grid2 = new Grid(new Position(x, z+1), cross);
        Grid grid3 = new Grid(new Position(x+1, z+1), cross);
        //TODO: connect grids
        GridContainer gridContainer = new GridContainer(4);
        gridContainer.grids[0] = grid0;
        gridContainer.grids[1] = grid1;
        gridContainer.grids[2] = grid2; 
        gridContainer.grids[3] = grid3;
        
        conGridsDirectly(grid1, grid0, TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY);
        conGridsDirectly(grid0, grid2, TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY);
        conGridsDirectly(grid2, grid3, TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY);
        conGridsDirectly(grid3, grid1, TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY);
        
        cross.setUserData("GridContainer", gridContainer);
        return cross;
    }

    /**
     * *
     * Build a two way one lane road from (fromX, fromZ) to (toX, toZ)
     *
     * @param fromX
     * @param fromZ
     * @param toX
     * @param toZ
     * @return
     */
    public boolean setTwoWayOneLaneRoad(int fromX, int fromZ, int toX, int toZ) {

        if (fromX == toX) {
            if (!checkTwoWayOneLaneRoadZAvailability(fromX, fromZ, toZ)) {
                return false;
            }
            setTwoWayOneLaneRoadZ(fromX, fromZ, toZ);
            return true;
        } else if (fromZ == toZ) {
            if (!checkTwoWayOneLaneRoadXAvailability(fromX, toX, fromZ)) {
                return false;
            }
            setTwoWayOneLaneRoadX(fromX, toX, fromZ);
            return true;
        } else {
            System.err.println("setTwoWayOneLaneRoad not supported");
            return false;
        }
    }

    public boolean checkTwoWayOneLaneRoadZAvailability(int x, int fromZ, int toZ) {
        for (int z = fromZ; z <= toZ; ++z) {
            if (gridManager.allGrids.containsKey(new Position(x, z))
                    || gridManager.allGrids.containsKey(new Position(x - 1, z))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTwoWayOneLaneRoadXAvailability(int fromX, int toX, int z) {
        for (int x = fromX; x <= toX; ++x) {
            if (gridManager.allGrids.containsKey(new Position(x, z))
                    || gridManager.allGrids.containsKey(new Position(x, z + 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * *
     * Build a two way one lane road from (fromX, z) to (toX, z)
     *
     * @param fromX location
     * @param toX location
     * @param z location
     */
    private void setTwoWayOneLaneRoadX(int fromX, int toX, int z) {
        Spatial[] roadSpatials = new Spatial[toX - fromX + 1];
        for (int x = fromX; x <= toX; ++x) {
            roadSpatials[x - fromX] = twoWayOneLaneRoad(x, z, false);
        }
        
        //set navipaths
        connectTwoWayOneLaneTiles(roadSpatials);

        //connect adjacent roads
        Grid xpGrid0 = gridManager.allGrids.get(new Position(toX + 1, z));
        Grid xpGrid1 = gridManager.allGrids.get(new Position(toX + 1, z + 1));
        if (xpGrid0 != null && xpGrid1 != null) {
            if (xpGrid0.spatial == xpGrid1.spatial
                    && xpGrid0.spatial.getName().equals("twoWayOneLaneRoad")) {
                connectTwoWayOneLaneTiles(roadSpatials[roadSpatials.length - 1], xpGrid0.spatial);
            }
        }
        Grid xnGrid0 = gridManager.allGrids.get(new Position(fromX - 1, z));
        Grid xnGrid1 = gridManager.allGrids.get(new Position(fromX - 1, z + 1));
        if (xnGrid0 != null && xnGrid1 != null) {
            if (xnGrid0.spatial == xnGrid1.spatial
                    && xnGrid0.spatial.getName().equals("twoWayOneLaneRoad")) {
                connectTwoWayOneLaneTiles(xnGrid0.spatial, roadSpatials[0]);
            }
        }

        setGrids(roadSpatials);
    }

    private void setUTurn(Grid fromGrid, Grid toGrid, int modiX, int modiZ) {
        Vector3f[] wayPoints = new Vector3f[4];
        wayPoints[0] = new Vector3f(fromGrid.position.x, 0, fromGrid.position.z);
        wayPoints[1] = new Vector3f(fromGrid.position.x + modiX, 0, fromGrid.position.z + modiZ);
        wayPoints[2] = new Vector3f(toGrid.position.x + modiX, 0, toGrid.position.z + modiZ);
        wayPoints[3] = new Vector3f(toGrid.position.x, 0, toGrid.position.z);
        NaviPath path = new NaviPath(fromGrid, toGrid, wayPoints, U_TURN_MAX_VELOCITY);
        fromGrid.edges.put(toGrid, path);
        toGrid.toThis.add(fromGrid);
    }

    private void setGrids(Spatial[] spatials) {
        for (Spatial road : spatials) {
            roadsNode.attachChild(road);
            GridContainer gridContainer = road.getUserData("GridContainer");
            for (Grid grid : gridContainer.grids) {
                gridManager.setGrid(grid);
            }
        }
    }

    private void connectTwoWayOneLaneTiles(Spatial[] roadSpatials) {
        for (int i = 0; i < roadSpatials.length - 1; ++i) {
            connectTwoWayOneLaneTiles(roadSpatials[i], roadSpatials[i + 1]);
        }
    }

    private void connectTwoWayOneLaneTiles(Spatial roadSpatial1, Spatial roadSpatial2) {
        Grid[] gridsA = ((GridContainer) roadSpatial1.getUserData("GridContainer")).grids;
        Grid[] gridsB = ((GridContainer) roadSpatial2.getUserData("GridContainer")).grids;

        conGridsDirectly(gridsB[0], gridsA[0], TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY);
        conGridsDirectly(gridsA[1], gridsB[1], TWO_WAY_ONE_LANE_ROAD_MAX_VELOCITY);
    }

    private void conGridsDirectly(Grid gridA, Grid gridB, float maxVelocity){
        Vector3f[] wayPoints = new Vector3f[2];
        wayPoints[0] = new Vector3f(gridA.position.x, 0, gridA.position.z);
        wayPoints[1] = new Vector3f(gridB.position.x, 0, gridB.position.z);
        NaviPath path = new NaviPath(gridA, gridB, wayPoints, maxVelocity);
        gridA.edges.put(gridB, path);
        gridB.toThis.add(gridA);
    }
    
    /**
     * *
     * Build a two way one lane road from (x, fromZ) to (x, toZ)
     *
     * @param x location
     * @param fromZ location
     * @param toZ location
     */
    private void setTwoWayOneLaneRoadZ(int x, int fromZ, int toZ) {
        Spatial[] roadSpatials = new Spatial[toZ - fromZ + 1];
        for (int z = fromZ; z <= toZ; ++z) {
            roadSpatials[z - fromZ] = twoWayOneLaneRoad(x, z, true);
        }

        //setnavipaths
        connectTwoWayOneLaneTiles(roadSpatials);

        //connect adjacent roads
        Grid zpGrid0 = gridManager.allGrids.get(new Position(x, toZ + 1));
        Grid zpGrid1 = gridManager.allGrids.get(new Position(x - 1, toZ + 1));
        if (zpGrid0 != null && zpGrid1 != null) {
            if (zpGrid0.spatial == zpGrid1.spatial//The adjacent road is with the same direction
                    && zpGrid0.spatial.getName().equals("twoWayOneLaneRoad")) {
                connectTwoWayOneLaneTiles(roadSpatials[roadSpatials.length - 1], zpGrid0.spatial);
            }
        }
        Grid znGrid0 = gridManager.allGrids.get(new Position(x, fromZ - 1));
        Grid znGrid1 = gridManager.allGrids.get(new Position(x - 1, fromZ - 1));
        if (znGrid0 != null && znGrid1 != null) {
            if (znGrid0.spatial == znGrid1.spatial
                    && znGrid0.spatial.getName().equals("twoWayOneLaneRoad")) {
                connectTwoWayOneLaneTiles(znGrid0.spatial, roadSpatials[0]);
            }else if(znGrid0.spatial != znGrid1.spatial
                    && znGrid0.spatial.getName().equals("twoWayOneLaneRoad")
                    && znGrid1.spatial.getName().equals("twoWayOneLaneRoad")){
                Grid znGrid00 = gridManager.allGrids.get(new Position(x, fromZ - 2));
                Grid znGrid11 = gridManager.allGrids.get(new Position(x-1, fromZ - 2));
                if (znGrid00 != null && znGrid11 != null
                        && znGrid0.spatial == znGrid00.spatial
                        && znGrid1.spatial == znGrid11.spatial) {
//                    removeTwoWayOneLaneRoad(znGrid0.spatial);
//                    removeTwoWayOneLaneRoad(znGrid1.spatial);
                }
            }
        }

        setGrids(roadSpatials);
    }
    
    public void removeTwoWayOneLaneRoad(Spatial road){
        road.setCullHint(Spatial.CullHint.Always);
        road.removeFromParent();
        
        Grid[] grids = ((GridContainer)road.getUserData("GridContainer")).grids;
        
        ArrayList<Grid> toThisGrid = new ArrayList<>(grids[0].toThis);
        for(Grid grid : toThisGrid){
            disConGrids(grid, grids[0]);
        }
        
        toThisGrid = new ArrayList<>(grids[1].toThis);
        for(Grid grid : toThisGrid){
            disConGrids(grid, grids[1]);
        }
        
        
        gridManager.allGrids.remove(grids[0].position);
        gridManager.allGrids.remove(grids[1].position);
    }
    
    private void disConGrids(Grid gridA, Grid gridB){
        if (!gridB.toThis.contains(gridA)) {
            return;
        }
        
        gridB.toThis.remove(gridA);
        gridA.edges.remove(gridB);
    }
}
