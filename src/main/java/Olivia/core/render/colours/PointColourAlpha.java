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
public class PointColourAlpha extends PointColour{
    protected float alpha;
    
    public PointColourAlpha(float a, float b, float c) {
        super(a, b, c);
        alpha = 1.0f;
    }
    
    public PointColourAlpha(float a, float b, float c, float alpha) {
        super(a, b, c);
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    
    
}
