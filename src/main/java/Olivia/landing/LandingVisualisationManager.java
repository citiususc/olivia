/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.landing;

import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ColourArray;
import Olivia.extended.IntensityColourArray;
import Olivia.extended.PointI;
import com.jogamp.opengl.GL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
//public class LandingVisualisationManager extends VisualisationManager<LandingVisualisationManager,PointI,PointArray<PointI>,OpenGLScreen,Overlay<LandingVisualisationManager>,OverlayArray<Overlay<LandingVisualisationManager>,LandingVisualisationManager>,LandingControlPane> implements ActionListener{
public class LandingVisualisationManager extends VisualisationManager<LandingVisualisationManager,PointI,PointArray<PointI>,OpenGLScreen,LandingControlPane> {
    
    public static final String APPROX_OBSTACLES_TXT = "obstacles.txt";
    
    protected LandingGroupArray groups;
    protected LandingInputReader inputReader;
    protected LandingApproachOverlayArray approaches;
    
    /*
     * Options
     */
    protected ArrayList<ColourArray> colours;
    protected int selectedColours;
    protected boolean showApproximations;
    
    public LandingVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String filePath) throws IOException,FileNotFoundException{
        super(id, gui, isStereo3D);
        name = "Landing " + id;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        //renderScreen.addActionListener(this);
        System.out.println("Creating Points for " + name);
        colours = new ArrayList<>();
        selectedColours = 1;
        inputReader = new LandingInputReader();
        groups = new LandingGroupArray();
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new LandingControlPane(this);
        System.out.println("Creating Approximations for " + name);
        approaches = new LandingApproachOverlayArray(this);
        System.out.println("Opening directory: " + filePath + " for" + name);
        readFromFiles(filePath);
        /*
        try {
            Path path = Paths.get(filePath +"/"+APPROX_OBSTACLES_TXT);
            approaches.readFromFile(path);
            System.out.println("Approach data file read");
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot read File" + e);
            System.out.println("There is no approach data");
        }
        catch (IOException e) {
            System.out.println("Cannot read from File" + e);
            System.out.println("There is no approach data");
        }
        */
        showApproximations = false;
        approaches.listenToActionsOnScreen();
        approaches.setSelectingCurrentByMouse(true);
        approaches.setDrawCurrent(true);
        System.out.println(name + " created");
    }
    
    
    public void toggleApproximations() {
        showApproximations = !showApproximations;
        overlays.select(0,showApproximations);
    }
    
    
    @Override
    public void draw() {
        super.draw();
        this.pointCloud.draw(renderScreen, colours.get(selectedColours), GL.GL_POINTS);
    }

    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        //super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "pointSelected":
                    if (approaches.setOverlayAtPointAsCurrent(renderScreen.getSelectedPoint())) {
                        gui.logIntoConsole("Selected new approach");
                    } else {
                        gui.logIntoConsole("No approach found at that point");
                    }
                break;
        }
    }
    */

    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        //essential
        inputReader.readFromFiles(filePath, this);
        colours.add(new IntensityColourArray(pointCloud));
        colours.add(new LandingColourArray(pointCloud, groups));
        colours.add(new LandingIntensityColourArray(pointCloud, groups));
        System.out.println("points " + pointCloud.size() + " " + colours.get(0).size());
        //camera.centerAt(pointCloud.getCentreCurrent());
        //System.out.println("Number of Groups " + groups.size() + " " + groups.get(28).getPointCloud().size());

        //non essential
        
        try {
            Path path = Paths.get(filePath +"/"+APPROX_OBSTACLES_TXT);
            approaches.readFromFile(path);
            this.addOverlay(approaches);
            overlays.select(0,showApproximations);
            System.out.println("Approaches File read, " + approaches.size() + " approaches read");
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot read from File" + e);
        }
    }
    
    public void setIntensityColouring() {
        this.selectedColours = 0;
        this.pointCloud.doRepack();
        System.out.println("set to Intensity colouring");
    }

    public void setLandingColouring() {
        this.selectedColours = 1;
        this.pointCloud.doRepack();
        System.out.println("set to landing colouring");
    }
    
    public void setLandingIntensityColouring() {
        this.selectedColours = 2;
        this.pointCloud.doRepack();
        System.out.println("set to landing intensitycolouring");
    }

    public void setGroups(LandingGroupArray groups) {
        this.groups = groups;
    }
    
    public LandingGroup getSelectedGroup() {
        Point3D_id point = renderScreen.getSelectedPoint();
        return groups.getGroupFromPoint(point);
    }
    
}
