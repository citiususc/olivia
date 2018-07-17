package PointCloudVisu.scanlines;

import PointCloudVisu.core.data.Group;
import PointCloudVisu.core.data.GroupArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.render.colours.PointColour;
import PointCloudVisu.extended.PointS;

/**
 *
 * @author oscar
 */
public class EdgesColourArray extends ColourArray {

    public EdgesColourArray(PointArray<PointS> points, GroupArray<Group> lgroups) {
        super(points);
        this.ensureCapacity(points.size());
        System.out.println("Loading scanline colours");
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
        System.out.println("scaline colours loaded");
    }
}
