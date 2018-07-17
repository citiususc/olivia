package PointCloudVisu.standard;

import PointCloudVisu.basic.*;
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
public class StandardVisualisationControlPane extends JPanel implements ActionListener {

    protected StandardVisualisation visualisation;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton rgbButton;
    protected JButton classificationButton;
    protected JButton returnNumberButton;
    protected JButton scanlineButton;

    public StandardVisualisationControlPane(StandardVisualisation visualisation) {
        this.visualisation = visualisation;

        colourPane = new JPanel(new GridLayout(2, 3));
        TitledBorder border = new TitledBorder("Point colour");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        colourPane.setBorder(border);
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        rgbButton = GUIManager.createButton("RGB", "Changes to RGB colouring", "rgb", this);
        classificationButton = GUIManager.createButton("Classification", "Changes to classification colouring", "classification", this);
        returnNumberButton = GUIManager.createButton("Return Number", "Changes to return number colouring", "returnNumber", this);
        scanlineButton = GUIManager.createButton("Scanline", "Changes to scanline colouring", "scanline", this);
        colourPane.add(intensityButton);
        colourPane.add(rgbButton);
        colourPane.add(classificationButton);
        colourPane.add(returnNumberButton);
        colourPane.add(scanlineButton);
        
        intensityButton.setEnabled(visualisation.getPoints().isHave_intensity());
        rgbButton.setEnabled(visualisation.getPoints().isHave_RGB());
        classificationButton.setEnabled(visualisation.getPoints().isHave_classification());
        returnNumberButton.setEnabled(visualisation.getPoints().isHave_returns());
        scanlineButton.setEnabled(visualisation.getPoints().isHave_scanlines());

        add(colourPane);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisation.setIntensityColouring();
                break;
            case "rgb":
                visualisation.setRGBColouring();
                break;
            case "classification":
                visualisation.setClassificationColouring();
                break;
            case "returnNumber":
                visualisation.setReturnNumberColouring();
                break;
            case "scanline":
                visualisation.setScanlineColouring();
                break;
        }
    }
}
