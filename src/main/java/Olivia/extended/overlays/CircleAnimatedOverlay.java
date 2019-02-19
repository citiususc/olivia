/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.AnimatedOverlay;
import Olivia.core.VisualisationManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class CircleAnimatedOverlay<VM extends VisualisationManager> extends AnimatedOverlay<CircleOverlay<VM>,VM> {
    
    public CircleAnimatedOverlay(VM visualisationManager, String name) {
        this(visualisationManager, name, 1l);
    }
    
    public CircleAnimatedOverlay(VM visualisationManager) {
        this(visualisationManager, "Animated circle overlay", 1l);
    }
    
    public CircleAnimatedOverlay(VM visualisationManager, String name, Long speed) {
        super(visualisationManager, name);
        this.speed = speed;
    }
    
    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            Olivia.textOutputter.println("Read " + lines.size());
            for(i=0;i<lines.size();i++){
                CircleOverlay circle = new CircleOverlay(this.visualisationManager);
                circle.parseLineAsCircle(lines.get(i), delimiter);
                this.add(circle);
            }
        }catch (IOException ex) {
            Olivia.textOutputter.println("Error Reading Overlay");
        }
    }
    
}
