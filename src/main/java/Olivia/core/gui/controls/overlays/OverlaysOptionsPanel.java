/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.Overlay;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author oscar.garcia
 */
public class OverlaysOptionsPanel extends JTabbedPane{
   
    protected Overlay overlay;
    protected TransformationsOptionsPanel transformationsPanel;
 
    public OverlaysOptionsPanel(){
        
    }
    
    public void setOverlay(Overlay overlay){
        int i;
        JPanel panel;
        this.overlay = overlay;
        this.removeAll();
        for(i=0;i<overlay.getOptionPanels().size();i++){
            panel = (JPanel) overlay.getOptionPanels().get(i);
            this.add(panel);
        }
    }
}
