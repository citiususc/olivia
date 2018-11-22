package Olivia.segmenter;

import Olivia.classifier.ClassifierGroup;
import Olivia.classifier.ClassifierGroupArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;
import java.awt.Color;
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
    
    protected ArrayList<PointColour> colours;

    public SegmenterColourArray(PointArray<PointI> points, ArrayList<Integer> groupIds) {
        super(points);
        ensureCapacity(points.size());
        colours = new ArrayList<>();
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
    
    protected ArrayList<PointColour> fillRainbowColours(int numberOfColours){
        float hue = 1.0f; //hue
        float saturation = 0.5f; //saturation
        float lightness=0.0f;
        
        ArrayList<PointColour> here_colours = new ArrayList<>(numberOfColours);
        Color here_color;
        
        float step = 1.0f / numberOfColours;
        
        for(int i=0;i<numberOfColours;i++){
            here_color = Color.getHSBColor(hue, saturation, lightness);
            here_colours.add(new PointColour(here_color));
            lightness = lightness + step;
        }
        
        return here_colours;    
    }
}
