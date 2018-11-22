package Olivia.basic;

import Olivia.core.OverlayArray;
import Olivia.extended.IntensityColourArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.extended.PointI;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.VisualisationManager;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.extended.PointStandard;
import Olivia.extended.RandomColourArray;
import Olivia.standard.StandardPointArray;
import Olivia.standard.StandardVisualisationControlPane;
import Olivia.standard.StandardVisualisationManager;
import com.jogamp.opengl.GL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the visualisation of point clouds
 *
 * @author oscar.garcia
 */
public class BasicVisualisationManager extends VisualisationManager<BasicVisualisationManager, PointI, PointArray<PointI>,OpenGLScreen,BasicVisualisationControlPane> {

    BasicInputReader inputReader;
    ArrayList<ColourArray> colours;
    int selectedColours;

    public BasicVisualisationManager(int id, MainFrame gui, boolean isStereo3D) {
        super(id, gui, isStereo3D);
        this.name = "Basic " + id;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        System.out.println("Creating Visualisation for " + name);
        inputReader = new BasicInputReader();
        colours = new ArrayList<>();
        selectedColours = 0;
        pointCloud = new PointArray();
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new BasicVisualisationControlPane(this);   
    }

    @Override
    public void draw() {
        super.draw();
        pointCloud.draw(renderScreen, colours.get(selectedColours), GL.GL_POINTS);
    }

    public void setIntensityColouring() {
        selectedColours = 0;
        pointCloud.doRepack();
        System.out.println("set to Intensity colouring");
    }

    public void setRandomColouring() {
        selectedColours = 1;
        pointCloud.doRepack();
        System.out.println("set to random colouring");
    }

    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        System.out.println("Opening directory: " + filePath + " for" + name);
        inputReader.readFromFiles(filePath, this);
        colours.add(new IntensityColourArray(pointCloud));
    }
    
    @Override
    public void destroy() {
        super.destroy();
        this.colours = null;
        this.inputReader = null;
    }
}
