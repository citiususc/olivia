/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.data.Point3D;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * An Overlay that may contain more overlays, and be contained itself, can be used to create complex overlays with different parts;
 * works similary to having nodes, children and parents in a traditional 3D engine, but was implemented this way to show a simplified view for simple overlays.
 * The Overlys it contains may be selected or unselected, by default the OverlayArray will be drawn with all its selected Overlays at the same time, but this 
 * behaviour can be changed to drawing the currentOverlay (to be like an iterator or slect by mouse) or all the overlays
 * @author oscar.garcia
 * @param <O> The kind of overlays to store
 * @param <VM> The visualisations it can work on
 */
public class OverlayArray<O extends Overlay<VM>,VM extends VisualisationManager> extends Overlay<VM>{
    
    /**
     * dummy, not actually tested
     */ 
    public static int MAX_OVERLAYS_SUPPORTED = 10000000;
    
    /**
     * The overlays here stored
     */
    protected ArrayList<O> overlays;
    
    /**
     * If index i of selected is true, then index i of overlays is selected
     */
    protected ArrayList<Boolean> selected;
    
    /**
     * Points to the currently selected overlay
     */
    protected int current;
    
    /**
     * Wheter the currentOVerlay is selected by mouse clicks on the renderScreen
     */
    protected boolean selectingCurrentByMouse;
    
    /**
     * The draw modes
     */
    protected boolean drawSelected, drawAll, drawCurrent;
    
    /**
     * Creates a new OverlayArray, with drawSelected to true and NOT selecting by mouse
     * @param visualisationManager the visualisationManager
     */
    public OverlayArray(VM visualisationManager){
        this(visualisationManager,"Overlay Array");
    }
    
    /**
     * Creates a new OverlayArray, with drawSelected to true and NOT selecting by mouse
     * @param visualisationManager the visualisationManager
     * @param name its name
     */
    public OverlayArray(VM visualisationManager, String name){
        super(visualisationManager,name);
        overlays = new ArrayList<>();
        selected = new ArrayList<>();
        current = MAX_OVERLAYS_SUPPORTED;
        selectingCurrentByMouse = false;
        drawAll=drawCurrent = false;
        drawSelected = true;
    }
    
    /**
     * Check whether it contains any Ovelays
     * @return true if there are no overlays
     */
    public boolean isEmpty(){
        return overlays.isEmpty();
    }

    /**
     * Check whether it is selecting the current overlay by mouse interaction, see setDrawCurrent() and listenToActionsOnScreen()
     * @return True if it is selecting the current overlay by mouse iteraction
     */
    public boolean isSelectingCurrentByMouse() {
        return selectingCurrentByMouse;
    }

    /**
     * Sets whether to select the current overlay by mouse interaction
     * @param selectingCurrentByMouse true if mouse selection is needed
     */
    public void setSelectingCurrentByMouse(boolean selectingCurrentByMouse) {
        this.selectingCurrentByMouse = selectingCurrentByMouse;
    }

    /**
     * Check whether it is drawing all the SELECTED=true overlays
     * @return true if it is drawing all the SELECTED=true overlays
     */
    public boolean isDrawSelected() {
        return drawSelected;
    }

    /**
     * Set whether to draw all the SELECTED=true overlays
     * @param drawSelected true o draw all the SELECTED=true overlays
     */
    public void setDrawSelected(boolean drawSelected) {
        this.drawSelected = drawSelected;
    }

    /**
     * Check whether it is drawing all overlays, regardless of their selected status
     * @return true if it is drawing all overlays
     */
    public boolean isDrawAll() {
        return drawAll;
    }

    /**
     * Set whether to draw all the overlays, regardless of their selected status
     * @param drawAll true to draw all overlays
     */
    public void setDrawAll(boolean drawAll) {
        this.drawAll = drawAll;
    }

    /**
     * Check whether to draw the current overlay, regardless of their selected status 
     * @return true if it is drawing the current overlays
     */
    public boolean isDrawCurrent() {
        return drawCurrent;
    }

    /**
     * Set whether to draw the current overlay, regardless of their selected status 
     * @param drawCurrent true to draw the current overlay
     */
    public void setDrawCurrent(boolean drawCurrent) {
        this.drawCurrent = drawCurrent;
    }
    
    /**
     * Sets all draw modes to false, so they can be activated afterwards one by one, if they are not, nothing will be drawn
     */
    public void setDrawModesToFalse(){
        this.drawAll = false;
        this.drawCurrent = false;
        this.drawSelected = false;
    }
    
