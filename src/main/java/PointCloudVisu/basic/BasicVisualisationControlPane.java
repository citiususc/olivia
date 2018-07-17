package PointCloudVisu.basic;

import PointCloudVisu.core.gui.GUIManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * This class hold the controls for the basic visualisation
 * 
 * @author oscar.garcia
 */
public class BasicVisualisationControlPane extends JPanel implements ActionListener {

    protected BasicVisualisation visualisation;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton randomButton;

    public BasicVisualisationControlPane(BasicVisualisation visualisation) {
        this.visualisation = visualisation;

        colourPane = new JPanel(new GridLayout(2, 3));
        TitledBorder border = new TitledBorder("Point colour");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        colourPane.setBorder(border);
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        randomButton = GUIManager.createButton("Random", "Changes to Random colouring", "random", this);
        colourPane.add(intensityButton);
        //colourPane.add(randomButton);

        add(colourPane);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisation.setIntensityColouring();
                break;
            case "random":
                visualisation.setRandomColouring();
                break;
        }
    }
}
