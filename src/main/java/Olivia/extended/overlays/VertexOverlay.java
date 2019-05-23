/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.gui.controls.overlays.VertexOptionsPanel;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.colours.ColourArray;
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
public class VertexOverlay<VM extends VisualisationManager> extends RenderableOverlay<VM>{

    
    protected PointArray<Point3D_id> vertices;
    protected ColourArray<PointColour> colours;
    
    public VertexOverlay(VM visualisationM, String name, int renderMode, int rasterMode, PointColour defaultColour) {
        super(visualisationM, name, renderMode, rasterMode, defaultColour);
        vertices = new PointArray<>();
        this.bounds = vertices.getBounds();
        colours = new ColourArray();
    }
    
    public VertexOverlay(VM visualisationM) {
        this(visualisationM, "VertexOverlay",GL2.GL_TRIANGLES, GL2.GL_LINE, new PointColour(1.0f,1.0f,1.0f));
    }
    
    public VertexOverlay(VM visualisationM, String name) {
        this(visualisationM, name,GL2.GL_TRIANGLES, GL2.GL_LINE, new PointColour(1.0f,1.0f,1.0f));
    }

    public String[] getSUPPORTED_PRIMITIVE_MODES_TEXT() {
        return SUPPORTED_PRIMITIVE_MODES_TEXT;
    }

    public String[] getSUPPORTED_RASTER_MODES_TEXT() {
        return SUPPORTED_RASTER_MODES_TEXT;
    }

    public String[] getSUPPORTED_COLOURS_TEXT() {
        return SUPPORTED_COLOURS_TEXT;
    }



    @Override
    public synchronized void moveTo(Point3D point) {
        //Olivia.textOutputter.println("Moving " + name + " to " + point + " with center at " + bounds.getCentre());
        vertices.moveTo(point);
        super.moveTo(point);
    }
    
    @Override
    public synchronized void displace(Point3D point) {
        //Olivia.textOutputter.println("Displacing " + name + " to " + point + " with center at " + bounds.getCentre());
        vertices.displace(point);
        super.displace(point); //To change body of generated methods, choose Tools | Templates.
    }
        
    public ColourArray getColours() {
        return colours;
    }

    public void setColours(ColourArray colours) {
        this.colours = colours;
    }
    
    public void add(Point3D_id point,PointColour colour){
        vertices.add(point);
        colours.add(colour);
        this.bounds = vertices.getBounds();
    }
    
    public void add(Point3D_id point){
        vertices.add(point);
        colours.add(this.defaultColour);
        this.bounds = vertices.getBounds();
    }

    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            Olivia.textOutputter.println("Read " + lines.size());
            for(i=0;i<lines.size();i++){
                parseLineAsVertex(lines.get(i), delimiter);
            }
            this.vertices.endedAddingOriginalPoints();
        }catch (IOException ex) {
            Olivia.textOutputter.println("Error Reading Overlay");
        }
    }
    
    public void parseLineAsVertex(String line, String delimiter) throws IOException{
        Point3D_id point;
        PointColour colour;
        String[] cols;
        cols = line.split(delimiter);
        if(cols.length>=3){
            double x = Double.parseDouble(cols[0]);
            double y = Double.parseDouble(cols[1]);
            double z = Double.parseDouble(cols[2]);
            point = new Point3D_id(x, y, z);
        }else{
            Olivia.textOutputter.println("Error Reading Overlay");
            throw new IOException();
        }
        if(cols.length==6){
            float r = Float.parseFloat(cols[3]);
            float g = Float.parseFloat(cols[4]);
            float b = Float.parseFloat(cols[5]);
            colour = new PointColour(r,g,b);
        }else{
            colour = defaultColour;
        }
        this.add(point, colour);
    }
    
    protected void parseLines(List<String> lines, String delimiter) throws IOException{
        if(lines.size()!=2){
            Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match lines!");
            throw new IOException();
        }
        this.setRenderMode(GL_LINES);
        for(int i=0;i<2;i++){
            parseLineAsVertex(lines.get(i), delimiter);
        }
    }
    
    protected void parseTriangle(List<String> lines, String delimiter) throws IOException{
        if(lines.size()!=3){
            Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match a triangle!");
            throw new IOException();
        }
        this.setRenderMode(GL_TRIANGLES);
        for(int i=0;i<3;i++){
            parseLineAsVertex(lines.get(i), delimiter);
        }
    }
    
    protected void parseQuad(List<String> lines, String delimiter) throws IOException{
        if(lines.size()!=4){
            Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match a quad!");
            throw new IOException();
        }
        this.setRenderMode(GL_QUADS);
        for(int i=0;i<4;i++){
            parseLineAsVertex(lines.get(i), delimiter);
        }
    }
    
    protected void parsePolygon(List<String> lines, String delimiter) throws IOException{
        if(lines.size()<3){
            Olivia.textOutputter.println("The number of lines " + lines.size() + " are too few for a polygon!");
            throw new IOException();
        }
        this.setRenderMode(GL_POLYGON);
        for(int i=0;i<lines.size();i++){
            parseLineAsVertex(lines.get(i), delimiter);
        }
    }
    
    
    protected void drawCode(int renderMode, int rasterMode ){
        int i;
        visualisationManager.getRenderScreen().getGl2().glPushMatrix();
        //Assumes triangles
        visualisationManager.getRenderScreen().getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, rasterMode);
        visualisationManager.getRenderScreen().getGl2().glBegin(renderMode);
        for (i=0;i<vertices.size();i++){
            visualisationManager.getRenderScreen().getGl2().glColor3f(colours.get(i).getR(), colours.get(i).getG(), colours.get(i).getB());
            visualisationManager.getRenderScreen().getGl2().glVertex3d(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
        }
        visualisationManager.getRenderScreen().getGl2().glEnd();
        visualisationManager.getRenderScreen().getGl2().glPopMatrix();
    }
    
    protected void drawVBO(int renderMode, int rasterMode ){
        int size;
        if(rasterMode==GL_POINT){
            size = visualisationManager.getRenderScreen().getPointSize();
        }else{
            size= visualisationManager.getRenderScreen().getLineWidth();
        }
        vertices.draw(visualisationManager.getRenderScreen(), colours, size, renderMode, rasterMode);
    }

    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        //drawCode(renderMode,rasterMode);
        drawVBO(renderMode, rasterMode);
    }
    
    protected void drawShape(int renderMode, int rasterMode) {
        if((SUPPORTED_PRIMITIVE_MODES.contains(renderMode))&(SUPPORTED_RASTER_MODES.contains(rasterMode))){
            //drawCode(renderMode, rasterMode);
            drawVBO(renderMode, rasterMode);
        }else{
            Olivia.textOutputter.println("Trying to draw unsupported mode");
        }
    }

    @Override
    public void repack(OpenGLScreen renderScreen) {
        vertices.repack(renderScreen);
    }

    @Override
    public void free(OpenGLScreen renderScreen) {
        vertices.freeVBO(renderScreen);
    }
    
    @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        VertexOptionsPanel optionsPanel = new VertexOptionsPanel(this);
        this.optionPanels.add(optionsPanel);
    }

}