    /**
     * Adds an Overlay e to the OverlayArray, e bounds are compounded with the OverlayArray bounds, e name is concatenated with its index,
     * the current overlays becomes e, visualisationManager.getGUI().updateControlPanes() is called
     * @param e An Overlay to be added, its name will be changed to add the array index
     * @param isSelected Whether e is selected or not
     * @return true if added, false if it could not be added (MAX_OVERLAYS_SUPPORTED reached or other problem)
     */
    public boolean add(O e, boolean isSelected){
        if(overlays.size()==MAX_OVERLAYS_SUPPORTED) return false;
        boolean ret = overlays.add(e);
        if(ret){
            this.bounds.add(e.getBounds());
            ret = selected.add(isSelected);
            if(current==MAX_OVERLAYS_SUPPORTED){
                current = 0;
            }else{
                current++;
            }
            e.setName(e.getName() + " " + current);
        }
        /*if(visualisationManager.isCentreSet()){
            e.centerOverlay(visualisationManager.getSelectingPoint());
        }else{
            //e.centerOverlay();
            //this cannot be done here, it messes things up
            //visualisationManager.setCentre(e.getCenter());
        }
        */
        visualisationManager.getGUI().updateControlPanes();
        return ret;
    }
    
    /**
     * Adds an Overlay e to the OverlayArray, e bounds are compounded with the OverlayArray bounds, e name is concatenated with its index,
     * the current overlays becomes e, visualisationManager.getGUI().updateControlPanes() is called
     * @param e An Overlay to be added, its name will be changed to add the array index, it will be set as selected
     * @return true if added, false if it could not be added (MAX_OVERLAYS_SUPPORTED reached or other problem)
     */
    public boolean add(O e){
        return this.add(e,true);
    }
    
    /**
     * Gets the overlays at index i
     * @param i the index of the overlays
     * @return The overlay at index i
     */
    public O get(int i){
        if(i<overlays.size()){
            return overlays.get(i);
        }
        //System.out.println("Overlays returning null!!");
        return null;     
    }
    
    /**
     * Gets the size, the number of overlays stored
     * @return the number of overlays currently stored
     */
    public int size(){
        return overlays.size(); 
    }
    
    /**
     * Given a Point3D point the OverlayArray iterates through its array to find one close enough to it in the 3D space and sets it as current,
     * Point3D.areCloseBy() is used, with its default epsilons and Overlay.getSelectingPoint() is used, NOT the bounds of the Overlay.
     * The first Overlay that is close enough will be set as current, it may not find any Overlay close enough
     * @param point The Point3D in the OpenGL space
     * @return true if an overlay was found close enough, false if no overlay found close enough to point
     */
    public boolean setOverlayAtPointAsCurrent(Point3D point) {
        if (point == null) {
            return false;
        }
        //System.out.println("checking for point " + point.getX() + " " + point.getY() + " " +point.getZ());
        int i = 0;
        for (O overlay : overlays) {
            if (point.areCloseBy(overlay.getSelectingPoint())) {
                current = i;
                System.out.println("Overlay at " + point + " set as current in " + this.name);
                return true;
            }
            i++;
        }
        return false;
    }
    
    /**
     * Given (x,y,z) coordinates the OverlayArray iterates through its array to find one close enough to them in the 3D space and sets it as current,
     * Point3D.areCloseBy() is used, with its default epsilons and Overlay.getSelectingPoint() is used, NOT the bounds of the Overlay.
     * The first Overlay that is close enough will be set as current, it may not find any Overlay close enough
     * @param coords (x,y,z) coordinates on the OpenGL space
     * @return true if an overlay was found close enough, false if no overlay found close enough the coords
     */
    public boolean setOverlayAtPointAsCurrent(double[] coords) {
        if (coords.length != 3) {
            return false;
        }
        Point3D_id point = new Point3D_id(coords[0],coords[1],coords[2]);
        return setOverlayAtPointAsCurrent(point);
    }
    
    /**
     * Given a Point3D point the OverlayArray iterates through its array to find one close enough to it in the 3D space and selects it according to isSelected,
     * Point3D.areCloseBy() is used, with its default epsilons and Overlay.getSelectingPoint() is used, NOT the bounds of the Overlay.
     * The first Overlay that is close enough will be selected, it may not find any Overlay close enough
     * @param point The Point3D in the OpenGL space
     * @param isSelected whether to select or unselect the overlay
     * @return true if an overlay was found close enough, false if no overlay found close enough to point
     */
    public boolean selectOverlayAtPoint(Point3D point, boolean isSelected) {
        if (point == null) {
            return false;
        }
        int i = 0;
        for (O overlay : overlays) {
            if (point.areCloseBy(overlay.getSelectingPoint())) {
                selected.set(i,isSelected);
                return true;
            }
            i++;
        }
        return false;
    }
    
