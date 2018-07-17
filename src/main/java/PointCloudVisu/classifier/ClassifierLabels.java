package PointCloudVisu.classifier;

import java.util.ArrayList;

/**
 * This class hold the classification labels following the LAS specification
 * 
 * @author jorge.martinez.sanchez
 */
public class ClassifierLabels {

    public static final int UNCLASSIFIED = 0;
    public static final int UNKNOWN = 1;
    public static final int GROUND = 2;
    public static final int LOW_VEG = 3;
    public static final int MEDIUM_VEG = 4;
    public static final int HIGH_VEG = 5;
    public static final int BUILDING = 6;
    public static final int LOW_POINT = 7;
    public static final int RESERVED = 8;
    public static final int WATER = 9;
    public static final int ROAD = 11;
    public static final int PARKING = 21;  // Field not in the LAS standard
    public static final int OTHER = 1337;
    
    public static final ArrayList<Integer> LABELS = new ArrayList<Integer>() {
        {
            add(UNCLASSIFIED);
            add(UNKNOWN);
            add(GROUND);
            add(LOW_VEG);
            add(MEDIUM_VEG);
            add(HIGH_VEG);
            add(BUILDING);
            add(LOW_POINT);
            add(WATER);
            add(ROAD);
            add(PARKING);
        }
    };
}
