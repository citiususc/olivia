package Olivia.scanlines;

import Olivia.core.data.Group;
import Olivia.core.data.GroupArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointS;
import java.util.Random;

/**
 * This class defines the colour of each scan line (random colours)
 *
 * @author jorge.martinez.sanchez
 */
public class ScanlinesColourArray extends ColourArray {

    public ScanlinesColourArray(PointArray<PointS> points, GroupArray<Group> groups) {
        super(points);
        ensureCapacity(points.size());
        Random rand = new Random(0);
        for (Group group : groups) {
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            PointColour colour = new PointColour(r, g, b);
            for (int i = 0; i < group.getPoints().size(); i++) {
                add(colour);
            }
        }
    }
}
