/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.vehicle;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import mygame.navigation.VehicleSteeringControl;

/**
 *
 * @author xinyun
 */
public class VehicleManager {
    Node vehicles = new Node();
    AssetManager assetManager;
    
    public VehicleManager(AssetManager am, Node rootNode){
        assetManager = am;
        rootNode.attachChild(vehicles);
    }
    
    public Spatial newVehicle(){
        Geometry vehicleGeom = new Geometry("Vehicle", new Box(2, 1, 4));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        vehicleGeom.setMaterial(mat);
        return vehicleGeom;
    }
    
    public void setVehicleRoute(Spatial vehicle){
        
        vehicles.attachChild(vehicle);
        VehicleSteeringControl vsControl = new VehicleSteeringControl();
        vehicle.addControl(vsControl);
    }
}
