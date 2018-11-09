package Olivia.core.gui.renderGUI.desktop;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import javafx.scene.Cursor;
import javax.swing.JInternalFrame;

/**
 *
 * @author oscar.garcia
 */
public class InternalFrame extends JInternalFrame{
    MainFrame gui;
    VisualisationManager visualisationM;

    public InternalFrame(MainFrame gui, VisualisationManager visualisationM, String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        this.gui = gui;
        this.visualisationM = visualisationM;
    }    
}
