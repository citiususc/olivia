package Olivia.scanlines;

import Olivia.core.Olivia;
import Olivia.core.data.Group;
import Olivia.core.data.GroupArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointS;

/**
 *
 * @author oscar
 */
public class EdgesColourArray extends ColourArray {

    public EdgesColourArray(PointArray<PointS> points, GroupArray<Group> lgroups) {
        super(points);
        this.ensureCapacity(points.size());
        Olivia.textOutputter.println("Loading scanline colours");
        for (PointS point : points) {
            switch (point.getEdge()) {
                case 0:
                    this.add(new PointColour(1f, 0f, 0f));
                    break;
                case 1:
                    this.add(new PointColour(0f, 1f, 0f));
                    break;
                default:
                    this.add(new PointColour(0f, 0f, 1f));
                    break;
            }
        }
        Olivia.textOutputter.println("scaline colours loaded");
    }
}
