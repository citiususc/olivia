package Olivia.extended;

/**
 * This is a PointI with return information
 *
 * @author jorge.martinez.sanchez
 */
public class PointR extends PointI {

    protected int rn;
    protected int nor;

    public PointR(double x, double y, double z, float I, int rn, int nor) {
        super(x, y, z, I);
        this.rn = rn;
        this.nor = nor;
    }

    public int getReturnNumber() {
        return rn;
    }

    public void setReturnNumber(int rn) {
        this.rn = rn;
    }

    public int getNumberOfReturns() {
        return nor;
    }

    public void setNumberOfReturns(int nor) {
        this.nor = nor;
    }
}
