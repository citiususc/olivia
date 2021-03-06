/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.RenderOptions;
import Olivia.core.render.colours.PointColour;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class CircleOverlayArray<VM extends VisualisationManager> extends OverlayArray<CircleOverlay<VM>,VM> {
    
    public CircleOverlayArray(VM visualisationManager, String name) {
        super(visualisationManager, name);
    }
    
    public CircleOverlayArray(VM visualisationManager) {
        this(visualisationManager, "circles");
    }
    
    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            System.out.println("Read " + lines.size());
            for(i=0;i<lines.size();i++){
                CircleOverlay circle = new CircleOverlay(this.visualisationManager);
                circle.parseLineAsCircle(lines.get(i), delimiter);
                this.add(circle);
            }
        }catch (IOException ex) {
            System.out.println("Error Reading Overlay");
        }
    }
    
}
