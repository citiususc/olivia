package Olivia.core.data;

import java.text.DecimalFormat;
import java.util.HashSet;

/**
 * This is a class for groups, the core package does not use them, but they are
 * so common a few ground rules may be good. A Group is a collection of points
 * with an ID and the summations of the coordinates. The core app uses points by
 * themselves, groups are always used in visualisations, for example to paint
 * colours
 *
 * @author oscar.garcia
 * @param <P> The kind of points this group collects
 */
public class Group<P extends Point3D_id> {

    /**
     * This means the ID is not set
     */
    public static int NO_ID = -1;
    /**
     * A hash set stores the points as it should be faster to check belonging
     */
    protected HashSet<P> points;
    /**
     * The id
     */
    protected int id;
    /**
     * The sum of X values
     */
    protected double sumX;
    /**
     * The sum of Y values
     */
    protected double sumY;
    /**
     * The sum of Z values
     */
    protected double sumZ;

    /**
     * Creates a hash set, does not set id
     */
    public Group() {
        points = new HashSet<>();
        id = NO_ID;
        sumX = 0;
        sumY = 0;
        sumZ = 0;
    }

    /**
     * getter
     *
     * @return The point collection
     */
    public HashSet getPoints() {
        return points;
    }

    /**
     * Adds a point to the collection
     *
     * @param point The point
     */
    public void addPoint(P point) {
        points.add(point);
        sumX += point.x;
        sumY += point.y;
        sumZ += point.z;
    }

    /**
     * getter
     *
     * @return The group ID
     */
    public int getId() {
        return id;
    }

    /**
     * setter
     *
     * @param id The ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the id only if it is not already set
     *
     * @param id The new id
     */
    public void setIdIfNotSet(int id) {
        if (this.id == NO_ID) {
            this.id = id;
        }
    }

    /**
     * getter
     *
     * @return The mean of the X values
     */
    public double getMeanX() {
        return sumX / points.size();
    }

    /**
     * getter
     *
     * @return The mean of the Y values
     */
    public double getMeanY() {
        return sumY / points.size();
    }

    /**
     * getter
     *
     * @return The mean of the Z values
     */
    public double getMeanZ() {
        return sumZ / points.size();
    }

    /**
     * This creates a description as HTML, override as needed
     *
     * @return The description as html
     */
    public String getDescriptionAsHTML() {
        DecimalFormat df = new DecimalFormat("#.00");
        String html = "<html><p>("
                + df.format(getMeanX()) + ", "
                + df.format(getMeanY()) + ", "
                + df.format(getMeanZ()) + ")<ul>"
                + "<li>Id: " + Integer.toString(id) + "</li>"
                + "<li>Size: " + Integer.toString(points.size()) + "</li>"
                + "</ul></html>";
        return html;
    }
}
