/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render;

import Olivia.core.data.Point3D;
import Olivia.core.render.colours.PointColourAlpha;
import com.jogamp.opengl.GL2;
import javafx.geometry.BoundingBox;

/**
 * This is bounding box that can be modified after creation, contains methods to obtain regular BoundingBox
 * @author oscar.garcia
 */
public class BoundingBoxO implements Renderable{
    
    /**
     * The minimum that will be added to the centre coordinates to build a 3D box 
     */
    static public double MINIMUM_BOUNDS = 0.01; 
    
    /**
     * The limits of the box, as point coordinates
     */
    protected double minX,minY,minZ,maxX,maxY,maxZ;
    
    /**
     * The centre of the box
     */
    protected Point3D centre;
    
    /*
    public BoundingBoxO(double minX, double minY, double minZ, double width, double height, double depth){
        super(minX,minY,minZ,width,height,depth);
        this.centre = new Point3D( (minX+minX+width)/2 ,
                                    (minY+minY+height)/2,
                                    (minZ+minY+)/2
        );
    }
    */
    
    /**
     * Creates a new bounding box from a standard Java BoundingBox
     * @param boundingBox 
     */
    public BoundingBoxO(BoundingBox boundingBox){
        minX = boundingBox.getMinX();
        minY = boundingBox.getMinY();
        minZ = boundingBox.getMinZ();
        maxX = boundingBox.getMaxX();
        maxY = boundingBox.getMaxY();
        maxZ = boundingBox.getMaxZ();
        this.centre = new Point3D( (minX+maxX)/2 ,
                                    (minY+maxY)/2,
                                    (minZ+maxZ)/2
        );
    }
    
    /**
     * Creates a new bounding box centered in (0,0,0)  and setting the bounds to MINIMUM_BOUNDS
     */
    public BoundingBoxO(){
        maxX = maxY = maxZ = MINIMUM_BOUNDS;
        minX = minY = minZ = -MINIMUM_BOUNDS;
        centre = new Point3D(0.0,0.0,0.0);
    }
    
    /**
     * Add a point to the bounding box, the point coordinates expand the existing box
     * @param point A point that now is part of the box
     */
    public void add(Point3D point){
        if(point.getX()<minX){
            minX = point.getX();
            centre.setX((maxX+minX)/2);
        }
        if(point.getX()>maxX){
            maxX = point.getX();
            centre.setX((maxX+minX)/2);
        }
        if(point.getY()<minY){
            minY = point.getY();
            centre.setY((maxY+minY)/2);
        }
        if(point.getY()>maxY){
            maxY = point.getY();
            centre.setY((maxY+minY)/2);
        }
        if(point.getZ()<minZ){
            minZ = point.getZ();
            centre.setZ((maxZ+minZ)/2);
        }
        if(point.getZ()>maxZ){
            maxZ = point.getZ();
            centre.setZ((maxZ+minZ)/2);
        }
    }
    
    /**
     * Expands the bounding box to include inside another bounding box
     * @param boundingBox a bounding box to be included inside
     */
    public void add(BoundingBoxO boundingBox){
        if(this.minX>boundingBox.getMinX()) this.minX = boundingBox.getMinX();
        if(this.minY>boundingBox.getMinY()) this.minY = boundingBox.getMinY();
        if(this.minZ>boundingBox.getMinZ()) this.minZ = boundingBox.getMinZ();
        if(this.maxX>boundingBox.getMaxX()) this.maxX = boundingBox.getMaxX();
        if(this.maxY>boundingBox.getMaxY()) this.maxY = boundingBox.getMaxY();
        if(this.maxZ>boundingBox.getMaxZ()) this.maxZ = boundingBox.getMaxZ();
        centre.setX((maxX+minX)/2);
        centre.setY((maxY+minY)/2);
        centre.setZ((maxZ+minZ)/2);
    }

    /**
     * Transform a BoundingBoxO to a normal Java BoundingBox
     * @return A normal javafx.geometry.BoundingBox box
     */
    public BoundingBox toBoundingBox(){
        return new BoundingBox(minX,minY,minZ,maxX-minX,maxY-minY,maxZ-minZ);
    }
    
    /**
     * Returns the centre
     * @return the centre of the box 
     */
    public Point3D getCentre() {
        return centre;
    }

    /**
     * Sets the centre of the box, CAUTION: does not change the bounds
     * @param centre the bew centre
     */
    public void setCentre(Point3D centre) {
        this.centre = centre;
    }

    /**
     * The minimum X value of the box
     * @return the minimum X value of the box
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Sets the minimum X value of the box
     * @param minX the minimum X value of the box
     */
    public void setMinX(double minX) {
        this.minX = minX;
    }

    /**
     * The minimum Y value of the box
     * @return the minimum Y value of the box
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Sets the minimum Y value of the box
     * @param minY the minimum Y value of the box
     */
    public void setMinY(double minY) {
        this.minY = minY;
    }

