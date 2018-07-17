package PointCloudVisu.classifier;

import static PointCloudVisu.classifier.ClassifierLabels.*;
import PointCloudVisu.core.gui.GUIManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

/**
 * This class hold the controls for the classification visualisation
 * 
 * @author jorge.martinez.sanchez
 */
public class ClassifierControlPane extends JPanel implements ActionListener {

    protected ClassifierVisualisation visualisation;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton segmentationButton;
    protected JButton classificationButton;
    protected JPanel classPane;
    protected JToggleButton unknownToggle;
    protected JToggleButton groundToggle;
    protected JToggleButton vegetationToggle;
    protected JToggleButton buildingToggle;
    protected JToggleButton roadToggle;
    protected JToggleButton otherToggle;
    protected JPanel neighPane;
    protected JToggleButton neighboursToggle;

    public ClassifierControlPane(ClassifierVisualisation visualisation) {
        this.visualisation = visualisation;
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        segmentationButton = GUIManager.createButton("Segmentation", "Changes to segmentation colouring", "segmentation", this);
        classificationButton = GUIManager.createButton("Classification", "Changes to classification colouring", "classification", this);

        colourPane = new JPanel(new GridLayout(1, 3));
        TitledBorder border = new TitledBorder("Point colour");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        colourPane.setBorder(border);
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        segmentationButton = GUIManager.createButton("Segmentation", "Changes to segmentation colouring", "segmentation", this);
        classificationButton = GUIManager.createButton("Classification", "Changes to classification colouring", "classification", this);
        colourPane.add(intensityButton);
        colourPane.add(segmentationButton);
        colourPane.add(classificationButton);

        neighPane = new JPanel();
        border = new TitledBorder("Neighbours");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        neighPane.setBorder(border);
        neighboursToggle = GUIManager.createToggleButton("Show/hide", "Show/Hide neighbours", "neighbours", visualisation.getNeighbourhood().isShow(), this);
        neighPane.add(neighboursToggle);

        classPane = new JPanel(new GridLayout(2, 3));
        border = new TitledBorder("Active Classes");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        classPane.setBorder(border);
        unknownToggle = GUIManager.createToggleButton("Unknown", "Show/Hide unknown points", "unknown", visualisation.activeClasses.contains(UNKNOWN), this);
        groundToggle = GUIManager.createToggleButton("Ground", "Show/Hide unknown ground", "ground", visualisation.activeClasses.contains(GROUND), this);
        vegetationToggle = GUIManager.createToggleButton("Vegetation", "Show/Hide vegetation points", "vegetation", visualisation.activeClasses.contains(MEDIUM_VEG), this);
        buildingToggle = GUIManager.createToggleButton("Building", "Show/Hide building points", "building", visualisation.activeClasses.contains(BUILDING), this);
        roadToggle = GUIManager.createToggleButton("Road", "Show/Hide road", "road", visualisation.activeClasses.contains(ROAD), this);
        otherToggle = GUIManager.createToggleButton("Other", "Show/Hide other", "other", visualisation.activeClasses.contains(RESERVED), this);

        classPane.add(unknownToggle);
        classPane.add(groundToggle);
        classPane.add(vegetationToggle);
        classPane.add(buildingToggle);
        classPane.add(roadToggle);
        classPane.add(otherToggle);

        add(neighPane);
        add(colourPane);
        add(classPane);
    }

    private void toogleClass(JToggleButton btn, int classId) {
        if (btn.isSelected()) {
            visualisation.showClass(classId);
        } else {
            visualisation.hideClass(classId);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisation.setIntensityColouring();
                break;
            case "segmentation":
                visualisation.setSegmenterColouring();
                break;
            case "classification":
                visualisation.setClassificationColouring();
                break;
            case "neighbours":
                visualisation.getNeighbourhood().toogleShow(neighboursToggle);
                break;
            case "unknown":
                toogleClass(unknownToggle, UNKNOWN);
                break;
            case "ground":
                toogleClass(groundToggle, GROUND);
                break;
            case "vegetation":
                toogleClass(vegetationToggle, LOW_VEG);
                toogleClass(vegetationToggle, MEDIUM_VEG);
                toogleClass(vegetationToggle, HIGH_VEG);
                break;
            case "building":
                toogleClass(buildingToggle, BUILDING);
                break;
            case "road":
                toogleClass(roadToggle, ROAD);
                break;
            case "other":
                toogleClass(otherToggle, RESERVED);
                break;
        }
    }
}