    /**
     * Given a Point3D point the OverlayArray iterates through its array to find one close enough to it in the 3D space and selects it ,
     * Point3D.areCloseBy() is used, with its default epsilons and Overlay.getSelectingPoint() is used, NOT the bounds of the Overlay.
     * The first Overlay that is close enough will be selected, it may not find any Overlay close enough
     * @param point The Point3D in the OpenGL space
     * @return true if an overlay was found close enough, false if no overlay found close enough to point
     */
    public boolean selectOverlayAtPoint(Point3D point) {
        return selectOverlayAtPoint(point, true);
    }
    
    /**
     * Given (x,y,z) coordinates point the OverlayArray iterates through its array to find one close enough to them in the 3D space and selects it according to isSelected,
     * Point3D.areCloseBy() is used, with its default epsilons and Overlay.getSelectingPoint() is used, NOT the bounds of the Overlay.
     * The first Overlay that is close enough will be selected, it may not find any Overlay close enough
     * @param coords coords (x,y,z) coordinates on the OpenGL space
     * @param isSelected whether to select or unselect the overlay
     * @return true if an overlay was found close enough, false if no overlay found close enough to the coords
     */
    public boolean selectOverlayAtPoint(double[] coords, boolean isSelected) {
        if (coords.length != 3) {
            return false;
        }
        Point3D_id point = new Point3D_id(coords[0],coords[1],coords[3]);
        return selectOverlayAtPoint(point, isSelected);
    }
    
    /**
     * Given (x,y,z) coordinates point the OverlayArray iterates through its array to find one close enough to them in the 3D space and selects it according,
     * Point3D.areCloseBy() is used, with its default epsilons and Overlay.getSelectingPoint() is used, NOT the bounds of the Overlay.
     * The first Overlay that is close enough will be selected, it may not find any Overlay close enough
     * @param coords coords (x,y,z) coordinates on the OpenGL space
     * @return true if an overlay was found close enough, false if no overlay found close enough to the coords
     */
    public boolean selectOverlayAtPoint(double[] coords) {
        return selectOverlayAtPoint(coords, true);
    }
    
    /**
     * Removes the overlay at index i, BEWARE: calling its free() method (to save memory), the OverlayArray bounds are NOT recomputed!
     * visualisationManager.getGUI().updateControlPanes() is called
     * @param i the index of the overlay, if it is out of bounds a message is given
     */
    public void remove(int i){
        if(i<overlays.size()){
            overlays.get(i).free(visualisationManager.getRenderScreen());
            overlays.remove(i);
            selected.remove(i);
            if(i==current){
                if(overlays.isEmpty()){
                    current = MAX_OVERLAYS_SUPPORTED;
                }else{
                    current = 0;
                }
            }
            visualisationManager.getGUI().updateControlPanes();
        }else{
            System.out.println("Index out of bounds/No current overlay");
        }       
    }
    
    /**
     * Removes overlay, BEWARE: calling its free() method (to save memory), the OverlayArray bounds are NOT recomputed!
     * visualisationManager.getGUI().updateControlPanes() is called
     * @param overlay the overlay to remove, if it is not found a message is given
     */
    public void remove(O overlay){
        int index = overlays.indexOf(overlays);
        if(index==-1){
            System.out.println("Overlay not found");
        }
        remove(index);       
    }
    
    /**
     * Removes the current overlay, BEWARE: calling its free() method (to save memory), the OverlayArray bounds are NOT recomputed!
     * visualisationManager.getGUI().updateControlPanes() is called
     */
    public void remove(){
        remove(current);       
    }

    /**
     * Sets the overlay at index i as selected according to value, visualisationManager.getGUI().updateControlPanes() is called
     * @param i the overlay index in this array
     * @param value true if selected, false if NOT selected
     * @return true if the operation could be completed, false if not (index out of bounds i.e.)
     */
    public boolean select(int i, boolean value){
        if(i<selected.size()){
            boolean ret = selected.set(i, value);
            visualisationManager.getGUI().updateControlPanes();
            return ret;
        }else{
            System.out.println("Index out of bounds/No current overlay");
            return false;
        }
    }
    
    /**
     * Sets the overlay as selected according to value, visualisationManager.getGUI().updateControlPanes() is called
     * @param overlay the overlay to select or unselect
     * @param value true if selected, false if NOT selected
     * @return true if the operation could be completed, fasle if not (the overlay is not in the array i.e.)
     */
    public boolean select(O overlay, boolean value){
        int i = overlays.indexOf(overlay);
        if(i!=-1){
            return select(i,value);
        }
        return false;
    }
    
