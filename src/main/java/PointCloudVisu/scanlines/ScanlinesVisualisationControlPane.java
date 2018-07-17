package PointCloudVisu.scanlines;

import PointCloudVisu.core.gui.GUIManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author oscar
 */
public class ScanlinesVisualisationControlPane extends JPanel implements ActionListener {

    protected ScanlinesVisualisation visualisation;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton edgeButton;
    protected JButton scanlineButton;

    public ScanlinesVisualisationControlPane(ScanlinesVisualisation visualisation) {
        this.visualisation = visualisation;

        colourPane = new JPanel(new GridLayout(2, 3));
        TitledBorder border = new TitledBorder("Point colour");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        colourPane.setBorder(border);
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        edgeButton = GUIManager.createButton("Edge", "Changes to Edge colouring", "edge", this);
        scanlineButton = GUIManager.createButton("Scanlines", "Changes to Random colouring", "scanline", this);
        colourPane.add(intensityButton);
        colourPane.add(edgeButton);
        colourPane.add(scanlineButton);

        add(colourPane);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisation.setIntensityColouring();
                break;
            case "edge":
                visualisation.setEdgesColouring();
                break;
            case "scanline":
                visualisation.setRandomColouring();
                break;
        }
    }
}
