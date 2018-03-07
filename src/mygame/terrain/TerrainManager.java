/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.terrain;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;

/**
 *
 * @author Xavier
 */
public class TerrainManager {
    
    private final AssetManager assetManager;
    private final Node rootNode; 
    final private TerrainQuad terrainQuad;
    
    
    public TerrainManager(AssetManager am, Node rootNode) {
        assetManager = am;
        this.rootNode = rootNode;
        Spatial terrain = assetManager.loadModel("PlainTerrain.j3o");
        rootNode.attachChild(terrain);
        terrainQuad = (TerrainQuad) rootNode.getChild("terrain-TerrainEditor - PlainTerrain");
    }
    
    
    public float getHeight(int x, int z){
        return terrainQuad.getHeight(new Vector2f(x,z));
    }
}
