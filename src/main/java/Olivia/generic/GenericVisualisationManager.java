/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.generic;

import Olivia.core.Olivia;
import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ClassificationPalette;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.GreyScalePalette;
import Olivia.core.render.colours.PointColour;
import Olivia.core.render.colours.PointColourPalette;
import Olivia.core.render.colours.RGBLoopColourPalette;
import Olivia.exec.ExecutionMenu;
import Olivia.extended.PaletteColourArray;
import Olivia.extended.PaletteGradientColourArray;
import Olivia.extended.RandomPaletteColourArray;
import Olivia.extended.SingleColourArray;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
public class GenericVisualisationManager extends VisualisationManager<GenericVisualisationManager, Point3D_id, GenericPointArray<Point3D_id>,OpenGLScreen,GenericVisualisationControlPane> {

    GenericInputReader inputReader;
    ArrayList<ColourArray> colours;
    protected int selectedColours,decimation;
    
    public GenericVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String filePath, String name, int decimation) throws IOException,FileNotFoundException{
        super(id, gui, isStereo3D, name);
        this.decimation = decimation;
        this.name = name;
        Olivia.textOutputter.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        Olivia.textOutputter.println("Creating Visualisation for " + name);
        inputReader = new GenericInputReader();
        colours = new ArrayList<>();
        selectedColours = 0;
        pointCloud = new GenericPointArray();
        Olivia.textOutputter.println("Opening directory: " + filePath + " for" + name);
        this.readFromFiles(filePath);
        Olivia.textOutputter.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        Olivia.textOutputter.println("Creating Control Pane for " + name);
        controlPane = new GenericVisualisationControlPane(this);
        ExecutionMenu executionMenu = new ExecutionMenu(this);
        this.jMenu = executionMenu;
        this.jMenu.setText(this.name);
        executionMenu.setDetectAllowed(false);
        jMenu.setEnabled(true);
        colours.add(new SingleColourArray(pointCloud,new PointColour(1.0f,1.0f,1.0f)));
        controlPane.AddColour("White");
        //colours.add(new SingleColourArray(pointCloud,new PointColour(1.0f,0.0f,0.0f)));
        //controlPane.AddColour("Red");
        for(int i=0; i< pointCloud.numberOfFields; i++){
            if ((pointCloud.getType(i) == GenericPointArray.COLOUR)||(pointCloud.getType(i) == GenericPointArray.COLOUR_INT)){
                colours.add(new GenericColourArray(pointCloud.getFieldValues(i)));
                controlPane.AddColour(pointCloud.getNames().get(i));
            }
        }
    }
    
    public GenericVisualisationManager(int id, MainFrame gui, boolean isStereo3D, String filePath, String name) throws IOException,FileNotFoundException{
        this(id, gui, isStereo3D, filePath, name, 1);
    }

    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath,this);
    }
    
    @Override
    public void draw(){
        pointCloud.draw(renderScreen,colours.get(selectedColours));
        super.draw();
    }
    
    public void setColour(int i){
        if(i<colours.size()){
            selectedColours = i;
        }
        pointCloud.doRepack();
    }
    
    public void createPaletteColourFromField(int field, PointColourPalette palette, String name){
        if(field<0) return;
        if(field>=this.pointCloud.getNumberOfFields()) return;
        if(pointCloud.getType(field)!=GenericPointArray.INT) return;
        ArrayList<Integer> indices = pointCloud.getFieldValues(field);
        colours.add(new PaletteColourArray(pointCloud,indices, palette));
        controlPane.AddColour(name + " " + pointCloud.getNames().get(field));
    }
    
    public void createRandomColourFromField(int field){
        RGBLoopColourPalette palette = new RGBLoopColourPalette();
        palette.shuffle(1);
        createPaletteColourFromField(field, palette, "Random" );
    }
    
    public void createRGBColourFromField(int field){
        RGBLoopColourPalette palette = new RGBLoopColourPalette(0.49f);
        createPaletteColourFromField(field, palette, "RGB" );
    }
    
    public void createClassificationColourFromField(int field){
        createPaletteColourFromField(field, new ClassificationPalette(), "Classification" );
    }
    
    public void createGreyscaleColourFromField(int field){
        GreyScalePalette palette = new GreyScalePalette(0.1f,0.01f);
        createPaletteColourFromField(field, palette, "Geyscale" );
    }
    
    public void createGradientColourFromField(int field, PointColourPalette palette, String name){
        if(field<0) return;
        if(field>=this.pointCloud.getNumberOfFields()) return;
        if( (pointCloud.getType(field)!=GenericPointArray.INT)&
            (pointCloud.getType(field)!=GenericPointArray.FLOAT)&
            (pointCloud.getType(field)!=GenericPointArray.DOUBLE)
                ) return;
        ArrayList<Number> indices = pointCloud.getFieldValues(field);
        colours.add(new PaletteGradientColourArray(pointCloud,indices, palette));
        controlPane.AddColour(name + " " + pointCloud.getNames().get(field));
    }
    
    public void createGradientColourFromField(int field){
        RGBLoopColourPalette palette = new RGBLoopColourPalette();
        createGradientColourFromField(field,palette,"RGB gradient");
    }
    
    public void createGreyscaleGradientFromField(int field){
        GreyScalePalette palette = new GreyScalePalette(0.02f,0.05f);
        Olivia.textOutputter.println("Geyscale gradient " + palette.size());
        createGradientColourFromField(field,palette,"Geyscale gradient");
    }
    
}
