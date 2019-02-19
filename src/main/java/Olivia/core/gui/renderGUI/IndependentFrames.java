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

/**
 * Used to render each visualisation in its own window (JFrame)
 * @author oscar.garcia
 */
public class IndependentFrames implements RenderGUI{
    /**
     * The main GUI
     */
    MainFrame gui;
    /**
     * Each window size
     */
    Dimension screenSize;
    /**
     * Each window preferred size
     */
    Dimension preferredSize;
    /**
     * Each window minimum size
     */
    Dimension minimumSize;
    /**
     * Whether the windows are decorated
     */
    boolean undecorated;
    /**
     * An array with all the windows opened
     */
    ArrayList<JFrame> frames;
    
    /**
     * Creates a new instance of IndependentFrames
     * @param gui The main GUI
     * @param screenSize The screen size of IndependentFrames, initial values of preferred, minimum and current size are taken from here
     * @param undecorated Whether the JFrames are decorated or not
     */
    public IndependentFrames(MainFrame gui, Dimension screenSize, boolean undecorated){
        this.frames = new ArrayList<>();
        this.gui = gui;
        this.screenSize = screenSize;
        this.undecorated = undecorated;
        
        this.preferredSize = screenSize;
        this.minimumSize = screenSize;
    }

    /**
     * Adds a new Visualisation in a new Window (JFrame). Here is the WindowAdapter with the windowClosing method that frees the memory
     * @param visualisationM The visualisation to be rendered
     * @return true if everything OK
     */
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
                Olivia.textOutputter.println(visualisationM.getId() + " closing");
                gui.getOlivia().removeVisualisation(visualisationM);
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
                if (gui.getOlivia().getLoadedVisualisations().contains(visualisationM) && visualisationM != gui.getActiveVisualisation()) {
                    gui.setActiveVisualisationManager(visualisationM);
                    gui.updateAll();
                }
                super.windowGainedFocus(e);
            }
            @Override
            public void windowActivated(WindowEvent e) {
                if (gui.getOlivia().getLoadedVisualisations().contains(visualisationM) && visualisationM != gui.getActiveVisualisation()) {
                    gui.setActiveVisualisationManager(visualisationM);
                    gui.updateAll();
                }
                super.windowActivated(e);
            }
        });
        return true;
    }

    /**
     * Does nothing
     * @return true
     */
    @Override
    public boolean updateRenderLayout() {
        return true;
    }

    /**
     * Does nothing
     * @return true
     */
    @Override
    public boolean init() {
        return true;
    }
    
    /**
     * Build new window (JFrame)
     * @param undecorated
     * @return true if everything OK
     */
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

    /**
     * Creates a new window
     * @return true if everything OK
     * @see buildNewWindow
     */
    @Override
    public boolean createNewWindow() {
        return buildNewWindow(undecorated);
    }

    /**
     * Closes all opened windows (JFrames) so they can free the memory
     * @return true if everything OK
     */
    @Override
    public boolean close() {
        for(JFrame jf:frames){
            jf.dispose();
        }
        return true;
    }
    
    /**
     * Removes a Window, a JFrame
     * @param frame The JFrame to remove
     * @return true if everything OK
     */
    public boolean removeFrame(JFrame frame){
        frames.remove(frame);
        return true;
    }
    
    /**
     * Scales the preferred size for the windows to be created from now on
     * @param scale screenSize will be multiplied by this and rounded
     */
    public void scalePreferredSize(float scale){
        this.preferredSize = new Dimension(Math.round(screenSize.width*scale),
                                          Math.round(screenSize.height*scale));
    }
    
    /**
     * Scales the minimum size for the windows to be created from now on
     * @param scale screenSize will be multiplied by this and rounded
     */
    public void scaleMinimumSize(float scale){
        this.minimumSize = new Dimension(Math.round(screenSize.width*scale),
                                          Math.round(screenSize.height*scale));
    }

    @Override
    public boolean showAll() {
        frames.forEach((f) -> {
                f.setState(JFrame.NORMAL); }
        );
        return true;
    }
    
}
