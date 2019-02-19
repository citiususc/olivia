/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.renderGUI;

import Olivia.core.Olivia;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * A window adapter to use with JFrames of a DetachedDesktopFrames with DesktopPanes as their content pane
 * @author oscar.garcia
 */
public class FrameAdapter extends WindowAdapter{
    DetachedDesktopFrames df;
    DesktopPane renderPane;
    JFrame detachedFrame;
    
    public FrameAdapter(DetachedDesktopFrames gui, JFrame detachedFrame, DesktopPane renderPane){
        this.df = gui;
        this.detachedFrame = detachedFrame;
        this.renderPane = renderPane;
    }
    
    /**
     * Closes all internalFrames of the renderPane
     * @param windowEvent 
     */
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        Olivia.textOutputter.println("Window closing");
        renderPane.closeAllInternalFrames();
        df.removeFrame(detachedFrame);
        super.windowClosing(windowEvent);
    }
    
}
