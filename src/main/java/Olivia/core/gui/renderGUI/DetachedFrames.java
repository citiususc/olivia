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
 *
 * @author oscar.garcia
 */
public class DetachedFrames extends IndependentFrames{
    ArrayList<DesktopPane> renderPanes;
    
    
    public DetachedFrames(MainFrame gui, Dimension screenSize, boolean undecorated){
        super(gui,screenSize,undecorated);
        this.renderPanes = new ArrayList<>();
    }
    
    
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
    public boolean createNewWindow() {
        return buildDetachedFrame(undecorated);
    }
    
    @Override
    public boolean removeFrame(JFrame frame){
        int index = this.frames.indexOf(frame);
        renderPanes.remove(index);
        super.removeFrame(frame);
        return true;
    }

    @Override
    public boolean close() {
        for(DesktopPane dp:renderPanes){
            dp.close();
        }
        return super.close();
    }
    
}
