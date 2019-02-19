package Olivia.core.gui.renderGUI;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * Manages the operation to do with the internal frames, very important to free memory, calls Olivia.core.Olivia.removeVisualisation() on closing
 * @author oscar.garcia
 */
public class FrameEventListener implements InternalFrameListener {
//public class FrameEventListener extends InternalFrameAdapter {
    /**
     * The mainGUI
     */
    protected MainFrame gui;
    /**
     * The visualisation the frame this is listening is rendering
     */
    protected VisualisationManager visualisationManager;

    /**
     * Creates a new instance of FrameEventListener, for just one frame
     * @param visualisationManager The visualisation the frame this is listening is rendering, the gui is set according to this
     */
    public FrameEventListener(VisualisationManager visualisationManager){
        this.gui = visualisationManager.getGUI(); //only acts with the visualisation gui
        this.visualisationManager = visualisationManager;
    }

    /**
     * Sets the active visualisation in the GUI as the one in this frame
     * @param e an event
     */
    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
        Olivia.textOutputter.println(visualisationManager.getId() + " activated");
        if (gui.getOlivia().getLoadedVisualisations().contains(visualisationManager) && visualisationManager != gui.getActiveVisualisation()) {
            gui.setActiveVisualisationManager(visualisationManager);
            gui.updateAll();
        }
    }

    /**
     * Updates the GUI
     * @param e an event
     */
    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        Olivia.textOutputter.println(visualisationManager.getId() + " closed");
        gui.updateAll();
    }

    /**
     * Removes the visualisation from the program, freeing its memory; updates the GUI
     * @param e an event
     */
    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        Olivia.textOutputter.println(visualisationManager.getId() + " closing");
        gui.getOlivia().removeVisualisation(visualisationManager);
        gui.updateAll();
        //animator.stop();
        //frame.dispose();
        //removeScreen(OpenGLScreen.this);
    }

    /**
     * Updates the GUI
     * @param e an event
     */
    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
        Olivia.textOutputter.println(visualisationManager.getId() + " deactivated");
        gui.updateAll();
    }

    /**
     * Repacks the visualisation and resumes the OpenGL animator, updates the GUI
     * @param e an event
     */
    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
        //OpenGLScreen.this.frame.setSize(640, 480);
        if (visualisationManager != null) {
            visualisationManager.repack();
        }
        gui.updateAll();
        Olivia.textOutputter.println(visualisationManager.getId() + " deiconified");
        visualisationManager.getRenderScreen().animatorResume();
    }

    /**
     * Pauses the OpenGL animator, updates the GUI
     * @param e an event
     */
    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
        visualisationManager.getRenderScreen().animatorPause();
        Olivia.textOutputter.println(visualisationManager.getId() + " iconified");
        gui.updateAll();
    }

    /**
     * Updates the GUI.
     * @param e an event
     */
    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        Olivia.textOutputter.println(visualisationManager.getId() + " opened");
        gui.updateAll();
    }
}
