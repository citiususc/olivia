package Olivia.scanlines;

import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Group;
import Olivia.core.data.GroupArray;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.IntensityColourArray;
import Olivia.extended.PointS;
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
public class ScanlinesVisualisationManager extends VisualisationManager<ScanlinesVisualisationManager, PointS, PointArray<PointS>,OpenGLScreen,ScanlinesVisualisationControlPane>  implements ActionListener {

    protected GroupArray<Group> groups;
    protected ScanlinesInputReader inputReader;
    protected ArrayList<ColourArray> colours;
    protected int selectedColour;
    protected Group selectedScan;
    protected PointArray selectedScanPoints;
    protected ColourArray selectedScanColours;
    /**
     * Text that a visualisation can provide
     */
    protected String textInfo;
    
    public ScanlinesVisualisationManager(int id, MainFrame gui, boolean isStereo3D){
        this(id, gui, isStereo3D, "Scanlines " + id);
    }

    public ScanlinesVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String name) {
        super(id, gui, isStereo3D);
        this.name = name;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        renderScreen.addActionListener(this);
        groups = new GroupArray<>();
        colours = new ArrayList<>();
        inputReader = new ScanlinesInputReader();
        selectedColour = 2;
        selectedScan = null;
        selectedScanPoints = new PointArray();
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new ScanlinesVisualisationControlPane(this);
    }

    public void setGroups(GroupArray groups) {
        this.groups = groups;
    }

    public void findSelectedGroup() {
        selectedScan = groups.getGroupFromPoint(renderScreen.getSelectedPoint());
        if (selectedScan == null) {
            return;
        }
        textInfo = selectedScan.getDescriptionAsHTML();
    }

    public void packSelectedGroupPoints() {
        if (selectedScan == null) {
            return;
        }
        selectedScanPoints.clear();
        selectedScanPoints.freeVBO(renderScreen);
        selectedScanPoints.addAll(selectedScan.getPoints());
        selectedScanColours = new ColourArray(selectedScanPoints) {
        };
        PointColour groupColour = new PointColour(1f, 1f, 1f);    // @todo Use real colour
        for (int i = 0; i < selectedScanPoints.size(); i++) {
            selectedScanColours.add(groupColour);
        }
        selectedScanPoints.doRepack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "groupSelected":
                findSelectedGroup();
                packSelectedGroupPoints();
                break;
            case "groupReleased":
                selectedScan = null;
                break;
        }
    }

    @Override
    public void draw() {
        if (selectedScan == null) {
            pointCloud.draw(renderScreen, colours.get(selectedColour), GL.GL_POINTS);
        } else {
            selectedScanPoints.draw(renderScreen, selectedScanColours, GL.GL_POINTS);
        }
        overlays.draw();
    }


    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        colours.add(new IntensityColourArray(pointCloud));
        colours.add(new EdgesColourArray(pointCloud, groups));
        colours.add(new ScanlinesColourArray(pointCloud, groups));
    }

    public void setIntensityColouring() {
        selectedColour = 0;
        pointCloud.doRepack();
        System.out.println("set to Intensity colouring");
    }

    public void setEdgesColouring() {
        selectedColour = 1;
        pointCloud.doRepack();
        System.out.println("set to edge colouring");
    }

    public void setRandomColouring() {
        selectedColour = 2;
        pointCloud.doRepack();
        System.out.println("set to random colouring");
    }
    

    @Override
    public void freeVBOs() {
        super.freeVBOs();
        this.selectedScanPoints.freeVBO(renderScreen);
    }

    @Override
    public void destroy() {
        super.destroy();
        this.colours = null;
        this.groups = null;
        this.inputReader = null;
        this.selectedScan = null;
        this.selectedScanColours = null;
        this.selectedScanPoints = null;
    }

}
