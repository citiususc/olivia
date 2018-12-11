package Olivia.core.gui.controls;

import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the line width of OpenGL primitives
 * 
 * @author oscar.garcia
 */
public class LineWidthPanel extends BasicArrowPanel implements ActionListener {

    protected MainFrame gui;

    public LineWidthPanel(MainFrame gui) {
        super("Line Width", "Increases line width", "Decreases line width");
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
                gui.getActiveVisualisation().getRenderScreen().increaseLineWidth();
                break;
            case "down":
                gui.getActiveVisualisation().getRenderScreen().decreaseLineWidth();
                break;
        }
    }
}
