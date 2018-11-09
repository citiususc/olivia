/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.renderGUI.desktop;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import static Olivia.core.gui.MainFrame.TITLE;
import Olivia.core.gui.RenderGUI;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

/**
 *
 * @author oscar.garcia
 */
public class DetachedFrames implements RenderGUI{
    MainFrame gui;
    Dimension screenSize;
    Dimension preferredSize;
    Dimension minimumSize;
    boolean undecorated;
    ArrayList<JFrame> frames;
    ArrayList<DesktopPane> renderPanes;
    
    
    public DetachedFrames(MainFrame gui, Dimension screenSize, boolean undecorated){
        this.frames = new ArrayList<>();
        this.renderPanes = new ArrayList<>();
        this.gui = gui;
        this.screenSize = screenSize;
        this.undecorated = undecorated;
        
        this.preferredSize = screenSize;
        this.minimumSize = screenSize;
    }
    
    
    private boolean buildDetachedFrame(boolean undecorated){
        DesktopPane renderPane = new DesktopPane(gui);
        renderPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
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
    
    
    

    @Override
    public boolean addVisualisation(VisualisationManager visualisationM) {
        if(frames.isEmpty()){
            createNewWindow();
        }
        return renderPanes.get(renderPanes.size()-1).addVisualisation(visualisationM);
    }

    
    @Override
    public boolean updateRenderLayout() {
        renderPanes.forEach((dp) -> {
            dp.updateRenderLayout();
        });
        return true;
    }
    

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean createNewWindow() {
        return buildDetachedFrame(undecorated);
    }
    
    public boolean removeFrame(JFrame frame){
        int index = this.frames.indexOf(frame);
        frames.remove(frame);
        renderPanes.remove(index);
        return true;
    }
    
    public void scalePreferredSize(float scale){
        this.preferredSize = new Dimension(Math.round(screenSize.width*scale),
                                          Math.round(screenSize.height*scale));
    }
    
    public void scaleMinimumSize(float scale){
        this.minimumSize = new Dimension(Math.round(screenSize.width*scale),
                                          Math.round(screenSize.height*scale));
    }

    @Override
    public boolean close() {
        for(JFrame jf:frames){
            jf.dispose();
        }
        return true;
    }
    
}
