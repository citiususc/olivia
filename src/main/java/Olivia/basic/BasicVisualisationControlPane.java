package Olivia.basic;

import Olivia.core.gui.GUIManager;
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

    protected BasicVisualisationManager visualisationM;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton randomButton;

    public BasicVisualisationControlPane(BasicVisualisationManager visualisationM) {
        this.visualisationM = visualisationM;

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
                visualisationM.setIntensityColouring();
                break;
            case "random":
                visualisationM.setRandomColouring();
                break;
        }
    }
}
