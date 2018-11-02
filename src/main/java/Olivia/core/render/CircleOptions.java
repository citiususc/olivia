/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render;

/**
 *
 * @author oscar.garcia
 */
public interface CircleOptions {
    
    public double getIntRadius();
    public void setIntRadius(double intRadius);
    public double getExtRadius();
    public void setExtRadius(double extRadius);
    public int getResolution();
    public void setResolution(int resolution);
    public boolean isCircunference();
    public void setCircunference(boolean circunference);
    
}
