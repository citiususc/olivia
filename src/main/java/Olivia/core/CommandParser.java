/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.render.RenderOptions;
import Olivia.core.render.colours.PointColour;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar.garcia
 */
public class CommandParser {
    
    //protected MainFrame gui;
    protected Olivia olivia;
    protected String delimiter = " ";
    
    public CommandParser(Olivia olivia){
        this.olivia = olivia;
    }
    
    /*
    public CommandParser(MainFrame gui){
        this.gui = gui;
    }*/
    
    public void runCommand(String line){
        String[] cols;
        cols = line.split(delimiter);
        switch (cols[0]){
            case "standard" :
                if(cols.length==3){
                    olivia.addNewStandardVisualisation(cols[1], Integer.parseInt(cols[2]));
                }else if (cols.length==4){
                    olivia.addNewStandardVisualisation(cols[1], cols[2], Integer.parseInt(cols[3]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "segmenter" :
                if(cols.length==2){
                    olivia.addNewSegmenterVisualisation(cols[1]);
                }else if(cols.length==3){
                    olivia.addNewSegmenterVisualisation(cols[1], cols[2]);
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "classifier" :
                if(cols.length==2){
                    olivia.addNewClassifierVisualisation(cols[1]);
                }else if(cols.length==3){
                    olivia.addNewClassifierVisualisation(cols[1], cols[2]);
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "scanlines" :
                if(cols.length==2){
                    olivia.addNewScanlinesVisualisation(cols[1]);
                }else if(cols.length==3){
                    olivia.addNewScanlinesVisualisation(cols[1], cols[2]);
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadNeighbours" :
                if(cols.length>1){
                    olivia.getGUI().getActiveVisualisation().loadNeighboursOverlay(new File(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadAreas" :
                if(cols.length>1){
                    olivia.getGUI().getActiveVisualisation().loadAreasOverlay(new File(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadLabeledCells" :
                if(cols.length>1){
                    olivia.getGUI().getActiveVisualisation().loadLabelledCells(new File(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadDensities" :
                if(cols.length>1){
                    olivia.getGUI().getActiveVisualisation().loadDensities(new File(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadNormals" :
                if(cols.length>1){
                    olivia.getGUI().getActiveVisualisation().loadNormals(new File(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadVertex" :
                if(cols.length>5){
                    olivia.getGUI().getActiveVisualisation().loadVertex(new File(cols[1]), cols[2], RenderOptions.getMode(cols[3]), RenderOptions.getMode(cols[4]), new PointColour(RenderOptions.getColor(cols[5])));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadAnimatedVertex" :
                if(cols.length>6){
                    olivia.getGUI().getActiveVisualisation().loadAnimatedVertex(new File(cols[1]), cols[2], RenderOptions.getMode(cols[3]), RenderOptions.getMode(cols[4]), new PointColour(RenderOptions.getColor(cols[5])), Long.parseLong(cols[6]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadCircles" :
                if(cols.length>2){
                    olivia.getGUI().getActiveVisualisation().loadCircles(new File(cols[1]), cols[2]);
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadAnimatedCircles" :
                if(cols.length>2){
                    olivia.getGUI().getActiveVisualisation().loadAnimatedCircles(new File(cols[1]), cols[2], Long.parseLong(cols[3]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "transformLastOverlay" :
                if(cols.length>6){
                    olivia.getGUI().getActiveVisualisation().transformLastOverlay(Float.parseFloat(cols[1]), Float.parseFloat(cols[2]), Float.parseFloat(cols[3]), Float.parseFloat(cols[4]), Float.parseFloat(cols[5]), Float.parseFloat(cols[6]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");
                }
                break;
            case "loadCamera" :
                if(cols.length>1){
                    olivia.getGUI().loadCamera(new File(cols[1]));
                }
                break;
            case "saveCamera" :
                if(cols.length>1){
                    olivia.getGUI().saveCamera(new File(cols[1]));
                }
                break;
            case "showAll" :
                olivia.getGUI().showAll();
                break;
            case "fullscreen" :
                olivia.getGUI().setFullscreen(true);
                break;
            case "maximize" :
                olivia.getGUI().setExtendedState( olivia.getGUI().getExtendedState()|Frame.MAXIMIZED_BOTH );
                break;
            case "pointSize" :
                if(cols.length==2){
                    olivia.getGUI().getActiveVisualisation().getRenderScreen().setPointSize(Integer.parseInt(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");    
                }
                break;
            case "lineWidth" :
                if(cols.length==2){
                    olivia.getGUI().getActiveVisualisation().getRenderScreen().setLineWidth(Integer.parseInt(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");    
                }
                break;
            case "eyeDist" :
                if(cols.length==2){
                    olivia.getGUI().getActiveVisualisation().getRenderScreen().getCamera().setIntraOcularDistance(Double.parseDouble(cols[1]));
                }else{
                    olivia.getOutputter().println(cols[0] + " command is incorrect, arguments do not match");    
                }
                break;
            case "#" :
                break;
            default :
                olivia.getOutputter().println("Command: Unrecognised command " + line);
                break;
        }
        
    }
    
    public void readFromFile(Path path) throws FileNotFoundException, IOException {
        olivia.getOutputter().println("Command: Reading commands from " + path);
        List<String> lines = Files.readAllLines(path);
        olivia.getOutputter().println("Command: read " + lines.size());
        for(String line:lines){
                runCommand(line);
        }
        olivia.getOutputter().println("Command: Command list ended");
    }
    
}
