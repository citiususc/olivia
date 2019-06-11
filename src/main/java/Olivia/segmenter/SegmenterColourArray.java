package Olivia.segmenter;

import Olivia.core.Olivia;
import Olivia.classifier.ClassifierGroup;
import Olivia.classifier.ClassifierGroupArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;
import java.util.ArrayList;
import java.util.Collections;
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

    /*
    public SegmenterColourArray(PointArray<PointI> points, ArrayList<Integer> groupIds) {
        super(points);
        ensureCapacity(points.size());
        colours = new ArrayList<>();
        Random rand = new Random();
        Olivia.textOutputter.println("Loading segmentation colours");
        for (int i = 0; i < points.size(); i++) {
            rand.setSeed(groupIds.get(i) * COLOUR_OFFSET);
            add(new PointColour(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        }
        Olivia.textOutputter.println("Loaded segmentation colours");
    }
    */

    /*
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
    }*/
    
    public SegmenterColourArray(PointArray<PointI> points, ArrayList<Integer> groupIds) {
        super(points);
        ensureCapacity(points.size());
        colours = fillColours(0.1f);
        int index=0;
        Olivia.textOutputter.println("Loading segmentation colours");
        for (int i = 0; i < points.size(); i++) {
            index = Math.floorMod(groupIds.get(i), colours.size());
            add(colours.get(index));
        }
        Olivia.textOutputter.println("Loaded segmentation colours");
    }
    
    public SegmenterColourArray(PointArray<PointI> points, ClassifierGroupArray<ClassifierGroup> groups) {
        super(points);
        ensureCapacity(points.size());
        colours = fillColours(0.1f);
        int index=0;
        for (ClassifierGroup group : groups) {
            //index = rand.nextInt(colours.size());
            for (int i = 0; i < group.getPoints().size(); i++) {
                add(colours.get(index));
            }
            index = Math.floorMod(index+1, colours.size());
        }
    }
    
    protected ArrayList<PointColour> fillColours(float step){
        ArrayList<PointColour> here_colours = new ArrayList<>();
        float r,g,b;
        
        r=0.0f;        
        while (r<=1.0f){
            g=0.0f;
            while(g<=1.0f){
                b=0.0f;
                while(b<=1.0f){
                    here_colours.add(new PointColour(r,g,b));
                    b=b+step;
                }
                g=g+step;
            }
            r=r+step;
        }
        
        Collections.shuffle(here_colours, new Random(1));
        return here_colours;    
        
    }
}
