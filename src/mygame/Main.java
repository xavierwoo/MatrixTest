package mygame;

import com.jme3.app.SimpleApplication;

import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import mygame.camera.CamManager;
import mygame.gloabal.GlobalVariables;
import mygame.grid.Grid;
import mygame.grid.GridManager;
import mygame.grid.NaviPath;
import mygame.grid.Position;
import mygame.road.RoadManager;
import mygame.terrain.TerrainManager;
import mygame.vehicle.VehicleManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    VehicleManager vehicleManager;
    CamManager camManager;
    RoadManager roadManager;
    GridManager gridManager;
    TerrainManager terrainManager;

    float time = 0;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();

    }

    @Override
    public void simpleInitApp() {

        gridManager = new GridManager(rootNode);
        camManager = new CamManager(cam, rootNode, inputManager);
        vehicleManager = new VehicleManager(assetManager, rootNode);
        terrainManager = new TerrainManager(assetManager, rootNode);
        roadManager = new RoadManager(assetManager, gridManager, terrainManager);
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);
        
        roadManager.setTwoWayOneLaneRoad(1, 1, 10, 1);

        roadManager.setTwoWayOneLaneRoad(5, 3, 5, 9);

        //GlobalVariables.gSpeed = 0;
        Grid start;
        Grid goal;
        ArrayList<NaviPath> route;
//        Spatial vehicle1 = vehicleManager.newVehicle();
//        start = gridManager.allGrids.get(new Position(8, 1));
//        goal = gridManager.allGrids.get(new Position(4, 2));
//        route = gridManager.AStar(start, goal);
//        if (route != null) {
//            vehicleManager.setVehicleRoute(vehicle1, 1, route);
//        }
//
//        Spatial vehicle2 = vehicleManager.newVehicle();
//        start = gridManager.allGrids.get(new Position(4, 2));
//        goal = gridManager.allGrids.get(new Position(8, 1));
//        route = gridManager.AStar(start, goal);
//        if (route != null) {
//            vehicleManager.setVehicleRoute(vehicle2, 2, route);
//        }

        Spatial vehicle3 = vehicleManager.newVehicle();
        start = gridManager.allGrids.get(new Position(4, 4));
        goal = gridManager.allGrids.get(new Position(5, 7));
        route = gridManager.AStar(start, goal);
        if (route != null) {
            vehicleManager.setVehicleRoute(vehicle3, 2, route);
        }

        Spatial vehicle4 = vehicleManager.newVehicle();
        start = gridManager.allGrids.get(new Position(5, 7));
        goal = gridManager.allGrids.get(new Position(4, 4));
        route = gridManager.AStar(start, goal);
        if (route != null) {
            vehicleManager.setVehicleRoute(vehicle4, 3, route);
        }

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        time += tpf;
        if (time > 10 && GlobalVariables.gSpeed == 0) {
            GlobalVariables.gSpeed = 1;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
