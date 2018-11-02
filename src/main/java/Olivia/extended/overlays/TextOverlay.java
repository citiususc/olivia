/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 *
 * @author oscar.garcia
 */
public class TextOverlay extends Overlay {

    public static final int DEFAULT_FONT = GLUT.BITMAP_HELVETICA_18;
    public static final PointColour DEFAULT_COLOUR = new PointColour(1.0f, 0.0f, 0.0f);

    protected int font;
    protected String text;
    protected PointColour colour;
    protected float[] rasterPos;
    protected boolean alwaysOnTop;
    
    public TextOverlay(VisualisationManager visualisationManager, Point3D point, String text, int font, PointColour colour) {
        super(visualisationManager);
        this.bounds.setCentre(point);
        this.font = font;
        this.text = text;
        this.colour = colour;
        this.rasterPos = new float[]{0.0f,0.0f,0.0f};
        this.alwaysOnTop = false;
    }
    
    public TextOverlay(VisualisationManager visualisationManager, Point3D point, String text, int font) {
        this(visualisationManager,point, text, font, DEFAULT_COLOUR);
    }

    public TextOverlay(VisualisationManager visualisationManager, Point3D point, String text) {
        this(visualisationManager,point, text, DEFAULT_FONT, DEFAULT_COLOUR);
    }

    public TextOverlay(VisualisationManager visualisationManager, Point3D point) {
        this(visualisationManager,point, "", DEFAULT_FONT, DEFAULT_COLOUR);
    }
    
    public TextOverlay(VisualisationManager visualisationManager, String text) {
        this(visualisationManager,new Point3D(0.0,0.0,0.0), text, DEFAULT_FONT, DEFAULT_COLOUR);
    }


    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PointColour getColour() {
        return colour;
    }

    public void setColour(PointColour colour) {
        this.colour = colour;
    }

    public float[] getRasterPos() {
        return rasterPos;
    }

    public void setRasterPos(float x, float y, float z) {
        this.rasterPos[0] = x;
        this.rasterPos[1] = y;
        this.rasterPos[2] = z;
    }

    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }
    

    @Override
    public void drawShape(OpenGLScreen renderScreen) {
        visualisationManager.getRenderScreen().getGl2().glPushMatrix();
        if(alwaysOnTop) visualisationManager.getRenderScreen().getGl2().glDisable(visualisationManager.getRenderScreen().getGl2().GL_DEPTH_TEST);
        visualisationManager.getRenderScreen().getGl2().glColor3f(colour.getR(), colour.getG(), colour.getB());
        visualisationManager.getRenderScreen().getGl2().glTranslated(bounds.getCentre().getX(), bounds.getCentre().getY(), bounds.getCentre().getZ());
        visualisationManager.getRenderScreen().getGl2().glRasterPos3f(rasterPos[0], rasterPos[1], rasterPos[2]);
        //visualisationManager.getRenderScreen().getGlut().glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18,(char)171);
        visualisationManager.getRenderScreen().getGlut().glutBitmapString(font, text);
        if(alwaysOnTop) visualisationManager.getRenderScreen().getGl2().glEnable(visualisationManager.getRenderScreen().getGl2().GL_DEPTH_TEST);
        visualisationManager.getRenderScreen().getGl2().glPopMatrix();
    }

}
