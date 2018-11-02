package Olivia.core.gui.controls;

import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel control the speed of the camera movement
 * 
 * @author jorge.martinez.sanchez
 */
public class SpeedArrowPanel extends BasicArrowPanel implements ActionListener {

    protected MainFrame gui;

    public SpeedArrowPanel(MainFrame gui) {
        super("Speed", "Increases camera speed", "Decreases camera speed");
        super.buttonUp.addActionListener(this);
        super.buttonDown.addActionListener(this);
        this.gui = gui;
    }

    /*
    public void setRenderScreen(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
    }
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gui.getActiveVisualisation() == null) {
            return;
        }
        
        switch (e.getActionCommand()) {
            case "up":
                gui.getActiveVisualisation().getRenderScreen().getCamera().increaseSpeed();
                break;
            case "down":
                gui.getActiveVisualisation().getRenderScreen().getCamera().decreaseSpeed();
                break;
        }
    }
}
