package Olivia.core.data;

import java.text.DecimalFormat;

/**
 * This is a point in 3D, it has (x,y,z) and an id which may be not set. This all
 * the core package needs, if you want something else (such as intensity) it is
 * up to the Visualisation. For intensity specifically, there is already a class
 *
 * @author oscar.garcia
 */
public class Point3D {

    /**
     * A default epsilon to use in the areCloseBy() methods
     */
    public static double EPSILON_CB = 0.5;

    /**
     * The X coordinate
     */
    protected double x;
    /**
     * The Y coordinate
     */
    protected double y;
    /**
     * The Z coordinate
     */
    protected double z;
    
    /**
     * Creates a Point3D at (0,0,0)
     */
    public Point3D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    /**
     * Creates a Point3D at (x,y,z)
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x coordinate
     * @param x the x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y coordinate
     * @param y the y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the z coordinate
     * @return the z coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the z coordinate
     * @param z the z coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Sets the point coordinates, overwriting the old ones
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public void setCoords(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Sets all coordinates to zero
     */
    public void setToZero(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    /**
     * Copies the coordinates of point in this instance, for when you need to keep the reference
     * @param point The point whose coordinates will be copied
     */
    public void copyCoords(Point3D point){
        this.x = point.getX();
        this.y = point.getY();
        this.z = point.getZ();
    }
    
    /**
     * Adds the coordinates of point to the ones in this instance, this.x += point.x ...
     * @param point The point whose coordinates values will be added to this
     */
    public void addToCoords(Point3D point){
        this.x += point.getX();
        this.y += point.getY();
        this.z += point.getZ();
    }
    
    /**
     * Adds to the coordinates of this instance the values given
     * @param x value to add to coordinate x
     * @param y value to add to coordinate y
     * @param z value to add to coordinate z
     */
    public void addToCoords(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    /**
     * Subtracts the coordinates of point to the ones in this instance, this.x -= point.x ...
     * @param point The point whose coordinates values will be subtracted to this
     */
    public void subToCoords(Point3D point){
        this.x -= point.getX();
        this.y -= point.getY();
        this.z -= point.getZ();
    }
    
    /**
     * Subtracts to the coordinates of this instance the values given
     * @param x value to subtract to coordinate x
     * @param y value to subtract to coordinate y
     * @param z value to subtract to coordinate z
     */
    public void subToCoords(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }
    
    /**
     * Calculates the dot product of this Point3D with another
     * @param point the other Point3D to do the dot product
     * @return the dot product, this.x*point.x + this.y*point.y + this.z*point.z
     */
    public double dotProduct(Point3D point){
        return (this.x*point.x + this.y*point.y + this.z*point.z);
    }
    
    /**
     * Calculates the norm of this Point3D
     * @return the norm sqrt(this.x*this.x + this.y*this.y + this.z*this.z)
     */
    public double Norm(){
        return Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
    }
    
    /**
     * If the point have id it checks them, else is a (x,y,z) comparison
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
        return (this.x == point.x) && (this.y == point.y) && (this.z == point.z);
    }

    /**
     * Creates a hash code of the point, based on its x, y, z coordinates
     * @return a hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    
    /**
     * To compare points, in case equal is too restricting
     * @param point The point to check if it is close by
     * @param epsilon The maximum distance in x, y and z point must be
     * @return true if ALL point coordinates are closer than epsilon
     */
    public boolean areCloseBy(Point3D point, double epsilon) {
        return ((((point.x - epsilon) <= this.x) && (this.x <= (point.x + epsilon)))
                && (((point.y - epsilon) <= this.y) && (this.y <= (point.y + epsilon)))
                && (((point.z - epsilon) <= this.z) && (this.z <= (point.z + epsilon))));
    }
    
    /**
     * To compare points, in case equal is too restricting, the default epsilon EPSILON_CB is used
     * @param point The point to check if it is close by
     * @return true if ALL point coordinates are closer than epsilon
     */
    public boolean areCloseBy(Point3D point) {
        return areCloseBy(point, EPSILON_CB);
    }
    
    /**
     * To compare points, in case equal is too restricting
     * @param coords an array of three doubles representing x, y, z
     * @param epsilon The maximum distance in x, y and z point must be
     * @return true if ALL coordinates are closer than epsilon
     */
    public boolean areCloseBy(double[] coords, double epsilon) {
        if(coords.length!=3) return false;
        return ((((coords[0] - epsilon) <= this.x) && (this.x <= (coords[0] + epsilon)))
                && (((coords[1] - epsilon) <= this.y) && (this.y <= (coords[1] + epsilon)))
                && (((coords[2] - epsilon) <= this.z) && (this.z <= (coords[2] + epsilon))));
    }
    
    /**
     * To compare points, in case equal is too restricting, the default epsilon EPSILON_CB is used
     * @param coords an array of three doubles representing x, y, z
     * @return true if ALL coordinates are closer than epsilon
     */
    public boolean areCloseBy(double[] coords) {
        return areCloseBy(coords, EPSILON_CB);
    }

    /**
     * This creates a description as HTML, override getDescriptionAsHTML(double x, double y, double z) if needed
     * @param center Center of mass need to recover original point coordinates
     * @return The description as html
     */
    public String getDescriptionAsHTML(double[] center) {
        return getDescriptionAsHTML(center[0],center[1],center[2]);
    }
    
    /**
     * This creates a description as HTML, override getDescriptionAsHTML(double x, double y, double z) if needed
     * @param center Center of mass need to recover original point coordinates
     * @return The description as html
     */
    public String getDescriptionAsHTML(Point3D center) {
        return getDescriptionAsHTML(center.getX(),center.getY(),center.getZ());
    }
    
    /**
     * This creates a description as HTML, adding the values of x, y, z to this ones, override as needed
     * @param x The x coordinate of the displacement this point had
     * @param y The y coordinate of the displacement this point had
     * @param z The z coordinate of the displacement this point had
     * @return The description as html
     */
    public String getDescriptionAsHTML(double x, double y, double z) {
        DecimalFormat df = new DecimalFormat("#.00");
        String d = "<html><p>("
                + "X : " + df.format(this.x + x) + " "
                + "Y : " + df.format(this.y + y) + " "
                + "Z : " + df.format(this.z + z) + ")<ul>"
                + "</ul></html>";
        return d;
    }
    
    /**
     * This creates a description as HTML, override as needed
     * @return The description as html
     */
    public String getDescriptionAsHTML() {
        DecimalFormat df = new DecimalFormat("#.00");
        String d = "<html><p>("
                + "X : " + df.format(x) + " "
                + "Y : " + df.format(y) + " "
                + "Z : " + df.format(z) + ")<ul>"
                + "</ul></html>";
        return d;
    }

    /**
     * To get a String describing the point according to x, y, z
     * @return a String with x, y, z
     */
    @Override
    public String toString() {
        return this.x + " " + this.y + " " + this.z;
    }
    
    /**
     * To get an array with three values, x, y, z
     * @return an array with three values, x, y, z
     */
    public double[] toArray(){
        double[] array = new double[3];
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        return array;
    }
}
