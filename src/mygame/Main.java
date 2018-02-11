package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import mygame.camera.CameraManager;
import mygame.vehicle.VehicleManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    VehicleManager vehicleManager;
    CameraManager cameraManager;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        
        cameraManager = new CameraManager(cam, rootNode);
        
        vehicleManager = new VehicleManager(assetManager, rootNode);
        
        Spatial vehicle = vehicleManager.newVehicle();
        
        vehicleManager.setVehicleRoute(vehicle);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}