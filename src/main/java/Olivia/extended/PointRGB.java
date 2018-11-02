package Olivia.extended;

import Olivia.core.data.Point3D_id;

/**
 * This is a Point3D_id with RGB colours, requires RGB values on creation
 *
 * @author jorge.martinez.sanchez
 */
public class PointRGB extends Point3D_id {

    protected double r;
    protected double g;
    protected double b;

    public PointRGB(double x, double y, double z, double r, double g, double b) {
        super(x, y, z);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}
