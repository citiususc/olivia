package PointCloudVisu.core.data;

import java.util.ArrayList;

/**
 * A GroupArray keeps the groups collected and allows for a few operations.
 *
 * @author oscar.garcia
 */
public class GroupArray<G extends Group> extends ArrayList<G> {

    /**
     * Gets the total number of point in all the groups in the array
     *
     * @return The total number of points
     */
    public int getTotalNumberOfPoints() {
        int total = 0;
        for (G group : this) {
            total += group.getPoints().size();
        }
        return total;
    }

    /**
     * Gets the first group the point is in
     *
     * @param point The point to locate
     * @return null if the point is not in any group, the group of the point
     */
    public G getGroupFromPoint(Point3D point) {
        for (G group : this) {
            if (group.getPoints().contains(point)) {
                return group;
            }
        }
        return null;
    }

    /**
     * Adds a group setting an ID if it is not set
     *
     * @param group The group to add
     * @return true if it has been succesful
     */
    @Override
    public boolean add(G group) {
        group.setIdIfNotSet(this.size());
        return super.add(group);

    }
}
