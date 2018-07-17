package PointCloudVisu.extended;

import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.PointColour;

/**
 * This creates RGB colours for all standard points
 *
 * @author jorge.martinez.sanchez oscar.garcia
 * @param <P> Point class with support for RGB
 */
public class StandardRGBColourArray<P extends PointStandard> extends ColourArray {

    public StandardRGBColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            float r = points.get(i).getR_asFloat();
            float g = points.get(i).getG_asFloat();
            float b = points.get(i).getB_asFloat();
            this.add(new PointColour(r, g, b));
        }
    }
}
