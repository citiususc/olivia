/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.render.OpenGLScreen;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class DensitiesOverlay<VM extends VisualisationManager> extends OverlayArray<Overlay<VM>,VM> {
    
    public DensitiesOverlay(VM visualisationManager, String name) {
        super(visualisationManager, name);
        this.listenToActionsOnScreen();
        this.setSelectingCurrentByMouse(true);
    }
    
    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i;
            String[] cols;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            System.out.println("Read " + lines.size());
            System.out.println(lines.get(0));
            for(i=1;i<lines.size();i++){
                cols = lines.get(i).split("\t");
                double x = Double.parseDouble(cols[0]);
                double y = Double.parseDouble(cols[1]);
                double z = Double.parseDouble(cols[2]);
                Point3D point = new Point3D(x, y, z);
                TextOverlay label = new TextOverlay(this.visualisationManager,point,"2D:" + cols[3] + "   3D:" + cols[4] );
                label.setRasterPos(0.0f,1.0f, 0.5f);
                label.setAlwaysOnTop(true);
                this.add(label);
            }
        }catch (IOException ex) {
            System.out.println("Error Reading Overlay");
        }
        System.out.println("Densities Overlay read");
    }

    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        super.drawShapeCurrent(renderScreen);
    }
    
}
