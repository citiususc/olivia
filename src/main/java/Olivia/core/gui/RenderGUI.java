package Olivia.core.gui;

import Olivia.core.VisualisationManager;

/**
 * This interface is used by the render GUI, the part of the GUI where the OpenGL windows will be rendered, to be
 * able to change among different ways of doing it, such as in different frames or with internal desktops.
 * Right now, the implementing classes are the ones that take care of removing visualisations, for example when 
 * their frame is disposed of, calling Olivia.removeVisualisation()
 * @author oscar.garcia
 */
public interface RenderGUI{
        
    /**
     * Adds a visualisation to be rendered.
     * @param visualisationM The visualisation
     * @return true if the visualisation was added
     */
    public boolean addVisualisation(VisualisationManager visualisationM);
    
    /**
     * Updates the layout of the RenderGUI, needed if external classes modify something.
     * @return true when the layout was modified
     */
    public boolean updateRenderLayout();
    
    /**
     * Shows all the visualisations
     * @return 
     */
    public boolean showAll();
    
    /**
     * Inits the RenderGUI components
     * @return true if the components where initialised
     */
    public boolean init();
    
    /**
     * Creates a new window (frame) to render visualisations (may not be applicable)
     * @return true if a new window was created, or no errors occurred
     */
    public boolean createNewWindow();
    
    /**
     * Closes the render GUI, remembering to call Olivia.removeVisualisation() to free the memory of all opened visualisations
     * @return 
     */
    public boolean close();
    
    //public boolean addRemove(VisualisationManager visualisationM);
  
}
