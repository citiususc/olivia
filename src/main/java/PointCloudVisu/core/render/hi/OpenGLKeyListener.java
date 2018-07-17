package PointCloudVisu.core.render.hi;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Implements the key actions for the render screen
 *
 * @author oscar.garcia
 */
public class OpenGLKeyListener implements KeyListener {

    protected OpenGLScreen renderScreen;

    public OpenGLKeyListener(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getExtendedKeyCode(); 
        
        switch (key) {
            case KeyEvent.VK_1:
                renderScreen.increasePointSize();
                return;
            case KeyEvent.VK_2:
                renderScreen.decreasePointSize();
                return;
            case KeyEvent.VK_3:
                renderScreen.increaseLineWidth();
                return;
            case KeyEvent.VK_4:
                renderScreen.decreaseLineWidth();
                return;
            case KeyEvent.VK_5:
                renderScreen.getVisualisation().getGeometry().activeNextList();
                return;
            case KeyEvent.VK_6:
                renderScreen.getVisualisation().getGeometry().activePreviousList();
                return;
            case KeyEvent.VK_7:
                renderScreen.increaseEyeDist();
                return;
            case KeyEvent.VK_8:
                renderScreen.decreaseEyeDist();
                return;
            case KeyEvent.VK_UP:
                renderScreen.getCamera().increaseRotX();
                return;
            case KeyEvent.VK_DOWN:
                renderScreen.getCamera().decreaseRotX();
                return;
            case KeyEvent.VK_LEFT:
                renderScreen.getCamera().increaseRotZ();
                return;
            case KeyEvent.VK_RIGHT:
                renderScreen.getCamera().decreaseRotZ();
                return;
            case KeyEvent.VK_A:
                renderScreen.getCamera().increaseTransX();
                return;
            case KeyEvent.VK_D:
                renderScreen.getCamera().decreaseTransX();
                return;
            case KeyEvent.VK_S:
                renderScreen.getCamera().decreaseTransZ();
                return;
            case KeyEvent.VK_W:
                renderScreen.getCamera().increaseTransZ();
                return;
            case KeyEvent.VK_E:
                renderScreen.getCamera().decreaseTransY();
                return;
            case KeyEvent.VK_Q:
                renderScreen.getCamera().increaseTransY();
                return;
            case KeyEvent.VK_F12:
                renderScreen.getCapture().setCaptureImage(true);
                return;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
