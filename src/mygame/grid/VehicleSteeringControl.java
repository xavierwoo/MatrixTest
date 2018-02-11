/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import mygame.grid.Position;
import mygame.grid.Grid;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;

/**
 *
 * @author xinyun
 */
public class VehicleSteeringControl extends AbstractControl implements Savable, Cloneable {
    
    final ArrayList<NaviPath> naviPaths = new ArrayList<>();
    int currPathIndex = 0;
    float t = 0;
    
    public VehicleSteeringControl() {
        super();
        
        
        Vector3f[] wayPoints = new Vector3f[2];
        wayPoints[0] = new Vector3f(1f, 0, 5);
        wayPoints[1] = new Vector3f(1f, 0, 2f);
        NaviPath naviPath = new NaviPath(new Grid(new Position(1, 5), null), new Grid(new Position(1, 2), null), wayPoints);
        naviPaths.add(naviPath);
        
        wayPoints = new Vector3f[3];
        wayPoints[0] = new Vector3f(1f, 0, 2);
        wayPoints[1] = new Vector3f(1f, 0, 1f);
        wayPoints[2] = new Vector3f(2f, 0, 1f);
        naviPath = new NaviPath(new Grid(new Position(1, 2), null), new Grid(new Position(2, 1), null), wayPoints);
        naviPaths.add(naviPath);
        
        
        wayPoints = new Vector3f[2];
        wayPoints[0] = new Vector3f(2, 0, 1);
        wayPoints[1] = new Vector3f(5, 0, 1);
        naviPath = new NaviPath(new Grid(new Position(2, 1), null), new Grid(new Position(5, 1), null), wayPoints);
        naviPaths.add(naviPath);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        if (t < 1) {
            NaviPath currPath = naviPaths.get(currPathIndex);
            Vector3f pos = currPath.getPosition(t);
            Vector3f dir = currPath.getDirection(t);
            
            spatial.setLocalTranslation(pos);
            spatial.lookAt(spatial.getLocalTranslation().add(dir), Vector3f.UNIT_Y);
            
            t += 0.005;
        }else if(currPathIndex < naviPaths.size() - 1){
            ++currPathIndex;
            t = 0;
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
