/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.gui.ExtraFrame;
import Olivia.core.gui.MainFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author oscar.garcia
 */
public class OverlaysOptionsFrame extends ExtraFrame{
    protected OverlaysOptionsPanel optionsPanel;
    
    public OverlaysOptionsFrame(MainFrame gui){
        super(gui);
        
        optionsPanel = new OverlaysOptionsPanel();
        
        this.setContentPane(optionsPanel);
        this.setVisible(false);
        this.setSize(600, 200);
        
        this.setTitle("Overlay Options");
        
    }
    
    
    public void update(){
        if(gui.getActiveVisualisation()!=null){
            if(gui.getActiveVisualisation().getOverlays().getCurrentOverlay()!=null){
                this.setTitle("Options for " + gui.getActiveVisualisation().getOverlays().getCurrentOverlay().getName());
                optionsPanel.setOverlay(gui.getActiveVisualisation().getOverlays().getCurrentOverlay());
            }
        }
    }
    
}
