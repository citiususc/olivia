package PointCloudVisu.core.data;

import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.render.VBOManager;
import com.jogamp.opengl.GL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Keeps an array of points, this is how the core app sees the point cloud
 * Implements the drawing of the points Draws the points using a VBO buffer
 * Keeps the center of mass of the point cloud
 *
 * @author oscar.garcia, jorge.martinez.sanchez
 */
public class PointArray<P extends Point3D> extends ArrayList<P> {

    /**
     * The current center of mass of the point cloud, done automatically
     */
    protected double[] centerOfMass = new double[]{0, 0, 0};
    /**
     * The original center of mass of the point cloud when first read
     */
    protected double[] centerOfMassOriginal;
    /**
     * To use in rendering
     */
    protected int[] vboNumVertices = new int[]{-1};
    /**
     * To use in rendering
     */
    protected int[] vboIndices = new int[]{-1};
    /*
    * Number of coloured point that fit in VBO buffer, to make sure the VBO buffer does not overflow. Should be the same as vboNumVertices, we keep it duplicated in case there are changes in the rendering mechanism
    */
    protected int vboBufferCapacity = -1;   
    /*
    * To make sure the VBO buffer does not overflow
    */
    protected boolean vboBuffer_alreadySet= false;

    /**
     * To track which points have been selected, if null all are
     */
    protected boolean[] selected;
    /**
     * LEGACY: buffer sizes will not be changed!!
     * To create appropiate buffer size = number of selected points
     */
    protected int numSelected;
    
    protected boolean doRepack= false;

    /**
     * Recalculates the center of mass with a new point
     *
     * @param point The new point
     */
    protected void addToCenterOfMass(Point3D point) {
        centerOfMass[0] += (point.getX() - centerOfMass[0]) / (this.size() + 1);
        centerOfMass[1] += (point.getY() - centerOfMass[1]) / (this.size() + 1);
        centerOfMass[2] += (point.getZ() - centerOfMass[2]) / (this.size() + 1);
    }

    /**
     * Adds a point to the list, and if it does not already have an ID its
     * position becomes the point ID
     *
     * @param point
     * @return same as ArrayList.add()
     */
    @Override
    public boolean add(P point) {
        point.setIdIfNotSet(this.size());
        addToCenterOfMass(point);
        return super.add(point);
    }
    
    
    public void setVboBuffer(int capacity, int[] indices, int[] vertices){
        if(!vboBuffer_alreadySet){
            this.vboBufferCapacity = capacity;
            this.vboIndices = indices;
            this.vboNumVertices = vertices;
            vboBuffer_alreadySet = true;
        }else{
            System.out.println("vboBuffer was already for " + this.vboBufferCapacity + " points, indices:" +this.vboIndices+ ", vertices: " + this.vboNumVertices +", will not be changed until destruction");
        }
    }
    
    public int[] getVboVertices(){
        return this.vboNumVertices;
    }
    
    public int[] getVboIndices(){
        return this.vboIndices;
    }
    
    public long getVboBufferCapacity(){
        return vboBufferCapacity;
    }
    
    public boolean isVboAlreadySet(){
        return this.vboBuffer_alreadySet;
    }

    /**
     * Drwas all the point in the array
     *
     * @param screen The render screen
     * @param colours The colour of each point
     * @param pointSize The size the points will be rendered
     * @param drawType The GL_TYPE to render (GL_POINTS, GL_LINES...)
     */
    public void draw(OpenGLScreen screen, ColourArray colours, int pointSize, int drawType) {
        if (size() != colours.size()) {
            System.out.println("Error: Points and colours do not match size");
        } else {
            if (selected == null){
                if (!vboBuffer_alreadySet){ //First time
                    VBOManager.build(screen, this, colours);
                }else if (doRepack){
                    doRepack = false;
                    VBOManager.repack(screen, this, colours);
                }
                VBOManager.draw(screen, vboIndices, vboNumVertices, drawType, pointSize);
            }else{
                if (!vboBuffer_alreadySet){ //First time
                    VBOManager.build(screen, this,colours, selected);
                }else if (doRepack){
                    doRepack = false;
                    VBOManager.repack(screen, this, colours, selected);
                }
                int[] vboSelectedVertices = new int[]{numSelected};
                VBOManager.draw(screen, vboIndices, vboSelectedVertices, drawType, pointSize);
                selected = null;
                numSelected = 0;
            }
        }
    }

    /**
     * Draws all the points, takes the point size directly for the render screen
     *
     * @param screen The render screen
     * @param colours The colour for each point
     * @param drawType The GL_TYPE to render (GL_POINTS, GL_LINES...)
     */
    public void draw(OpenGLScreen screen, ColourArray colours, int drawType) {
        int drawSize = drawType == GL.GL_POINTS ? screen.getPointSize() : screen.getLineWidth();
        draw(screen, colours, drawSize, drawType);
    }

    /**
     * Draws selected the points, takes the point size for the render screen
     *
     * @param screen The render screen
     * @param colours The colour for each point
     * @param selected Array of selected flags
     * @param numSelected Number of selected points
     * @param drawType The GL_TYPE to render (GL_POINTS, GL_LINES...)
     */
    public void drawSelected(OpenGLScreen screen, ColourArray colours, boolean[] selected, int numSelected, int drawType) {
        this.selected = selected;
        this.numSelected = numSelected;
        draw(screen, colours, screen.getPointSize(), drawType);
    }

    /**
     * This indicates the packing of the point in the buffers must be redone,
     * for example when changing the colours, if it is not called keeps drawing
     * the same image
     */
    public void repack() {
        doRepack = true;
    }
    
    public void freeVBO(OpenGLScreen screen){
        VBOManager.free(screen, this);
        vboBuffer_alreadySet = false;
    }
    
    /**
     * Gets the center of the point cloud as a point
     *
     * @return A point with the coordiantes of the center
     */
    public Point3D getCenterAsPoint() {
        if (this.isEmpty()) {
            return new Point3D(0, 0, 0);
        } else {
            return new Point3D(centerOfMass[0], centerOfMass[1], centerOfMass[2]);
        }
    }

    /**
     * Gets the center of the point cloud as three doubles
     *
     * @return 3 doubles as (x,y,z)
     */
    public double[] getCenterOfMass() {
        return centerOfMass;
    }

    public double[] getCenterOfMassOriginal() {
        return centerOfMassOriginal;
    }

    /**
     * Centers the points at the center of the point cloud
     */
    public void centerPoints() {
        System.out.println("Center " + centerOfMass[0] + " " + centerOfMass[1] + " " + centerOfMass[2]);
        for (P point : this) {
            point.setX((point.getX() - centerOfMass[0]));
            point.setY((point.getY() - centerOfMass[1]));
            point.setZ((point.getZ() - centerOfMass[2]));
        }
        if (centerOfMassOriginal == null) {
            centerOfMassOriginal = new double[]{centerOfMass[0], centerOfMass[1], centerOfMass[2]};
        }
        centerOfMass[0] = 0.0;
        centerOfMass[1] = 0.0;
        centerOfMass[2] = 0.0;
    }
    
    /*
    * To check if VBO is destroyed when exiting 
    */
    @Override protected void finalize() throws Throwable {
        if(vboBuffer_alreadySet){
            System.out.println("Caution! destroying a point array without cleaning its VBO buffer!");
        }
        System.out.println("Caution! destroying a point array ");
        super.finalize();
    }
    
}
