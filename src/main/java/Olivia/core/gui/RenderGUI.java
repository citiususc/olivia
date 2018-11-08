package Olivia.core.gui;

import Olivia.core.VisualisationManager;

/**
 *
 * @author oscar.garcia
 */
public interface RenderGUI{
        
    public boolean addVisualisation(VisualisationManager visualisationM);
    
    //public boolean addRemove(VisualisationManager visualisationM);
    
    public boolean updateRenderLayout();
    
    public boolean init();
    public boolean createNewWindow();
    public boolean close();
    
}
