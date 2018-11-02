package Olivia.core.render.hi;

import Olivia.core.VisualisationManager;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;

/**
 * Implements the mouse actions for the render screen and the mouse selection
 *
 * @author oscar.garcia
 */
public class AWTMouseListener implements MouseListener, MouseWheelListener, MouseMotionListener {

    /**
     * The render screen
     */
    //protected OpenGLScreen renderScreen;
    
    private final VisualisationManager visualisationManager;
    
    /**
     * The instance that takes care of the mouse point selection
     */
    protected OpenGLMouseSelection mouseSelection;
    /**
     * Window coordinates
     */
    protected float wX, wY;
    /**
     * Camera rotatations
     */
    protected float crX,

    /**
     * MainCamera rotatations
     */
    crY, 

    /**
     * MainCamera rotatations
     */
    crZ;
    /**
     * Camera translations
     */
    protected float ctX,

    /**
     * MainCamera translations
     */
    ctY, 

    /**
     * MainCamera translations
     */
    ctZ;
    /**
     * The radius for mouse picking
     */
    public static double MOUSE_PICK_RADIUS = 0.2;
    /**
     * The error margin of the distance to the point
     */
    public static double MOUSE_PICK_EPSILON = 0.5;

    public AWTMouseListener(VisualisationManager visualisationManager) {
        this.visualisationManager = visualisationManager;
        mouseSelection = new OpenGLMouseSelection(visualisationManager);
    }

    public OpenGLMouseSelection getMouseSelection() {
        return mouseSelection;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (visualisationManager == null) {
            return;
        }
        visualisationManager.getRenderScreen().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        wX = me.getX();
        wY = me.getY();
        if (SwingUtilities.isLeftMouseButton(me)) {
            crX = visualisationManager.getRenderScreen().getCamera().getRotX();
            crZ = visualisationManager.getRenderScreen().getCamera().getRotZ();
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            visualisationManager.getRenderScreen().fireEvent("pointSelected");
        } else if (SwingUtilities.isRightMouseButton(me)) {
            ctX = visualisationManager.getRenderScreen().getCamera().getTransX();
            ctY = visualisationManager.getRenderScreen().getCamera().getTransY();
            visualisationManager.getRenderScreen().fireEvent("pointReleased");
            visualisationManager.getRenderScreen().fireEvent("groupReleased");
        } else if (SwingUtilities.isMiddleMouseButton(me)) {
            crX = visualisationManager.getRenderScreen().getCamera().getRotX();
            crZ = visualisationManager.getRenderScreen().getCamera().getRotZ();
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            visualisationManager.getRenderScreen().fireEvent("groupSelected");

        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (visualisationManager == null) {
            return;
        }
        visualisationManager.getRenderScreen().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (visualisationManager == null) {
            return;
        }
        int step = 25;

        if (mwe.getWheelRotation() < 0) {
            visualisationManager.getRenderScreen().getCamera().addToTransZ(step);
        } else {
            visualisationManager.getRenderScreen().getCamera().addToTransZ(-step);
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (visualisationManager == null) {
            return;
        }
        float speed = 0.5f;

        if (SwingUtilities.isLeftMouseButton(me)) {
            visualisationManager.getRenderScreen().getCamera().setRotZ(crZ + (float) (me.getX() - wX) * speed);
            visualisationManager.getRenderScreen().getCamera().setRotX(crX + (float) (me.getY() - wY) * speed);
        }
        if (SwingUtilities.isRightMouseButton(me)) {
            visualisationManager.getRenderScreen().getCamera().setTransX(ctX + (float) (me.getX() - wX) * speed);
            visualisationManager.getRenderScreen().getCamera().setTransY(ctY - (float) (me.getY() - wY) * speed);
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getWindowX() {
        return (int) wX;
    }

    public int getWindowY() {
        return (int) wY;
    }
}
