/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.extended.overlays.CubeShapeOverlay;
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
public class CubeShapeOverlayOptionsPanel extends JPanel implements ChangeListener{
    
    protected CubeShapeOverlay overlay;
    
    protected JSpinner sizeXSpinner;
    protected SpinnerNumberModel sizeXModel;
    protected JLabel sizeXLabel;
    protected JSpinner sizeYSpinner;
    protected SpinnerNumberModel sizeYModel;
    protected JLabel sizeYLabel;
    protected JSpinner sizeZSpinner;
    protected SpinnerNumberModel sizeZModel;
    protected JLabel sizeZLabel;
    
    public CubeShapeOverlayOptionsPanel(CubeShapeOverlay overlay){
        this.overlay = overlay;
        this.setName("Cube");
        
        sizeXModel = new SpinnerNumberModel(overlay.getSizeX(),0.0,10000.0,0.1);
        sizeYModel = new SpinnerNumberModel(overlay.getSizeY(),0.0,10000.0,0.1);
        sizeZModel = new SpinnerNumberModel(overlay.getSizeZ(),0.0,10000.0,0.1);
        
        sizeXSpinner = new JSpinner(sizeXModel);
        sizeXSpinner.setToolTipText("Set the size in X");
        sizeXSpinner.addChangeListener(this);
        sizeYSpinner = new JSpinner(sizeYModel);
        sizeYSpinner.setToolTipText("Set the size in Y");
        sizeYSpinner.addChangeListener(this);
        sizeZSpinner = new JSpinner(sizeZModel);
        sizeZSpinner.setToolTipText("Set the size in Z");
        sizeZSpinner.addChangeListener(this);
        
        sizeXLabel = new JLabel("X Size");
        sizeXLabel.setLabelFor(sizeXSpinner);
        sizeYLabel = new JLabel("Y Size");
        sizeYLabel.setLabelFor(sizeYSpinner);
        sizeZLabel = new JLabel("Z Size");
        sizeZLabel.setLabelFor(sizeZSpinner);
        
        GridLayout layout = new GridLayout(2,0);
        this.setLayout(layout);
        this.add(sizeXLabel);
        this.add(sizeYLabel);
        this.add(sizeZLabel);
        this.add(sizeXSpinner);
        this.add(sizeYSpinner);
        this.add(sizeZSpinner);
        
        
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if((overlay!=null)&(this.isShowing())){//To avoid passing argument before being displayed
            overlay.setSizeX(sizeXModel.getNumber().doubleValue());
            overlay.setSizeY(sizeYModel.getNumber().doubleValue());
            overlay.setSizeZ(sizeZModel.getNumber().doubleValue());
        }
    }
    
}
