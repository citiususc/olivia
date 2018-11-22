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
    
    protected PointColour color_unclassified = new PointColour(0.75f, 0.75f, 0.75f);
    protected PointColour color_unknown = new PointColour(1.0f, 1.0f, 0.5f);
    protected PointColour color_ground = new PointColour(0.4f, 0.2f, 0.0f);
    protected PointColour color_low_veg = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour color_medium_veg = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour color_high_veg = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour color_building = new PointColour(1.0f, 0.0f, 0.0f);
    protected PointColour color_low_point = new PointColour(1.0f, 0.6f, 0.0f);
    protected PointColour color_reserved = new PointColour(1.0f, 1.0f, 0.0f);
    protected PointColour color_water = new PointColour(0.0f, 1.0f, 1.0f);
    protected PointColour color_road = new PointColour(0.5f, 0.5f, 0.5f);
    protected PointColour color_parking = new PointColour(0.0f, 0.0f, 1.0f);

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
                colour = color_unclassified;
                break;
            case PointStandard.UNKNOWN:
                colour = color_unknown;
                break;
            case PointStandard.GROUND:
                colour = color_ground;
                break;
            case PointStandard.LOW_VEG:
                colour = color_low_veg;
                break;
            case PointStandard.MEDIUM_VEG:
                colour = color_medium_veg;
                break;
            case PointStandard.HIGH_VEG:
                colour = color_high_veg;
                break;
            case PointStandard.BUILDING:
                colour = color_building;
                break;
            case PointStandard.LOW_POINT:
                colour = color_low_point;
                break;
            case PointStandard.RESERVED:
                colour = color_reserved;
                break;
            case PointStandard.WATER:
                colour = color_water;
                break;
            case PointStandard.ROAD:
                colour = color_road;
                break;
            case PointStandard.PARKING:
                colour = color_parking;
                break;
            default:
                colour = color_ground;
                break;
        }
        return colour;
    }
    
    
    public void setPointColour(int classID, PointColour colour) {
        switch (classID) {
            case PointStandard.UNCLASSIFIED:
                color_unclassified.setRGB(colour);
                break;
            case PointStandard.UNKNOWN:
                color_unknown.setRGB(colour);
                break;
            case PointStandard.GROUND:
                color_ground.setRGB(colour);
                break;
            case PointStandard.LOW_VEG:
                color_low_veg.setRGB(colour);
                break;
            case PointStandard.MEDIUM_VEG:
                color_medium_veg.setRGB(colour);
                break;
            case PointStandard.HIGH_VEG:
                color_high_veg.setRGB(colour);
                break;
            case PointStandard.BUILDING:
                color_building.setRGB(colour);
                break;
            case PointStandard.LOW_POINT:
                color_low_point.setRGB(colour);
                break;
            case PointStandard.RESERVED:
                color_reserved.setRGB(colour);
                break;
            case PointStandard.WATER:
                color_water.setRGB(colour);
                break;
            case PointStandard.ROAD:
                color_road.setRGB(colour);
                break;
            case PointStandard.PARKING:
                color_parking.setRGB(colour);
                break;
            default:
                color_ground.setRGB(colour);
                break;
        }
    }
    
    
}
