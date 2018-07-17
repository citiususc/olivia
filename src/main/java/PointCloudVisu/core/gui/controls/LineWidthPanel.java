package PointCloudVisu.core.gui.controls;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the line width of OpenGL primitives
 * 
 * @author oscar.garcia
 */
public class LineWidthPanel extends BasicArrowPanel implements ActionListener {

    protected OpenGLScreen renderScreen;

    public LineWidthPanel(OpenGLScreen renderScreen) {
        super("Line Width", "Increases line width", "Decreases line width");
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
                renderScreen.increaseLineWidth();
                break;
            case "down":
                renderScreen.decreaseLineWidth();
                break;
        }
    }
}
