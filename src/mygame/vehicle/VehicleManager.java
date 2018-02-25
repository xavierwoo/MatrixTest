/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.vehicle;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import mygame.grid.Grid;
import mygame.grid.NaviPath;
import mygame.grid.Position;
import mygame.grid.VehicleSteeringControl;

/**
 *
 * @author xinyun
 */
public class VehicleManager {
    Node vehicles = new Node("vehicles");
    AssetManager assetManager;
    
    public VehicleManager(AssetManager am, Node rootNode){
        assetManager = am;
        rootNode.attachChild(vehicles);
    }
    
    public Spatial newVehicle(){
        Geometry vehicleGeom = new Geometry("Vehicle", new Box(0.2f, 0.13f, 0.35f));
        vehicleGeom.setLocalTranslation(0, 0.13f, 0f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        vehicleGeom.setMaterial(mat);
        Node vehicleNode = new Node("Vehicle Node");
        vehicleNode.attachChild(vehicleGeom);
        return vehicleNode;
    }
    
    public void setVehicleRoute(Spatial vehicle, float acceleration, ArrayList<NaviPath> route){
        
        vehicles.attachChild(vehicle);
        VehicleSteeringControl vsControl = new VehicleSteeringControl(acceleration, route);
        vehicle.addControl(vsControl);
    }
    
    public ArrayList<NaviPath> genTestRout(){
        ArrayList<NaviPath> naviPaths = new ArrayList<>();
        Vector3f[] wayPoints = new Vector3f[2];
        wayPoints[0] = new Vector3f(1f, 0, 5);
        wayPoints[1] = new Vector3f(1f, 0, 2f);
        NaviPath naviPath = new NaviPath(new Grid(new Position(1, 5), null), new Grid(new Position(1, 2), null), wayPoints, 4);
        naviPaths.add(naviPath);

        wayPoints = new Vector3f[3];
        wayPoints[0] = new Vector3f(1f, 0, 2);
        wayPoints[1] = new Vector3f(1f, 0, 1f);
        wayPoints[2] = new Vector3f(2f, 0, 1f);
        naviPath = new NaviPath(new Grid(new Position(1, 2), null), new Grid(new Position(2, 1), null), wayPoints, 4);
        naviPaths.add(naviPath);

        wayPoints = new Vector3f[2];
        wayPoints[0] = new Vector3f(2, 0, 1);
        wayPoints[1] = new Vector3f(5, 0, 1);
        naviPath = new NaviPath(new Grid(new Position(2, 1), null), new Grid(new Position(5, 1), null), wayPoints, 4);
        naviPaths.add(naviPath);
        return naviPaths;
    }
}
