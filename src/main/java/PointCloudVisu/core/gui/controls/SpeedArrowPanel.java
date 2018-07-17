package PointCloudVisu.core.gui.controls;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel control the speed of the camera movement
 * 
 * @author jorge.martinez.sanchez
 */
public class SpeedArrowPanel extends BasicArrowPanel implements ActionListener {

    protected OpenGLScreen renderScreen;

    public SpeedArrowPanel(OpenGLScreen renderScreen) {
        super("Speed", "Increases camera speed", "Decreases camera speed");
        super.buttonUp.addActionListener(this);
        super.buttonDown.addActionListener(this);
        this.renderScreen = renderScreen;
    }

    public void setRenderScreen(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (renderScreen == null) {
            return;
        }
        switch (e.getActionCommand()) {
            case "up":
                renderScreen.getCamera().increaseSpeed();
                break;
            case "down":
                renderScreen.getCamera().decreaseSpeed();
                break;
        }
    }
}
