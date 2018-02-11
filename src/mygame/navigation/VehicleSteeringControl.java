/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.navigation;

import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author xinyun
 */
public class VehicleSteeringControl extends  AbstractControl implements Savable, Cloneable{

    @Override
    protected void controlUpdate(float tpf) {
        spatial.setLocalTranslation(spatial.getLocalTranslation().add(new Vector3f(0.1f, 0, 0)));
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
