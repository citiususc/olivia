package PointCloudVisu.core.render.hi;

import PointCloudVisu.core.PointCloudVisu;
import PointCloudVisu.core.render.OpenGLScreen;
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
public class OpenGLMouseListener implements MouseListener, MouseWheelListener, MouseMotionListener {

    /**
     * The render screen
     */
    protected OpenGLScreen renderScreen;
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
    protected float crX, crY, crZ;
    /**
     * Camera translations
     */
    protected float ctX, ctY, ctZ;
    /**
     * The radius for mouse picking
     */
    public static double MOUSE_PICK_RADIUS = 0.2;
    /**
     * The error margin of the distance to the point
     */
    public static double MOUSE_PICK_EPSILON = 0.5;

    public OpenGLMouseListener(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
        mouseSelection = new OpenGLMouseSelection(renderScreen);
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
        if (renderScreen.getVisualisation() == null) {
            return;
        }
        renderScreen.getVisualisation().getRenderScreen().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        wX = me.getX();
        wY = me.getY();
        if (SwingUtilities.isLeftMouseButton(me)) {
            crX = renderScreen.getCamera().getRotX();
            crZ = renderScreen.getCamera().getRotZ();
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            renderScreen.fireEvent("pointSelected");
            if (PointCloudVisu.isMirroring()) {
                PointCloudVisu.doSelectionMirroring(new String[]{"pointSelected"});
            }
        } else if (SwingUtilities.isRightMouseButton(me)) {
            ctX = renderScreen.getCamera().getTransX();
            ctY = renderScreen.getCamera().getTransY();
            renderScreen.fireEvent("pointReleased");
            renderScreen.fireEvent("groupReleased");
            if (PointCloudVisu.isMirroring()) {
                PointCloudVisu.doSelectionMirroring(new String[]{"pointReleased", "groupReleased"});
            }
        } else if (SwingUtilities.isMiddleMouseButton(me)) {
            crX = renderScreen.getCamera().getRotX();
            crZ = renderScreen.getCamera().getRotZ();
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            renderScreen.fireEvent("groupSelected");
            if (PointCloudVisu.isMirroring()) {
                PointCloudVisu.doSelectionMirroring(new String[]{"groupSelected"});
            }
        }
        if (PointCloudVisu.isMirroring()) {
            PointCloudVisu.doCameraMirroring();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (renderScreen.getVisualisation() == null) {
            return;
        }
        renderScreen.getVisualisation().getRenderScreen().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        if (renderScreen.getVisualisation() == null) {
            return;
        }
        int step = 25;

        if (mwe.getWheelRotation() < 0) {
            renderScreen.getCamera().addToTransZ(step);
        } else {
            renderScreen.getCamera().addToTransZ(-step);
        }
        if (PointCloudVisu.isMirroring()) {
            PointCloudVisu.doCameraMirroring();
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (renderScreen.getVisualisation() == null) {
            return;
        }
        float speed = 0.5f;

        if (SwingUtilities.isLeftMouseButton(me)) {
            renderScreen.getCamera().setRotZ(crZ + (float) (me.getX() - wX) * speed);
            renderScreen.getCamera().setRotX(crX + (float) (me.getY() - wY) * speed);
        }
        if (SwingUtilities.isRightMouseButton(me)) {
            renderScreen.getCamera().setTransX(ctX + (float) (me.getX() - wX) * speed);
            renderScreen.getCamera().setTransY(ctY - (float) (me.getY() - wY) * speed);
        }
        if (PointCloudVisu.isMirroring()) {
            PointCloudVisu.doCameraMirroring();
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
