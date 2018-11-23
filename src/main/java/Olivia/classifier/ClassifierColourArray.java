package Olivia.classifier;

import static Olivia.classifier.ClassifierLabels.*;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;

/**
 * This class defines the colour of each classification label
 * 
 * @author jorge.martinez.sanchez
 * @author oscar.garcia
 */
public class ClassifierColourArray extends ColourArray {

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

    public ClassifierColourArray(PointArray<PointI> points, ClassifierGroupArray<ClassifierGroup> groups) {
        super(points);
        ensureCapacity(points.size());
        for (ClassifierGroup group : groups) {
            for (int i = 0; i < group.getPoints().size(); i++) {
                add(getGroupColour(group.getType()));
            }
        }
    }
    
    public ClassifierColourArray(PointArray<PointI> points, int groupType) {
        super(points);
        ensureCapacity(points.size());
        for (int i = 0; i < points.size(); i++) {
            add(getGroupColour(groupType));
        }
    }

    public PointColour getGroupColour(int type) {
        PointColour colour;
        switch (type) {
            case UNCLASSIFIED:
                colour = color_unclassified;
                break;
            case UNKNOWN:
                colour = color_unknown;
                break;
            case GROUND:
                colour = color_ground;
                break;
            case LOW_VEG:
                colour = color_low_veg;
                break;
            case MEDIUM_VEG:
                colour = color_medium_veg;
                break;
            case HIGH_VEG:
                colour = color_high_veg;
                break;
            case BUILDING:
                colour = color_building;
                break;
            case LOW_POINT:
                colour = color_low_point;
                break;
            case RESERVED:
                colour = color_reserved;
                break;
            case WATER:
                colour = color_water;
                break;
            case ROAD:
                colour = color_road;
                break;
            case PARKING:
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
            case UNCLASSIFIED:
                color_unclassified.setRGB(colour);
                break;
            case UNKNOWN:
                color_unknown.setRGB(colour);
                break;
            case GROUND:
                color_ground.setRGB(colour);
                break;
            case LOW_VEG:
                color_low_veg.setRGB(colour);
                break;
            case MEDIUM_VEG:
                color_medium_veg.setRGB(colour);
                break;
            case HIGH_VEG:
                color_high_veg.setRGB(colour);
                break;
            case BUILDING:
                color_building.setRGB(colour);
                break;
            case LOW_POINT:
                color_low_point.setRGB(colour);
                break;
            case RESERVED:
                color_reserved.setRGB(colour);
                break;
            case WATER:
                color_water.setRGB(colour);
                break;
            case ROAD:
                color_road.setRGB(colour);
                break;
            case PARKING:
                color_parking.setRGB(colour);
                break;
            default:
                color_ground.setRGB(colour);
                break;
        }
    }
}
