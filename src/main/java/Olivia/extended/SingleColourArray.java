package Olivia.extended;

import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import java.awt.Color;

/**
 * Put the same color along the array, used mainly for drawing OpenGL primitives
 *
 * @author jorge.martinez.sanchez
 */
public class SingleColourArray extends ColourArray {

    public SingleColourArray(PointArray points, Color color) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            float r = (float) color.getRed();
            float g = (float) color.getGreen();
            float b = (float) color.getBlue();
            this.add(new PointColour(r, g, b));
        }
    }
    
    public SingleColourArray(int size, Color color) {
        super(size);
        for (int i = 0; i < size; i++) {
            float r = (float) color.getRed();
            float g = (float) color.getGreen();
            float b = (float) color.getBlue();
            this.add(new PointColour(r, g, b));
        }
    }
    
    public SingleColourArray(int size, PointColour color) {
        super(size);
        for (int i = 0; i < size; i++) {
            float r = color.getR();
            float g = color.getG();
            float b = color.getB();
            this.add(new PointColour(r, g, b));
        }
    }
    
    public SingleColourArray(PointArray points, PointColour color) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            float r = color.getR();
            float g = color.getG();
            float b = color.getB();
            this.add(new PointColour(r, g, b));
        }
    }
    
}
