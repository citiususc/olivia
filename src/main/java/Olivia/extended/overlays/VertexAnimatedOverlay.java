/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.AnimatedOverlay;
import Olivia.core.VisualisationManager;
import Olivia.core.render.colours.PointColour;
import Olivia.core.render.RenderOptions;
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
public class VertexAnimatedOverlay<VM extends VisualisationManager> extends AnimatedOverlay<VertexOverlay<VM>,VM> implements RenderOptions{
    
    protected PointColour defaultColour;
    protected int renderMode;
    protected int rasterMode; 
    
    public VertexAnimatedOverlay(VM visualisationManager) {
        this(visualisationManager, "Vertex Animated Overlay", RenderOptions.GL_POINTS, RenderOptions.GL_POINT, new PointColour(1.0f,1.0f,1.0f),1l);
    }
    
    public VertexAnimatedOverlay(VM visualisationManager, String name) {
        this(visualisationManager, name, RenderOptions.GL_POINTS, RenderOptions.GL_POINT, new PointColour(1.0f,1.0f,1.0f), 1l);
    }
    
    public VertexAnimatedOverlay(VM visualisationManager, String name, int renderMode, int rasterMode, PointColour defaultColour, Long speed) {
        super(visualisationManager, name);
        this.renderMode = renderMode;
        this.rasterMode = rasterMode;
        this.defaultColour = defaultColour;
        this.speed = speed;
    }
       
    protected VertexOverlay readTriangle(List<String> lines, String delimiter, int rasterMode, PointColour defaultColour, int numFrame) throws IOException{
        VertexOverlay overlay = new VertexOverlay(visualisationManager,"Frame" + numFrame);
        overlay.setRasterMode(rasterMode);
        overlay.setDefaultColour(defaultColour);
        overlay.parseTriangle(lines, delimiter);
        return overlay;
    }
    
    protected VertexOverlay readQuad(List<String> lines, String delimiter, int rasterMode, PointColour defaultColour, int numFrame) throws IOException{
        VertexOverlay overlay = new VertexOverlay(visualisationManager,"Frame" + numFrame);
        overlay.setRasterMode(rasterMode);
        overlay.setDefaultColour(defaultColour);
        overlay.parseQuad(lines, delimiter);
        return overlay;
    }
    
    
    protected void readPoints(List<String> lines, String delimiter,int rasterMode, PointColour defaultColour) throws IOException{
        int i, numFrame=0;
        for(i=0;i<lines.size();i++){
            VertexOverlay overlay = new VertexOverlay(visualisationManager,"Frame" + numFrame);
            overlay.setRasterMode(rasterMode);
            overlay.setDefaultColour(defaultColour);
            overlay.setRenderMode(VertexOverlay.GL_POINTS);
            overlay.parseLineAsVertex(lines.get(i), delimiter);
            numFrame++;
            this.add(overlay);
        }
    }
    
    protected void readTriangles(List<String> lines, String delimiter, int rasterMode) throws IOException{
        int i, numFrame=0;
        if(Math.floorMod(lines.size(),3)!=0){
            Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match a division in triangles! doing nothing");
            return;
        }
        for(i=0;i<lines.size();i=i+3){
            VertexOverlay overlay = readTriangle(lines.subList(i, i+3),delimiter,rasterMode,defaultColour,numFrame);
            numFrame++;
            this.add(overlay);
        }
    }
    
    protected void readQuads(List<String> lines, String delimiter, int rasterMode) throws IOException{
        int i, numFrame=0;
        if(Math.floorMod(lines.size(),4)!=0){
            Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match a division in quads! doing nothing");
            return;
        }
        for(i=0;i<lines.size();i=i+4){
            VertexOverlay overlay = readQuad(lines.subList(i, i+4),delimiter,rasterMode,defaultColour,numFrame);
            numFrame++;
            this.add(overlay);
        }
    }
    
    public void readFromFile(Path path, int renderMode, int rasterMode) throws FileNotFoundException,IOException{
        try{
            if(!VertexOverlay.SUPPORTED_PRIMITIVE_MODES.contains(renderMode)){
                Olivia.textOutputter.println("Trying to read unsupported mode!");
                return;
            }
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            Olivia.textOutputter.println("Read " + lines.size());
            
            if(renderMode == VertexOverlay.GL_TRIANGLES){
                if(Math.floorMod(lines.size(),3)!=0){
                    Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match a division in triangles! doing nothing");
                    return;
                }
                readTriangles(lines,delimiter,rasterMode);
            }
            if(renderMode == VertexOverlay.GL_QUADS){
                if(Math.floorMod(lines.size(),4)!=0){
                    Olivia.textOutputter.println("The number of lines " + lines.size() + " does not match a division in quads! doing nothing");
                    return;
                }
                readQuads(lines,delimiter,rasterMode);
            }
            if(renderMode == VertexOverlay.GL_POINTS){
                readPoints(lines,delimiter,rasterMode,defaultColour);
            }
            
        }catch (IOException ex) {
            Olivia.textOutputter.println("Error Reading Overlay");
        }
    }
    
    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        readFromFile(path, renderMode, rasterMode);
    }

    @Override
    public int getRenderMode() {
        return renderMode;
    }

    @Override
    public int getRasterMode() {
        return rasterMode;
    }

    @Override
    public PointColour getDefaultColour() {
        return defaultColour;
    }

    @Override
    public void setRenderMode(int renderMode) {
        this.renderMode = renderMode;
    }
    
    @Override
    public void setRenderMode(String renderMode) {
        this.renderMode = RenderOptions.getMode(renderMode);
    }

    @Override
    public void setRasterMode(int rasterMode) {
        this.rasterMode = rasterMode;
    }
    
    @Override
    public void setRasterMode(String rasterMode) {
        this.rasterMode = RenderOptions.getMode(rasterMode);
    }
    
    @Override
    public void setDefaultColour(PointColour colour){
        this.defaultColour = colour;
    }
    
    @Override
    public void setDefaultColour(String name){
        this.defaultColour = new PointColour(RenderOptions.getColor(name));
    }
    
    @Override
    public void setDefaultColour(Color colour){
        this.defaultColour = new PointColour(colour);
    }

    @Override
    public void changeColour(PointColour colour) {
        this.defaultColour.setR(colour.getR());
        this.defaultColour.setG(colour.getG());
        this.defaultColour.setB(colour.getB());
        this.repack(visualisationManager.getRenderScreen());
    }

    @Override
    public void changeColour(String name) {
        PointColour col = new PointColour(RenderOptions.getColor(name));
        this.changeColour(col);
    }

    @Override
    public void changeColour(Color colour) {
        PointColour col = new PointColour(colour);
        this.changeColour(col);
    }
    
}