    /**
     * Sets the overlay as selected, visualisationManager.getGUI().updateControlPanes() is called
     * @param overlay the overlay to select
     * @return true if the operation could be completed, false if not (the overlay is not in the array i.e.)
     */
    public boolean select(O overlay){
        return select(overlay,true);
    }
    
    /**
     * Sets the current overlay as selected, visualisationManager.getGUI().updateControlPanes() is called
     * @return true if the operation could be completed, false if not
     */
    public boolean select(){
        return select(current,Boolean.TRUE);
    }
    
    /**
     * Sets the current overlay as selected according to value, visualisationManager.getGUI().updateControlPanes() is called
     * @param value true if selected, false if NOT selected
     * @return true if the operation could be completed, fasle if not (the overlay is not in the array i.e.)
     */
    public boolean select(boolean value){
        return select(current,value);
    }
    
    /**
     * Sets the overlay at index i as selected, visualisationManager.getGUI().updateControlPanes() is called
     * @param i the overlay index in this array
     * @return true if the operation could be completed, false if not (index out of bounds i.e.)
     */
    public boolean select(int i){
        return select(i, Boolean.TRUE);
    }
    
    /**
     * Sets the current overlay as NOT selected, visualisationManager.getGUI().updateControlPanes() is called
     * @return true if the operation could be completed, false if not
     */
    public boolean unSelect(){
        return select(current, Boolean.FALSE);
    }
    
    /**
     * Sets the overlay at index i as NOT selected, visualisationManager.getGUI().updateControlPanes() is called
     * @param i the overlay index in this array
     * @return true if the operation could be completed, false if not (index out of bounds i.e.)
     */
    public boolean unSelect(int i){
        return select(i, Boolean.FALSE);
    }
    
    /**
     * Checks whether the overlay at index i is selected
     * @param i the overlay index in this array
     * @return true if the operation could be completed, false if not (index out of bounds i.e.)
     */
    public boolean isSelected(int i){
         if(i<selected.size()){
            return selected.get(i);
        }else{
            System.out.println("Index out of bounds/No current overlay");
            return false;
        }
    }
    
    /**
     * Checks whether the current overlay is selected
     * @return true if the operation could be completed
     */
    public boolean isSelected(){
        return isSelected(current);
    }
    
    /**
     * Draws the overlays stored int he array,
     * if drawSelected==true draws all the overlays for which isSelected(overlay) is true,
     * if drawCurrent==true draws the current overleay regardless of whether it is selected,
     * if drawAllt==true draws all the overlays regardless of whether they are selected.
     * @param renderScreen 
     */
    @Override
    protected void drawShape(OpenGLScreen renderScreen){
        if(drawSelected) drawShapeSelected(renderScreen);
        if(drawCurrent) drawShapeCurrent(renderScreen);
        if(drawAll) drawShapeAll(renderScreen);
    }
    
    /**
     * Draws all the overlays in the array this.overlays
     * @param renderScreen The render OpenGL screen where to draw
     */
    protected void drawShapeAll(OpenGLScreen renderScreen){
        int i;
        for(i=0;i<overlays.size();i++){
            overlays.get(i).draw(renderScreen);
        }
    }

    /**
     * Draws all the overlays in the this.overlays array if their index corresponds to an true in the array this.selected
     * @param renderScreen The render OpenGL screen where to draw
     */
    protected void drawShapeSelected(OpenGLScreen renderScreen){
        int i;
        for(i=0;i<overlays.size();i++){
            if(selected.get(i)){
                overlays.get(i).draw(renderScreen);
            }
        }
    } 
    
    /**
     * Draws the current overlay
     * @param renderScreen The render OpenGL screen where to draw
     */
    protected void drawShapeCurrent(OpenGLScreen renderScreen){
        if(current<overlays.size()){
                overlays.get(current).draw(renderScreen);
        }
    }

    /**
     * Calls repack() for all the overlays in the array, see Overlay.repack()
     * @param renderScreen The render OpenGL screen where to draw
     */
    @Override
    public void repack(OpenGLScreen renderScreen){
        int i;
        for(i=0;i<overlays.size();i++){
            overlays.get(i).repack(renderScreen);
        }
    }
    
    /**
     * Removes ALL the overlays form the array, see OverlayArray.remove()
     */
    public void clear(){
        int i;
        for(i=0;i<overlays.size();i++){
            overlays.remove(i);
        }
    }
    
