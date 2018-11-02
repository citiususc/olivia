/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render;

import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.GL2;
import java.awt.Color;
import java.util.ArrayList;

/**
 * This interface defines the most usual options for renderable objects, for render and raster modes, and colours
 * and gives ways to go from a name to a value and back, for the GUI;
 * a class that extends from this may limit the number of options it accepts by changing the ArrayLists.
 * Methods getMode(), getModeName() and so should be made more dependent on the array, so if the arrayd are changed the methods work anyway, would have to remove swith() clauses because they do not work with arrays
 * @author oscar.garcia
 */
public interface RenderOptions {
    
    /**
     * This means vertices will be rendered in isolation, one by one, as points
     */
    public final static int GL_POINTS = GL2.GL_POINTS;
    /**
     * This means vertices will be rendered in pairs, in twos, as lines
     */
    public final static int GL_LINES = GL2.GL_LINES;
    /**
     * This means vertices will be rendered in groups of four, in fours, as quads
     */
    public final static int GL_QUADS = GL2.GL_QUADS;
    /**
     * This means vertices will be rendered in triplets, in threes, as triangles
     */
    public final static int GL_TRIANGLES = GL2.GL_TRIANGLES;
    /**
     * This means all vertices will be rendered as a polygon, with lines from one to the next
     */
    public final static int GL_POLYGON = GL2.GL_POLYGON;
    
    /**
     * This means shapes will be rastered as points, no lines between vertices or faces
     */
    public final static int GL_POINT = GL2.GL_POINT;
    /**
     * This means shapes will be rastered clear as lines, with lines between vertices but no faces
     */
    public final static int GL_LINE = GL2.GL_LINE;
    /**
     * This means shapes will be rastered as filled, with lines between vertices and faces
     */
    public final static int GL_FILL = GL2.GL_FILL;
    
    
    /**
     * This ArrayList contains all supported render (primitive) modes, if a class overrides it will limit or increase the available ones
     * @see SUPPORTED_PRIMITIVE_MODES_TEXT
     * @see getMode()
     * @see getModeName()
     */
    public static final ArrayList<Integer> SUPPORTED_PRIMITIVE_MODES = new ArrayList<Integer>() {{
        add(GL_POINTS);
        add(GL_LINES);
        add(GL_QUADS);
        add(GL_TRIANGLES);
        add(GL_POLYGON);
    }};
    
    /**
     * This ArrayList contains all supported raster modes, if a class overrides it will limit or increase the available ones
     * @see SUPPORTED_RASTER_MODES_TEXT
     * @see getMode()
     * @see getModeName()
     */
    public static final ArrayList<Integer> SUPPORTED_RASTER_MODES = new ArrayList<Integer>() {{
        add(GL_POINT);
        add(GL_LINE);
        add(GL_FILL);
    }};
    
    /**
     * This ArrayList contains all supported render (primitive) modes as names, if a class overrides it will limit or increase the available ones
     * @see SUPPORTED_PRIMITIVE_MODES
     * @see getMode()
     * @see getModeName()
     */
    public static final String[] SUPPORTED_PRIMITIVE_MODES_TEXT = new String[] {
        "GL_POINTS",
        "GL_LINES",
        "GL_QUADS",
        "GL_TRIANGLES",
        "GL_POLYGON"
    };
    
    /**
     * This ArrayList contains all supported raster modes as names, if a class overrides it will limit or increase the available ones
     * @see SUPPORTED_RASTER_MODES
     * @see getMode()
     * @see getModeName()
     */
    public static final String[] SUPPORTED_RASTER_MODES_TEXT = new String[] {
        "GL_POINT",
        "GL_LINE",
        "GL_FILL"
    };
    
    /**
     * This ArrayList contains all supported colours as names, if a class overrides it will limit or increase the available ones
     */
    public static final String[] SUPPORTED_COLOURS_TEXT = new String[] {
        "WHITE",
        "RED",
        "GREEN",
        "BLUE"
    };
    
    /**
     * Gets the mode as an interger depending on its name
     * @param name the name, should be one of SUPPORTED_PRIMITIVE_MODES_TEXT or SUPPORTED_RASTER_MODES_TEXT
     * @return a render o raster mode
     */
    public static int getMode(String name){
        switch(name){
            case "GL_POINTS" : return GL_POINTS;
            case "GL_LINES" : return GL_LINES;
            case "GL_QUADS" : return GL_QUADS;
            case "GL_TRIANGLES" : return GL_TRIANGLES;
            case "GL_POLYGON" : return GL_POLYGON;
            case "GL_POINT" : return GL_POINT;
            case "GL_LINE" : return GL_LINE;
            case "GL_FILL" : return GL_FILL;
            default : return GL_POINTS;
        }
    }
    
    /**
     * Gets the mode name
     * @param mode A rester or render mode, should be one of SUPPORTED_PRIMITIVE_MODES or SUPPORTED_RASTER_MODES
     * @return A name for the mode
     */
    public static String getModeName(int mode){
        switch(mode){
            case GL_POINTS : return "GL_POINTS";
            case GL_LINES : return "GL_LINES";
            case GL_QUADS : return "GL_QUADS";
            case GL_TRIANGLES : return "GL_TRIANGLES";
            case GL_POLYGON : return "GL_POLYGON";
            case GL_POINT : return "GL_POINT";
            case GL_LINE : return "GL_LINE";
            case GL_FILL : return "GL_FILL";
            default : return "Unsupported";
        }
    }
    
    /**
     * Gets a colour depending of its name
     * @param name the name of the colour
     * @return the colour
     */
    public static Color getColor(String name){
        switch(name){
            case "WHITE" : return Color.WHITE;
            case "RED" : return Color.RED;
            case "GREEN" : return Color.GREEN;
            case "BLUE" : return Color.BLUE;
            default : return Color.WHITE;
        }
    }
    
    /**
     * Gets the name of a colour
     * @param color the colour
     * @return the name of a colour
     */
    public static String getColorName(Color color){
            if(color.equals(Color.WHITE)) return "WHITE";
            if(color.equals(Color.RED)) return "RED";
            if(color.equals(Color.GREEN)) return "GREEN";
            if(color.equals(Color.BLUE)) return "BLUE";
            return "WHITE";
    }
    
    /**
     * Gets the current render mode
     * @return the render mode
     */
    public int getRenderMode();
    
    /**
     * Gets the current raster mode
     * @return the raster mode
     */
    public int getRasterMode();
    
    /**
     * Gets the current default colour
     * @return the colour
     */
    public PointColour getDefaultColour();
    
    /**
     * Gets the name 
     * @return the name
     */
    public String getName();
    
    /**
     * Sets the name
     * @param name the name
     */
    public void setName(String name);
    
    /**
     * Sets the render mode
     * @param renderMode the render mode
     */
    public void setRenderMode(int renderMode);
    
    /**
     * Sets the render mode from its name
     * @param renderMode the render mode name
     */
    public void setRenderMode(String renderMode);

    /**
     * Sets the raster mode
     * @param rasterMode the raster mode
     */
    public void setRasterMode(int rasterMode);
    /**
     * Sets the raster mode from its name
     * @param rasterMode the raster mode name
     */
    public void setRasterMode(String rasterMode);
    
    /**
     * Sets teh default colour
     * @param colour the colour
     */
    public void setDefaultColour(PointColour colour);
    
    /**
     * Sets teh default colour
     * @param name the colour name
     */
    public void setDefaultColour(String name);
    
    /**
     * Sets teh default colour
     * @param colour the colour
     */
    public void setDefaultColour(Color colour);
    
}
