package Olivia.core.gui.controls;

import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the point size of OpenGL primitives
 *
 * @author oscar.garcia
 */
public class PointSizePanel extends BasicArrowPanel implements ActionListener {

    protected MainFrame gui;

    public PointSizePanel(MainFrame gui) {
        super("Point Size", "Increases point size", "Decreases point size");
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
                gui.getActiveVisualisation().getRenderScreen().increasePointSize();
                break;
            case "down":
                gui.getActiveVisualisation().getRenderScreen().decreasePointSize();
                break;
        }
    }
}
