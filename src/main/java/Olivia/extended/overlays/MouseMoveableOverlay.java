/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.gui.controls.overlays.MouseMoveableOverlayOptionsPanel;
import java.awt.event.ActionEvent;

/**
 *
 * @author oscar.garcia
 */
public abstract class MouseMoveableOverlay<VM extends VisualisationManager> extends Overlay<VM>{
    protected boolean movingByMouse;
    MouseMoveableOverlayOptionsPanel moveOptionPanel;
    
    protected boolean movingAbsolute;
    
    public MouseMoveableOverlay(VM visualisationManager, String name) {
        super(visualisationManager, name);
        movingByMouse=false;
        movingAbsolute=true;
        this.listenToActionsOnScreen();
    }
    
    public MouseMoveableOverlay(VM visualisationManager) {
        this(visualisationManager, "moveable");
    }

    public boolean isMovingByMouse() {
        return movingByMouse;
    }

    public void setMovingByMouse(boolean movingByMouse) {
        this.movingByMouse = movingByMouse;
    }
    
    
    
    public void toggleMovingByMouse(){
        movingByMouse= !movingByMouse;
    }
    
    public boolean isMovingAbsolute() {
        return movingAbsolute;
    }

    /*
    public void setMovingAbsolute(boolean movingAbsolute) {
        this.movingAbsolute = movingAbsolute;
    }
    */
    
    protected synchronized void selfMoveTo(Point3D point){
        Point3D point2 = new Point3D(0.0,0.0,0.0);
        point2.copyCoords(point);
        bounds.moveTo(point2);
        transformations.setCentre(point2);
        this.fireEvent("moved");
        System.out.println("self bounds ");
        
    }
    
     /**
     * In this case affects the transformations!!
     * @param point 
     */
    @Override
    public synchronized void moveTo(Point3D point){
        if(!movingAbsolute){
            this.selfMoveTo(point);
        }else{
            super.moveTo(point);
        }
        //System.out.println("displacement " + this.visualisationManager.getDisplacement());
        System.out.println("moved bounds " + this.bounds.getCentre());
        System.out.println("moved trans " + this.transformations.getTransX() + " " + this.transformations.getTransY() + " " + this.transformations.getTransZ());
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(movingByMouse){
            if(movingAbsolute){
                Point3D move=new Point3D(0.0,0.0,0.0);
                move.copyCoords(this.visualisationManager.getRenderScreen().getSelectedPoint());
                //move.addToCoords(this.visualisationManager.getDisplacement());
                move.subToCoords(this.visualisationManager.getDisplacement());
                this.moveTo(move);
            }else{
                this.moveTo(this.visualisationManager.getRenderScreen().getSelectedPoint());
            }
        }
    }
    
    
    @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        moveOptionPanel= new MouseMoveableOverlayOptionsPanel(this);
        this.optionPanels.add(moveOptionPanel);
    }
    
}
