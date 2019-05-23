/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended;

import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.core.Olivia;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.RGBLoopColourPalette;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
public class RandomPaletteColourArray extends ColourArray<PointColour> {
    
    public RandomPaletteColourArray(PointArray points) {
        this(points,0.1f);
    }
    
    public RandomPaletteColourArray(PointArray points, float step) {
        super(points);
        Olivia.textOutputter.println("Creating random colours with step "+ step +" for each point");
        palette = new RGBLoopColourPalette(step);
        palette.shuffle(1);
        int index=0;
        for (int i = 0; i < points.size(); i++) {
            index = Math.floorMod(i, palette.size());
            add(palette.get(index));
        }
        Olivia.textOutputter.println("Created random colours with step "+ step +" for each point");
    }
    
    public RandomPaletteColourArray(PointArray points, float step, ArrayList<Integer> indices) {
        super();
        Olivia.textOutputter.println("Palette not specified, using random palette");
        palette = new RGBLoopColourPalette(step);
        palette.shuffle(1);
        int index=0;
        if(points.size()!=indices.size()){
            Olivia.textOutputter.println("Points and indices do not match sizes, doing random");
            Olivia.textOutputter.println("Creating random colours with step "+ step +" for each point");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(i, palette.size());
                add(palette.get(index));
            }
            Olivia.textOutputter.println("Created random colours with step "+ step +" for each point");
        }else{
            Olivia.textOutputter.println("Creating random colours with step "+ step +" for each point depending on indices");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(indices.get(i), palette.size());
                add(palette.get(index));
            }
            Olivia.textOutputter.println("Created random colours with step "+ step +" for each point depending on indices");
        }
    }
    
    public RandomPaletteColourArray(PointArray points, ArrayList<Integer> indices) {
        this(points, 0.1f, indices);
    }
    
}
