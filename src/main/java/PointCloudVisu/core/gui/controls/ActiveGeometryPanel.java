package PointCloudVisu.core.gui.controls;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the eye distance for 3D stereoscopic visualisation
 * 
 * @author jorge.martinez.sanchez
 */
public class ActiveGeometryPanel extends BasicArrowPanel implements ActionListener {

    protected OpenGLScreen renderScreen;

    public ActiveGeometryPanel(OpenGLScreen renderScreen) {
        super("Geometry", "Show next geometry", "Show previous geometry");
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
                renderScreen.getVisualisation().getGeometry().activeNextList();
                break;
            case "down":
                renderScreen.getVisualisation().getGeometry().activePreviousList();
                break;
        }
    }
}
