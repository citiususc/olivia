/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.hi;

import Olivia.core.AnimatedOverlay;
import Olivia.core.Overlay;
import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.overlays.TextOverlay;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 *
 * @author oscar.garcia
 */
public class PointSelectorOverlay extends AnimatedOverlay{
    
    protected OverlayArray frame1;
    protected OverlayArray frame2;
    protected final Highlight highlight;
    protected final TextOverlay arrow; //need two arrows because displace() and moveTo() operation would be done twice otherwise
    protected final TextOverlay arrow2;
    
    private class Highlight extends Overlay {
        
        public Highlight(VisualisationManager visualisationManager, Point3D point) {
            super(visualisationManager);
        }
        
        @Override
        public void drawShape(OpenGLScreen renderScreen) {
            visualisationManager.getRenderScreen().getGl2().glColor3f(1.0f, 1.0f, 1.0f);
            visualisationManager.getRenderScreen().getGl2().glPointSize(renderScreen.getPointSize()*2);
            visualisationManager.getRenderScreen().getGl2().glBegin(GL2.GL_POINTS);
            visualisationManager.getRenderScreen().getGl2().glVertex3d(bounds.getCentre().getX(),bounds.getCentre().getY(),bounds.getCentre().getZ());
            visualisationManager.getRenderScreen().getGl2().glEnd();
        }

    }
    
    
    public PointSelectorOverlay(VisualisationManager visualisationManager) {
        super(visualisationManager);
        frame1 = new OverlayArray(visualisationManager);
        frame2 = new OverlayArray(visualisationManager);
        highlight = new Highlight(visualisationManager, new Point3D(0.0,0.0,0.0));
        arrow = new TextOverlay(visualisationManager, new Point3D(0.0,0.0,0.0),"<--",GLUT.BITMAP_HELVETICA_18,new PointColour(1.0f, 0.0f, 0.0f));
        arrow.setRasterPos(0.1f, -0.1f, 0.0f);
        arrow.setAlwaysOnTop(true);
        arrow2 = new TextOverlay(visualisationManager, new Point3D(0.0,0.0,0.0),"<--",GLUT.BITMAP_HELVETICA_18,new PointColour(1.0f, 0.0f, 0.0f));
        arrow2.setRasterPos(0.1f, -0.1f, 0.0f);
        arrow2.setAlwaysOnTop(true);
        frame1.add(arrow,true);
        frame1.add(highlight,true);
        frame2.add(arrow2,true); 
        /*this.add(frame1,0);
        this.add(frame2,100);
        this.setDuration(200l);*/
        this.add(frame1);
        this.add(frame2);
        this.speed = 2l;
        this.setPlay(true);
    }
    
}
