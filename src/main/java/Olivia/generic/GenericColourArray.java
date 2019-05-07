/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.generic;

import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
public class GenericColourArray extends ColourArray{
    
    public GenericColourArray(ArrayList colourList) {
        super();
        
        PointColour colour;
        
        for (int i = 0; i < colourList.size(); i++) {
            colour = (PointColour) colourList.get(i);
            this.add(colour);
        }
    }
    
}
