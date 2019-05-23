/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.colours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author oscar.garcia
 */
public class PointColourPalette<P extends PointColour> extends ArrayList<P>{
    
    
    public void shuffle(){
        Collections.shuffle(this);
    }
    
    public void shuffle(int seed){
        Collections.shuffle(this, new Random(seed));
    }
    
    
}
