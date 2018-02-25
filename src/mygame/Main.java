package mygame;

import com.jme3.app.SimpleApplication;

import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import mygame.camera.CamManager;
import mygame.grid.Grid;
import mygame.grid.GridManager;
import mygame.grid.NaviPath;
import mygame.grid.Position;
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
        
        
        roadManager.setTestRoads();
        Spatial vehicle = vehicleManager.newVehicle();
        Grid start = gridManager.allGrids.get(new Position(1, 1));
        Grid goal = gridManager.allGrids.get(new Position(8, 1));
        ArrayList<NaviPath> route = gridManager.AStar(start, goal);
        
        vehicleManager.setVehicleRoute(vehicle, 4, route);
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
