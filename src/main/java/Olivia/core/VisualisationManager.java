/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.data.Point3D;
import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JPanel;
import com.jogamp.newt.event.InputEvent;

/**
 * This is the class that controls the Point Cloud visualisation, is has the data for the point cloud, the overlays and the render screen;
 * it has references to the GUI and may have its own control panels or jMenus to allow interaction with the user.
 * The JMenus may need to be treated in a similar way as the control panes, with their class defined and so, but because they are not yet used anywhere that is not so.
 * @author oscar.garcia
 * @param <VM> This is a reference to itself so it can be used afterwards, for example whe defining the OverlayArray\<Overlay\<VM\>,VM\>
 * @param <P> This is the class of the points of the cloud, at least they have to be Point3D_id (x,y,z and id), but usually they have more data
 * @param <A> The class of the PointArray, unlikely to be changed, but maybe an specific pointcloud needs something extra
 * @param <R> The class of the OpenGL screen where all will be rendered, unlikely to be changed, but may need to be extended to change the default way everything is rendered
 * @param <CP> The class of the control panel, each different visualisation is likely to need its own
 */
public abstract class VisualisationManager<VM extends VisualisationManager, P extends Point3D_id, A extends PointArray<P>, R extends OpenGLScreen, CP extends JPanel> {
    
    /**
     * The id of this visualisation, set on creation by the main class
     */
    protected int id;
    
    /**
     * The full GUI of the application
     */
    protected MainFrame gui;
    
    /**
     * A field to check if it is being redered on 3D
     */
    protected boolean isStereo3D;
    
    /**
     * A field to check if the point beign selected by the mouse should be drawn
     */
    protected boolean drawMouseSelection;
    
    /**
     * The displacemetn of the point cloud; due to issues with OpenGL the point cloud should be centered in (0,0,0), so all points are displaced, that displacement is stored here for future reference
     */
    protected Point3D displacement;
    
    /**
     * A field to check if the diplacement of the main pont cloud has been made
     */
    protected boolean displacementSet;
    
    /**
     * A JMenu to be added in the main menu to control the visualisation
     */
    protected JMenu jMenu;
    
    /**
     * Everyone listening for events, like pointSelected
     */
    //protected ArrayList<ActionListener> listeners;
    
    /**
     * The main point cloud, only one allowed by visualisation, everything else must be Overlay
     */
    protected A pointCloud;
    
    /**
     * The OpenGL screen where all will be rendered
     */
    protected R renderScreen;
    
    /**
     * The overlays to be addded on top of the main point cloud
     */
    protected OverlayArray<Overlay<VM>,VM> overlays;
    
    /**
     * The control pane to control this specific visualisation
     */
    protected CP controlPane;
    
    /**
     * This visualisation name
     */
    protected String name;
    
    /*
    public VisualisationManager(A pointCloud, R renderScreen, OA overlays, CP controlPane){
        if(pointCloud == null){
            System.exit(-1);
        }
        if(renderScreen == null){
            System.exit(-1);
        }
        if(overlays == null){
            System.exit(-1);
        }
        if(controlPane == null){
            System.exit(-1);
        }
        this.pointCloud = pointCloud;
        this.renderScreen = renderScreen;
        this.overlays = overlays;
        this.controlPane = controlPane;
    }
    */
    
    /**
     * Creates a new visualisation with name "Visualisation + id", displacementSet is set to false and the jMenu is not enabled
     * @param id This visualisation id, must be unique
     * @param gui The application GUI
     * @param isStereo3D Whether it is stereo 3D or not (cannot be changed afterwards TODO:allow it to be changed)
     */
    public VisualisationManager(int id, MainFrame gui, boolean isStereo3D){
        this(id,gui,isStereo3D,"Visualisation-" +id);
    }
    
    /**
     * Creates a new visualisation with name "Visualisation + id", displacementSet is set to false and the jMenu is not enabled
     * @param id This visualisation id, must be unique
     * @param gui The application GUI
     * @param isStereo3D Whether it is stereo 3D or not (cannot be changed afterwards TODO:allow it to be changed)
     * @param name The visualisation name, needs not be unique
     */
    public VisualisationManager(int id, MainFrame gui, boolean isStereo3D, String name){
        displacement = new Point3D();
        displacementSet = false;
        this.id = id;
        this.gui = gui;
        this.isStereo3D = isStereo3D;
        this.name = name;
        jMenu = new JMenu(name);
        jMenu.setEnabled(false);
    }
    
