/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.renderGUI;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import static Olivia.core.gui.MainFrame.TITLE;
import Olivia.core.gui.RenderGUI;
import com.jogamp.newt.awt.NewtCanvasAWT;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author oscar.garcia
 */
public class IndependentFrames implements RenderGUI{
    MainFrame gui;
    Dimension screenSize;
    Dimension preferredSize;
    Dimension minimumSize;
    boolean undecorated;
    ArrayList<JFrame> frames;
    
    public IndependentFrames(MainFrame gui, Dimension screenSize, boolean undecorated){
        this.frames = new ArrayList<>();
        this.gui = gui;
        this.screenSize = screenSize;
        this.undecorated = undecorated;
        
        this.preferredSize = screenSize;
        this.minimumSize = screenSize;
    }

    @Override
    public boolean addVisualisation(VisualisationManager visualisationM) {
        createNewWindow(); //Always create a window
        visualisationM.getRenderScreen().createRenderFrameNEWT();
        NewtCanvasAWT canvas = new NewtCanvasAWT(visualisationM.getRenderScreen().getWindow());
        frames.get(frames.size()-1).setTitle(visualisationM.getName());
        frames.get(frames.size()-1).add(canvas);
        frames.get(frames.size()-1).addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(visualisationM.getId() + " closing");
                Olivia.removeVisualisation(visualisationM);
                gui.updateAll();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                visualisationM.getRenderScreen().animatorPause();
                super.windowIconified(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                visualisationM.getRenderScreen().animatorResume();
                super.windowDeiconified(e);
            }
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (Olivia.getLoadedVisualisations().contains(visualisationM) && visualisationM != gui.getActiveVisualisation()) {
                    gui.setActiveVisualisationManager(visualisationM);
                    gui.updateAll();
                }
                super.windowGainedFocus(e);
            }
            @Override
            public void windowActivated(WindowEvent e) {
                if (Olivia.getLoadedVisualisations().contains(visualisationM) && visualisationM != gui.getActiveVisualisation()) {
                    gui.setActiveVisualisationManager(visualisationM);
                    gui.updateAll();
                }
                super.windowActivated(e);
            }
        });
        return true;
    }

    @Override
    public boolean updateRenderLayout() {
        return true;
    }

    @Override
    public boolean init() {
        return true;
    }
    
    private boolean buildNewWindow(boolean undecorated){
        JFrame newFrame = new JFrame();
        newFrame.setUndecorated(undecorated);
        //stereoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(preferredSize);
        newFrame.setMinimumSize(minimumSize);
        newFrame.setTitle(TITLE);
        newFrame.setVisible(true);
        newFrame.setLocationRelativeTo(null);
        frames.add(newFrame);
        return true;
    }

    @Override
    public boolean createNewWindow() {
        return buildNewWindow(undecorated);
    }

    @Override
    public boolean close() {
        for(JFrame jf:frames){
            jf.dispose();
        }
        return true;
    }
    
    public boolean removeFrame(JFrame frame){
        frames.remove(frame);
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
    
}
