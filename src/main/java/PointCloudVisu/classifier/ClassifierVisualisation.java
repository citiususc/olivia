package PointCloudVisu.classifier;

import static PointCloudVisu.classifier.ClassifierLabels.*;
import PointCloudVisu.segmenter.SegmenterColourArray;
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

/**
 * This class manages the visualisation of classified point clouds
 * 
 * @author jorge.martinez.sanchez
 */
public class ClassifierVisualisation extends Visualisation<PointI,PointArray<PointI>> implements ActionListener {

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

    public ClassifierVisualisation(OpenGLScreen renderScreen) {
        super(renderScreen);
        super.renderScreen.addActionListener(this);
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
    }

    public void setGroups(ClassifierGroupArray<ClassifierGroup> groups) {
        this.groups = groups;
    }

    public void showClass(int classId) {
        if (!activeClasses.contains(classId)) {
            activeClasses.add(classId);
            flagSelectedPoints(selectedFlags);
            points.repack();
        }
    }

    public void hideClass(int classId) {
        if (activeClasses.contains(classId)) {
            activeClasses.remove(activeClasses.indexOf(classId));
            flagSelectedPoints(selectedFlags);
            points.repack();
        }
    }

    public void flagSelectedPoints(boolean[] selectedFlags) {
        numSelected = 0;
        Arrays.fill(selectedFlags, false);
        for (ClassifierGroup group : groups) {
            if (activeClasses.contains(group.getType()) || (activeClasses.contains(OTHER) && !LABELS.contains(group.getType()))) {
                for (Object point : group.getPoints()) {
                    selectedFlags[((Point3D) point).getId()] = true;
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
        super.textInfo = selectedGroup.getDescriptionAsHTML();
    }

    public void packSelectedGroupPoints() {
        if (selectedGroup == null) {
            return;
        }
        selectedGroupPoints.clear();
        selectedGroupPoints.addAll(selectedGroup.getPoints());
        selectedGroupColours = new ColourArray(selectedGroupPoints) {
        };
        PointColour groupColour = ClassifierColourArray.getGroupColour(selectedGroup.getType());
        for (int i = 0; i < selectedGroupPoints.size(); i++) {
            selectedGroupColours.add(groupColour);
        }
        selectedGroupPoints.repack();
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
            case "groupReleased":
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
        colours.add(new SegmenterColourArray(points, groups));
        colours.add(new ClassifierColourArray(points, groups));
        System.out.println("Read " + points.size() + " points");
        System.out.println("Read " + groups.size() + " groups");
    }

    public ClassifierGroup getSelectedGroup() {
        Point3D point = renderScreen.getSelectedPoint();
        return groups.getGroupFromPoint(point);
    }

    public void setIntensityColouring() {
        selectedColour = 0;
        points.repack();
        System.out.println("set to Intensity colouring");
    }

    public void setSegmenterColouring() {
        selectedColour = 1;
        points.repack();
        System.out.println("set to segmentation colouring");
    }

    public void setClassificationColouring() {
        selectedColour = 2;
        points.repack();
        System.out.println("set to classification colouring");
    }
}
