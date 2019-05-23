/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended;

import Olivia.core.Olivia;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.core.render.colours.PointColourPalette;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
public class PaletteGradientColourArray extends ColourArray<PointColour> {
    
    public PaletteGradientColourArray(PointArray points, ArrayList<Number> indices, PointColourPalette<PointColour> palette) {
        super(points);
        int index=0;
        
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        
        if(points.size()!=indices.size()){
            Olivia.textOutputter.println("Points and indices do not match sizes, doing white");
            PointColour col = new PointColour(1.0f,1.0f,1.0f);
            for (int i = 0; i < points.size(); i++) {
                add(col);
            }
        }else{
      
            for(int i = 0; i<indices.size(); i++){
                if(min>indices.get(i).doubleValue()){
                    min=indices.get(i).doubleValue();
                }else if (max<indices.get(i).doubleValue()){
                    max=indices.get(i).doubleValue();
                }
            }
        
            double step = (max-min)/(palette.size()-1);
            //Olivia.textOutputter.println("Max: "+ max + "  Min: " + min);
            Olivia.textOutputter.println("Creating colours with palette for each point depending on indices");
            for (int i = 0; i < points.size(); i++) {
                Long bin = Math.round( (indices.get(i).doubleValue()-min)/step);
                //Olivia.textOutputter.println("I: " + indices.get(i).doubleValue() + "  Step: " + step + " bin: " + bin);
                int bin2 = bin.intValue();
                add(palette.get(bin2));
            }
            Olivia.textOutputter.println("Created colours with palette for each point depending on indices");
        }
    }
    
    
}
