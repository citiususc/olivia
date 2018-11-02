/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended;

/**
 *
 * @author oscar
 */
public class PointS extends PointR {
    
    protected int dir;
    protected int edge;
    
    public PointS(double x, double y, double z, float I, int rn, int nor, int dir, int edge) {
        super(x, y, z, I,rn,nor);
        this.dir = dir;
        this.edge = edge;
    }
    
    public int getDirection() {
        return dir;
    }

    public void setDirection(int dir) {
        this.dir = dir;
    }

    public int getEdge() {
        return edge;
    }

    public void setEdge(int edge) {
        this.nor = edge;
    }
    
}
