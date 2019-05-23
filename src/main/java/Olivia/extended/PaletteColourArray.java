/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended;

import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import java.util.ArrayList;
import java.util.Collections;
import Olivia.core.Olivia;
import Olivia.core.render.colours.PointColourPalette;
import Olivia.core.render.colours.RGBLoopColourPalette;

/**
 *
 * @author oscar.garcia
 */
public class PaletteColourArray extends ColourArray<PointColour> {
        
    public PaletteColourArray(PointArray points, ArrayList<Integer> indices, PointColourPalette<PointColour> palette) {
        super(points);
        int index=0;
        if(indices==null){
            Olivia.textOutputter.println("Points and indices do not match sizes, doing circular assignation");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(i, palette.size());
                add(palette.get(index));
            }
            Olivia.textOutputter.println("Created colours with palette for each point");
        }
        if(points.size()!=indices.size()){
            Olivia.textOutputter.println("Points and indices do not match sizes, doing circular assignation");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(i, palette.size());
                add(palette.get(index));
            }
            Olivia.textOutputter.println("Created colours with palette for each point");
        }else{
            Olivia.textOutputter.println("Creating colours with palette for each point depending on indices");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(indices.get(i), palette.size());
                add(palette.get(index));
            }
            Olivia.textOutputter.println("Created colours with palette for each point depending on indices");
        }
    }
    
}
