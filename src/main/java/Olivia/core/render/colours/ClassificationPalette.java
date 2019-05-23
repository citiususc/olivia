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
public class ClassificationPalette extends PointColourPalette<PointColour>{
    
    protected PointColour color_unclassified = new PointColour(0.75f, 0.75f, 0.75f);
    protected PointColour color_unknown = new PointColour(1.0f, 1.0f, 0.5f);
    protected PointColour color_ground = new PointColour(0.4f, 0.2f, 0.0f);
    protected PointColour color_low_veg = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour color_medium_veg = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour color_high_veg = new PointColour(0.0f, 0.65f, 0.0f);
    protected PointColour color_building = new PointColour(1.0f, 0.0f, 0.0f);
    protected PointColour color_low_point = new PointColour(1.0f, 0.6f, 0.0f);
    protected PointColour color_reserved = new PointColour(1.0f, 1.0f, 0.0f);
    protected PointColour color_water = new PointColour(0.0f, 1.0f, 1.0f);
    protected PointColour color_road = new PointColour(0.5f, 0.5f, 0.5f);
    protected PointColour color_parking = new PointColour(0.0f, 0.0f, 1.0f);
    protected PointColour color_wire = new PointColour(1.0f, 1.0f, 0.0f);
    protected PointColour color_pole = new PointColour(1.0f, 1.0f, 1.0f);
    
    public ClassificationPalette(){
        this.add(color_unclassified);
        this.add(color_unknown);
        this.add(color_ground);
        this.add(color_low_veg);
        this.add(color_medium_veg);
        this.add(color_high_veg);
        this.add(color_building);
        this.add(color_low_point);
        this.add(color_reserved);
        this.add(color_water);
        this.add(color_unknown);
        this.add(color_road);
        this.add(color_unknown);
        this.add(color_unknown);
        this.add(color_wire);
        this.add(color_pole);
        this.add(color_unknown);
        this.add(color_unknown);
        this.add(color_unknown);
        this.add(color_unknown);
        this.add(color_unknown);
        this.add(color_parking);
    }
    
}
