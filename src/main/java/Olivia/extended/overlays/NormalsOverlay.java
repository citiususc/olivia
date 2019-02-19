/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.PointColour;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class NormalsOverlay <VM extends VisualisationManager> extends VertexOverlay<VM>{
    public static float DEFAULT_LENGTH=0.5f;
    
    protected float length;
    
    public NormalsOverlay(VM visualisationM, String name) {
        super(visualisationM, name);
        length = DEFAULT_LENGTH;
    }
    
    // Convert from (point, normal components) to normal vector (point, point)
    /*
    protected void parseNormals() {
        double[] center = visualisationManager.getPoints().getCenterOfMassOriginal();
        visualisationManager.getGUI().logIntoConsole("Parsing normals...");
        for (int i = 0; i < vertices.size() - 1; i += 2) {
            Point3D_id p = (Point3D_id) vertices.get(i);
            Point3D_id v = (Point3D_id) vertices.get(i + 1);
            v.setX(v.getX() + center[0]);
            v.setY(v.getY() + center[1]);
            v.setZ(v.getZ() + center[2]);
            Point3D_id n = new Point3D_id(p.getX() + v.getX() * length, p.getY() + v.getY() * length, p.getZ() + v.getZ() * length);
            vertices.set(i + 1, n);
        }
    }
    */
    


    //Format
    // point x y z 
    // normal componets x y z
    
    @Override
    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i;
            double px,py,pz,vx,vy,vz;
            float r,g,b;
            Point3D_id point;
            PointColour colour;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            String[] cols;
            //double center[] = visualisationManager.getPoints().getCenterOfMassOriginal();
            Olivia.textOutputter.println("Read " + lines.size());
            if(Math.floorMod(lines.size(), 2)!=0) throw new IOException("Error Reading Overlay, not an even number of lines"); 
            for(i=0;i<lines.size()-1;i=i+2){
                //point
                cols = lines.get(i).split(delimiter);
                if(cols.length>=3){
                    px = Double.parseDouble(cols[0]);
                    py = Double.parseDouble(cols[1]);
                    pz = Double.parseDouble(cols[2]);
                    point = new Point3D_id(px, py, pz);
                }else{
                    Olivia.textOutputter.println("Error Reading Overlay");
                    throw new IOException("Error Reading Overlay");
                }
                if(cols.length==6){
                    r = Float.parseFloat(cols[3]);
                    g = Float.parseFloat(cols[4]);
                    b = Float.parseFloat(cols[5]);
                    colour = new PointColour(r,g,b);
                }else{
                    colour = defaultColour;
                }
                this.add(point, colour);
                //normal components
                cols = lines.get(i+1).split(delimiter);
                if(cols.length>=3){
                    vx = Double.parseDouble(cols[0]);
                    vy = Double.parseDouble(cols[1]);
                    vz = Double.parseDouble(cols[2]);
                }else{
                    Olivia.textOutputter.println("Error Reading Overlay");
                    throw new IOException("Error Reading Overlay");
                }
                if(cols.length==6){
                    r = Float.parseFloat(cols[3]);
                    g = Float.parseFloat(cols[4]);
                    b = Float.parseFloat(cols[5]);
                    colour = new PointColour(r,g,b);
                }else{
                    colour = defaultColour;
                }
                point = new Point3D_id(px + vx * length, py + vy * length, pz + vz * length);
                this.add(point, colour);
            }
        }catch (IOException ex) {
            Olivia.textOutputter.println("Error Reading Overlay");
        }
    }
    
    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        super.drawShape(GL_LINES,GL_LINE);
    }
    
}
