/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.gui.MainFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author oscar.garcia
 */
public class OverlaysOptionsFrame extends JFrame{
    protected MainFrame gui;
    protected OverlaysOptionsPanel optionsPanel;
    
    public OverlaysOptionsFrame(MainFrame gui){
        this.gui = gui;
        
        optionsPanel = new OverlaysOptionsPanel();
        
        this.setContentPane(optionsPanel);
        this.setVisible(false);
        this.setSize(600, 200);
        
        WindowListener exitListener = new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        e.getWindow().setVisible(false);
                        gui.updateAll();
                    }
        };
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(exitListener);
        
        this.setTitle("Overlay Options");
        
    }
    
    
    public void update(){
        if(gui.getActiveVisualisation()!=null){
            if(gui.getActiveVisualisation().getOvelays().getCurrentOverlay()!=null){
                this.setTitle("Options for " + gui.getActiveVisualisation().getOvelays().getCurrentOverlay().getName());
                optionsPanel.setOverlay(gui.getActiveVisualisation().getOvelays().getCurrentOverlay());
            }
        }
    }
    
}
