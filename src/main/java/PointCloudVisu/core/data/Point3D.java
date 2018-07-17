package PointCloudVisu.core.data;

import java.text.DecimalFormat;

/**
 * This is a point in 3D, it has (x,y,z) and an id which may be not set. This all
 * the core package needs, if you want something else (such as intensity) it is
 * up to the Visualisation. For intensity specifically, there is already a class
 *
 * @author oscar.garcia
 */
public class Point3D {

    public static int NO_DATA = -1;
    public static float EPSILON_CB = 0.5f;

    protected int id;
    protected double x;
    protected double y;
    protected double z;

    public Point3D(double x, double y, double z) {
        this.id = NO_DATA;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdIfNotSet(int id) {
        if (this.id == NO_DATA) {
            this.id = id;
        }
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * If the point have id it checks them, else is a (x,y,z) comparisson
     *
     * @param other The point to compare
     * @return true if they are the same
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Point3D)) {
            return false;
        }
        Point3D point = (Point3D) other;
        if ((this.id != -1) && (point.getId() != -1)) {
            return (this.id == point.getId());
        } else {
            return (this.x == point.x) && (this.y == point.y) && (this.z == point.z);
        }
    }

    /**
     * This is added when overriding equal. Using only the id for the hash
     * because the x,y,z may be modified and the points saved in Group.points
     * would no longer have the same key, probably should change the HashSet in
     * Group
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.id;
        return hash;
    }

    /*
     * In case equal is too restricting
     */
    public boolean areCloseBy(Point3D point) {
        return ((((point.x - EPSILON_CB) <= this.x) && (this.x <= (point.x + EPSILON_CB)))
                && (((point.y - EPSILON_CB) <= this.y) && (this.y <= (point.y + EPSILON_CB)))
                && (((point.z - EPSILON_CB) <= this.z) && (this.z <= (point.z + EPSILON_CB))));
    }

    /*
     * In case equal is too restricting
     */
    public boolean areCloseBy(Point3D point, float eps) {
        return (((point.x - eps <= this.x) && (this.x <= point.x + eps))
                && ((point.y - eps <= this.y) && (this.y <= point.y + eps))
                && ((point.z - eps <= this.z) && (this.z <= point.z + eps)));
    }

    /**
     * This creates a description as HTML, override as needed
     *
     * @param center Center of mass need to recover original point coordinates
     * @return The description as html
     */
    public String getDescriptionAsHTML(double[] center) {
        DecimalFormat df = new DecimalFormat("#.00");
        String d = "<html><p>("
                + "X : " + df.format(x + center[0]) + " "
                + "Y : " + df.format(y + center[1]) + " "
                + "Z : " + df.format(z + center[2]) + ")<ul>"
                + "<li>Id: " + Integer.toString(id) + "</li>"
                + "</ul></html>";
        return d;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y + " " + this.z;
    }
}
