package Olivia.segmenter;

import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;
import Olivia.extended.IntensityColourArray;
import com.jogamp.opengl.GL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static Olivia.segmenter.SegmenterColourArray.COLOUR_INDEX_SEGMENTATION;

/**
 * This class manages the visualisation of segmented point clouds
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterVisualisationManager extends VisualisationManager<SegmenterVisualisationManager, PointI, PointArray<PointI>,OpenGLScreen,SegmenterControlPane> implements ActionListener {

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
    /**
     * Text that a visualisation can provide
     */
    protected String textInfo;

    public SegmenterVisualisationManager(int id, MainFrame gui, boolean isStereo3D){
        this(id, gui, isStereo3D, "Segmenter " + id);
    }
    
    public SegmenterVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String name) {
        super(id, gui, isStereo3D);
        this.name = name;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        renderScreen.addActionListener(this);
        colours = new ArrayList<>();
        groupIds = new ArrayList<>();
        inputReader = new SegmenterInputReader();
        groups = new SegmenterGroupArray<>();
        selectedColour = COLOUR_INDEX_SEGMENTATION;
        showSegmented = true;
        showUnsegmented = false;
        selectedGroup = null;
        selectedGroupPoints = new PointArray();
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new SegmenterControlPane(this);
    }

    public void setGroups(SegmenterGroupArray<SegmenterGroup> groups) {
        this.groups = groups;
    }

    public void setGroupIds(ArrayList<Integer> groupIdcs) {
        this.groupIds = groupIdcs;
    }

    public void findSelectedGroup() {
        Point3D_id point = renderScreen.getSelectedPoint();
        selectedGroup = groups.getGroupFromPoint(point);
        if (selectedGroup == null) {
            return;
        }
        textInfo = selectedGroup.getDescriptionAsHTML();
    }

    public void packSelectedGroupPoints() {
        if (selectedGroup == null) {
            return;
        }
        selectedGroupPoints.clear();
        selectedGroupPoints.freeVBO(renderScreen);
        selectedGroupPoints.addAll(groups.get(selectedGroup.getId()).getPoints());
        selectedGroupColours = new ColourArray(selectedGroupPoints) {
        };
        PointColour groupColour = colours.get(1).get(renderScreen.getSelectedPoint().getId());
        for (int i = 0; i < selectedGroupPoints.size(); i++) {
            selectedGroupColours.add(groupColour);
        }
        selectedGroupPoints.doRepack();       
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
        pointCloud.doRepack();
    }

    public void toggleUnsegmented() {
        showUnsegmented = !showUnsegmented;
        flagSelectedPoints(selectedFlags);
        pointCloud.doRepack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "groupSelected":
                findSelectedGroup();
                packSelectedGroupPoints();
                break;
            case "pointReleased":
                selectedGroup = null;
                break;
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (selectedGroup == null) {
            pointCloud.drawSelected(renderScreen, colours.get(selectedColour), selectedFlags, numSelected, GL.GL_POINTS);
        } else {
            selectedGroupPoints.draw(renderScreen, selectedGroupColours, GL.GL_POINTS);
        }
    }

    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        selectedFlags = new boolean[pointCloud.size()];
        flagSelectedPoints(selectedFlags);
        colours.add(new IntensityColourArray(pointCloud));
        colours.add(new SegmenterColourArray(pointCloud, groupIds));
        System.out.println("Read " + pointCloud.size() + " points");
        System.out.println("Read " + groups.size() + " groups");
    }

    public void setColouring(int colourIndex) {
        selectedColour = colourIndex;
        pointCloud.doRepack();
    }
    
    @Override
    public void freeVBOs() {
        super.freeVBOs();
        this.selectedGroupPoints.freeVBO(renderScreen);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        this.groupIds = null;
        this.groups = null;
        this.inputReader = null;
        this.selectedFlags = null;
        this.selectedGroup = null;
        this.selectedGroupColours = null;
        this.selectedGroupPoints = null;
    }

}
