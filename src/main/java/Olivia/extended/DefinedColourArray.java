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

/**
 *
 * @author oscar.garcia
 */
public class DefinedColourArray extends ColourArray {
    
    protected ArrayList<PointColour> colours;
    
    public DefinedColourArray(PointArray points) {
        this(points,0.1f);
    }
    
    public DefinedColourArray(PointArray points, float step) {
        super(points);
        Olivia.textOutputter.println("Creating random colours with step "+ step +" for each point");
        colours = fillColoursRGB(step);
        Collections.shuffle(colours);
        int index=0;
        for (int i = 0; i < points.size(); i++) {
            index = Math.floorMod(i, colours.size());
            add(colours.get(index));
        }
        Olivia.textOutputter.println("Created random colours with step "+ step +" for each point");
    }
    
    public DefinedColourArray(PointArray points, float step, ArrayList<Integer> indices) {
        super(points);
        colours = fillColoursRGB(step);
        Collections.shuffle(colours);
        int index=0;
        if(points.size()!=indices.size()){
            Olivia.textOutputter.println("Points and indices do not match sizes, doing random");
            Olivia.textOutputter.println("Creating random colours with step "+ step +" for each point");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(i, colours.size());
                add(colours.get(index));
            }
            Olivia.textOutputter.println("Created random colours with step "+ step +" for each point");
        }else{
            Olivia.textOutputter.println("Creating random colours with step "+ step +" for each point depending on indices");
            for (int i = 0; i < points.size(); i++) {
                index = Math.floorMod(indices.get(i), colours.size());
                add(colours.get(index));
            }
            Olivia.textOutputter.println("Created random colours with step "+ step +" for each point depending on indices");
        }
    }
    
    public DefinedColourArray(PointArray points, ArrayList<Integer> indices) {
        this(points, 0.1f, indices);
    }
    
    
    
    protected ArrayList<PointColour> fillColoursRGB(float step){
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
        
        //Collections.shuffle(here_colours);
        return here_colours;    
        
    }
    
}
