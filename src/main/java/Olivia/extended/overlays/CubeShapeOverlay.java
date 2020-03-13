/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.controls.overlays.CubeShapeOverlayOptionsPanel;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.GL2;

/**
 *
 * @author oscar.garcia
 */
public class CubeShapeOverlay<VM extends VisualisationManager> extends BasicShapeOverlay<VM> {
    
    public static final double DEFAULT_SIZE_X = 10.0;
    public static final double DEFAULT_SIZE_Y = 10.0;
    public static final double DEFAULT_SIZE_Z = 10.0;
    
    
    protected double sizeX;
    protected double sizeY;
    protected double sizeZ;
    
    public CubeShapeOverlay(VM visualisationManager, String name, double sizeX, double sizeY, double sizeZ) {
        super(visualisationManager, name);
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.sizeZ=sizeZ;
        this.rasterMode = GL2.GL_FILL;
        this.defaultColour = new PointColour(0.0f,0.0f,1.0f);
    }
    
    public CubeShapeOverlay(VM visualisationManager) {
        this(visualisationManager,"Cube",DEFAULT_SIZE_X,DEFAULT_SIZE_Y,DEFAULT_SIZE_Z);
    }

    public double getSizeX() {
        return sizeX;
    }

    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public double getSizeZ() {
        return sizeZ;
    }

    public void setSizeZ(double sizeZ) {
        this.sizeZ = sizeZ;
    }
    
    protected void drawCube(OpenGLScreen renderScreen, float alpha) {
        double x1,x2,y1,y2,z1,z2;
        if(this.movingAbsolute){
            x1 = x2 = this.bounds.getCentre().getX();
            y1 = y2 = this.bounds.getCentre().getY();
            z1 = z2 = this.bounds.getCentre().getZ();
        }else{
            x1 = x2 = 0.0;
            y1 = y2 = 0.0;
            z1 = z2 = 0.0;
        }
        
        x1 -= sizeX/2;
        x2 += sizeX/2;
        y1 -= sizeY/2;
        y2 += sizeY/2;
        z1 -= sizeZ/2;
        z2 += sizeZ/2;
        
        renderScreen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, this.rasterMode);
        renderScreen.getGl2().glBegin(GL2.GL_QUADS);
        renderScreen.getGl2().glColor4f(this.defaultColour.getR(), this.defaultColour.getG(), this.defaultColour.getB(),alpha);
        renderScreen.getGl2().glVertex3d(x1, y1, z1);
        renderScreen.getGl2().glVertex3d(x1, y2, z1);
        renderScreen.getGl2().glVertex3d(x2, y2, z1);
        renderScreen.getGl2().glVertex3d(x2, y1, z1);
        
        renderScreen.getGl2().glVertex3d(x1, y1, z2);
        renderScreen.getGl2().glVertex3d(x1, y2, z2);
        renderScreen.getGl2().glVertex3d(x2, y2, z2);
        renderScreen.getGl2().glVertex3d(x2, y1, z2);
        
        renderScreen.getGl2().glVertex3d(x1, y1, z1);
        renderScreen.getGl2().glVertex3d(x1, y1, z2);
        renderScreen.getGl2().glVertex3d(x1, y2, z2);
        renderScreen.getGl2().glVertex3d(x1, y2, z1);
        
        renderScreen.getGl2().glVertex3d(x2, y1, z1);
        renderScreen.getGl2().glVertex3d(x2, y1, z2);
        renderScreen.getGl2().glVertex3d(x2, y2, z2);
        renderScreen.getGl2().glVertex3d(x2, y2, z1);
        
        renderScreen.getGl2().glVertex3d(x1, y1, z1);
        renderScreen.getGl2().glVertex3d(x1, y1, z2);
        renderScreen.getGl2().glVertex3d(x2, y1, z2);
        renderScreen.getGl2().glVertex3d(x2, y1, z1);
        
        renderScreen.getGl2().glVertex3d(x1, y2, z1);
        renderScreen.getGl2().glVertex3d(x1, y2, z2);
        renderScreen.getGl2().glVertex3d(x2, y2, z2);
        renderScreen.getGl2().glVertex3d(x2, y2, z1);
        
        renderScreen.getGl2().glEnd();
        
    }

    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        this.drawCube(renderScreen, 1.0f);
    }
    
        @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        CubeShapeOverlayOptionsPanel controls = new CubeShapeOverlayOptionsPanel(this);
        this.optionPanels.add(controls);
    }
    
}
