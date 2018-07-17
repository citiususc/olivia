package PointCloudVisu.core.gui.controls;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the point size of OpenGL primitives
 *
 * @author oscar.garcia
 */
public class PointSizePanel extends BasicArrowPanel implements ActionListener {

    protected OpenGLScreen renderScreen;

    public PointSizePanel(OpenGLScreen renderScreen) {
        super("Point Size", "Increases point size", "Decreases point size");
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
                renderScreen.increasePointSize();
                break;
            case "down":
                renderScreen.decreasePointSize();
                break;
        }
    }
}
