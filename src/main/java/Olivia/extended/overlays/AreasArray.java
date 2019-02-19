/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.Overlay;
import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.gui.controls.overlays.AreasOptionPanel;
import Olivia.core.render.colours.PointColour;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Overlay to read polygons with labels, designed to show areas
 * @author oscar.garcia
 * @param <VM> The visualisation class with which it works
 */
public class AreasArray<VM extends VisualisationManager> extends OverlayArray<Overlay<VM>,VM> implements Consumer<Path>{
    
    protected PointColour colour;
    protected int areasRead;
    protected int font;
    
    public AreasArray(VM visualisationManager) {
        super(visualisationManager);
        colour = new PointColour(0.0f,0.0f,1.0f);
        areasRead = 0;
        this.font = TextOverlay.DEFAULT_FONT;
    }

    public PointColour getColour() {
        return colour;
    }

    public void setColour(PointColour colour) {
        this.colour = colour;
    }

    public void setFont(int font) {
        this.font = font;
    }
    
    public void changeColour(PointColour colour) {
        this.colour.setRGB(colour);
        this.repack(visualisationManager.getRenderScreen());
    }
    
    public void readFromFiles(Path path) throws FileNotFoundException, IOException {
        name = path.subpath(path.getNameCount()-1, path.getNameCount()).toString();
        Stream<Path> files = Files.list(path);
        files.forEach(this);
        Olivia.textOutputter.println("Read " + areasRead + " areas");
    }
    
    public void readArea(Path path) throws FileNotFoundException, IOException {
        String line;
        String[] cols;
        List<String> lines = Files.readAllLines(path);
        if(lines.size()<5) throw new IOException("too few lines");
        TextOverlay label = new TextOverlay(visualisationManager,lines.get(0));
        List<String> polygon = lines.subList(1, lines.size()-1);
        String delimiter = "\t";
        for(int i=0;i<polygon.size();i++){
            line = polygon.get(i);
            cols = line.split(delimiter);
            if(cols.length <4) throw new IOException("too few colums");
            polygon.set(i, cols[0] + delimiter + cols[1] + delimiter + cols[2]);
        }
        line = polygon.get(0);
        cols = line.split(delimiter);
        //label.setCentre(new Point3D(Double.parseDouble(cols[0]),Double.parseDouble(cols[1]),Double.parseDouble(cols[2])));
        label.moveTo(new Point3D(Double.parseDouble(cols[0]),Double.parseDouble(cols[1]),Double.parseDouble(cols[2])));
        //Olivia.textOutputter.println("on creation" + label.getBounds().getCentre());
        label.setRasterPos(0.0f, 0.0f,0.0f);
        label.setColour(colour);
        label.setFont(font);
        VertexOverlay polygonOverlay = new VertexOverlay(visualisationManager,"Area " + areasRead);
        polygonOverlay.setDefaultColour(colour);
        polygonOverlay.parsePolygon(polygon, delimiter);
        this.add(label);
        this.add(polygonOverlay);
        areasRead++;
    }

    @Override
    public void accept(Path t) {
        try{
            readArea(t);
        }catch(Exception e){
            Olivia.textOutputter.println("Area could not be read " + e);
        }
    }
    
    @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        AreasOptionPanel optionsPanel = new AreasOptionPanel(this);
        this.optionPanels.add(optionsPanel);
    }
    
}
