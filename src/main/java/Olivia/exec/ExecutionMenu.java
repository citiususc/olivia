/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.exec;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author oscar.garcia
 */
public class ExecutionMenu extends JMenu implements ActionListener {
    protected VisualisationManager vM;
    protected ExecutionOutputScreen outputScreen;
    
    protected JMenuItem setFolder;
    protected JMenu execute;
    protected JMenuItem execSegment;
    protected JMenuItem execDetect;
    
    protected boolean segmentAllowed;
    protected boolean detectAllowed;
    
    protected JFileChooser directoryFC;
    
    public ExecutionMenu(VisualisationManager vM){
        super("Execute");
        this.vM = vM;
        segmentAllowed = true;
        detectAllowed = true;
        //pB = new OliviaProcessBuilder("/home/oscar.garcia/Nextcloud/LiDAR/rule-based-classifier-master/bin/");
        //pB = new OliviaProcesses("/home/oscar.garcia/Nextcloud/LiDAR/rule-based-classifier-master/bin/");
        
        setFolder = new JMenuItem("set folder");
        setFolder.getAccessibleContext().setAccessibleDescription("Sets the folder that includers the executables");
        setFolder.setActionCommand("setFolder");
        setFolder.addActionListener(this);
        
        this.add(setFolder);
        
        execute = new JMenu("Execute");
        
        this.add(execute);
        
        execSegment = new JMenuItem("segment");
        execSegment.getAccessibleContext().setAccessibleDescription("Executes the segmentation on this file");
        execSegment.setActionCommand("segment");
        execSegment.addActionListener(this);
        
        execute.add(execSegment);
        
        execDetect = new JMenuItem("detect");
        execDetect.getAccessibleContext().setAccessibleDescription("Executes the detection on this file");
        execDetect.setActionCommand("detect");
        execDetect.addActionListener(this);
        
        execute.add(execDetect);
        
        outputScreen = new ExecutionOutputScreen(vM.getGUI().getOlivia());
        execSegment.setEnabled(outputScreen.getOliviaProcesses().getAvailableCommands().contains("segment")&&segmentAllowed);
        execDetect.setEnabled(outputScreen.getOliviaProcesses().getAvailableCommands().contains("detect")&&detectAllowed);
        outputScreen.setVisible(false);
        
        directoryFC = new JFileChooser();
        directoryFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "setFolder":
                int returnVal = directoryFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = directoryFC.getSelectedFile();
                    outputScreen.getOliviaProcesses().setExecutablesFolder(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "FOUND: " + outputScreen.getOliviaProcesses().getAvailableCommands().toString());
                } else {
                    Olivia.textOutputter.println("Cannot select directory.");
                }
                execSegment.setEnabled(outputScreen.getOliviaProcesses().getAvailableCommands().contains("segment")&&segmentAllowed);
                execDetect.setEnabled(outputScreen.getOliviaProcesses().getAvailableCommands().contains("detect")&&detectAllowed);
                break;
            case "segment" :
                /*if(pB.executeSegment(vM.getMainFilePath())==0){
                    vM.getGUI().getOlivia().addNewSegmenterVisualisation("/home/oscar.garcia/NetBeansProjects/Olivia_public/olivia_public/jogl/results.xyz", "Segmentation Result");
                };*/
                outputScreen.setVisible(true);
                outputScreen.setUp("segment", vM.getMainFilePath());
                //outputScreen.performSegment(vM.getMainFilePath());
                break;
            case "detect" :
                /*if(pB.executeSegment(vM.getMainFilePath())==0){
                    vM.getGUI().getOlivia().addNewSegmenterVisualisation("/home/oscar.garcia/NetBeansProjects/Olivia_public/olivia_public/jogl/results.xyz", "Segmentation Result");
                };*/
                outputScreen.setVisible(true);
                outputScreen.setUp("detect", vM.getMainFilePath());
                //outputScreen.performSegment(vM.getMainFilePath());
                break;
        }
    }

    public boolean isSegmentAllowed() {
        return segmentAllowed;
    }

    public void setSegmentAllowed(boolean segmentAllowed) {
        this.segmentAllowed = segmentAllowed;
        execSegment.setEnabled(segmentAllowed);
    }

    public boolean isDetectAllowed() {
        return detectAllowed;
    }

    public void setDetectAllowed(boolean detectAllowed) {
        this.detectAllowed = detectAllowed;
        execDetect.setEnabled(detectAllowed);
    }
    
    
    
}
