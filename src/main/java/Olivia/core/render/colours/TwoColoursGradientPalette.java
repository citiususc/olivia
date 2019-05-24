/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.colours;

/**
 *
 * @author oscar.garcia
 */
public class TwoColoursGradientPalette extends PointColourPalette<PointColour>{
    
    protected PointColour begin;
    protected PointColour end;
    
    public TwoColoursGradientPalette(int numColours, PointColour begin, PointColour end){
        this.begin = begin;
        this.end = end;
        
        float r,g,b,value;
        
        for (int i = 0; i< numColours; i++){
            value = (i*1.0f)/numColours;
            r = (end.getR() - begin.getR()) * value + begin.getR(); 
            g = (end.getG() - begin.getG()) * value + begin.getG();
            b = (end.getB() - begin.getB()) * value + begin.getB();
            this.add(new PointColour(r,g,b));
        }
    }
    
    public TwoColoursGradientPalette(int numColours){
        this(numColours, new PointColour(0.0f,0.0f,1.0f),new PointColour(1.0f,0.0f,0.0f));
    }
    
}
