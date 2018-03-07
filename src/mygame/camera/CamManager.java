/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.camera;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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
    final private CameraNode cameraNode;
    final private InputManager inputManager;
    final private Node cameraFocusNode = new Node("cameraFocusNode");
    
    public CamManager(Camera cam, Node rootNode, InputManager inputManager){
        this.inputManager = inputManager;
        cameraNode = new CameraNode("Camera Node", cam);
        cameraNode.setControlDir(ControlDirection.SpatialToCamera);
        rootNode.attachChild(cameraFocusNode);
        cameraFocusNode.setLocalTranslation(new Vector3f(5,0,5));
        cameraFocusNode.attachChild(cameraNode);
        cameraNode.setLocalTranslation(new Vector3f(0, 10, 10));
        cameraNode.lookAt(cameraFocusNode.getLocalTranslation(), Vector3f.UNIT_Y);
        
        initCamInput();
    }
    
    private void initCamInput(){
        inputManager.addMapping("CamForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CamBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CamRight", new KeyTrigger(KeyInput.KEY_D));
        
        //TODO: needs to be modified
        ActionListener camAl = (String name, boolean isPressed, float tpf) -> {
            if (name.equals("CamForward") && isPressed) {
                cameraNode.setLocalTranslation(cameraNode.getLocalTranslation().add(0, 0, -1));
            }
            if (name.equals("CamBackward") && isPressed) {
                cameraNode.setLocalTranslation(cameraNode.getLocalTranslation().add(0, 0, 1));
            }
            if (name.equals("CamLeft") && isPressed) {
                cameraNode.setLocalTranslation(cameraNode.getLocalTranslation().add(-1, 0, 0));
            }
            if (name.equals("CamRight") && isPressed) {
                cameraNode.setLocalTranslation(cameraNode.getLocalTranslation().add(1, 0, 0));
            }
        };
        
        inputManager.addListener(camAl, "CamForward", "CamBackward", "CamLeft", "CamRight");
    }
}
