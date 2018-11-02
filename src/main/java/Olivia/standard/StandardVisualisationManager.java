/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.standard;

import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ColourArray;
import Olivia.extended.GradientColourArray;
import Olivia.extended.IntensityColourArray;
import Olivia.extended.PointStandard;
import Olivia.extended.StandardClassificationColourArray;
import Olivia.extended.StandardRGBColourArray;
import Olivia.extended.StandardReturnColourArray;
import Olivia.extended.StandardScanlineColourArray;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
//public class StandardVisualisationManager<V extends StandardVisualisation, R extends OpenGLScreen, O extends Overlay<V>, OA extends OverlayArray<O,V>, CP extends StandardVisualisationControlPane> extends VisualisationManager<V,R,O,OA,CP> {
//public class StandardVisualisationManager extends VisualisationManager<StandardVisualisationManager, PointStandard, StandardPointArray<PointStandard>,OpenGLScreen,Overlay<StandardVisualisationManager>,OverlayArray<Overlay<StandardVisualisationManager>,StandardVisualisationManager>,StandardVisualisationControlPane> {
public class StandardVisualisationManager extends VisualisationManager<StandardVisualisationManager, PointStandard, StandardPointArray<PointStandard>,OpenGLScreen,StandardVisualisationControlPane> {
     
    StandardInputReader inputReader;
    ArrayList<ColourArray> colours;
    protected int selectedColours, intensityIndex, returnsIndex, scanlineIndex, classificationIndex, rgbIndex, gradientIndex, decimation;
    
    
    public StandardVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String filePath, int decimation) throws IOException,FileNotFoundException{
        super(id, gui, isStereo3D);
        this.decimation = decimation;
        this.name = "Standard " + id;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        System.out.println("Creating Visualisation for " + name);
        inputReader = new StandardInputReader();
        colours = new ArrayList<>();
        selectedColours = 0;
        pointCloud = new StandardPointArray();
        System.out.println("Opening directory: " + filePath + " for" + name);
        this.readFromFiles(filePath,decimation);
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new StandardVisualisationControlPane(this);
    }
    
    public StandardVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String filePath) throws IOException,FileNotFoundException{
        this(id, gui, isStereo3D,filePath,1);
    }

    
    
    @Override
    public void draw(){
        pointCloud.draw(renderScreen,colours.get(selectedColours));
        super.draw();
    }
    
    public void readFromFiles(String filePath, int decimation) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this,decimation);
        intensityIndex = returnsIndex = scanlineIndex = classificationIndex = rgbIndex = gradientIndex = -1;
        int counter=0;
        if(pointCloud.isHave_intensity()){
            colours.add(new IntensityColourArray(pointCloud));
            intensityIndex = counter; counter++;
            colours.add(new GradientColourArray(pointCloud));
            gradientIndex = counter; counter++;
            System.out.println("Loaded intensity colours");
        }
        if(pointCloud.isHave_RGB()){
            colours.add(new StandardRGBColourArray(pointCloud));
            rgbIndex = counter; counter++;
            System.out.println("Loaded RGB colours");
        }
        if(pointCloud.isHave_classification()){
            colours.add(new StandardClassificationColourArray(pointCloud));
            classificationIndex = counter; counter++;
            System.out.println("Loaded Classification colours");
        }
        if(pointCloud.isHave_returns()){
            colours.add(new StandardReturnColourArray(pointCloud));
            returnsIndex = counter; counter++;
            System.out.println("Loaded Return Number colours");
        }
        if(pointCloud.isHave_scanlines()){
            colours.add(new StandardScanlineColourArray(pointCloud));
            scanlineIndex = counter; counter++;
            System.out.println("Loaded Scanline colours");
        }
    }

    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        readFromFiles(filePath, decimation);
    }
    
    public void setIntensityColouring() {
        if (intensityIndex == -1) return;
        selectedColours = intensityIndex;
        pointCloud.repack();
        System.out.println("set to Intensity colouring");
    }

    public void setRGBColouring() {
        if (rgbIndex == -1) return;
        selectedColours = rgbIndex;
        pointCloud.repack();
        System.out.println("set to RGB colouring");
    }
    
    public void setClassificationColouring() {
        if (classificationIndex == -1) return;
        selectedColours = classificationIndex;
        pointCloud.repack();
        System.out.println("set to Classification colouring");
    }
    
    public void setReturnNumberColouring() {
        if (returnsIndex == -1) return;
        selectedColours = returnsIndex;
        pointCloud.repack();
        System.out.println("set to Return colouring");
    }
    
    public void setScanlineColouring() {
        if (scanlineIndex == -1) return;
        selectedColours = scanlineIndex;
        pointCloud.repack();
        System.out.println("set to Scanline colouring");
    }

    public void setGradientColouring() {
        if (gradientIndex == -1) return;
        selectedColours = gradientIndex;
        pointCloud.repack();
        System.out.println("set to Gradient colouring");
    }
}
