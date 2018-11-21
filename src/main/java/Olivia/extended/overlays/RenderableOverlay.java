/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import Olivia.core.render.RenderOptions;
import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.GL2;
import java.awt.Color;

/**
 * An Overlay that can be rendered in OpenGL, with raster and render modes and a default colour
 * @author oscar.garcia
 * @param <VM> The visualisation it is using
 */
public abstract class RenderableOverlay<VM extends VisualisationManager> extends Overlay<VM> implements RenderOptions{

    /**
     * The dault colour to be used
     */
    protected PointColour defaultColour;
    
    /**
     * The render mode to be used, this means whether vertices will be considered points, triangles, quads ...
     */
    protected int renderMode;
    
    /**
     * The raster mode to be used, this means whether the triangles will be filled, draw by lines, points ...
     */
    protected int rasterMode;
    
    /**
     * Creates a new RenderableOverlay with renderMode=GL_TRIANGLES, rasterMode=GL_LINE and white default colour 
     * @param visualisationManager The visualisationManager
     * @param name The name of the overlay
     */
    public RenderableOverlay(VM visualisationManager, String name) {
        this(visualisationManager, name,GL2.GL_TRIANGLES, GL2.GL_LINE, new PointColour(1.0f,1.0f,1.0f));
    }
    
    /**
     * Creates a new RenderableOverlay
     * @param visualisationManager The visualisationManager
     * @param name The name of the overlay
     * @param renderMode The renderMode, those supported should depend on RenderOptions
     * @param rasterMode The rasterMode, those supported should depend on RenderOptions
     * @param defaultColour The defaultColour
     */
    public RenderableOverlay(VM visualisationManager, String name, int renderMode, int rasterMode, PointColour defaultColour) {
        super(visualisationManager, name);
        this.renderMode = renderMode;
        this.rasterMode = rasterMode;
        this.defaultColour = defaultColour;
    }

    /**
     * Gets the render mode
     * @return the render mode
     */
    @Override
    public int getRenderMode() {
        return renderMode;
    }

    /**
     * Gets the raster mode
     * @return the raster mode
     */
    @Override
    public int getRasterMode() {
        return rasterMode;
    }

    /**
     * Gets the default colour
     * @return the default colour
     */
    @Override
    public PointColour getDefaultColour() {
        return defaultColour;
    }

    /**
     * Sets the render mode
     * @param renderMode the render mode, according to OpenGL
     */
    @Override
    public void setRenderMode(int renderMode) {
        this.renderMode = renderMode;
    }
    
    /**
     * Sets the render mode
     * @param renderMode the render mode, as given in RenderOptions
     * @see Olivia.core.render.RenderOptions
     */
    @Override
    public void setRenderMode(String renderMode) {
        this.renderMode = RenderOptions.getMode(renderMode);
    }

    /**
     * Sets the raster mode
     * @param rasterMode the raster mode, according to OpenGL
     */
    @Override
    public void setRasterMode(int rasterMode) {
        this.rasterMode = rasterMode;
    }
    
    /**
     * Sets the raster mode
     * @param rasterMode the raster mode, as given in RenderOptions
     * @see Olivia.core.render.RenderOptions
     */
    @Override
    public void setRasterMode(String rasterMode) {
        this.rasterMode = RenderOptions.getMode(rasterMode);
    }

    /**
     * Sets the default colour and repacks the overlay
     * @param colour the new default colour
     */
    @Override
    public void setDefaultColour(PointColour colour){
        //this.defaultColour = colour;
        this.defaultColour.setR(colour.getR());
        this.defaultColour.setG(colour.getG());
        this.defaultColour.setB(colour.getB());
        this.repack(visualisationManager.getRenderScreen());
    }
    
    /**
     * Sets the default colour and repacks the overlay
     * @param name the new default colour in name as given in RenderOptions
     * @see Olivia.core.render.RenderOptions
     */
    @Override
    public void setDefaultColour(String name){
        PointColour col = new PointColour(RenderOptions.getColor(name));
        this.defaultColour.setR(col.getR());
        this.defaultColour.setG(col.getG());
        this.defaultColour.setB(col.getB());
        this.repack(visualisationManager.getRenderScreen());
    }
    
    /**
     * Sets the default colour and repacks the overlay
     * @param colour the new default colour
     */
    @Override
    public void setDefaultColour(Color colour){
        this.defaultColour = new PointColour(colour);
        this.repack(visualisationManager.getRenderScreen());
    }
    
}
