package Olivia.landing;

import Olivia.core.gui.GUIManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

/**
 *
 * @author oscar.garcia
 */
public class LandingControlPane extends JPanel implements ActionListener {

    protected LandingVisualisationManager visualisationManager;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton landingButton;
    protected JButton landingIntensityButton;
    protected JToggleButton toggleApproxB;
    protected JLabel groupLabel;

    public LandingControlPane(LandingVisualisationManager visualisationManager) {
        this.visualisationManager = visualisationManager;

        colourPane = new JPanel(new GridLayout(1, 3));
        TitledBorder border = new TitledBorder("Point colour");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        colourPane.setBorder(border);
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        landingButton = GUIManager.createButton("Landing", "Changes to landing colouring", "landing", this);
        landingIntensityButton = GUIManager.createButton("Landing Intensity", "Changes to landing intensity colouring", "landingI", this);
        colourPane.add(intensityButton);
        colourPane.add(landingButton);
        colourPane.add(landingIntensityButton);

        toggleApproxB = new JToggleButton("Approaches", false);
        toggleApproxB.setToolTipText("Show the approaches to the selected point");
        toggleApproxB.setActionCommand("showApproximations");
        toggleApproxB.addActionListener(this);

        groupLabel = new JLabel("<html><h1>No group selected</h1></html>");
        groupLabel.setToolTipText("selected group");

        visualisationManager.getRenderScreen().addActionListener(this);

        this.add(colourPane);
        this.add(toggleApproxB);
        this.add(groupLabel);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisationManager.setIntensityColouring();
                break;
            case "landing":
                visualisationManager.setLandingColouring();
                break;
            case "landingI":
                visualisationManager.setLandingIntensityColouring();
                break;
            case "showApproximations":
                visualisationManager.toggleApproximations();
                //toggleApproxB.setSelected(!toggleApproxB.isSelected());
                break;
            case "pointSelected":
                if (visualisationManager.getRenderScreen().getSelectedPoint() != null) {
                    changeGroupLabel();
                }
                break;
        }
    }

    public void changeGroupLabel() {
        groupLabel.setText(visualisationManager.getSelectedGroup().toHtmlString());
    }
}
