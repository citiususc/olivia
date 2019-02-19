package Olivia.core.data;

import Olivia.core.Olivia;
import Olivia.core.render.BoundingBoxO;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.Renderable;
import Olivia.core.render.Transformations;
import Olivia.core.render.VBOManager;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;

/**
 * Keeps an array of points, this is how the core app sees the point cloud
 * Implements the drawing of the points Draws the points using a VBO buffer
 * Keeps the center of mass of the point cloud
 *
 * @author oscar.garcia, jorge.martinez.sanchez
 * @param <P> the type of points this array will contain
 */
public class PointArray<P extends Point3D_id> extends ArrayList<P> implements Renderable {

    /**
     * The current center of mass of the point cloud, to get the real point add this value
     */
    //protected Point3D centreCurrent = new Point3D(0.0,0.0,0.0);
    /**
     * The original center of mass of the point cloud when first read
     */
    protected Point3D centreOriginal = new Point3D(0.0,0.0,0.0);
    /**
     * To use in rendering
     */
    protected int[] vboNumVertices = new int[]{-1};
    /**
     * To use in rendering
     */
    protected int[] vboIndices = new int[]{-1};
    /**
    * Number of coloured point that fit in VBO buffer, to make sure the VBO buffer does not overflow. Should be the same as vboNumVertices, we keep it duplicated in case there are changes in the rendering mechanism
    */
    protected int vboBufferCapacity = -1;   
    /**
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
    
    /**
     * To indicate in the draw() method whether a repack is needed
     */
    protected boolean doRepack= false;
    
    /**
     * To indicate if the contained points have been modified
     */
    protected boolean stillOriginal = true;
    
    /**
     * The bounding box of all the points in the array
     */
    protected BoundingBoxO bounds = new BoundingBoxO();
    
    /**
     * Recalculates the center of mass with a new point
     *
     * @param point The new point
     */
    /*
    protected void addToCenterOfMass(Point3D_id point) {
        centreCurrent[0] += (point.getX() - centreCurrent[0]) / (this.size() + 1);
        centreCurrent[1] += (point.getY() - centreCurrent[1]) / (this.size() + 1);
        centreCurrent[2] += (point.getZ() - centreCurrent[2]) / (this.size() + 1);
    }
    */
    
    /**
     * Recalculates the original centre of mass with a new point
     * @param point The new point added
     */
    protected void addToCenterOfMassOriginal(Point3D_id point) {
        centreOriginal.setX( centreOriginal.getX() +
                (point.getX() - centreOriginal.getX() )/ (this.size() + 1)
        );
        centreOriginal.setY( centreOriginal.getY() +
                (point.getY() - centreOriginal.getY()) / (this.size() + 1)
        );
        centreOriginal.setZ( centreOriginal.getZ() +
                (point.getZ() - centreOriginal.getZ()) / (this.size() + 1)
        );
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
        if(stillOriginal) addToCenterOfMassOriginal(point);
        bounds.add(point);
        return super.add(point);
    }
    
    /**
     * Maybe needs a few changes; Sets up the VBO buffer, if it was already set it does nothing and prints a message
     * @param capacity The capacity of the buffer
     * @param indices The indices of the buffer
     * @param vertices The vertices of the buffer
     */
    public void setVboBuffer(int capacity, int[] indices, int[] vertices){
        if(!vboBuffer_alreadySet){
            this.vboBufferCapacity = capacity;
            this.vboIndices = indices;
            this.vboNumVertices = vertices;
            vboBuffer_alreadySet = true;
        }else{
            Olivia.textOutputter.println("vboBuffer was already for " + this.vboBufferCapacity + " points, indices:" +this.vboIndices+ ", vertices: " + this.vboNumVertices +", will not be changed until destruction");
        }
    }
    
    /**
     * Gets the VBO vertices
     * @return an array with length vboBufferCapacity
     */
    public int[] getVboVertices(){
        return this.vboNumVertices;
    }
    
    /**
     * Gets the VBO indices
     * @return an array with length vboBufferCapacity
     */
    public int[] getVboIndices(){
        return this.vboIndices;
    }
    
    /**
     * Gets the VBO buffer capacity
     * @return the VBO buffer capacity
     */
    public long getVboBufferCapacity(){
        return vboBufferCapacity;
    }
    
    /**
     * Returns whether the VBO buffer was already set
     * @return true if the VBO buffer is already set
     */
    public boolean isVboAlreadySet(){
        return this.vboBuffer_alreadySet;
    }

