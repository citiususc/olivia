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
    protected PointColour defaultColour;

    public SingleColourArray(PointArray points, Color color) {
        super(points);
        float r = (float) color.getRed();
        float g = (float) color.getGreen();
        float b = (float) color.getBlue();
        defaultColour = new PointColour(r, g, b);
        for (int i = 0; i < points.size(); i++) {
            this.add(defaultColour);
        }
    }
    
    public SingleColourArray(int size, Color color) {
        super(size);
        float r = (float) color.getRed();
        float g = (float) color.getGreen();
        float b = (float) color.getBlue();
        defaultColour = new PointColour(r, g, b);
        for (int i = 0; i < size; i++) {
            this.add(defaultColour);
        }
    }
    
    public SingleColourArray(int size, PointColour color) {
        super(size);
        float r = color.getR();
        float g = color.getG();
        float b = color.getB();
        defaultColour = new PointColour(r, g, b);
        for (int i = 0; i < size; i++) {
            this.add(defaultColour);
        }
    }
    
    public SingleColourArray(PointArray points, PointColour color) {
        super(points);
        float r = color.getR();
        float g = color.getG();
        float b = color.getB();
        defaultColour = new PointColour(r, g, b);
        for (int i = 0; i < points.size(); i++) {
            this.add(defaultColour);
        }
    }
    
    public void changeColour(PointColour color){
        defaultColour.setR(color.getR());
        defaultColour.setG(color.getG());
        defaultColour.setB(color.getB());
    }
    
    public void changeColour(Color color){
        defaultColour.setR((float) color.getRed());
        defaultColour.setG((float) color.getGreen());
        defaultColour.setB((float) color.getBlue());
    }
    
}
