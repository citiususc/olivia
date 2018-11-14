package Olivia.core.gui.renderGUI;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import javax.swing.JInternalFrame;

/**
 * Class that extends JInternalFrame, just here in case we want to modify something, non necessary now
 * @author oscar.garcia
 */
public class InternalFrame extends JInternalFrame{
    /**
     * The main GUI
     */
    MainFrame gui;
    /**
     * The visualisation manager of this Frame
     */
    VisualisationManager visualisationM;

    /**
     * Creates a new instance of an InternalFrame
     * @param gui The main GUI
     * @param visualisationM The visualisation manager of this Frame
     * @param title the title
     * @param resizable is resizable?
     * @param closable is closeable?
     * @param maximizable is maximizable?
     * @param iconifiable is iconifiable?
     */
    public InternalFrame(MainFrame gui, VisualisationManager visualisationM, String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        this.gui = gui;
        this.visualisationM = visualisationM;
    }    
}
