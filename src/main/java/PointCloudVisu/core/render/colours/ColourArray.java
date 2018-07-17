package PointCloudVisu.core.render.colours;

import PointCloudVisu.core.data.PointArray;
import java.util.ArrayList;

/**
 * An array to save the color of each point independently, tries to ensure there
 * is enough space but DOES NOT initialise all the values to save time, so it
 * may not be the same size as the PointArray To render the points, each point
 * needs a colour
 *
 * @author oscar.garcia
 */
public abstract class ColourArray extends ArrayList<PointColour> {

    /**
     * It needs a PointArray to be instantiated, it DOES NOT initialise all the
     * values to save time, so it may not be the same size as the PointArray
     *
     * @param points The PointArray whose colours we want to define
     */
    public ColourArray(PointArray points) {
        this.ensureCapacity(points.size());
    }
}
