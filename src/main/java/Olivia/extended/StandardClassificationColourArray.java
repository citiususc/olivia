package Olivia.extended;

import Olivia.core.render.colours.ColourArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;

/**
 * This creates classification colours for all standard points
 *
 * @author jorge.martinez.sanchez oscar.garcia
 * @param <P> Point class with support for classification (cannot redefine standard classes, need more code for that)
 */
public class StandardClassificationColourArray<P extends PointStandard> extends ColourArray {
    
    protected PointColour COLOR_UNCLASSIFIED = new PointColour(0.75f, 0.75f, 0.75f);
    protected PointColour COLOR_UNKNOWN = new PointColour(1.0f, 1.0f, 0.5f);
    protected PointColour COLOR_GROUND = new PointColour(0.4f, 0.2f, 0.0f);
    protected PointColour COLOR_LOW_VEG = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour COLOR_MEDIUM_VEG = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour COLOR_HIGH_VEG = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour COLOR_BUILDING = new PointColour(1.0f, 0.0f, 0.0f);
    protected PointColour COLOR_LOW_POINT = new PointColour(1.0f, 0.6f, 0.0f);
    protected PointColour COLOR_RESERVED = new PointColour(1.0f, 1.0f, 0.0f);
    protected PointColour COLOR_WATER = new PointColour(0.0f, 1.0f, 1.0f);
    protected PointColour COLOR_ROAD = new PointColour(0.5f, 0.5f, 0.5f);
    protected PointColour COLOR_PARKING = new PointColour(0.0f, 0.0f, 1.0f);

    public StandardClassificationColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            this.add(getPointColour(points.get(i).getClassification()));
        }
    }
    
    public PointColour getPointColour(int classID) {
        PointColour colour;
        switch (classID) {
            case PointStandard.UNCLASSIFIED:
                colour = COLOR_UNCLASSIFIED;
                break;
            case PointStandard.UNKNOWN:
                colour = COLOR_UNKNOWN;
                break;
            case PointStandard.GROUND:
                colour = COLOR_GROUND;
                break;
            case PointStandard.LOW_VEG:
                colour = COLOR_LOW_VEG;
                break;
            case PointStandard.MEDIUM_VEG:
                colour = COLOR_MEDIUM_VEG;
                break;
            case PointStandard.HIGH_VEG:
                colour = COLOR_HIGH_VEG;
                break;
            case PointStandard.BUILDING:
                colour = COLOR_BUILDING;
                break;
            case PointStandard.LOW_POINT:
                colour = COLOR_LOW_POINT;
                break;
            case PointStandard.RESERVED:
                colour = COLOR_RESERVED;
                break;
            case PointStandard.WATER:
                colour = COLOR_WATER;
                break;
            case PointStandard.ROAD:
                colour = COLOR_ROAD;
                break;
            case PointStandard.PARKING:
                colour = COLOR_PARKING;
                break;
            default:
                colour = COLOR_GROUND;
                break;
        }
        return colour;
    }
}
