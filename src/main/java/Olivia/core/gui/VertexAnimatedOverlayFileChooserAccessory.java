/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui;

import Olivia.extended.overlays.VertexAnimatedOverlay;
import Olivia.extended.overlays.VertexOverlay;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author oscar.garcia
 */
public class VertexAnimatedOverlayFileChooserAccessory<VAO extends VertexAnimatedOverlay> extends VertexOverlayFileChooserAccessory<VAO>{

    protected JComboBox<String> speedComboBox;
    protected JLabel labelSpeed;
    
    public static final String[] SPEED_TEXT = new String[] {
        "1x",
        "2x",
        "10x",
        "20x",
        "50x"
    };
    
    public static long getSpeed(String name){
        switch(name){
            case "1x" : return 1l;
            case "2x" : return 2l;
            case "10x" : return 10l;
            case "20x" : return 20l;
            case "50x" : return 50l;
            default : return 1l;
        }
    }
    
    
    public VertexAnimatedOverlayFileChooserAccessory(VAO vertexAnimatedOverlay){
        super(vertexAnimatedOverlay);
        
        this.speedComboBox = new JComboBox<>(SPEED_TEXT);
        speedComboBox.setToolTipText("How fast the animation will play");
        speedComboBox.addActionListener(this);
        
        labelSpeed = new JLabel("Speed");
        labelSpeed.setToolTipText("How fast the animation will play");
        
        this.removeAll();
            
        this.add(labelRender);
        this.add(labelRaster);
        this.add(labelColour);
        this.add(labelName);
        this.add(labelSpeed);
        this.add(primitiveModeComboBox);
        this.add(rasterModeComboBox);
        this.add(colourComboBox);
        this.add(textFieldName);
        this.add(speedComboBox);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        overlay.setSpeed(getSpeed(speedComboBox.getItemAt(speedComboBox.getSelectedIndex())));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        overlay.setSpeed(getSpeed(speedComboBox.getItemAt(speedComboBox.getSelectedIndex())));
    }
    
}
