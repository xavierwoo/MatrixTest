/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author Xavier
 */
public class Grid implements Savable{
    public final Position position;
    
    public final Spatial spatial;
    
    public HashSet<NaviPath> edges = new HashSet<>();
    
    public Grid(Position position, Spatial spatial){
        this.position = position;
        this.spatial = spatial;
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
