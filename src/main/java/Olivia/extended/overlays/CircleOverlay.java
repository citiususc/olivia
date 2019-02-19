/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.render.CircleOptions;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.GL2;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class CircleOverlay<VM extends VisualisationManager> extends RenderableOverlay<VM> implements CircleOptions{
    
    protected double intRadius;
    protected double extRadius;
    protected int resolution;
    //protected ColourArray colours;
    protected boolean circunference;
    
    public CircleOverlay(VM visualisationManager, String name, int renderMode, int rasterMode, PointColour defaultColour, double intRadius, double extRadius, int resolution) {
        super(visualisationManager, name, renderMode, rasterMode, defaultColour);
        this.extRadius = extRadius;
        this.intRadius = intRadius;
        this.resolution = resolution;
        this.circunference = false;
    }
    
    public CircleOverlay(VM visualisationManager, String name, double intRadius, double extRadius, int resolution) {
        this(visualisationManager, name, GL2.GL_QUAD_STRIP, GL2.GL_FILL, new PointColour(1.0f,0.0f,0.0f),intRadius, extRadius, resolution);
    }

    public CircleOverlay(VM visualisationManager, String name) {
        this(visualisationManager, name, GL2.GL_QUAD_STRIP, GL2.GL_FILL, new PointColour(1.0f,0.0f,0.0f),0.0, 1.0, 10);
    }
    
    public CircleOverlay(VM visualisationManager) {
        this(visualisationManager, "circle", GL2.GL_QUAD_STRIP, GL2.GL_FILL, new PointColour(1.0f,0.0f,0.0f),0.0, 1.0, 10);
    }

    @Override
    public double getIntRadius() {
        return intRadius;
    }

    @Override
    public void setIntRadius(double intRadius) {
        this.intRadius = intRadius;
    }

    @Override
    public double getExtRadius() {
        return extRadius;
    }

    @Override
    public void setExtRadius(double extRadius) {
        this.extRadius = extRadius;
    }

    @Override
    public int getResolution() {
        return resolution;
    }

    @Override
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    @Override
    public boolean isCircunference() {
        return circunference;
    }

    @Override
    public void setCircunference(boolean circunference) {
        this.circunference = circunference;
    }
    
    protected void setBounds(Point3D point){
        this.bounds.setCentre(point);
        this.bounds.setMaxX(point.getX()+extRadius);
        this.bounds.setMinX(point.getX()-extRadius);
        this.bounds.setMaxY(point.getY()+extRadius);
        this.bounds.setMinY(point.getY()-extRadius);
        this.bounds.setMaxZ(point.getZ()+0.1);
        this.bounds.setMinZ(point.getZ()-0.1);
    }
    
    /*
    * X, Y, Z, radio_int, radio_ext, R, G, B, alpha
    * Not yet reading alpha
    */
    public void parseLineAsCircle(String line, String delimiter) throws IOException{
        Point3D point;
        PointColour colour;
        String[] cols;
        cols = line.split(delimiter);
        if(cols.length>=5){
            double x = Double.parseDouble(cols[0]);
            double y = Double.parseDouble(cols[1]);
            double z = Double.parseDouble(cols[2]);
            this.intRadius = Double.parseDouble(cols[3]);
            this.extRadius = Double.parseDouble(cols[4]);
            point = new Point3D(x, y, z);
            this.setBounds(point);
        }else{
            Olivia.textOutputter.println("Error Reading Circle");
            throw new IOException();
        }
        if(cols.length>=8){
            float r = Float.parseFloat(cols[5]);
            float g = Float.parseFloat(cols[6]);
            float b = Float.parseFloat(cols[7]);
            colour = new PointColour(r,g,b);
            this.defaultColour = colour;
        }
    }
    
        public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            Olivia.textOutputter.println("Read " + lines.size());
            parseLineAsCircle(lines.get(0), delimiter);
        }catch (IOException ex) {
            Olivia.textOutputter.println("Error Reading Circle");
        }
    }
    
    
    
    protected void drawCircunference(OpenGLScreen renderScreen) {
        double iter;
        double x = this.bounds.getCentre().getX();
        double y = this.bounds.getCentre().getY();
        double z = this.bounds.getCentre().getZ();

        double rot = 0;//(float) (Math.PI/1);

        /*renderScreen.getGl2().glLineWidth(3.0f);
        renderScreen.getGl2().glColor3f(0.3f, 0.3f, 0.3f);*/

        double step = (Math.PI * 2) / resolution;

        iter = 0;

        renderScreen.getGl2().glBegin(GL2.GL_LINE_LOOP);
        renderScreen.getGl2().glColor3f(defaultColour.getR(), defaultColour.getG(), defaultColour.getB());
        while (iter <= (float) (Math.PI * 2)) {
            //renderScreen.getGl2().glColor3f(colours.get(i).getR(), colours.get(i).getG(), colours.get(i).getB());
            renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * extRadius + x, Math.cos(iter + rot) * extRadius + y, z);
            iter = iter + step;
            //Olivia.textOutputter.println("ended at " + iter);
        }
        renderScreen.getGl2().glEnd();
        
    }
    
    
    protected void drawCircle(OpenGLScreen renderScreen) {
        double iter;
        double x = this.bounds.getCentre().getX();
        double y = this.bounds.getCentre().getY();
        double z = this.bounds.getCentre().getZ();

        double rot = 0;//(float) (Math.PI/1);

        /*renderScreen.getGl2().glLineWidth(3.0f);
        renderScreen.getGl2().glColor3f(0.3f, 0.3f, 0.3f);*/

        double step = (Math.PI * 2) / resolution;

        iter = 0;

        renderScreen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        renderScreen.getGl2().glBegin(GL2.GL_QUAD_STRIP);
        renderScreen.getGl2().glColor3f(defaultColour.getR(), defaultColour.getG(), defaultColour.getB());
        while (iter <= (float) (Math.PI * 2)) {
            //renderScreen.getGl2().glColor3f(colours.get(i).getR(), colours.get(i).getG(), colours.get(i).getB());
                renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * intRadius + x, Math.cos(iter + rot) * intRadius + y, z);
                renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * extRadius + x, Math.cos(iter + rot) * extRadius + y, z);
                iter = iter + step;
            //Olivia.textOutputter.println("ended at " + iter);
        }
        renderScreen.getGl2().glEnd();
        
    }
    

    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        //this.drawCircle(renderScreen);
        this.drawCircunference(renderScreen);
    }

    
    
    
}
