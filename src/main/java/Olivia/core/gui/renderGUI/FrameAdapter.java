/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.renderGUI;

import Olivia.core.gui.MainFrame;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 *
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
    
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        System.out.println("Window closing");
        renderPane.closeAllInternalFrames();
        df.removeFrame(detachedFrame);
        super.windowClosing(windowEvent);
    }
    
}