    /**
     * Sets the current overlay as the one with the next index as the current one, looping back to zero if the end is reached
     */
    public void gotoNextOverlay(){
        if(overlays.size()>1){
            current = current + 1;
            current = Math.floorMod(current, overlays.size());
            //System.out.println("Current overlay" + current);
        }
    }
    
    /**
     * Sets the current overlay as the one with the next index as the current one, looping back to the last one if the begining is reached
     */
    public void gotoPreviousOverlay(){
        if(overlays.size()>1){
            current = current - 1;
            current = Math.floorMod(current, overlays.size());
            //System.out.println("Current overlay" + current);
        }
    }
    
    /**
     * Gets the current overlay
     * @return the current overlay
     */
    public O getCurrentOverlay(){
        return this.get(current);
    }
    
    /**
     * Set as the current overlay the one with index i
     * @param i The index of the overlay, bounds are checked
     */
    public void setCurrentOverlay(int i){
        if((i>=0)&(i<overlays.size())){
            this.current = i;
        }
    }
    
    /**
     * Sets the next overlay as current and returns it, looping back to zero if the end is reached
     * @return The overlay next to current, that after this call becomes the new current
     */
    public O getNextOverlay(){
        gotoNextOverlay();
        return getCurrentOverlay();
    }
    
    /**
     * Sets the previous overlay as current and returns it, looping back to the last one if the begining is reached
     * @return The overlay previous to current, that after this call becomes the new current
     */
    public O getPreviousOverlay(){
        gotoPreviousOverlay();
        return getCurrentOverlay();
    }

    /**
     * Calls Overlay.free() for all the overlays in the array, does NOT remove them
     * @param renderScreen The render OpenGL screen where to draw
     */
    @Override
    public void free(OpenGLScreen renderScreen) {
        int i;
        for(i=0;i<overlays.size();i++){
            if(selected.get(i)){
                overlays.get(i).free(renderScreen);
            }
        }
    }

    /*
    @Override
    public void centerOverlay() {
        int i;
        for(i=0;i<overlays.size();i++){
            if(selected.get(i)){
                overlays.get(i).centerOverlay();
            }
        }
    }

    @Override
    public void centerOverlay(double x, double y, double z) {
        int i;
        this.centreCurrent.setCoords(x, y, z);
        for(i=0;i<overlays.size();i++){
            //if(selected.get(i)){
                overlays.get(i).centerOverlay(x,y,z);
            //}
        }
    }

    @Override
    public void centerOverlay(Point3D centre) {
        this.centerOverlay(centre.getX(),centre.getY(),centre.getZ());
    }
    */

    /**
     * Performs actions depending on ActionEvents,
     * if ActionEvent.getActionCommand() is "pointSelected" and this.isSelectingCurrentByMouse==true an overlay close enough to the point given by the render screen is set as current
     * @param e The action event, e.getActionCommand() == "pointSelected" supported
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "pointSelected":
                if(selectingCurrentByMouse){
                    if (this.setOverlayAtPointAsCurrent(visualisationManager.getRenderScreen().getSelectedPoint())) {
                        System.out.println("Current overlay selected by mouse");
                    } else {
                        System.out.println("No current overlay in mouse position");
                    }
                }
                break;
        }
    }

    /**
     * Moves the OverlayArray to a Point3D point, this point becomes the OverlayArray.getBounds() new centre,
     * all the overlays included in this array are displaced the same amount to new centres
     * @param point A Point3D where to move the OverlayArray
     */
    @Override
    public synchronized void moveTo(Point3D point) {
        int i;
        //Point3D disp = new Point3D(point.getX() - this.bounds.getCentre().getX(), point.getY() - this.bounds.getCentre().getY(), point.getZ() - this.bounds.getCentre().getZ() );     
        Point3D disp = new Point3D(this.bounds.getCentre().getX() - point.getX(), this.bounds.getCentre().getY() - point.getY(), this.bounds.getCentre().getZ() - point.getZ());
        for(i=0;i<overlays.size();i++){
            overlays.get(i).displace(disp);
        }
        super.moveTo(point);
    }

    /**
     * Displaces the OverlayArray according to a Point3D point, the OverlayArray.getBounds() new centre depends is the old centre minus the coordinates of the point,
     * all the overlays included in this array are displaced the same amount to new centres
     * @param point A Point3D whose (x,y,z) coordinates will be substracted from all the 3D components of the Overlay
     */
    @Override
    public synchronized void displace(Point3D point) {
        int i;
        for(i=0;i<overlays.size();i++){
           overlays.get(i).displace(point);
        }
        super.displace(point);
    }

    
}
