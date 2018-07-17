package PointCloudVisu.neighbours;

import PointCloudVisu.core.gui.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class hold the controls for the neighbours visualisation
 *
 * @author jorge.martinez.sanchez
 */
public class NeighboursControlPane extends JPanel implements ActionListener {

    protected NeighboursVisualisation visualisation;
    protected JButton intensityButton;

    public NeighboursControlPane(NeighboursVisualisation visualisation) {
        this.visualisation = visualisation;
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        add(intensityButton);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisation.setIntensityColouring();
                break;
        }
    }
}
