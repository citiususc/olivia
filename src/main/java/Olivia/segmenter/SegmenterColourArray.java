package Olivia.segmenter;

import Olivia.classifier.ClassifierGroup;
import Olivia.classifier.ClassifierGroupArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class defines the colour of each segmentation group (random colours)
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterColourArray extends ColourArray {

    private static final int COLOUR_OFFSET = 1000;    // To get colours more distinct
    protected static final int COLOUR_INDEX_INTENSITY = 0;
    protected static final int COLOUR_INDEX_SEGMENTATION = 1;

    public SegmenterColourArray(PointArray<PointI> points, ArrayList<Integer> groupIds) {
        super(points);
        ensureCapacity(points.size());
        Random rand = new Random();
        System.out.println("Loading segmentation colours");
        for (int i = 0; i < points.size(); i++) {
            rand.setSeed(groupIds.get(i) * COLOUR_OFFSET);
            add(new PointColour(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        }
        System.out.println("Loaded segmentation colours");
    }

    public SegmenterColourArray(PointArray<PointI> points, ClassifierGroupArray<ClassifierGroup> groups) {
        super(points);
        ensureCapacity(points.size());
        Random rand = new Random(0);
        for (ClassifierGroup group : groups) {
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
