package PointCloudVisu.core.gui.controls;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the eye distance for 3D stereoscopic visualisation
 * 
 * @author jorge.martinez.sanchez
 */
public class EyeDistPanel extends BasicArrowPanel implements ActionListener {

    protected OpenGLScreen renderScreen;

    public EyeDistPanel(OpenGLScreen renderScreen) {
        super("Eye dist", "Increases eye distance", "Decrease eye distance");
        super.buttonUp.addActionListener(this);
        super.buttonDown.addActionListener(this);
        this.renderScreen = renderScreen;
    }

    public void setRenderScreen(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "up":
                renderScreen.increaseEyeDist();
                break;
            case "down":
                renderScreen.decreaseEyeDist();
                break;
        }
    }
}