    /**
     * This method will be called to read the data from files, not all data may be need to be read now, but the main point cloud probably should
     * @param filePath The path where the data are
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public abstract void readFromFiles(String filePath) throws FileNotFoundException, IOException;
    
    /**
     * Adds an ActionListener
     *
     * @param al The action listener
     */
    /*
    public void addActionListener(ActionListener al) {
        listeners.add(al);
    }
    */

    /**
     * Removes an Action Listener
     *
     * @param al The action listener
     */
    /*
    public void removeActionListener(ActionListener al) {
        listeners.remove(al);
    }
    */

    /**
     * Fires the ActionEvent "pointSelected" to all listeners Should be
     * protected, but it is being called from the mouse listener
     *
     * @param name The name/identifier of the event
     */
    /*
    public void fireEvent(String name) {
        ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_FIRST, name);
        for (ActionListener ac : listeners) {
            ac.actionPerformed(ae);
        }
    }
    */
    
    /**
     * Gets thsi visualisation id 
     * @return The id (should be unique, but it is not guaranteed by this class)
     */    
    public int getId(){
        return id;
    }
    
    /**
     * Gets the GUI
     * @return The application GUI
     */
    public MainFrame getGUI(){
        return gui;
    }
    
    /**
     * Checks whether it is rendering in stereo 3D
     * @return whether it is rendering in stereo 3D
     */
    public boolean isStereo3D(){
        return isStereo3D;
    }

    /**
     * Checks whether it is highlighting the point selected with the mouse in the pointcloud
     * @return whether it is highlighting the point selected with the mouse in the pointcloud
     */
    public boolean isDrawMouseSelection() {
        return drawMouseSelection;
    }

    /**
     * Sets whether to highlight the point selected with the mouse in the pointcloud
     * @param drawMouseSelection true to highlight the point selected with the mouse in the pointcloud
     */
    public void setDrawMouseSelection(boolean drawMouseSelection) {
        this.drawMouseSelection = drawMouseSelection;
    }
    
    /**
     * Gets the jMenu, if it has no name set yet it sets its name to the same one from this
     * @return the JMenu of the visualisation
     */
    public JMenu getjMenu() {
        if(jMenu.getText().equals("")) jMenu.setText(name);
        return jMenu;
    }
       
    /**
     * Gets the pointCloud, it is nedded in a few places, mainly to get a point from its id or the size, maybe it would be good to limit its access
     * @return The main Point Cloud
     */
    public A getPointCloud() {
        return pointCloud;
    }
    
    /**
     * This sets the main point cloud, displaces it so it is centred in (0,0,0), using the point given by PointArray.getCentreOriginal(),
     * displacementSet is set to true, and the displacement is set
     * it should be used instead of directly reading the point in readFromFiles() into the point cloud for these reasons
     * @param points 
     */
    public void setPointCloud(A points) {
        if(displacementSet){
            System.out.println("CAUTION: using a previous displacement or trying to add a second point cloud to the same visualisation");
            return;
        }else{
            displacement = points.getCentreOriginal();
            displacementSet=true;
        }
        //displacement = points.getBounds().getCentre();
        this.pointCloud = points;
        //this.pointCloud.centerPointsAtCoordOrigin();
        this.pointCloud.displace(displacement);
        //centre = pointCloud.getCentreOriginal();
        /*
        System.out.println("Centering camera at" + points.getBounds().getCentre());
        this.getRenderScreen().getCamera().centerAt(points.getBounds().getCentre());
        */
        
    }
    
    /**
     * Gets the render screen
     * @return the render screen
     */
    public R getRenderScreen(){
        return renderScreen;
    }
    
    /**
     * Gets the overlays of the visualisation, same as with getPointCloud() should we repeat Overlays methods to encapsulate access??
     * @return this visualisation overlays
     */
    public OverlayArray getOvelays() {
        return overlays;
    }
    
    /**
     * Adds an overlay to the visualisation and displaces it so its coordinates have the same reference as the main point cloud,
     * for this reason it should be used instead of VisualisalisationManager.getOverlays().add(), see VisualisalisationManager.getOverlays()
     * @param overlay The overlay to be added
     */
    public void addOverlay(Overlay overlay){
        overlay.displace(displacement);
        overlays.add(overlay);
    }
    
