package Olivia.core.gui.renderGUI;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import Olivia.core.gui.RenderGUI;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import com.jogamp.newt.awt.NewtCanvasAWT;
import java.awt.Color;
import java.beans.PropertyVetoException;
import javax.swing.BorderFactory;


/**
 * Creates a desktop pane where visualisations can be rendered
 * @author oscar.garcia
 */
public class DesktopPane extends JDesktopPane implements RenderGUI{
    /**
     * The main GUI
     */
    MainFrame gui;

    /**
     * Creates a new instance of DesktopPane
     * @param gui the main GUI
     */
    public DesktopPane(MainFrame gui) {
        this.gui = gui;
    }

    /**
     * Adds a new visualisation in a new internal frame
     * @param visualisationM The visualisation to render
     * @return true if there where no problems
     */
    @Override
    public boolean addVisualisation(VisualisationManager visualisationM) {
        InternalFrame frame = new InternalFrame(gui,visualisationM,visualisationM.getName(), true, true, true, true);
        frame.setClosable(true);
        frame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        visualisationM.getRenderScreen().createRenderFrameNEWT();
        NewtCanvasAWT canvas = new NewtCanvasAWT(visualisationM.getRenderScreen().getWindow());
        //canvas.setSize(new Dimension(this.getWidth()-40, this.getHeight()-40));
        frame.add(canvas);
        frame.setSize(this.getWidth(), this.getHeight());
        frame.setPreferredSize(this.getPreferredSize());
        frame.setMinimumSize(this.getMinimumSize());
        frame.addMouseListener(new InternalFrameMouseAdapter(frame));
        //Border border = BorderFactory.createLineBorder(Color.RED,20);
        frame.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,10));
        //frame.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        //frame.setBorder(BorderFactory.createEmptyBorder());
        //frame.setBorder(BorderFactory.createTitledBorder(frame.getBorder(),visualisationM.getName()));
        //frame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        frame.addInternalFrameListener(new FrameEventListener(visualisationM));
        /*frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (gui.isActiveVisualisationManager(visualisationM)) {
                    if (((JInternalFrame) e.getComponent()).isMaximum()) {
                        System.out.println(visualisationM.getId() + " Maximized");
                        setInactiveScreensIconify(true); // Iconify hidden render screens to stop them for being rendered                     
                    } else {
                        System.out.println(visualisationM.getId() + " Un-maximized");
                        setInactiveScreensIconify(false); // Undo the previous iconify
                        gui.updateRenderFrameLayout();
                    }
                }
            }
        });*/
        this.add(frame);
        frame.setVisible(true);
        return true;
    }

    
    /**
     * Updates the render layout when changes are made externally.
     * @return true if the layout was changed
     */
    @Override
    public boolean updateRenderLayout() {
        /*JInternalFrame[] frames = getAllFrames();
        if(frames.length>0){
            int width = this.getWidth() / frames.length;
            int height = this.getHeight();
            for (int i = 0; i < frames.length; i++) {
                frames[i].setSize(width, height);
                Point pos = frames[i].getLocation();
                frames[i].setLocation(i * width, pos.y);
            }
        }*/
        return true;
    }
    
    
     /**
     * Iconifies or de-iconifies the inactive render screens
     *
     * @param iconify The flag to minimize (true) or de-minize (false) the frame
     */
    public void setInactiveScreensIconify(boolean iconify) {
        for (JInternalFrame frame : getAllFrames()) {
            //if (visuManager != activeVisualisationManager) {
                try {
                    frame.setIcon(iconify);
                }
                catch (PropertyVetoException ex) {
                }
            //}
        }
    }
    
    /**
     * Closes all internal frames
     */
    public void closeAllInternalFrames(){
        JInternalFrame frames[] = this.getAllFrames();
        for (JInternalFrame frame : frames) {
            this.getDesktopManager().closeFrame(frame);
            try {
                frame.setClosed(true);
            } catch (PropertyVetoException ex) {
                
            }
            this.remove(frame);
        }
    }

    /**
     * Inits the desktop pane (does nothing)
     * @return true if ok
     */
    @Override
    public boolean init() {
        return true;
    }

    /**
     * Creates a new window, all new visualisation will be added to the last window created
     * @return 
     */
    @Override
    public boolean createNewWindow() {
        return false;
    }
    
    /**
     * Closes the desktop pane by closing all internal frames (internal frames then call Olivia.removeVisualisation() with their FrameEventListener)
     * @return true if the DesktopPane is closed
     * @see Olivia.core.Olivia.removeVisualisation()
     */
    @Override
    public boolean close(){
        closeAllInternalFrames();
        return true;
    }
    
    
}
