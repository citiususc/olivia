package PointCloudVisu.extended;

import PointCloudVisu.core.data.Point3D;
import java.text.DecimalFormat;

/**
 * This is a Point3D with intensity
 *
 * @author oscar.garcia
 */
public class PointI extends Point3D {

    protected float I;

    public PointI(double x, double y, double z, float I) {
        super(x, y, z);
        this.I = I;
    }

    public PointI(int id, double x, double y, double z, float I) {
        super(id, x, y, z);
        this.I = I;
    }

    public float getIntensity() {
        return I;
    }

    public void setIntensity(float intensity) {
        this.I = intensity;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y + " " + this.z + " " + this.I;
    }

    @Override
    public String getDescriptionAsHTML(double[] center) {
        DecimalFormat df = new DecimalFormat("#.00");
        return "<html><p>"
                + "<b>Id</b>: " + Integer.toString(id) + " "
                + "<b>X</b> : " + df.format(x + center[0]) + " "
                + "<b>Y</b> : " + df.format(y + center[1]) + " "
                + "<b>Z</b> : " + df.format(z + center[2]) + " "
                + "<b>I</b>: " + df.format(I)
                + "</html>";
    }
}
