package Olivia.core.data;

import java.text.DecimalFormat;

/**
 * This is a point in 3D, it has (x,y,z) and an id which may be not set. This all
 * the core package needs, if you want something else (such as intensity) it is
 * up to the Visualisation. For intensity specifically, there is already a class
 *
 * @author oscar.garcia
 */
public class Point3D_id extends Point3D{

    public static int NO_DATA = -1;

    protected int id;

    public Point3D_id(double x, double y, double z) {
        super(x,y,z);
        this.id = NO_DATA;
    }

    public Point3D_id(int id, double x, double y, double z) {
        super(x,y,z);
    }
    
    public Point3D_id(Point3D point) {
        super(point.x,point.y,point.z);
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
        if (!(other instanceof Point3D_id)) {
            return false;
        }
        Point3D_id point = (Point3D_id) other;
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

    /**
     * This creates a description as HTML, override as needed
     *
     * @param center Center of mass need to recover original point coordinates
     * @return The description as html
     */
    /*
    @Override
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
    */
    
    /**
     * This creates a description as HTML, override as needed
     *
     * @param x The x coordinate of the displacement this point had
     * @param y The y coordinate of the displacement this point had
     * @param z The z coordinate of the displacement this point had
     * @return The description as html
     */
    @Override
    public String getDescriptionAsHTML(double x, double y, double z) {
        DecimalFormat df = new DecimalFormat("#.00");
        String d = "<html><p>("
                + "X : " + df.format(this.x + x) + " "
                + "Y : " + df.format(this.y + y) + " "
                + "Z : " + df.format(this.z + z) + ")<ul>"
                + "<li>Id: " + Integer.toString(id) + "</li>"
                + "</ul></html>";
        return d;
    }
    
    @Override
    public String getDescriptionAsHTML() {
        DecimalFormat df = new DecimalFormat("#.00");
        String d = "<html><p>("
                + "X : " + df.format(x) + " "
                + "Y : " + df.format(y) + " "
                + "Z : " + df.format(z) + ")<ul>"
                + "<li>Id: " + Integer.toString(id) + "</li>"
                + "</ul></html>";
        return d;
    }

}
