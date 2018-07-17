package PointCloudVisu.segmenter;

import PointCloudVisu.core.Visualisation;
import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.render.colours.PointColour;
import PointCloudVisu.extended.PointI;
import PointCloudVisu.extended.IntensityColourArray;
import com.jogamp.opengl.GL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static PointCloudVisu.segmenter.SegmenterColourArray.COLOUR_INDEX_SEGMENTATION;

/**
 * This class manages the visualisation of segmented point clouds
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterVisualisation extends Visualisation<PointI,PointArray<PointI>> implements ActionListener {

    private SegmenterGroupArray<SegmenterGroup> groups;
    protected SegmenterInputReader inputReader;
    protected ArrayList<ColourArray> colours;
    protected ArrayList<Integer> groupIds;
    protected int selectedColour;
    protected int numSelected;
    protected boolean showSegmented;
    protected boolean showUnsegmented;
    protected boolean[] selectedFlags;
    protected SegmenterGroup selectedGroup;
    protected PointArray selectedGroupPoints;
    protected ColourArray selectedGroupColours;

    public SegmenterVisualisation(OpenGLScreen renderScreen) {
        super(renderScreen);
        super.renderScreen.addActionListener(this);
        colours = new ArrayList<>();
        groupIds = new ArrayList<>();
        inputReader = new SegmenterInputReader();
        groups = new SegmenterGroupArray<>();
        selectedColour = COLOUR_INDEX_SEGMENTATION;
        showSegmented = true;
        showUnsegmented = false;
        selectedGroup = null;
        selectedGroupPoints = new PointArray();
    }

    public void setGroups(SegmenterGroupArray<SegmenterGroup> groups) {
        this.groups = groups;
    }

    public void setGroupIds(ArrayList<Integer> groupIdcs) {
        this.groupIds = groupIdcs;
    }

    public void findSelectedGroup() {
        Point3D point = renderScreen.getSelectedPoint();
        selectedGroup = groups.getGroupFromPoint(point);
        if (selectedGroup == null) {
            return;
        }
        super.textInfo = selectedGroup.getDescriptionAsHTML();
    }

    public void packSelectedGroupPoints() {
        if (selectedGroup == null) {
            return;
        }
        selectedGroupPoints.clear();
        selectedGroupPoints.addAll(groups.get(selectedGroup.getId()).getPoints());
        selectedGroupColours = new ColourArray(selectedGroupPoints) {
        };
        PointColour groupColour = colours.get(1).get(renderScreen.getSelectedPoint().getId());
        for (int i = 0; i < selectedGroupPoints.size(); i++) {
            selectedGroupColours.add(groupColour);
        }
        selectedGroupPoints.repack();
    }

    public void flagSelectedPoints(boolean[] selectedFlags) {
        numSelected = selectedFlags.length;
        Arrays.fill(selectedFlags, true);
        if (!showSegmented) {
            for (int i = 0; i < inputReader.getNumUnsegmented(); i++) {
                selectedFlags[i] = false;
            }
            numSelected = inputReader.getNumUnsegmented();
        }
        if (!showUnsegmented) {
            int numPts = inputReader.getNumSegmented() + inputReader.getNumUnsegmented();
            for (int i = inputReader.getNumSegmented(); i < numPts; i++) {
                selectedFlags[i] = false;
            }
            numSelected = inputReader.getNumSegmented();
        }
    }

    public void toggleSegmented() {
        showSegmented = !showSegmented;
        flagSelectedPoints(selectedFlags);
        points.repack();
    }

    public void toggleUnsegmented() {
        showUnsegmented = !showUnsegmented;
        flagSelectedPoints(selectedFlags);
        points.repack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "groupSelected":
                if (super.selected) {
                    findSelectedGroup();
                    packSelectedGroupPoints();
                }
                break;
            case "pointReleased":
                if (super.selected) {
                    selectedGroup = null;
                }
                break;
        }
    }

    @Override
    public void draw() {
        if (selectedGroup == null) {
            points.drawSelected(renderScreen, colours.get(selectedColour), selectedFlags, numSelected, GL.GL_POINTS);
        } else {
            selectedGroupPoints.draw(renderScreen, selectedGroupColours, GL.GL_POINTS);
        }
    }

    @Override
    public void readFromFile(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        selectedFlags = new boolean[points.size()];
        flagSelectedPoints(selectedFlags);
        colours.add(new IntensityColourArray(points));
        colours.add(new SegmenterColourArray(points, groupIds));
        System.out.println("Read " + points.size() + " points");
        System.out.println("Read " + groups.size() + " groups");
    }

    public void setColouring(int colourIndex) {
        selectedColour = colourIndex;
        points.repack();
    }
}
