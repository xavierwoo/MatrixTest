package mygame;

import com.jme3.app.SimpleApplication;

import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import mygame.camera.CamManager;
import mygame.grid.GridManager;
import mygame.road.RoadManager;
import mygame.vehicle.VehicleManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    VehicleManager vehicleManager;
    CamManager camManager;
    RoadManager roadManager;
    GridManager gridManager;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        
        gridManager = new GridManager(rootNode);
        camManager = new CamManager(cam, rootNode);
        vehicleManager = new VehicleManager(assetManager, rootNode);
        roadManager = new RoadManager(assetManager, gridManager);
        
        Spatial vehicle = vehicleManager.newVehicle();
        
        vehicleManager.setVehicleRoute(vehicle);
        
        roadManager.setTestRoads();
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