    /**
     * The minimum Z value of the box
     * @return the minimum Z value of the box
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     * Sets the minimum Z value of the box
     * @param minZ the minimum Z value of the box
     */
    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    /**
     * The maximum X value of the box
     * @return the maximum X value of the box
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Sets the maximum X value of the box
     * @param maxX the maximum X value of the box
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * The maximum Y value of the box
     * @return the maximum Y value of the box
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Sets the maximum Y value of the box
     * @param maxY the maximum Y value of the box
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    /**
     * The maximum Z value of the box
     * @return the maximum Z value of the box
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * Sets the maximum Z value of the box
     * @param maxZ the maximum Z value of the box
     */
    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }
    
    /**
     * Draws the bounding box in OpenGL as Quads with GL_FRONT_AND_BACK, in white with lines
     * @param renderScreen 
     */
    @Override
    public void draw(OpenGLScreen renderScreen) {
        draw(renderScreen, new PointColourAlpha(1.0f, 1.0f, 1.0f), GL2.GL_LINE);
    }
    
    /**
     * Draws the bounding box in OpenGL as Quads with GL_FRONT_AND_BACK
     * @param renderScreen The OpenGL screen where to draw
     * @param colour The colour of the box
     * @param rasterMode the raster mode (GL_FILL, GL_LINES, GL_POINT)
     */
    public void draw(OpenGLScreen renderScreen, PointColourAlpha colour, int rasterMode) {
        renderScreen.getGl2().glBegin(GL2.GL_QUADS);
        renderScreen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, rasterMode);
        renderScreen.getGl2().glColor4f(colour.getR(), colour.getG(), colour.getB(),colour.getAlpha());
        //Z=max_Z
        renderScreen.getGl2().glVertex3d(maxX, maxY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, maxY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, minY, maxZ);
        renderScreen.getGl2().glVertex3d(maxX, minY, maxZ);
        //Z=minZ
        renderScreen.getGl2().glVertex3d(maxX, maxY, minZ);
        renderScreen.getGl2().glVertex3d(minX, maxY, minZ);
        renderScreen.getGl2().glVertex3d(minX, minY, minZ);
        renderScreen.getGl2().glVertex3d(maxX, minY, minZ);
        //Y=max_Y
        renderScreen.getGl2().glVertex3d(maxX, maxY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, maxY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, maxY, minZ);
        renderScreen.getGl2().glVertex3d(maxX, maxY, minZ);
        //Y=minY
        renderScreen.getGl2().glVertex3d(maxX, minY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, minY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, minY, minZ);
        renderScreen.getGl2().glVertex3d(maxX, minY, minZ);
        //X=max_X
        renderScreen.getGl2().glVertex3d(maxX, maxY, maxZ);
        renderScreen.getGl2().glVertex3d(maxX, minY, maxZ);
        renderScreen.getGl2().glVertex3d(maxX, minY, minZ);
        renderScreen.getGl2().glVertex3d(maxX, maxY, minZ);
        //X=minX
        renderScreen.getGl2().glVertex3d(minX, maxY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, minY, maxZ);
        renderScreen.getGl2().glVertex3d(minX, minY, minZ);
        renderScreen.getGl2().glVertex3d(minX, maxY, minZ);
        renderScreen.getGl2().glEnd();
    }

    /**
     * Moves the bounding box to a new centre, displaces the bounds as well, so the box does not change size
     * @param point A point to be the new centre
     */
    @Override
    public synchronized void moveTo(Point3D point) {
        maxX += point.getX() -centre.getX();
        minX += point.getX() -centre.getX();
        maxY += point.getY() -centre.getY();
        minY += point.getY() -centre.getY();
        maxZ += point.getZ() -centre.getZ();
        minZ += point.getZ() -centre.getZ();
        centre.copyCoords(point);
    }
    
    /**
     * Moves the bounding box to centre (0,0,0), displaces the bounds as well, so the box does not change size
     */
    public void moveToZero() {
        maxX -= centre.getX();
        minX -= centre.getX();
        maxY -= centre.getY();
        minY -= centre.getY();
        maxZ -= centre.getZ();
        minZ -= centre.getZ();
        centre.setToZero();
    }
    
    /**
     * Dispalces the bounding box substracting the coordinates of point, displaces the centre and the bounds as well, so the box does not change size
     * @param point A point with the coordinates to substract
     */
    @Override
    public synchronized void displace(Point3D point) {
        maxX -= point.getX();
        minX -= point.getX();
        maxY -= point.getY();
        minY -= point.getY();
        maxZ -= point.getZ();
        minZ -= point.getZ();
        centre.subToCoords(point);
    }
    
    /**
     * Does nothing, not needed because it uses no VBOs (in the current implementation, to save memory, as it is not usually rendered)
     * @param renderScreen The render screen where it is being rendered
     */
    @Override
    public void repack(OpenGLScreen renderScreen) {
        }

    /**
     * Does nothing, not needed because it uses no VBOs (in the current implementation, to save memory, as it is not usually rendered)
     * @param renderScreen 
     */
    @Override
    public void free(OpenGLScreen renderScreen) {
        }

    /**
     * The bounds of a bounidng box are itself, so it retruns a reference to itself
     * @return The same BoundingBoxO, itself
     */
    @Override
    public BoundingBoxO getBounds() {
        return this;
    }

    /**
     * Returns the OpenGL transformations of the bounding box, should make none, as it would usually depend on another renderable
     * @return This box OpenGL transformations (usually it should have none)
     */
    @Override
    public Transformations getTransformations() {
        return new Transformations();
    }
    
}
