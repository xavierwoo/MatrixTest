/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.camera;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;

/**
 *
 * @author xinyun
 */
public class CamManager {
    CameraNode cameraNode;
    Node cameraFocusNode = new Node("cameraFocusNode");
    
    public CamManager(Camera cam, Node rootNode){
        cameraNode = new CameraNode("Camera Node", cam);
        cameraNode.setControlDir(ControlDirection.SpatialToCamera);
        rootNode.attachChild(cameraFocusNode);
        cameraFocusNode.setLocalTranslation(new Vector3f(5,0,5));
        cameraFocusNode.attachChild(cameraNode);
        cameraNode.setLocalTranslation(new Vector3f(0, 10, 10));
        cameraNode.lookAt(cameraFocusNode.getLocalTranslation(), Vector3f.UNIT_Y);
    }
}
