/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.renderGUI;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import static Olivia.core.gui.MainFrame.TITLE;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

/**
 * Renders the visualisation in multiple windows (frames) with an internal desktop.
 * @author oscar.garcia
 */
public class DetachedDesktopFrames extends IndependentFrames{
    /**
     * An array with all the DesktopPanes, the DesktopPanes should have the same index in the array as the JFrame they are in 
     */
    ArrayList<DesktopPane> renderPanes;
    
    /**
     * Creates a new instance of DetachedDesktopFrames
     * @param gui The MainGUI
     * @param screenSize The screen size of DetachedDesktopFrames, initial values of preferred, minimum and current size are taken from here
     * @param undecorated Whether the JFrames are decorated or not
     */
    public DetachedDesktopFrames(MainFrame gui, Dimension screenSize, boolean undecorated){
        super(gui,screenSize,undecorated);
        this.renderPanes = new ArrayList<>();
    }
    
    /**
     * Build a new JFrame with and internal DesktopPane and adds it to the arrays
     * @param undecorated Whether the JFrame is decorated or not
     * @return true if everything OK
     */
    private boolean buildDetachedFrame(boolean undecorated){
        DesktopPane renderPane = new DesktopPane(gui);
        renderPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        //renderPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        //int renderHeight = screenSize.height - menuBar.getHeight() - controlPanel.getHeight();
        //int renderHeight =Math.round(screenSize.height*(3/4));
        renderPane.setMinimumSize(minimumSize);
        JFrame newFrame = new JFrame();
        newFrame.setUndecorated(undecorated);
        newFrame.setContentPane(renderPane);
        //stereoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(preferredSize);
        newFrame.setMinimumSize(minimumSize);
        newFrame.setTitle(TITLE);
        newFrame.setVisible(true);
        newFrame.setLocationRelativeTo(null);
        FrameAdapter fel = new FrameAdapter(this,newFrame,renderPane);
        newFrame.addWindowListener(fel);
        frames.add(newFrame);
        renderPanes.add(renderPane);
        return true;
    }
    
    /**
     * Adds a new visualisation to be rendered, it will be rendered in the last created window
     * @param visualisationM The visualisation to be rendered
     * @return true if everything OK
     */
    @Override
    public boolean addVisualisation(VisualisationManager visualisationM) {
        if(frames.isEmpty()){
            createNewWindow();
        }
        return renderPanes.get(renderPanes.size()-1).addVisualisation(visualisationM);
    }

    /**
     * Updates the render layout of all the DesktopPanes in all the frames
     * @return 
     */
    @Override
    public boolean updateRenderLayout() {
        renderPanes.forEach((dp) -> {
            dp.updateRenderLayout();
        });
        return true;
    }
    
    /**
     * Creates a new window to render
     * @return true if everything OK
     * @see buildDetachedFrame()
     */
    @Override
    public boolean createNewWindow() {
        return buildDetachedFrame(undecorated);
    }
    
    /**
     * Removes a window (frame) from the render window
     * @param frame the JFrame to be removed
     * @return true if everything ok
     */
    @Override
    public boolean removeFrame(JFrame frame){
        int index = this.frames.indexOf(frame);
        renderPanes.remove(index);
        super.removeFrame(frame);
        return true;
    }

    /**
     * Closes all windows and desktops
     * @return true if everything OK
     */
    @Override
    public boolean close() {
        for(DesktopPane dp:renderPanes){
            dp.close();
        }
        return super.close();
    }
    
}
