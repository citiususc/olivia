package Olivia.classifier;

import static Olivia.classifier.ClassifierLabels.*;
import Olivia.core.Overlay;
import Olivia.core.OverlayArray;
import Olivia.segmenter.SegmenterColourArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;
import Olivia.extended.IntensityColourArray;
import com.jogamp.opengl.GL2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class manages the visualisation of classified point clouds
 * 
 * @author jorge.martinez.sanchez
 */
//public class ClassifierVisualisationManager extends VisualisationManager<ClassifierVisualisationManager, PointI, PointArray<PointI>,OpenGLScreen,Overlay<ClassifierVisualisationManager>,OverlayArray<Overlay<ClassifierVisualisationManager>,ClassifierVisualisationManager>,ClassifierControlPane> implements ActionListener {
public class ClassifierVisualisationManager extends VisualisationManager<ClassifierVisualisationManager, PointI, PointArray<PointI>,OpenGLScreen,ClassifierControlPane> implements ActionListener {

    private ClassifierGroupArray<ClassifierGroup> groups;
    protected ClassifierInputReader inputReader;
    protected ArrayList<ColourArray> colours;
    protected int selectedColour;
    protected int numSelected;
    protected ArrayList<Integer> activeClasses;
    protected boolean[] selectedFlags;
    protected ClassifierGroup selectedGroup;
    protected PointArray selectedGroupPoints;
    protected ColourArray selectedGroupColours;
    
    /**
     * Text that a visualisation can provide
     */
    protected String textInfo;

    public ClassifierVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String filePath) {
        super(id, gui, isStereo3D);
        this.name = "Classification " + id;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        renderScreen.addActionListener(this);
        System.out.println("Creating Visualisation for " + name);
        colours = new ArrayList<>();
        inputReader = new ClassifierInputReader();
        groups = new ClassifierGroupArray<>();
        selectedColour = 2;
        activeClasses = new ArrayList();
        activeClasses.add(GROUND);
        activeClasses.add(LOW_VEG);
        activeClasses.add(MEDIUM_VEG);
        activeClasses.add(HIGH_VEG);
        activeClasses.add(BUILDING);
        activeClasses.add(WATER);
        activeClasses.add(ROAD);
        selectedGroup = null;
        selectedGroupPoints = new PointArray();
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new ClassifierControlPane(this);
    }

    public void setGroups(ClassifierGroupArray<ClassifierGroup> groups) {
        this.groups = groups;
    }

    public void showClass(int classId) {
        if (!activeClasses.contains(classId)) {
            activeClasses.add(classId);
            flagSelectedPoints(selectedFlags);
            pointCloud.repack();
        }
    }

    public void hideClass(int classId) {
        if (activeClasses.contains(classId)) {
            activeClasses.remove(activeClasses.indexOf(classId));
            flagSelectedPoints(selectedFlags);
            pointCloud.repack();
        }
    }

    public void flagSelectedPoints(boolean[] selectedFlags) {
        numSelected = 0;
        Arrays.fill(selectedFlags, false);
        for (ClassifierGroup group : groups) {
            if (activeClasses.contains(group.getType()) || (activeClasses.contains(OTHER) && !LABELS.contains(group.getType()))) {
                for (Object point : group.getPoints()) {
                    selectedFlags[((Point3D_id) point).getId()] = true;
                    numSelected++;
                }
            }
        }
    }

    public void findSelectedGroup() {
        selectedGroup = groups.getGroupFromPoint(renderScreen.getSelectedPoint());
        if (selectedGroup == null) {
            return;
        }
        textInfo = selectedGroup.getDescriptionAsHTML();
    }

    public void packSelectedGroupPoints() {
        if (selectedGroup == null) {
            return;
        }
        renderScreen.animatorPause();
        selectedGroupPoints.clear();
        selectedGroupPoints.freeVBO(renderScreen);
        selectedGroupPoints.addAll(selectedGroup.getPoints());
        selectedGroupColours = new ColourArray(selectedGroupPoints) {
        };
        PointColour groupColour = ClassifierColourArray.getGroupColour(selectedGroup.getType());
        for (int i = 0; i < selectedGroupPoints.size(); i++) {
            selectedGroupColours.add(groupColour);
        }
        selectedGroupPoints.repack();
        renderScreen.animatorResume();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "groupSelected":
                findSelectedGroup();
                packSelectedGroupPoints();
                break;
            case "groupReleased":
                selectedGroup = null;
                break;
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (selectedGroup == null) {
            pointCloud.drawSelected(renderScreen, colours.get(selectedColour), selectedFlags, numSelected, GL2.GL_POINTS);
        } else {
            selectedGroupPoints.draw(renderScreen, selectedGroupColours, GL2.GL_POINTS);
        }
    }

    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        selectedFlags = new boolean[pointCloud.size()];
        flagSelectedPoints(selectedFlags);
        colours.add(new IntensityColourArray(pointCloud));
        colours.add(new SegmenterColourArray(pointCloud, groups));
        colours.add(new ClassifierColourArray(pointCloud, groups));
        System.out.println("Read " + pointCloud.size() + " points");
        System.out.println("Read " + groups.size() + " groups");
    }

    public ClassifierGroup getSelectedGroup() {
        Point3D_id point = renderScreen.getSelectedPoint();
        return groups.getGroupFromPoint(point);
    }

    public void setIntensityColouring() {
        selectedColour = 0;
        pointCloud.repack();
        System.out.println("set to Intensity colouring");
    }

    public void setSegmenterColouring() {
        selectedColour = 1;
        pointCloud.repack();
        System.out.println("set to segmentation colouring");
    }

    public void setClassificationColouring() {
        selectedColour = 2;
        pointCloud.repack();
        System.out.println("set to classification colouring");
    }
    
    @Override
    public void freeVBOs() {
        super.freeVBOs();
        this.selectedGroupPoints.freeVBO(renderScreen);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        this.activeClasses = null;
        this.colours = null;
        this.groups = null;
        this.inputReader = null;
        this.selectedFlags = null;
        this.selectedGroup = null;
        this.selectedGroupPoints = null;
        this.selectedGroupColours = null;
        this.textInfo = null;
    }
}
