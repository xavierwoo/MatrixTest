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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Xavier
 */
public class Grid implements Savable{
    public final Position position;
    
    public final Spatial spatial;
    
    public HashMap<Grid, NaviPath> edges = new HashMap<>();
    public HashSet<Grid> toThis = new HashSet<>();
    
    public Grid(Position position, Spatial spatial){
        this.position = position;
        this.spatial = spatial;
    }
    
    @Override
    public String toString(){
        return position.toString();
    }

    @Override
    public int hashCode(){
        return Objects.hash(position.x, position.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grid other = (Grid) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.spatial, other.spatial)) {
            return false;
        }
        return true;
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
