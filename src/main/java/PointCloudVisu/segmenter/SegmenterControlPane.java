package PointCloudVisu.segmenter;

import PointCloudVisu.core.gui.GUIManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import static PointCloudVisu.segmenter.SegmenterColourArray.*;

/**
 * This class hold the controls for the segmentation visualisation
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterControlPane extends JPanel implements ActionListener {

    protected SegmenterVisualisation visualisation;
    protected JPanel colourPane;
    protected JButton intensityButton;
    protected JButton segmentationButton;
    protected JPanel classPane;
    protected JToggleButton segmentedToggle;
    protected JToggleButton unsegmentedToggle;
    protected JPanel neighPane;
    protected JToggleButton neighboursToggle;

    public SegmenterControlPane(SegmenterVisualisation visualisation) {
        this.visualisation = visualisation;

        colourPane = new JPanel(new GridLayout(2, 3));
        TitledBorder border = new TitledBorder("Point colour");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        colourPane.setBorder(border);
        intensityButton = GUIManager.createButton("Intensity", "Changes to intensity colouring", "intensity", this);
        segmentationButton = GUIManager.createButton("Segmentation", "Changes to segmentation colouring", "segmentation", this);
        colourPane.add(intensityButton);
        colourPane.add(segmentationButton);

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
        segmentedToggle = GUIManager.createToggleButton("Segmented", "Show/Hide segmented points", "segmented", visualisation.showSegmented, this);
        unsegmentedToggle = GUIManager.createToggleButton("Unsegmented", "Show/Hide unsegmented points", "unsegmented", visualisation.showUnsegmented, this);
        classPane.add(segmentedToggle);
        classPane.add(unsegmentedToggle);

        add(neighPane);
        add(colourPane);
        add(classPane);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "intensity":
                visualisation.setColouring(COLOUR_INDEX_INTENSITY);
                break;
            case "segmentation":
                visualisation.setColouring(COLOUR_INDEX_SEGMENTATION);
                break;
            case "neighbours":
                visualisation.getNeighbourhood().toogleShow(neighboursToggle);
                break;
            case "segmented":
                visualisation.toggleSegmented();
                break;
            case "unsegmented":
                visualisation.toggleUnsegmented();
                break;
        }
    }
}