    /**
     * Draws all the points in the array
     *
     * @param screen The render screen
     * @param colours The colour of each point
     * @param pointSize The size the points will be rendered
     * @param renderMode The GL_TYPE to render (GL_POINTS, GL_LINES...)
     * @param rasterMode The GL_TYPE to raster (GL_LINE, GL_FILL...)
     */
    public void draw(OpenGLScreen screen, ColourArray colours, int pointSize, int renderMode, int rasterMode) {
        if (size() != colours.size()) {
            Olivia.textOutputter.println("Error: Points and colours do not match size");
        } else {
            if (selected == null){
                if (!vboBuffer_alreadySet){ //First time
                    VBOManager.build(screen, this, colours);
                }else if (doRepack){
                    doRepack = false;
                    VBOManager.repack(screen, this, colours);
                }
                VBOManager.draw(screen, vboIndices, vboNumVertices, renderMode, rasterMode, pointSize);
            }else{
                if (!vboBuffer_alreadySet){ //First time
                    VBOManager.build(screen, this,colours, selected);
                }else if (doRepack){
                    doRepack = false;
                    VBOManager.repack(screen, this, colours, selected);
                }
                int[] vboSelectedVertices = new int[]{numSelected};
                VBOManager.draw(screen, vboIndices, vboSelectedVertices, renderMode, rasterMode, pointSize);
                selected = null;
                numSelected = 0;
            }
        }
    }
    
    /**
     * Draws all the points in the array
     * @param screen The render screen
     * @param pointSize The size the points will be rendered
     * @param renderMode The GL_TYPE to render (GL_POINTS, GL_LINES...)
     * @param rasterMode The GL_TYPE to raster (GL_LINE, GL_FILL...)
     */
    public void draw(OpenGLScreen screen, int pointSize, int renderMode, int rasterMode) {
        if(this.isEmpty()) return; //do nothing if empty
        if (selected == null){
                if (!vboBuffer_alreadySet){ //First time
                    VBOManager.build(screen, this);
                }else if (doRepack){
                    doRepack = false;
                    VBOManager.repack(screen, this);
                }
                VBOManager.draw(screen, vboIndices, vboNumVertices, renderMode, rasterMode, pointSize);
            }else{
                if (!vboBuffer_alreadySet){ //First time
                    VBOManager.build(screen, this, selected);
                }else if (doRepack){
                    doRepack = false;
                    VBOManager.repack(screen, this, selected);
                }
                int[] vboSelectedVertices = new int[]{numSelected};
                VBOManager.draw(screen, vboIndices, vboSelectedVertices, renderMode, rasterMode, pointSize);
                selected = null;
                numSelected = 0;
            }
    }

    /**
     * Draws all the points, takes the point size directly for the render screen
     *
     * @param screen The render screen
     * @param colours The colour for each point
     * @param renderMode The GL_TYPE to render (GL_POINTS, GL_LINES...)
     * @param rasterMode The GL_TYPE to raster (GL_LINE, GL_FILL...)
     */
    public void draw(OpenGLScreen screen, ColourArray colours, int renderMode, int rasterMode) {
        int drawSize = renderMode == GL2.GL_POINTS ? screen.getPointSize() : screen.getLineWidth();
        draw(screen, colours, drawSize, renderMode,rasterMode);
    }
    
    /**
     * Draws all the points, takes the point size directly for the render screen
     *
     * @param screen The render screen
     * @param colours The colour for each point
     * @param renderMode The GL_TYPE to render (GL_POINTS, GL_LINES...)
     */
    public void draw(OpenGLScreen screen, ColourArray colours, int renderMode) {
        int drawSize = renderMode == GL2.GL_POINTS ? screen.getPointSize() : screen.getLineWidth();
        draw(screen, colours, drawSize, renderMode,GL2.GL_POINT);
    }
    
    /**
     * Draws all the points, takes the point size directly for the render screen
     *
     * @param screen The render screen
     * @param colours The colour for each point
     */
    public void draw(OpenGLScreen screen, ColourArray colours) {
        draw(screen, colours, GL2.GL_POINTS, GL2.GL_POINT);
    }
    
    /**
     * Draws all the points, takes the point size directly for the render screen
     * @param screen The render screen
     * @param renderMode The GL_TYPE to render (GL_POINTS, GL_LINES...)
     */
    public void draw(OpenGLScreen screen, int renderMode) {
        int drawSize = renderMode == GL2.GL_POINTS ? screen.getPointSize() : screen.getLineWidth();
        draw(screen, drawSize, renderMode,GL2.GL_POINT);
    }

    /**
     * Draws all the points, takes the point size directly for the render screen
     * @param screen The render screen
     */
    public void draw(OpenGLScreen screen) {
        draw(screen, GL2.GL_POINTS);
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
        draw(screen, colours, screen.getPointSize(), drawType, GL2.GL_POINT);
    }

    /**
     * This indicates the packing of the point in the buffers must be redone,
     * for example when changing the colours, if it is not called keeps drawing
     * the same image
     */
    public void doRepack() {
        doRepack = true;
    }
    
    /**
     * May need a rethink, Sets do repack as true, for when the data changes and the VBO buffer needs to be refilled
     * @param renderScreen The render screen
     */
    @Override
    public void repack(OpenGLScreen renderScreen) {
        doRepack();
    }
    
    /**
     * Frees the native memory (the VBO buffer)
     * @param renderScreen The render screen
     */
    @Override
    public void free(OpenGLScreen renderScreen) {
        freeVBO(renderScreen);
    }
    
    /**
     * Frees the native memory of the VBO buffer
     * @param screen 
     */
    public void freeVBO(OpenGLScreen screen){
        VBOManager.free(screen, this);
        vboBuffer_alreadySet = false;
    }
    
