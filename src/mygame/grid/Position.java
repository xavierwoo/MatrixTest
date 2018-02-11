/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import java.util.Objects;

/**
 *
 * @author Xavier
 */
public class Position {
    final public int x;
    final public int z;
    
    public Position(int x, int z){
        this.x = x;
        this.z = z;
    }
    
    @Override
    public boolean equals(Object o){
        if (o.getClass() != Position.class) {
            return false;
        }
        
        Position oo = (Position)o;
        return oo.x == x && oo.z == z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}