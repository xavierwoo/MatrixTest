/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import mygame.gloabal.GlobalVariables;

/**
 *
 * @author xinyun
 */
public class VehicleSteeringControl extends AbstractControl implements Savable, Cloneable {

    final private static float VELOCITY_TOLERANCE = 0.2f;
    final private static float PARKING_VELOCITY = 0.5f;
    final private float BRAKE_ACCELERATION = 4f;
    final private ArrayList<NaviPath> naviPaths;
    private int currPathIndex = 0;
    private float t = 0;
    private float velocity = 0;
    final private float acceleration;

    public VehicleSteeringControl(float acceleration, ArrayList<NaviPath> paths) {
        super();
        this.acceleration = acceleration;
        naviPaths = paths;
    }

    @Override
    protected void controlUpdate(float tpf) {
//        if (GlobalVariables.gSpeed == 0) {
//            return;
//        }
        if (t < 1 && t >= 0) {
            NaviPath currPath = naviPaths.get(currPathIndex);
            Vector3f pos = currPath.getPosition(t);
            Vector3f dir = currPath.getDirection(t);

            if (currPathIndex < naviPaths.size() - 1) {
                NaviPath nextPath = naviPaths.get(currPathIndex + 1);
                if (velocity < currPath.maxSpeed - VELOCITY_TOLERANCE
                        &&
                        velocity < nextPath.maxSpeed - VELOCITY_TOLERANCE) {
                    velocity += acceleration * tpf;
                } else if (velocity > currPath.maxSpeed + VELOCITY_TOLERANCE
                        ||
                        velocity > nextPath.maxSpeed + VELOCITY_TOLERANCE) {
                    velocity -= BRAKE_ACCELERATION * tpf;
                }
            } else {
                if (velocity < PARKING_VELOCITY - VELOCITY_TOLERANCE) {
                    velocity += acceleration * tpf;
                } else if (velocity > PARKING_VELOCITY + VELOCITY_TOLERANCE) {
                    velocity -= BRAKE_ACCELERATION * tpf;
                }
            }

            spatial.setLocalTranslation(pos);
            spatial.lookAt(spatial.getLocalTranslation().add(dir), Vector3f.UNIT_Y);

            t += velocity * tpf / currPath.length * GlobalVariables.gSpeed;

        } else if (currPathIndex < naviPaths.size() - 1) {
            ++currPathIndex;
            t = 0;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
