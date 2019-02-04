package Olivia.core.gui.controls;

import Olivia.core.gui.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the eye distance for 3D stereoscopic visualisation
 * 
 * @author jorge.martinez.sanchez
 */
public class EyeDistPanel extends BasicArrowPanel implements ActionListener {

    protected MainFrame gui;

    public EyeDistPanel(MainFrame gui) {
        super("Eye dist", "Increases eye distance", "Decrease eye distance");
        super.buttonUp.addActionListener(this);
        super.buttonDown.addActionListener(this);
        this.gui = gui;
    }

    /*
    public void setupRenderScreen(OpenGLScreen renderScreen) {
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
                gui.getActiveVisualisation().getRenderScreen().increaseEyeDist();
                break;
            case "down":
                gui.getActiveVisualisation().getRenderScreen().decreaseEyeDist();
                break;
        }
    }
}
