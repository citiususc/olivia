/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.extended.overlays.MouseMoveableOverlay;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author oscar.garcia
 */
public class MouseMoveableOverlayOptionsPanel extends JPanel implements ChangeListener,ActionListener{
    
    protected MouseMoveableOverlay overlay;
    
    protected JSpinner xSpinner;
    protected JSpinner ySpinner;
    protected JSpinner zSpinner;
    
    protected SpinnerNumberModel xModel;
    protected SpinnerNumberModel yModel;
    protected SpinnerNumberModel zModel;
    
    protected JLabel xLabel;
    protected JLabel yLabel;
    protected JLabel zLabel;
    
    protected JLabel toggleLabel;
    
    protected JToggleButton moveByMouseToggle;
    
    protected JLabel moveLabel;
    protected JLabel coordsLabel;
    
    public MouseMoveableOverlayOptionsPanel(MouseMoveableOverlay overlay){
        this.overlay = overlay;
        overlay.addActionListener(this);
        this.setName("Position"); 
        xModel = new SpinnerNumberModel(0.0,-10000000.0,10000000.0,0.1);
        yModel = new SpinnerNumberModel(0.0,-10000000.0,10000000.0,0.1);
        zModel = new SpinnerNumberModel(0.0,-10000000.0,10000000.0,0.1);
        xModel.setValue(overlay.getBounds().getCentre().getX());
        yModel.setValue(overlay.getBounds().getCentre().getY());
        zModel.setValue(overlay.getBounds().getCentre().getZ());
        xSpinner = new JSpinner(xModel);
        xSpinner.setToolTipText("Center on X");
        xSpinner.addChangeListener(this);
        ySpinner = new JSpinner(yModel);
        ySpinner.setToolTipText("Center on Y");
        ySpinner.addChangeListener(this);
        zSpinner = new JSpinner(zModel);
        zSpinner.setToolTipText("Center on Z");
        zSpinner.addChangeListener(this);
        
        xSpinner.setEnabled(!overlay.isMovingByMouse());
        ySpinner.setEnabled(!overlay.isMovingByMouse());
        zSpinner.setEnabled(!overlay.isMovingByMouse());
        
        xLabel = new JLabel("X");
        xLabel.setLabelFor(xSpinner);
        yLabel = new JLabel("Y");
        yLabel.setLabelFor(ySpinner);
        zLabel = new JLabel("Z");
        zLabel.setLabelFor(zSpinner);
        
        moveByMouseToggle = new JToggleButton("Move By Mouse");
        moveByMouseToggle.setToolTipText("Toggles whether to move the overlay to the mouse selected point");
        moveByMouseToggle.setSelected(overlay.isMovingByMouse());
        moveByMouseToggle.addChangeListener(this);
        toggleLabel = new JLabel("");
        toggleLabel.setLabelFor(moveByMouseToggle);
        
        
        moveLabel = new JLabel("Moving on");
        coordsLabel = new JLabel("Absolute Coords");
        if(!overlay.isMovingAbsolute()) coordsLabel.setText("Self Coords");
        
        GridLayout layout = new GridLayout(2,0);
        this.setLayout(layout);
        this.add(moveLabel);
        this.add(xLabel);
        this.add(yLabel);
        this.add(zLabel);
        this.add(toggleLabel);
        this.add(coordsLabel);
        this.add(xSpinner);
        this.add(ySpinner);
        this.add(zSpinner);
        this.add(moveByMouseToggle);
    }

    
    @Override
    public void stateChanged(ChangeEvent e) {
        if((overlay!=null)&(this.isShowing())){//To avoid passing argument before being displayed
            overlay.setMovingByMouse(moveByMouseToggle.isSelected());
            xSpinner.setEnabled(!moveByMouseToggle.isSelected());
            ySpinner.setEnabled(!moveByMouseToggle.isSelected());
            zSpinner.setEnabled(!moveByMouseToggle.isSelected());
            if(!moveByMouseToggle.isSelected()){
                overlay.moveTo(xModel.getNumber().doubleValue(), yModel.getNumber().doubleValue(), zModel.getNumber().doubleValue());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "moved":
                xModel.setValue(overlay.getBounds().getCentre().getX());
                yModel.setValue(overlay.getBounds().getCentre().getY());
                zModel.setValue(overlay.getBounds().getCentre().getZ());
                break;
        }
    }

    
}
