/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.exec;

import java.io.File;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author oscar.garcia
 */
public class OliviaProcesses {
    public final static String DEAFAULT_OUTPUT_FOLDER = "output";
    protected ExecutablesWorker cw;
    boolean isWindows;
    //boolean isLinux;
    protected String executablesFolder;
    protected String outputFolder;
    ExecutionOutputScreen outputScreen;
    protected ArrayList<String> availableCommands;
    
    public OliviaProcesses(ExecutionOutputScreen outputScreen, String executablesFolder){
        this.executablesFolder = executablesFolder;
        isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        this.outputScreen = outputScreen;
        this.outputFolder = DEAFAULT_OUTPUT_FOLDER;
        //outputScreen.setVisible(false);
        //isLinux
        availableCommands = new ArrayList<>();
        File folder = new File(executablesFolder);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            }else {
                switch(fileEntry.getName()){
                    case "segment" :
                        availableCommands.add("segment");
                        
                }
            }
        }
    }
    
    public ArrayList<String> getAvailableCommands(){
        return availableCommands;
    }


    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
    
    public int executeSegment(String filePath){
        return executeSegment(filePath,getSegmentArgs());
    }
    
    public int executeSegment(String filePath, String args){
        //ProcessBuilder builder = new ProcessBuilder();
        ExecutablesWorker worker;
        outputScreen.setVisible(true);
        if (isWindows) {
            //builder.command("cmd.exe", "/c", "dir");
            return -1;
        } else {
            //builder.command(executablesFolder + "segment", "-i" +filePath, "-n 1000000");
            worker = new ExecutablesWorker(outputScreen, executablesFolder + "segment", "-i" +filePath, "-O"+outputFolder, args);
        }
        outputScreen.addText("Executing segmentation on " + filePath);
        worker.addPropertyChangeListener(outputScreen);
        //builder.directory(new File(System.getProperty("user.home")));
        worker.execute();
        return 1;
    }
    
    public String getArgsForCommand(String command){
        switch(command){
            case "segment" :
                return "-n 1000000";
            default :
                return "";
        }
    }
    
    public String getSegmentArgs(){
        return getArgsForCommand("segment");
    }
    
    
}