    /**
     * Gets the center of the point cloud as three doubles
     *
     * @return 3 doubles as (x,y,z)
     */
    /*
    public Point3D getCentreCurrent() {
        return centreCurrent;
    }
    */

    /**
     * Gets the original centre of mass
     * @return The original centre of mass
     */
    public Point3D getCentreOriginal() {
        return centreOriginal;
    }
    
    /**
     * Whether the centre of mass is still the original, after the initial points have been added the PointArray can be set as original, all points added afterwards will change the centre of mass and return true here
     * @return true if points have been added after it was declared original
     */
    public boolean isStillOriginal() {
        return stillOriginal;
    }

    /**
     * States that all the original points have been added
     */
    public void endedAddingOriginalPoints() {
        this.stillOriginal = false;
    }



    /*
    public void centerPoints(double x, double y, double z) {        
        Olivia.textOutputter.println("Center at" + x + " " + y + " " + z);
        for (P point : this) {
            point.setX((point.getX() - x));
            point.setY((point.getY() - y));
            point.setZ((point.getZ() - z));
        }
        centreCurrent[0] = 0.0;
        centreCurrent[1] = 0.0;
        centreCurrent[2] = 0.0;
    }
    */

    /*
    protected void undoCenterPoints(){
        Olivia.textOutputter.println("Undo Center " + centreCurrent);
        for (P point : this) {
            point.setX(point.getX() + centreCurrent.getX());
            point.setY(point.getY() + centreCurrent.getY());
            point.setZ(point.getZ() + centreCurrent.getZ());
        }
        centreCurrent.setToZero();
    }
    */
    
    /**
     * Centers the points at the center of the point cloud
     */
    /*
    public void centerPointsAt(Point3D point) {
        Olivia.textOutputter.println("Centerint at " + point.getX() + " " + point.getY() + " " + point.getZ());
        for (P mpoint : this) {
            mpoint.setX((mpoint.getX() - point.getX()));
            mpoint.setY((mpoint.getY() - point.getY()));
            mpoint.setZ((mpoint.getZ() - point.getZ()));
        }
        bounds.moveTo(point);
    }
    */

    /*
    public void centerPoints(Point3D point) {
        centerPoints(point.getX(),point.getY(),point.getZ());
    }
    */

    /**
     * Centers the points at the center of the point cloud
     */
    /*
    public void centerPoints() {
        centerPoints(centreOriginal.getX(),centreOriginal.getY(),centreOriginal.getZ());
    }
    */
    
    /**
     * Centers the points at the center of mass of the point cloud
     */
    /*
    public void centerPointsAtCoordOrigin() {
        if(stillOriginal){
            Olivia.textOutputter.println("CAUTION: Centering points to their centre of mass before all points have been added?");
        }else{
            Olivia.textOutputter.println("Centering points to their centre of mass");
        }
        for (P point : this) {
            point.setX((point.getX() - centreOriginal.getX()));
            point.setY((point.getY() - centreOriginal.getY()));
            point.setZ((point.getZ() - centreOriginal.getZ()));
        }
        bounds.moveToZero();
    }
    */
    
    
    /**
     * To check if VBO is destroyed when exiting 
     * @throws Throwable the same as the standard finalize()
     */
    @Override protected void finalize() throws Throwable {
        if(vboBuffer_alreadySet){
            Olivia.textOutputter.println("Caution! destroying a point array without cleaning its VBO buffer!");
        }
        Olivia.textOutputter.println("Caution! destroying a point array ");
        super.finalize();
    }

    /**
     * Gets the bounding box containing all the points
     * @return The bounding box for all the points
     */
    @Override
    public BoundingBoxO getBounds() {
        return bounds;
    }

    /**
     * Moves the bounds.centre to the new points, displacing all the points by the vector (bounds.getCentre() - point)
     * @param point the point where the new centre will be be
     */
    @Override
    public synchronized void moveTo(Point3D point) {
        //Olivia.textOutputter.println("Moving to " + point);
        for (P my_point : this) {
            my_point.setX(my_point.getX() - bounds.getCentre().getX() + point.getX());
            my_point.setY(my_point.getY() - bounds.getCentre().getY() + point.getY());
            my_point.setZ(my_point.getZ() - bounds.getCentre().getZ() + point.getZ());
        }
        doRepack = true;
        bounds.moveTo(point);
    }
    
    /**
     * Displaces all the points by a vector point, displaces the bounds
     * @param point the displacement vector
     */
    @Override
    public synchronized void displace(Point3D point) {
        //Olivia.textOutputter.println("Displacing with " + point);
        for (P my_point : this) {
            my_point.setX(my_point.getX() - point.getX());
            my_point.setY(my_point.getY() - point.getY());
            my_point.setZ(my_point.getZ() - point.getZ());
        }
        doRepack = true;
        bounds.displace(point);
    }
    
    
    //Do not allow transformations directly on points?
    /**
     * Gets the OpenGL transformations being applied to this array
     * @return 
     */
    @Override
    public Transformations getTransformations() {
        return new Transformations();
    }

}
