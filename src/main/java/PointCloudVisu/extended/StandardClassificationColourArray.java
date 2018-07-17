package PointCloudVisu.extended;

import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.PointColour;

/**
 * This creates classification colours for all standard points
 *
 * @author jorge.martinez.sanchez oscar.garcia
 * @param <P> Point class with support for classification (cannot redefine standard classes, need more code for that)
 */
public class StandardClassificationColourArray<P extends PointStandard> extends ColourArray {
    
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

    public StandardClassificationColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            this.add(getPointColour(points.get(i).getClassification()));
        }
    }
    
    public static PointColour getPointColour(int type) {
        PointColour colour;
        switch (type) {
            case PointStandard.UNCLASSIFIED:
                colour = new PointColour(COLOR_UNCLASSIFIED[0], COLOR_UNCLASSIFIED[1], COLOR_UNCLASSIFIED[2]);
                break;
            case PointStandard.UNKNOWN:
                colour = new PointColour(COLOR_UNKNOWN[0], COLOR_UNKNOWN[1], COLOR_UNKNOWN[2]);
                break;
            case PointStandard.GROUND:
                colour = new PointColour(COLOR_GROUND[0], COLOR_GROUND[1], COLOR_GROUND[2]);
                break;
            case PointStandard.LOW_VEG:
                colour = new PointColour(COLOR_LOW_VEG[0], COLOR_LOW_VEG[1], COLOR_LOW_VEG[2]);
                break;
            case PointStandard.MEDIUM_VEG:
                colour = new PointColour(COLOR_MEDIUM_VEG[0], COLOR_MEDIUM_VEG[1], COLOR_MEDIUM_VEG[2]);
                break;
            case PointStandard.HIGH_VEG:
                colour = new PointColour(COLOR_HIGH_VEG[0], COLOR_HIGH_VEG[1], COLOR_HIGH_VEG[2]);
                break;
            case PointStandard.BUILDING:
                colour = new PointColour(COLOR_BUILDING[0], COLOR_BUILDING[1], COLOR_BUILDING[2]);
                break;
            case PointStandard.LOW_POINT:
                colour = new PointColour(COLOR_LOW_POINT[0], COLOR_LOW_POINT[1], COLOR_LOW_POINT[2]);
                break;
            case PointStandard.RESERVED:
                colour = new PointColour(COLOR_RESERVED[0], COLOR_RESERVED[1], COLOR_RESERVED[2]);
                break;
            case PointStandard.WATER:
                colour = new PointColour(COLOR_WATER[0], COLOR_WATER[1], COLOR_WATER[2]);
                break;
            case PointStandard.ROAD:
                colour = new PointColour(COLOR_ROAD[0], COLOR_ROAD[1], COLOR_ROAD[2]);
                break;
            case PointStandard.PARKING:
                colour = new PointColour(COLOR_PARKING[0], COLOR_PARKING[1], COLOR_PARKING[2]);
                break;
            default:
                colour = new PointColour(COLOR_GROUND[0], COLOR_GROUND[1], COLOR_GROUND[2]);
                break;
        }
        return colour;
    }
}
