/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.data.Point3D;
import Olivia.core.gui.controls.overlays.TransformationsOptionsPanel;
import Olivia.core.render.BoundingBoxO;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.Renderable;
import Olivia.core.render.Transformations;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * This class is used to render figures, graphs, shapes, animations or others on top of the point cloud; when extending from it the main method to override is drawShape(), which takes care of the rendering.
 * The class allow to define bounds, optionPanels or listen to action on the screen to control the Overlay from the GUI 
 * Fires events:
 * "moved" when moved by moveTo()
 * @author oscar.garcia
 * @param <VM> In case the overlay only works in a particular visualisation
 */
public abstract class Overlay<VM extends VisualisationManager> implements ActionListener,Renderable{
    
    /**
     * The visualisationManager where this Overlay is 
     */
    protected VM visualisationManager;
    
    /**
     * The boundign box of this overlay, used mainly for the center
     */
    protected BoundingBoxO bounds;
    
    /**
     * The OpenGL transfomation to apply to this Overlay
     */
    protected Transformations transformations;
    
    /**
     * The option panels that allow to control this overlay from the GUI
     */
    protected ArrayList<JPanel> optionPanels;
    
    /**
     * The name
     */
    protected String name;
    
    /**
     * Everyone listening for events, like pointSelected
     */
    protected ArrayList<ActionListener> listeners;
    
    /**
     * Creates a new Overlay, centerd in (0,0,0), with no OpenGL transformations and name Overlay
     * @param visualisationManager its visualisationManager
     */
    public Overlay(VM visualisationManager){
        this(visualisationManager,"Overlay");
    }
    
    /**
     * Creates a new Overlay, centerd in (0,0,0), with no OpenGL transformations
     * @param visualisationManager its visualisationManager
     * @param name its name
     */
    public Overlay(VM visualisationManager, String name){
        this.visualisationManager = visualisationManager;
        this.name = name;
        this.bounds = new BoundingBoxO();
        this.transformations = new Transformations();
        transformations.setCentre(bounds.getCentre());
        optionPanels = new ArrayList<>();
        listeners = new ArrayList<>();
    }
    
    /**
     * Gets the overlay name
     * @return the overlay name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the overlay name
     * @param name the overlay name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the tranformations (OpenGL operations) of this overlay
     * @return the transformations
     */
    public Transformations getTransformations() {
        return transformations;
    }
    
    /**
     * Adds an ActionListener
     *
     * @param al The action listener
     */
    public void addActionListener(ActionListener al) {
        listeners.add(al);
    }

    /**
     * Removes an Action Listener
     *
     * @param al The action listener
     */
    public void removeActionListener(ActionListener al) {
        listeners.remove(al);
    }

