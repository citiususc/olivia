/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render;

import Olivia.core.data.Point3D;

/**
 * This interface defines all that is absolutely needed to render something on OpenGL on this application, all classes intended to be drawn or rendered should extends from this one
 * @author oscar.garcia
 */
public interface Renderable {
    
    /**
     * Renders the object on an OpenGL screen
     * @param renderScreen the render screen where to render
     */
    public void draw(OpenGLScreen renderScreen);
    
    /**
     * Gets the bounds, to know where it isin the scene
     * @return the bounds
     */
    public BoundingBoxO getBounds();
    
    /**
     * Moves the object to a point in the scene, should move the bounds too
     * @param point the point where to move
     */
    public void moveTo(Point3D point);
    
    /**
    * Different from move, displaces all vertices or other minus a value in point
    * @param point the point which contains the coordinates to be substracted
    */
    public void displace(Point3D point);
    
    /**
     * Gets the OpenGL trasnformations to be applied to the object
     * @return the OpenGL trasnformations
     */
    public Transformations getTransformations();
    
    /**
     * Repacks the object when changes are made to it, some objects may no need to do anything in this method
     * @param renderScreen the render screen where to render
     */
    public void repack(OpenGLScreen renderScreen);
    
    /**
     * Free the objects native memory, some objects may no need to do anything in this method
     * @param renderScreen the render screen where it was being rendered
     */
    public void free(OpenGLScreen renderScreen);
    
}