    /**
     * Gets the control pane of the visualisation
     * @return The control pane
     */
    public CP getControlPane(){
        return controlPane;
    }
    
    /**
     * Sets the name of the visualisation
     * @param name this visualisation name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Gets the name of the visualisation
     * @return this visualisation name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the displacement of the visualisation, should be the original centre of the main point cloud, see VisualisationManager.setPointCloud(),
     * may be (0,0,0) if diplacementSet==false
     * @return The displacement from the original data to centre in (0,0,0)
     * @see setPointCloud()
     */
    public Point3D getDisplacement() {
        return displacement;
    }


    /**
     * USE CAREFULLY Sets the displacement of the visualisation, undoes the original displacement (if any) and displaces again both overlays and the point cloud.
     * It is a different method from changeDisplacement because there may be a need in the future of just setting the displacement and not moving everething, for now no
     * @param displacement The displacement from the original data to centre in (0,0,0)
     */
    public void setDisplacement(Point3D displacement) {
        this.changeDisplacement(displacement);
    }
    
    /**
     * USE CAREFULLY Sets the displacement of the visualisation, undoes the original displacement and displaces again both overlays and the point cloud.
     * It is a different method from setDisplacement because there may be a need in the future of just setting the displacement and not moving everething, for now no
     * @param newDisplacement The new displacement
     */
    protected synchronized void changeDisplacement(Point3D newDisplacement){
        if(newDisplacement.equals(displacement)) return;
        System.out.println("CAUTION: Redisplacing " + this.name +", original data coordinates of the points may be lost");
        Point3D undo_displacement = new Point3D(newDisplacement.getX()-displacement.getX(),newDisplacement.getY()-displacement.getY(),+newDisplacement.getZ()-displacement.getZ());
        this.displacement = newDisplacement;
        for(int i=0; i<overlays.size();i++){
            overlays.get(i).displace(undo_displacement);     
        }
        this.pointCloud.displace(undo_displacement);
        //this.displacement = newDisplacement; //If this is here sometimes it is executed twice, race condition???
        displacementSet = true;
        this.repack();
    }

    /**
     * Checks whether there is a displacement in this visualisation
     * @return true if thsi visualisation is displaced
     */
    public boolean isDisplacementSet() {
        return displacementSet;
    }
    
     
     /**
     * Gets the name of the visualisation
     * @return The text information
     */
    public String getTextInfo() {
        return name;
    }
    
    /**
     * Draws the overlays only, do not remember why now TODO
     */
    public void draw(){
        //points.draw(renderScreen);
        overlays.draw();
    }
    
    /**
     * Because the point cloud uses VBOs, and thay are native programming, they need to be manually freed, this method does that, ONLY FOR THE MAIN POINT CLOUD
     */
    public void freeVBOs(){
        pointCloud.freeVBO(renderScreen);
    }
    
    /**
     * Destroys the visualisation, not very well checked with the overlays, probable memory leaks somewhere TODO
     */
    public void destroy(){
        freeVBOs();
        overlays.remove();
        pointCloud.clear();
    }
    
    /**
     * Repacks the overlays and point cloud, to call when changes are made
     */
    public void repack(){
        pointCloud.repack(renderScreen);
        overlays.repack(renderScreen);
    }
    
    /**
     * Checks the correctness of the visualisation, because so many things depend on class generics thsy cannot be instantieted here, so this methods checks if they exist,
     * checks if renderScreen, pointCloud, overlays and controlPane are valid and instantiated
     * @return false if something mayor is missing or null, true if everthing is instantiated
     */
    public boolean checkCorrectness(){
        if(renderScreen==null){
            System.out.println("Render Screen not defined!!");
            return false;
        }
        if(pointCloud==null){
            System.out.println("Points not defined!!");
            return false;
        }
        if(overlays==null){
            System.out.println("Overlays not defined!!");
            return false;
        }
        if(controlPane==null){
            System.out.println("controlPane not defined!!");
            return false;
        }
        return true;
    }
    
    public void windowInteracted(InputEvent event){
        if (Olivia.getLoadedVisualisations().contains(this) && this != gui.getActiveVisualisation()) {
                gui.setActiveVisualisationManager(this);
                gui.updateAll();
        }
    }

}
