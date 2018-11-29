/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * THIS CLASS DOES NOT WORK EXACTLY AS INTENDED, RETHINK
 * An empty visualisation to be able to load overlays without an underlying point cloud
 * @author oscar.garcia
 */
//public class EmptyVisualisationManager extends VisualisationManager<EmptyVisualisationManager, Point3D_id, PointArray<Point3D>,OpenGLScreen,Overlay<EmptyVisualisationManager>,OverlayArray<Overlay<EmptyVisualisationManager>,EmptyVisualisationManager>,JPanel>{
public class EmptyVisualisationManager extends VisualisationManager<EmptyVisualisationManager, Point3D_id, PointArray<Point3D_id>,OpenGLScreen,JPanel>{

    public EmptyVisualisationManager(int id, MainFrame gui, boolean isStereo3D) {
        super(id, gui, isStereo3D);
        name = "Clear " + id;
        System.out.println("Creating Render Screen for " + name);
        renderScreen = new OpenGLScreen(this);
        System.out.println("Creating points for " + name + " (there are none)");
        pointCloud = new PointArray();
        System.out.println("Creating Overlay Array for " + name);
        overlays = new OverlayArray<>(this);
        System.out.println("Creating Control Pane for " + name);
        controlPane = new JPanel();
    }
    
    @Override
    public void readFromFiles(String filePath) throws FileNotFoundException, IOException {
        System.out.println("Nothing to read");
    }
    
}