    /**
     * Fires the ActionEvent "pointSelected" to all listeners Should be
     * protected, but it is being called from the mouse listener
     *
     * @param name The name/identifier of the event
     */
    protected void fireEvent(String name) {
        ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_FIRST, name);
        for (ActionListener ac : listeners) {
            ac.actionPerformed(ae);
        }
    }
    
    /**
     * This method may be called to draw, but is legacy, draw(OpenGLScreen renderScreen) should be used when possible,
     * Draws the overlay in the renderScreen of the Overlays visualisationManger
     */
    public void draw(){
        this.draw(visualisationManager.getRenderScreen());
    }
    
    /**
     * Draws the overlay in the given render screen, applying its trasformations, but to simplify only the renderScreen of the Ovelays visualisationManger can be used;
     * this behaviour is subject to change in the future.
     * Should not be directly overriden if possible, please override drawShape()
     * @param renderScreen 
     */
    @Override
    public void draw(OpenGLScreen renderScreen){
        if(renderScreen.equals(visualisationManager.getRenderScreen())){
            renderScreen.getGl2().glPushMatrix();
            //renderScreen.getGl2().glLoadIdentity();
            //this.transformations.setCentre(this.bounds.getCentre());
            this.transformations.doTranslateAndRotate(renderScreen);
            this.drawShape(renderScreen);
            renderScreen.getGl2().glPopMatrix();
        }else{
            Olivia.textOutputter.println("This object can only be rendered in its own render screen");
        }
    }

    /**
     * This method takes care of drawing the overlay in OpenGL, by whichever means
     * @param renderScreen the OpenGL screen to use
     */
    protected abstract void drawShape(OpenGLScreen renderScreen);
    
    /**
     * This method is used to repack the overlay when it changes, used mainly for VBOs, does nothing in default, needs to be overriden if necessary
     * @param renderScreen 
     */
    @Override
    public void repack(OpenGLScreen renderScreen){}
    
    /**
     * This frees the native memory of the overlay, used mainly for VBOs, does nothing in default, needs to be overriden if necessary
     * @param renderScreen 
     */
    @Override
    public void free(OpenGLScreen renderScreen){}
    
     /**
     * Gets the point used to select the overlay, in case it is not the center, if not overriden it is the same as bounds.getCentre(), but this one should be used in case other overlay decides to use another
     *
     * @return A point with the coordiantes of the point
     */
    public Point3D getSelectingPoint(){
        return bounds.getCentre();
    }
    
    /**
     * Moves the overlay somewhere, this should not change the transformations or do OpenGL operations, 
     * but change instead the geometry of the Overlay; by default it just moves its bounds
     * @param point 
     */
    @Override
    public synchronized void moveTo(Point3D point){
        bounds.moveTo(point);
        this.fireEvent("moved");
    }
    
    public synchronized void moveTo(double x, double y, double z){
        Point3D point = new Point3D(x,y,z);
        this.moveTo(point);
    }
    
    /**
     * Displaces the overlay by substracting (x,y,z) to its geometry, this should not change the transformations or do OpenGL operations, 
     * but change instead the geometry of the Overlay; by deafult it just displaces its bounds
     * @param point 
     */
    @Override
    public synchronized void displace(Point3D point){
        bounds.displace(point);
    }

    /*
    public void resetCentres(Point3D point){
        this.centreCurrent.copyCoords(point);
        //this.centreOriginal.copyCoords(point);
    }
       
    public void centerOverlay(){
        centreCurrent.copyCoords(centreOriginal);
    }
    
    public void centerOverlay(double x, double y, double z){
        centreCurrent.setCoords(centreOriginal.getX()- x,centreOriginal.getY() -y,centreOriginal.getZ() -z);
        //centreCurrent.setCoords(x -centreOriginal.getX(),y -centreOriginal.getY(),z -centreOriginal.getZ());
        /*centreCurrent.setCoords(
                x + centreCurrent.getX() - centreOriginal.getX(),
                y + centreCurrent.getY() - centreOriginal.getY(),
                z + centreCurrent.getZ() - centreOriginal.getZ()
        );*/
    /*}
    public void centerOverlay(Point3D centre){
        centerOverlay(centre.getX(),centre.getY(),centre.getZ());
    }
    
    protected void undoCenterOverlay(){
        centreCurrent.setToZero();
    }
    */
    
    /**
     * The action to perform in an event, by defualt it does nothing, needs to be overriden
     * @param e The action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //ignore
    }
    
    /**
     * Adds the overlay as an actionListener to the renderScreen of its visualisationManager; a version where other screen could be selected was deemed unnecessary
     */
    public void listenToActionsOnScreen(){
        visualisationManager.getRenderScreen().addActionListener(this);
    }

    /**
     * Gets the bounds of the overlay; setting this bounds correctly is up to the programmer
     * @return the bounds
     */
    @Override
    public BoundingBoxO getBounds() {
        return bounds;
    }   
    
    /**
     * Creates the option panels of the overlay to allow it to be controlled by the GUI; the panels are not created when creating the overlay in case they are not necessary.
     * The only panel that is created here is a TransformationsOptionsPanel, override if more panels are needed
     */
    protected void createOptionPanels(){
        TransformationsOptionsPanel tranformationsPanel = new TransformationsOptionsPanel(this);
        optionPanels.add(tranformationsPanel);
    }
    
    /**
     * Gets the optionPanels; if they do not yet exist, they are created
     * @return The option panels, check createOptionPanels() to see which ones exist
     */
    public ArrayList<JPanel> getOptionPanels(){
        if(optionPanels.isEmpty()){
            createOptionPanels();
        }
        return optionPanels;
    }
    
}
