package PointCloudVisu.basic;

import PointCloudVisu.extended.IntensityColourArray;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.extended.PointI;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.Visualisation;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.extended.RandomColourArray;
import com.jogamp.opengl.GL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the visualisation of point clouds
 *
 * @author oscar.garcia
 */
public class BasicVisualisation extends Visualisation<PointI, PointArray<PointI>> {

    BasicInputReader inputReader;
    ArrayList<ColourArray> colours;
    int selectedColours;

    public BasicVisualisation(OpenGLScreen screen) {
        super(screen);
        inputReader = new BasicInputReader();
        colours = new ArrayList<>();
        selectedColours = 0;      
    }

    @Override
    public void draw() {
        points.draw(renderScreen, colours.get(selectedColours), GL.GL_POINTS);
    }

    public void setIntensityColouring() {
        selectedColours = 0;
        points.repack();
        System.out.println("set to Intensity colouring");
    }

    public void setRandomColouring() {
        selectedColours = 1;
        points.repack();
        System.out.println("set to random colouring");
    }

    @Override
    public void readFromFile(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        colours.add(new IntensityColourArray(points));
        //colours.add(new RandomColourArray(points));
    }
}
