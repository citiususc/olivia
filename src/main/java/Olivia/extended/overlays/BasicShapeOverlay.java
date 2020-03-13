/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.gui.controls.overlays.BasicShapeOverlayControlPanel;

/**
 *
 * @author oscar.garcia
 */
public abstract class BasicShapeOverlay<VM extends VisualisationManager> extends RenderableOverlay<VM>{
    

    public BasicShapeOverlay(VM visualisationManager, String name) {
        super(visualisationManager, name);
        this.movingAbsolute=false;
    }    
    
    @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        BasicShapeOverlayControlPanel control = new BasicShapeOverlayControlPanel(this);
        this.optionPanels.add(control);
    }

    
}
