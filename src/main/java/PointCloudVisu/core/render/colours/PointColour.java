package PointCloudVisu.core.render.colours;

/**
 * To do stuff with colours, mostly just stores RGB values, maybe be needed for
 * something, but quite generic
 *
 * @author oscar.garcia
 */
public class PointColour {

    protected float R;
    protected float G;
    protected float B;

    public PointColour(float a, float b, float c) {
        this.R = a;
        this.G = b;
        this.B = c;
    }

    public Float getR() {
        return R;
    }

    public void setR(float a) {
        this.R = a;
    }

    public Float getG() {
        return G;
    }

    public void setG(float b) {
        this.G = b;
    }

    public Float getB() {
        return B;
    }

    public void setB(float c) {
        this.B = c;
    }
}
