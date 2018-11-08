package Olivia.core.gui.renderGUI.desktop;

import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import Olivia.core.gui.RenderGUI;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import com.jogamp.newt.awt.NewtCanvasAWT;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author oscar.garcia
 */
public class DesktopPane extends JDesktopPane implements RenderGUI{
    MainFrame gui;

    public DesktopPane(MainFrame gui) {
        this.gui = gui;
    }

    @Override
    public boolean addVisualisation(VisualisationManager visualisationM) {
        InternalFrame frame = new InternalFrame(gui,visualisationM,visualisationM.getName(), true, true, true, true);
        frame.setClosable(true);
        visualisationM.getRenderScreen().createRenderFrameNEWT();
        NewtCanvasAWT canvas = new NewtCanvasAWT(visualisationM.getRenderScreen().getWindow());
        frame.add(canvas);
        frame.setResizable(true);
        frame.addInternalFrameListener(new FrameEventListener(visualisationM));
        frame.addComponentListener(new ComponentAdapter() {
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
        });
        this.add(frame);
        frame.setVisible(true);
        return true;
    }

    @Override
    public boolean updateRenderLayout() {
        JInternalFrame[] frames = getAllFrames();
        if(frames.length>0){
            int width = this.getWidth() / frames.length;
            int height = this.getHeight();
            for (int i = 0; i < frames.length; i++) {
                frames[i].setSize(width, height);
                Point pos = frames[i].getLocation();
                frames[i].setLocation(i * width, pos.y);
            }
        }
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

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean createNewWindow() {
        return false;
    }
    
    @Override
    public boolean close(){
        closeAllInternalFrames();
        return true;
    }
    
    
}
