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
public class RGBLoopColourPalette extends PointColourPalette<PointColour>{
    
    protected float step;
    
    public RGBLoopColourPalette(float step){
        float r,g,b;
        
        r=0.0f;        
        while (r<=1.0f){
            g=0.0f;
            while(g<=1.0f){
                b=0.0f;
                while(b<=1.0f){
                    this.add(new PointColour(r,g,b));
                    b=b+step;
                }
                g=g+step;
            }
            r=r+step;
        }
    }
    
    public RGBLoopColourPalette(){
        this(0.1f);
    }
    
}
