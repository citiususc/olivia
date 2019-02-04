package Olivia.core.render.hi;

import Olivia.core.VisualisationManager;
import java.awt.Cursor;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseEvent.PointerClass;
import com.jogamp.newt.event.MouseListener;
import java.awt.Component;


/**
 * Implements the mouse actions for the render screen and the mouse selection
 *
 * @author oscar.garcia
 */
public class NEWTMouseListener implements MouseListener{

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

    public NEWTMouseListener(VisualisationManager visualisationManager) {
        this.visualisationManager = visualisationManager;
        mouseSelection = new OpenGLMouseSelection(visualisationManager);
    }

    public OpenGLMouseSelection getMouseSelection() {
        return mouseSelection;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //debug(me,"mouseClicked");
        if(me.getPointerType(0).getPointerClass() == PointerClass.Onscreen){
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            visualisationManager.getRenderScreen().fireEvent("pointSelected");
            visualisationManager.getRenderScreen().windowInteracted(me);
        }
    
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //debug(me,"mousePressed");
        if (visualisationManager == null) {
            return;
        }
        if(me.getPointerType(0).getPointerClass() == PointerClass.Onscreen){
            wX = me.getX();
            wY = me.getY();
            crX = visualisationManager.getRenderScreen().getCamera().getRotX();
            crZ = visualisationManager.getRenderScreen().getCamera().getRotZ();
            visualisationManager.getRenderScreen().windowInteracted(me);
            return;
        }
        
        //visualisationManager.getRenderScreen().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        //((Component)visualisationManager.getRenderScreen().getWindow().getParent()).setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        wX = me.getX();
        wY = me.getY();
        if (MouseButtonCodes.isMouseLeft(me)) {
            crX = visualisationManager.getRenderScreen().getCamera().getRotX();
            crZ = visualisationManager.getRenderScreen().getCamera().getRotZ();
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            visualisationManager.getRenderScreen().fireEvent("pointSelected");
        } else if (MouseButtonCodes.isMouseRight(me)) {
            ctX = visualisationManager.getRenderScreen().getCamera().getTransX();
            ctY = visualisationManager.getRenderScreen().getCamera().getTransY();
            visualisationManager.getRenderScreen().fireEvent("pointReleased");
            visualisationManager.getRenderScreen().fireEvent("groupReleased");
        } else if (MouseButtonCodes.isMouseCenter(me)) {
            crX = visualisationManager.getRenderScreen().getCamera().getRotX();
            crZ = visualisationManager.getRenderScreen().getCamera().getRotZ();
            mouseSelection.pick(me.getX(), me.getY(), MOUSE_PICK_RADIUS, MOUSE_PICK_EPSILON);
            visualisationManager.getRenderScreen().fireEvent("groupSelected");

        }
        visualisationManager.getRenderScreen().windowInteracted(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //debug(me,"mouseRelased");
        if (visualisationManager == null) {
            return;
        }
        //visualisationManager.getRenderScreen().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //((Component)visualisationManager.getRenderScreen().getWindow().getParent()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //debug(me,"mouseEntered");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //debug(me,"mouseExited");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseEvent me) {
        //debug(me,"mouseWheelMoved");
        if (visualisationManager == null) {
            return;
        }
        float step = 2f;
        
        if(me.getPointerType(0).getPointerClass() == PointerClass.Offscreen){
            /*if (me.getRotation()[1] < 0) {
                visualisationManager.getRenderScreen().getCamera().addToTransZ(step);
            } else {
                visualisationManager.getRenderScreen().getCamera().addToTransZ(-step);
            }*/
            visualisationManager.getRenderScreen().getCamera().addToTransZ(step*me.getRotation()[1]*me.getRotationScale());
        }else{
            return;
            //visualisationManager.getRenderScreen().getCamera().addToTransZ(step*me.getRotation()[1]*me.getRotationScale());
            
        }
        visualisationManager.getRenderScreen().windowInteracted(me);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        //debug(me,"mouseDragged");
        if (visualisationManager == null) {
            return;
        }
        float speed = 0.5f;
        
        if(me.getPointerType(0).getPointerClass() == PointerClass.Onscreen){
            visualisationManager.getRenderScreen().getCamera().setRotZ(crZ + (float) (me.getX() - wX) * speed);
            visualisationManager.getRenderScreen().getCamera().setRotX(crX + (float) (me.getY() - wY) * speed);
            visualisationManager.getRenderScreen().windowInteracted(me);
            return;
        }

        if (MouseButtonCodes.isMouseLeft(me)) {
            visualisationManager.getRenderScreen().getCamera().setRotZ(crZ + (float) (me.getX() - wX) * speed);
            visualisationManager.getRenderScreen().getCamera().setRotX(crX + (float) (me.getY() - wY) * speed);
        }
        if (MouseButtonCodes.isMouseRight(me)) {
            visualisationManager.getRenderScreen().getCamera().setTransX(ctX + (float) (me.getX() - wX) * speed);
            visualisationManager.getRenderScreen().getCamera().setTransY(ctY - (float) (me.getY() - wY) * speed);
        }
        visualisationManager.getRenderScreen().windowInteracted(me);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //debug(me,"mouseMoved");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getWindowX() {
        return (int) wX;
    }

    public int getWindowY() {
        return (int) wY;
    }
    
    protected void debug(MouseEvent me, String text){
        System.out.print( text + " ");
        if(me.getPointerType(0).getPointerClass() == PointerClass.Offscreen){
            System.out.print( "offscreen " + me.getButton());
        }else if(me.getPointerType(0).getPointerClass() == PointerClass.Onscreen){
            System.out.print( "onscreen b:" + me.getButton() + " nb:" + me.getButtonDownCount());
        }
        System.out.println("");
    }
}
