/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.grid;

import mygame.grid.Grid;
import com.jme3.math.Vector3f;

/**
 *
 * @author Xavier
 */
public class NaviPath {
    final Vector3f[] p;
    final Grid source;
    final Grid target;
    final float length;
    
    public NaviPath(Grid source, Grid target, Vector3f[] wayPoints){
        this.source = source;
        this.target = target;
        this.p = wayPoints;
        
        float chord = p[0].distance(p[p.length-1]);
        float conNet = 0;
        for (int i = 0; i < p.length - 1; ++i) {
            conNet += p[i].distance(p[i+1]);
        }
        length = (chord + conNet)/2;
    }
    
    
    public Vector3f getDirection(float t){
        if (t < 0 || t > 1) {
            throw new Error("getPosition t=" + t);
        }
        switch (p.length) {
                case 2:
                    return p[1].subtract(p[0]).normalizeLocal();
                case 3:
                   return p[1].subtract(p[0]).multLocal(2*(1-t))
                            .addLocal(p[2].subtract(p[1]).multLocal(2*t)).normalizeLocal();
                case 4:
                    return p[1].subtract(p[0]).multLocal(3 * (1 - t) * (1 - t))
                            .addLocal(p[2].subtract(p[1]).multLocal(6 * (1 - t) * t))
                            .addLocal(p[3].subtract(p[2]).multLocal(3 * t * t)).normalizeLocal();
                default:
                    throw new Error("Way points number error!");
            }
    }
    
    public Vector3f getPosition(float t){
        if (t < 0 || t > 1) {
            throw new Error("getPosition t=" + t);
        }
        switch (p.length) {
                case 2:
                    return p[1].subtract(p[0]).multLocal(t).addLocal(p[0]);
                case 3:
                    return p[0].mult((1-t)*(1-t))
                            .addLocal(p[1].mult(2*t*(1-t)))
                            .addLocal(p[2].mult(t*t));
                case 4:
                    return p[0].mult((1 - t) * (1 - t) * (1 - t))
                            .addLocal(p[1].mult(3 * (1 - t) * (1 - t) * t))
                            .addLocal(p[2].mult(3 * (1 - t) * t * t))
                            .addLocal(p[3].mult(t * t * t));
                default:
                    throw new Error("Way points number error!");
            }
    }
}
