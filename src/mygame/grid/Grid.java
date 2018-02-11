/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import com.jme3.scene.Spatial;
import java.util.HashSet;

/**
 *
 * @author Xavier
 */
public class Grid {
    final Position position;
    
    final Spatial spatial;
    
    HashSet<NaviPath> edges = new HashSet<>();
    
    public Grid(Position position, Spatial spatial){
        this.position = position;
        this.spatial = spatial;
    }
    
}
