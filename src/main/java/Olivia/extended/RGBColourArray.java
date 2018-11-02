package Olivia.extended;

import Olivia.core.render.colours.ColourArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;

/**
 * This creates RGB colours for all RGB points
 *
 * @author jorge.martinez.sanchez
 */
public class RGBColourArray extends ColourArray {

    public RGBColourArray(PointArray<PointRGB> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            float r = (float) points.get(i).getR();
            float g = (float) points.get(i).getG();
            float b = (float) points.get(i).getB();
            this.add(new PointColour(r, g, b));
        }
    }
}
