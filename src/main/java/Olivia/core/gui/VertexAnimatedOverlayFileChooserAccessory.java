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
 * An accessory to indicate how the animated vertex file should be read
 * @author oscar.garcia
 * @param <VAO> The class of VertexAnimatedOverlay this can control
 */
public class VertexAnimatedOverlayFileChooserAccessory<VAO extends VertexAnimatedOverlay> extends VertexOverlayFileChooserAccessory<VAO>{

    /**
     * To set the speed of the animation
     */
    protected JComboBox<String> speedComboBox;
    /**
     * The speed combo box label
     */
    protected JLabel labelSpeed;
    /**
     * The available speeds, as string
     */
    public static final String[] SPEED_TEXT = new String[] {
        "1x",
        "2x",
        "10x",
        "20x",
        "50x"
    };
    /**
     * Gets the available speeds, as numbers
     * @param name the speed name, as in SPEED_TEXT
     * @return the speed as a long
     */
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
    
    /**
     * Creates an instance, needs to know the overlay it will read, this overlay needs to exits previously
     * @param vertexAnimatedOverlay an overlay to control
     */
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

    /**
     * Performs the actions
     * @param e an event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        overlay.setSpeed(getSpeed(speedComboBox.getItemAt(speedComboBox.getSelectedIndex())));
    }
    
    /**
     * Performs the actions on property changed
     * @param evt an event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        overlay.setSpeed(getSpeed(speedComboBox.getItemAt(speedComboBox.getSelectedIndex())));
    }
    
}
