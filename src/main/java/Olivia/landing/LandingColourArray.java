package Olivia.landing;

import Olivia.core.render.colours.ColourArray;
import Olivia.extended.PointI;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;

/**
 *
 * @author oscar.garcia
 */
public class LandingColourArray extends ColourArray {

    public LandingColourArray(PointArray<PointI> points, LandingGroupArray lgroups) {
        super(points);
        this.ensureCapacity(points.size());
        System.out.println("Loading landing colours");
        for (PointI point : points) {
            for (LandingGroup group : lgroups) {
                if (group.getPoints().contains(point)) {
                    this.add(getGroupColour(group));
                }
            }
        }
        System.out.println("landing colours loaded");
    }

    private PointColour getGroupColour(LandingGroup lGroup) {
        PointColour ret;// = new PointColour(0.0f,0.0f,0.0f);;

        switch (lGroup.getType2()) {
            default:
                ret = new PointColour(0.0f, 0.0f, 0.0f);
                break;
            //UNSORTED, BAD
            case 0:
                if (lGroup.getType1() <= 1) {
                    ret = new PointColour(0.0f, 0.0f, 0.0f);
                } else {
                    ret = new PointColour(0.5f, 0.0f, 0.0f);
                }
                break;
            //Planar
            case 1:
                switch (lGroup.getType1()) {
                    default:
                        ret = new PointColour(0.0f, 0.0f, 0.0f);
                        break;
                    case 2:
                        ret = new PointColour(0.4f, 0.0f, 0.0f);
                        break;
                    case 5:
                        ret = new PointColour(0.5f, 0.0f, 0.0f);
                        break;
                    case 6:
                        ret = new PointColour(0.55f, 0.0f, 0.0f);
                        break;
                    case 7:
                        ret = new PointColour(0.6f, 0.0f, 0.0f);
                        break;
                    case 8:
                        ret = new PointColour(0.65f, 0.0f, 0.0f);
                        break;
                    case 9:
                        ret = new PointColour(0.7f, 0.0f, 0.0f);
                        break;
                    case 10:
                        ret = new PointColour(0.8f, 0.0f, 0.0f);
                        break;
                    case 11:
                        ret = new PointColour(0.9f, 0.0f, 0.0f);
                        break;
                    case 12:
                        ret = new PointColour(1.0f, 0.0f, 0.0f);
                        break;
                }
                break;
            //Part Risky
            case 2:
                ret = new PointColour(0.0f, 0.0f, 0.5f);
                break;
            //Risky
            case 3:
                switch (lGroup.getType1()) {
                    default:
                        ret = new PointColour(0.0f, 0.0f, 0.60f);
                        break;
                    case 5:
                        ret = new PointColour(0.0f, 0.0f, 0.65f);
                        break;
                    case 6:
                        ret = new PointColour(0.0f, 0.0f, 0.7f);
                        break;
                    case 7:
                        ret = new PointColour(0.0f, 0.0f, 0.75f);
                        break;
                    case 8:
                        ret = new PointColour(0.0f, 0.0f, 0.80f);
                        break;
                    case 9:
                        ret = new PointColour(0.0f, 0.0f, 0.85f);
                        break;
                    case 10:
                        ret = new PointColour(0.0f, 0.0f, 0.9f);
                        break;
                    case 11:
                        ret = new PointColour(0.0f, 0.0f, 0.95f);
                        break;
                    case 12:
                        ret = new PointColour(0.0f, 0.0f, 1.0f);
                        break;
                }
                break;
            case 4:
                ret = new PointColour(0.5f, 0.0f, 0.5f);
                break;
            //Part Good
            case 6:
                switch (lGroup.getType1()) {
                    default:
                        ret = new PointColour(0.5f, 0.5f, 0.0f);
                        break;
                    case 9:
                        ret = new PointColour(0.0f, 0.5f, 0.0f);
                        break;
                    case 10:
                        ret = new PointColour(0.0f, 0.5f, 0.0f);
                        break;
                    case 11:
                        ret = new PointColour(0.0f, 0.5f, 0.0f);
                        break;
                    case 12:
                        ret = new PointColour(0.0f, 0.5f, 0.0f);
                        break;
                }
                break;
            //Good
            case 7:
                switch (lGroup.getType1()) {
                    default:
                        ret = new PointColour(0.7f, 0.7f, 0.0f);
                        break;
                    case 5:
                        ret = new PointColour(0.8f, 0.8f, 0.0f);
                        break;
                    case 6:
                        ret = new PointColour(0.8f, 0.8f, 0.0f);
                        break;
                    case 7:
                        ret = new PointColour(0.9f, 0.9f, 0.0f);
                        break;
                    case 8:
                        ret = new PointColour(1.0f, 1.0f, 0.0f);
                        break;
                    case 9:
                        ret = new PointColour(0.0f, 0.7f, 0.0f);
                        break;
                    case 10:
                        ret = new PointColour(0.0f, 0.8f, 0.0f);
                        break;
                    case 11:
                        ret = new PointColour(0.0f, 0.8f, 0.0f);
                        break;
                    case 12:
                        ret = new PointColour(0.0f, 1.0f, 0.0f);
                        break;
                }
                break;
            //APROX
            case 8:
                ret = new PointColour(1.0f, 0.5f, 0.7f);
                break;
            //Good AProx
            case 9:
                switch (lGroup.getType1()) {
                    default:
                        ret = new PointColour(0.7f, 0.7f, 0.5f);
                        break;
                    case 5:
                        ret = new PointColour(0.8f, 0.8f, 0.5f);
                        break;
                    case 6:
                        ret = new PointColour(0.8f, 0.8f, 0.5f);
                        break;
                    case 7:
                        ret = new PointColour(0.9f, 0.9f, 0.5f);
                        break;
                    case 8:
                        ret = new PointColour(1.0f, 1.0f, 0.5f);
                        break;
                    case 9:
                        ret = new PointColour(0.0f, 0.7f, 0.5f);
                        break;
                    case 10:
                        ret = new PointColour(0.0f, 0.8f, 0.5f);
                        break;
                    case 11:
                        ret = new PointColour(0.0f, 0.8f, 0.5f);
                        break;
                    case 12:
                        ret = new PointColour(0.0f, 1.0f, 0.5f);
                        break;
                }
                break;
            //PERFECT AProx
            case 10:
                switch (lGroup.getType1()) {
                    default:
                        ret = new PointColour(0.9f, 0.60f, 0.60f);
                        break;
                    case 5:
                        ret = new PointColour(0.9f, 0.65f, 0.65f);
                        break;
                    case 6:
                        ret = new PointColour(0.9f, 0.7f, 0.7f);
                        break;
                    case 7:
                        ret = new PointColour(0.9f, 0.75f, 0.75f);
                        break;
                    case 8:
                        ret = new PointColour(0.9f, 0.8f, 0.8f);
                        break;
                    case 9:
                        ret = new PointColour(0.85f, 0.85f, 0.85f);
                        break;
                    case 10:
                        ret = new PointColour(0.9f, 0.9f, 0.9f);
                        break;
                    case 11:
                        ret = new PointColour(0.95f, 0.95f, 0.95f);
                        break;
                    case 12:
                        ret = new PointColour(1.0f, 1.0f, 1.0f);
                        break;
                }
                break;
        }
        return ret;
    }
}
