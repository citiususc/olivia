package PointCloudVisu.scanlines;

import PointCloudVisu.core.Visualisation;
import PointCloudVisu.core.data.Group;
import PointCloudVisu.core.data.GroupArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.render.colours.PointColour;
import PointCloudVisu.extended.IntensityColourArray;
import PointCloudVisu.extended.PointS;
import com.jogamp.opengl.GL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class ScanlinesVisualisation extends Visualisation<PointS,PointArray<PointS>> implements ActionListener {

    protected GroupArray<Group> groups;
    protected ScanlinesInputReader inputReader;
    protected ArrayList<ColourArray> colours;
    protected int selectedColour;
    protected Group selectedScan;
    protected PointArray selectedScanPoints;
    protected ColourArray selectedScanColours;

    public ScanlinesVisualisation(OpenGLScreen renderScreen) {
        super(renderScreen);
        super.renderScreen.addActionListener(this);
        groups = new GroupArray<>();
        colours = new ArrayList<>();
        inputReader = new ScanlinesInputReader();
        selectedColour = 2;
        selectedScan = null;
        selectedScanPoints = new PointArray();
    }

    public void setGroups(GroupArray groups) {
        this.groups = groups;
    }

    public void findSelectedGroup() {
        selectedScan = groups.getGroupFromPoint(renderScreen.getSelectedPoint());
        if (selectedScan == null) {
            return;
        }
        super.textInfo = selectedScan.getDescriptionAsHTML();
    }

    public void packSelectedGroupPoints() {
        if (selectedScan == null) {
            return;
        }
        selectedScanPoints.clear();
        selectedScanPoints.addAll(selectedScan.getPoints());
        selectedScanColours = new ColourArray(selectedScanPoints) {
        };
        PointColour groupColour = new PointColour(1f, 1f, 1f);    // @todo Use real colour
        for (int i = 0; i < selectedScanPoints.size(); i++) {
            selectedScanColours.add(groupColour);
        }
        selectedScanPoints.repack();
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
                    selectedScan = null;
                }
                break;
        }
    }

    @Override
    public void draw() {
        if (selectedScan == null) {
            points.draw(renderScreen, colours.get(selectedColour), GL.GL_POINTS);
        } else {
            selectedScanPoints.draw(renderScreen, selectedScanColours, GL.GL_POINTS);
        }
    }

    @Override
    public void readFromFile(String file) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(file, this);
        colours.add(new IntensityColourArray(points));
        colours.add(new EdgesColourArray(points, groups));
        colours.add(new ScanlinesColourArray(points, groups));
    }

    public void setIntensityColouring() {
        selectedColour = 0;
        points.repack();
        System.out.println("set to Intensity colouring");
    }

    public void setEdgesColouring() {
        selectedColour = 1;
        points.repack();
        System.out.println("set to edge colouring");
    }

    public void setRandomColouring() {
        selectedColour = 2;
        points.repack();
        System.out.println("set to random colouring");
    }
}
