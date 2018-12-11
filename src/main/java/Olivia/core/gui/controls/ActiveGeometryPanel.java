package Olivia.core.gui.controls;

import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel controls the eye distance for 3D stereoscopic visualisation
 * 
 * @author jorge.martinez.sanchez
 */
public class ActiveGeometryPanel extends BasicArrowPanel implements ActionListener {

    protected MainFrame gui;

    public ActiveGeometryPanel(MainFrame gui) {
        super("Geometry", "Show next geometry", "Show previous geometry");
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
        }/*
        switch (e.getActionCommand()) {
            case "up":
                gui.getActiveVisualisation().getGeometry().activeNextList();
                break;
            case "down":
                gui.getActiveVisualisation().getGeometry().activePreviousList();
                break;
        }*/
    }

}
