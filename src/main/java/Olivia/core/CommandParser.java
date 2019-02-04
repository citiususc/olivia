/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.gui.MainFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class CommandParser {
    
    //protected MainFrame gui;
    protected String delimiter = " ";
    
    public CommandParser(){
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
                if(cols.length>2){
                    addStandardVisualisation(cols[1], Integer.parseInt(cols[2]));
                }
                break;
            case "segmenter" :
                if(cols.length>1){
                    addSegmenterVisualisation(cols[1]);
                }
                break;
            case "classifier" :
                if(cols.length>1){
                    addClassifierVisualisation(cols[1]);
                }
                break;
            default :
                Olivia.println("Command: Unrecognised command " + line);
                break;
        }
        
    }
    
    public void readFromFile(Path path) throws FileNotFoundException, IOException {
        Olivia.println("Command: Reading commnads from " + path);
        List<String> lines = Files.readAllLines(path);
        Olivia.println("Command: read " + lines.size());
        for(String line:lines){
                runCommand(line);
        }
    }
    
    
    protected void addClassifierVisualisation(String filePath){
        Olivia.println("Command: Loading Classifier Visualisation from file " + filePath);
        Olivia.addNewClassifierVisualisation(filePath);
        Olivia.println("Command: Loaded Classifier Visualisation from file " + filePath);
    }
    
    protected void addSegmenterVisualisation(String filePath){
        Olivia.println("Command: Loading Segmenter Visualisation from file " + filePath);
        Olivia.addNewSegmenterVisualisation(filePath);
        Olivia.println("Command: Loaded Segmenter Visualisation from file " + filePath);
    }
    
    protected void addStandardVisualisation(String filePath, int decimation){
        Olivia.println("Command: Loading Standard Visualisation from file " + filePath + " and decimation " + decimation);
        Olivia.addNewStandardVisualisation(filePath, decimation);
        Olivia.println("Command: Loaded Standard Visualisation from file " + filePath + " and decimation " + decimation);
    }
    
}
