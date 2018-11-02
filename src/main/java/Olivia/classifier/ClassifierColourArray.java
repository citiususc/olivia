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
 */
public class ClassifierColourArray extends ColourArray {

    private static final float[] COLOR_UNCLASSIFIED = {0.75f, 0.75f, 0.75f};
    private static final float[] COLOR_UNKNOWN = {1.0f, 1.0f, 0.5f};
    private static final float[] COLOR_GROUND = {0.4f, 0.2f, 0.0f};
    private static final float[] COLOR_LOW_VEG = {0.0f, 0.65f, 0.0f};
    private static final float[] COLOR_MEDIUM_VEG = {0.0f, 0.65f, 0.0f};
    private static final float[] COLOR_HIGH_VEG = {0.0f, 0.65f, 0.0f};
    private static final float[] COLOR_BUILDING = {1.0f, 0.0f, 0.0f};
    private static final float[] COLOR_LOW_POINT = {1.0f, 0.6f, 0.0f};
    private static final float[] COLOR_RESERVED = {1.0f, 1.0f, 0.0f};
    private static final float[] COLOR_WATER = {0.0f, 1.0f, 1.0f};
    private static final float[] COLOR_ROAD = {0.5f, 0.5f, 0.5f};
    private static final float[] COLOR_PARKING = {0.0f, 0.0f, 1.0f};

    public ClassifierColourArray(PointArray<PointI> points, ClassifierGroupArray<ClassifierGroup> groups) {
        super(points);
        ensureCapacity(points.size());
        for (ClassifierGroup group : groups) {
            for (int i = 0; i < group.getPoints().size(); i++) {
                add(getGroupColour(group.getType()));
            }
        }
    }

    public static PointColour getGroupColour(int type) {
        PointColour colour;
        switch (type) {
            case UNCLASSIFIED:
                colour = new PointColour(COLOR_UNCLASSIFIED[0], COLOR_UNCLASSIFIED[1], COLOR_UNCLASSIFIED[2]);
                break;
            case UNKNOWN:
                colour = new PointColour(COLOR_UNKNOWN[0], COLOR_UNKNOWN[1], COLOR_UNKNOWN[2]);
                break;
            case GROUND:
                colour = new PointColour(COLOR_GROUND[0], COLOR_GROUND[1], COLOR_GROUND[2]);
                break;
            case LOW_VEG:
                colour = new PointColour(COLOR_LOW_VEG[0], COLOR_LOW_VEG[1], COLOR_LOW_VEG[2]);
                break;
            case MEDIUM_VEG:
                colour = new PointColour(COLOR_MEDIUM_VEG[0], COLOR_MEDIUM_VEG[1], COLOR_MEDIUM_VEG[2]);
                break;
            case HIGH_VEG:
                colour = new PointColour(COLOR_HIGH_VEG[0], COLOR_HIGH_VEG[1], COLOR_HIGH_VEG[2]);
                break;
            case BUILDING:
                colour = new PointColour(COLOR_BUILDING[0], COLOR_BUILDING[1], COLOR_BUILDING[2]);
                break;
            case LOW_POINT:
                colour = new PointColour(COLOR_LOW_POINT[0], COLOR_LOW_POINT[1], COLOR_LOW_POINT[2]);
                break;
            case RESERVED:
                colour = new PointColour(COLOR_RESERVED[0], COLOR_RESERVED[1], COLOR_RESERVED[2]);
                break;
            case WATER:
                colour = new PointColour(COLOR_WATER[0], COLOR_WATER[1], COLOR_WATER[2]);
                break;
            case ROAD:
                colour = new PointColour(COLOR_ROAD[0], COLOR_ROAD[1], COLOR_ROAD[2]);
                break;
            case PARKING:
                colour = new PointColour(COLOR_PARKING[0], COLOR_PARKING[1], COLOR_PARKING[2]);
                break;
            default:
                colour = new PointColour(COLOR_GROUND[0], COLOR_GROUND[1], COLOR_GROUND[2]);
                break;
        }
        return colour;
    }
}
