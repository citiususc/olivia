/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.extended.overlays.CircleShapeOverlay;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author oscar.garcia
 */
public class CircleShapeOverlayOptionsPanel extends JPanel implements ChangeListener{
    
    protected CircleShapeOverlay overlay;
    
    protected JSpinner radiusSpinner;
    protected SpinnerNumberModel radiusModel;
    protected JLabel radiusLabel;
    
    protected JSpinner resolutionSpinner;
    protected SpinnerNumberModel resolutionModel;
    protected JLabel resolutionLabel;
    
    
    public CircleShapeOverlayOptionsPanel(CircleShapeOverlay overlay){
        this.overlay = overlay;
        this.setName("Circle");
        radiusModel = new SpinnerNumberModel(0.0,0.0,10000.0,0.1);
        radiusModel.setValue(overlay.getRadius());
        resolutionModel = new SpinnerNumberModel(overlay.getResolution(),4,1000,1);
        resolutionModel.setValue(overlay.getResolution());
        
        radiusSpinner = new JSpinner(radiusModel);
        radiusSpinner.setToolTipText("Set the radius");
        radiusSpinner.addChangeListener(this);
        resolutionSpinner = new JSpinner(resolutionModel);
        resolutionSpinner.setToolTipText("Set the resolution");
        resolutionSpinner.addChangeListener(this);
        
        radiusLabel = new JLabel("Radius");
        radiusLabel.setLabelFor(radiusSpinner);
        resolutionLabel = new JLabel("Resolution");
        resolutionLabel.setLabelFor(resolutionSpinner);
        
        GridLayout layout = new GridLayout(2,0);
        this.setLayout(layout);
        this.add(radiusLabel);
        this.add(resolutionLabel);
        this.add(radiusSpinner);
        this.add(resolutionSpinner);
        
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if((overlay!=null)&(this.isShowing())){//To avoid passing argument before being displayed
            overlay.setRadius(radiusModel.getNumber().doubleValue());
            overlay.setResolution(resolutionModel.getNumber().intValue());
        }
    }
    
    
}
