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

    public static double EPSILON_CB = 0.5;

    protected double x;
    protected double y;
    protected double z;
    
    public Point3D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void setCoords(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setToZero(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public void copyCoords(Point3D point){
        this.x = point.getX();
        this.y = point.getY();
        this.z = point.getZ();
    }
    
    public void addToCoords(Point3D point){
        this.x += point.getX();
        this.y += point.getY();
        this.z += point.getZ();
    }
    
    public void addToCoords(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    public void subToCoords(Point3D point){
        this.x -= point.getX();
        this.y -= point.getY();
        this.z -= point.getZ();
    }
    
    public void subToCoords(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }
    
    
    public double dotProduct(Point3D point){
        return (this.x*point.x + this.y*point.y + this.z*point.z);
    }
    
    public double Norm(){
        return Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
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
        return (this.x == point.x) && (this.y == point.y) && (this.z == point.z);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    
        /*
     * In case equal is too restricting
     */
    public boolean areCloseBy(Point3D point, double epsilon) {
        return ((((point.x - epsilon) <= this.x) && (this.x <= (point.x + epsilon)))
                && (((point.y - epsilon) <= this.y) && (this.y <= (point.y + epsilon)))
                && (((point.z - epsilon) <= this.z) && (this.z <= (point.z + epsilon))));
    }
    
        /*
     * In case equal is too restricting
     */
    public boolean areCloseBy(Point3D point) {
        return areCloseBy(point, EPSILON_CB);
    }
    
    /*
     * In case equal is too restricting
     */
    public boolean areCloseBy(double[] coords, double epsilon) {
        if(coords.length!=3) return false;
        return ((((coords[0] - epsilon) <= this.x) && (this.x <= (coords[0] + epsilon)))
                && (((coords[1] - epsilon) <= this.y) && (this.y <= (coords[1] + epsilon)))
                && (((coords[2] - epsilon) <= this.z) && (this.z <= (coords[2] + epsilon))));
    }
    
        /*
     * In case equal is too restricting
     */
    public boolean areCloseBy(double[] coords) {
        return areCloseBy(coords, EPSILON_CB);
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
                + "</ul></html>";
        return d;
    }
    
    public String getDescriptionAsHTML() {
        DecimalFormat df = new DecimalFormat("#.00");
        String d = "<html><p>("
                + "X : " + df.format(x) + " "
                + "Y : " + df.format(y) + " "
                + "Z : " + df.format(z) + ")<ul>"
                + "</ul></html>";
        return d;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y + " " + this.z;
    }
    
    public double[] toArray(){
        double[] array = new double[3];
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        return array;
    }
}
