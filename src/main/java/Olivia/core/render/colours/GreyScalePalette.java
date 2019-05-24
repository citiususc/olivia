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
public class GreyScalePalette extends PointColourPalette<PointColour>{
    
    protected float step;
    protected float offset;
    
    public GreyScalePalette(float step, float offset){
        this.step = step;
        this.offset = offset;
        float r,g,b;
        
   
        r=offset; 
        g=offset;
        b=offset;
        while (r<=1.0f){
            r=r+step;
            g=g+step;
            b=b+step;
            this.add(new PointColour(r,g,b));
        }
    }
    
    public GreyScalePalette(float step){
        this(step,0.0f);
    }
    
    public GreyScalePalette(int numColours, float offset){
        this.offset = offset;
        float r,g,b;
        
        step = 1.0f/(numColours+1);
      
        r=offset; 
        g=offset;
        b=offset;
        for(int i = 0; i < numColours; i++){
            r=r+step;
            g=g+step;
            b=b+step;
            this.add(new PointColour(r,g,b));
        }
    }
    
    public GreyScalePalette(int numColours){
        this(numColours,0.0f);
    }
    
}
