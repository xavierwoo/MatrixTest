/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.terrain;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

/**
 *
 * @author Xavier
 */
public class Terrain {
    
    AssetManager assetManager;

    public Terrain(AssetManager am) {
        assetManager = am;
    }
    
    
    
    Mesh createTileMesh(){
        Mesh tile = new Mesh();
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(0, 0, 0);
        vertices[1] = new Vector3f(1, 0, 0);
        vertices[2] = new Vector3f(0, 0, 1);
        vertices[3] = new Vector3f(1, 0, 1);
        
        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0,0);
        texCoord[1] = new Vector2f(1,0);
        texCoord[2] = new Vector2f(0,1);
        texCoord[3] = new Vector2f(1,1);
        
        short[] indexes = {1, 0, 2, 1, 2, 3};
        
        tile.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        tile.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        tile.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        tile.updateBound();
        //tile.setMode(Mesh.Mode.LineLoop);
        return tile;
    }
    
    public Geometry createTile(){
        Geometry tile = new Geometry("tile", createTileMesh());
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        tile.setMaterial(mat);
        return tile;
    }
}
