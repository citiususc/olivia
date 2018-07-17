package PointCloudVisu.extended;

import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.render.colours.PointColour;
import java.awt.Color;

/**
 * Put the same color along the array, used mainly for drawing OpenGL primitives
 *
 * @author jorge.martinez.sanchez
 */
public class SingleColourArray extends ColourArray {

    public SingleColourArray(PointArray points, Color color) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            float r = (float) color.getRed();
            float g = (float) color.getGreen();
            float b = (float) color.getBlue();
            this.add(new PointColour(r, g, b));
        }
    }
}
