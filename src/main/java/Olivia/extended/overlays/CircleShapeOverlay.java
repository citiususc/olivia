/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.controls.overlays.CircleShapeOverlayOptionsPanel;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.GL2;

/**
 *
 * @author oscar.garcia
 */
public class CircleShapeOverlay<VM extends VisualisationManager> extends BasicShapeOverlay<VM> {
    public static final int DEFAULT_RESOLUTION = 102;
    public static final double DEFAULT_RADIUS = 10.0;
    
    protected double radius;
    protected int resolution;

    public CircleShapeOverlay(VM visualisationManager, String name, Double radius, int resolution) {
        super(visualisationManager, name);
        this.radius=radius;
        this.resolution=resolution;
        this.rasterMode = GL2.GL_FILL;
        this.defaultColour = new PointColour(0.0f,0.0f,1.0f);
    }
    
    public CircleShapeOverlay(VM visualisationManager) {
        this(visualisationManager, "Circle", DEFAULT_RADIUS,DEFAULT_RESOLUTION);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
    
    protected void drawCircle(OpenGLScreen renderScreen, float alpha) {
        double iter;
        double x,y,z;
        if(this.movingAbsolute){
            x = this.bounds.getCentre().getX();
            y = this.bounds.getCentre().getY();
            z = this.bounds.getCentre().getZ();
            //System.out.println("moved bounds " + this.bounds.getCentre());
        }else{
            x = 0.0;
            y = 0.0;
            z = 0.0;
        }

        double step = (Math.PI * 2) / this.resolution;

        iter = 0;

        renderScreen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, this.rasterMode);
        renderScreen.getGl2().glBegin(GL2.GL_TRIANGLE_STRIP);
        renderScreen.getGl2().glColor4f(this.defaultColour.getR(), this.defaultColour.getG(), this.defaultColour.getB(),alpha);
        while (iter <= (float) (Math.PI * 2)) {
            renderScreen.getGl2().glVertex3d(Math.sin(iter) * radius + x, Math.cos(iter) * radius + y, z);
            renderScreen.getGl2().glVertex3d(x, y, z);
            iter = iter + step;
        }
        renderScreen.getGl2().glEnd();
        /*iter = 0;
        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        renderScreen.getGl2().glColor4f(0.0f, 0.0f, 1.0f,alpha);
        while (iter <= (float) (Math.PI * 2)) {
            renderScreen.getGl2().glVertex3d(Math.sin(iter) * radius + x, Math.cos(iter) * radius + y, z);
            iter = iter + step;
        }*/
        renderScreen.getGl2().glEnd();

    }

    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        this.drawCircle(renderScreen, 1.0f);
    }
    
    @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        CircleShapeOverlayOptionsPanel controls = new CircleShapeOverlayOptionsPanel(this);
        this.optionPanels.add(controls);
    }
    
}
