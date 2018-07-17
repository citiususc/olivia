package PointCloudVisu.standard;

import PointCloudVisu.extended.IntensityColourArray;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.Visualisation;
import PointCloudVisu.extended.StandardClassificationColourArray;
import PointCloudVisu.extended.PointStandard;
import PointCloudVisu.extended.StandardRGBColourArray;
import PointCloudVisu.extended.StandardReturnColourArray;
import PointCloudVisu.extended.StandardScanlineColourArray;
import com.jogamp.opengl.GL;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the visualisation of point clouds
 *
 * @author oscar.garcia
 */
public class StandardVisualisation extends Visualisation<PointStandard,StandardPointArray<PointStandard>> {

    StandardInputReader inputReader;
    ArrayList<ColourArray> colours;
    protected int selectedColours, intensityIndex, returnsIndex, scanlineIndex, classificationIndex, rgbIndex;

    public StandardVisualisation(OpenGLScreen screen) {
        super(screen);
        inputReader = new StandardInputReader();
        colours = new ArrayList<>();
        selectedColours = 0;
        points = new StandardPointArray();
    }

    @Override
    public void draw() {
        points.draw(renderScreen, colours.get(selectedColours), GL.GL_POINTS);
    }

    public void setIntensityColouring() {
        if (intensityIndex == -1) return;
        selectedColours = intensityIndex;
        points.repack();
        System.out.println("set to Intensity colouring");
    }

    public void setRGBColouring() {
        if (rgbIndex == -1) return;
        selectedColours = rgbIndex;
        points.repack();
        System.out.println("set to RGB colouring");
    }
    
    public void setClassificationColouring() {
        if (classificationIndex == -1) return;
        selectedColours = classificationIndex;
        points.repack();
        System.out.println("set to Classification colouring");
    }
    
    public void setReturnNumberColouring() {
        if (returnsIndex == -1) return;
        selectedColours = returnsIndex;
        points.repack();
        System.out.println("set to Return colouring");
    }
    
    public void setScanlineColouring() {
        if (scanlineIndex == -1) return;
        selectedColours = scanlineIndex;
        points.repack();
        System.out.println("set to Return colouring");
    }

    @Override
    public void readFromFile(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        intensityIndex = returnsIndex = scanlineIndex = classificationIndex = rgbIndex = -1;
        int counter=0;
        if(points.isHave_intensity()){
            colours.add(new IntensityColourArray(points));
            intensityIndex = counter; counter++;
            System.out.println("Loaded intensity colours");
        }
        if(points.isHave_RGB()){
            colours.add(new StandardRGBColourArray(points));
            rgbIndex = counter; counter++;
            System.out.println("Loaded RGB colours");
        }
        if(points.isHave_classification()){
            colours.add(new StandardClassificationColourArray(points));
            classificationIndex = counter; counter++;
            System.out.println("Loaded Classification colours");
        }
        if(points.isHave_returns()){
            colours.add(new StandardReturnColourArray(points));
            returnsIndex = counter; counter++;
            System.out.println("Loaded Return Number colours");
        }
        if(points.isHave_scanlines()){
            colours.add(new StandardScanlineColourArray(points));
            scanlineIndex = counter; counter++;
            System.out.println("Loaded Scanline colours");
        }
    }
    
//    public void readFromFile(File file) throws FileNotFoundException, IOException {
//        inputReader.readFromFiles(file, this);
//        intensityIndex = returnsIndex = scanlineIndex = classificationIndex = rgbIndex = -1;
//        int counter=0;
//        if(points.isHave_intensity()){
//            colours.add(new IntensityColourArray(points));
//            intensityIndex = counter; counter++;
//            System.out.println("Loaded intensity colours");
//        }
//        if(points.isHave_RGB()){
//            colours.add(new StandardRGBColourArray(points));
//            rgbIndex = counter; counter++;
//            System.out.println("Loaded RGB colours");
//        }
//        if(points.isHave_classification()){
//            colours.add(new StandardClassificationColourArray(points));
//            classificationIndex = counter; counter++;
//            System.out.println("Loaded Classification colours");
//        }
//        if(points.isHave_returns()){
//            colours.add(new StandardReturnColourArray(points));
//            returnsIndex = counter; counter++;
//            System.out.println("Loaded Return Number colours");
//        }
//        if(points.isHave_scanlines()){
//            colours.add(new StandardScanlineColourArray(points));
//            scanlineIndex = counter; counter++;
//            System.out.println("Loaded Scanline colours");
//        }
//    }
    
}
