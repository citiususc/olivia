package PointCloudVisu.extended;

/**
 * This is a LiDAR point, it has many extra attibutes, most of wich may not be
 * needed This class may be excesive for you visualisation, or may be to small,
 * it is presented as an example and legacy code
 *
 * @author oscar.garcia
 */
public class PointL extends PointI {

    protected float f;
    protected float g;
    protected int type;
    protected int gIdx;

    public PointL(double x, double y, double z) {
        super(x, y, z, NO_DATA);
    }

    public PointL(int id, double x, double y, double z) {
        super(id, x, y, z, NO_DATA);
    }

    public PointL(int id, double x, double y, double z, float intensity, int type) {
        super(id, x, y, z, intensity);
        this.type = type;
    }

    public PointL(double x, double y, double z, double I) {
        super(x, y, z, (float) I);
    }

    public PointL(double x, double y, double z, float I, int id) {
        super(id, x, y, z, I);
        this.type = NO_DATA;
    }
    
    public float getF() {
        return f;
    }

    public float getG() {
        return g;
    }

    public int getType() {
        return type;
    }

    void setType(int t) {
        type = t;
    }

    public int getGroupId() {
        return gIdx;
    }
}
