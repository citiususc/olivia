/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import java.io.File;
import java.io.FileNotFoundException;
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
                if(cols.length>2){
                    olivia.addNewStandardVisualisation(cols[1], Integer.parseInt(cols[2]));
                }
                break;
            case "segmenter" :
                if(cols.length>1){
                    olivia.addNewSegmenterVisualisation(cols[1]);
                }
                break;
            case "classifier" :
                if(cols.length>1){
                    olivia.addNewClassifierVisualisation(cols[1]);
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
            default :
                olivia.println("Command: Unrecognised command " + line);
                break;
        }
        
    }
    
    public void readFromFile(Path path) throws FileNotFoundException, IOException {
        olivia.println("Command: Reading commnads from " + path);
        List<String> lines = Files.readAllLines(path);
        olivia.println("Command: read " + lines.size());
        for(String line:lines){
                runCommand(line);
        }
    }
    
}
