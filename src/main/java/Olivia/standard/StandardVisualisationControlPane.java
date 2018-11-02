package Olivia.standard;

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
public class StandardVisualisationControlPane extends JPanel implements ActionListener {

    protected StandardVisualisationManager visualisationM;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton rgbButton;
    protected JButton classificationButton;
    protected JButton returnNumberButton;
    protected JButton scanlineButton;
    protected JButton gradientButton;

    public StandardVisualisationControlPane(StandardVisualisationManager visualisationM) {
        this.visualisationM = visualisationM;

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
        gradientButton = GUIManager.createButton("Gradient", "Changes to gradient colouring", "gradient", this);
        colourPane.add(intensityButton);
        colourPane.add(rgbButton);
        colourPane.add(classificationButton);
        colourPane.add(returnNumberButton);
        colourPane.add(scanlineButton);
        colourPane.add(gradientButton);
        
        intensityButton.setEnabled(visualisationM.getPointCloud().isHave_intensity());
        rgbButton.setEnabled(visualisationM.getPointCloud().isHave_RGB());
        classificationButton.setEnabled(visualisationM.getPointCloud().isHave_classification());
        returnNumberButton.setEnabled(visualisationM.getPointCloud().isHave_returns());
        scanlineButton.setEnabled(visualisationM.getPointCloud().isHave_scanlines());
        gradientButton.setEnabled(visualisationM.getPointCloud().isHave_intensity());

        add(colourPane);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisationM.setIntensityColouring();
                break;
            case "rgb":
                visualisationM.setRGBColouring();
                break;
            case "classification":
                visualisationM.setClassificationColouring();
                break;
            case "returnNumber":
                visualisationM.setReturnNumberColouring();
                break;
            case "scanline":
                visualisationM.setScanlineColouring();
                break;
            case "gradient":
                visualisationM.setGradientColouring();
                break;
        }
    }
}
