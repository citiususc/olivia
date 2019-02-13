/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.exec;

import Olivia.core.VisualisationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author oscar.garcia
 */
public class ExecutionMenu extends JMenu implements ActionListener {
    protected VisualisationManager vM;
    //protected OliviaProcessBuilder pB;
    //protected OliviaProcesses pB;
    protected OutputScreen outputScreen;
    
    protected JMenuItem execLanding;
    
    public ExecutionMenu(VisualisationManager vM){
        super("Execute");
        this.vM = vM;
        //pB = new OliviaProcessBuilder("/home/oscar.garcia/Nextcloud/LiDAR/rule-based-classifier-master/bin/");
        //pB = new OliviaProcesses("/home/oscar.garcia/Nextcloud/LiDAR/rule-based-classifier-master/bin/");
        
        execLanding = new JMenuItem("segment");
        execLanding.getAccessibleContext().setAccessibleDescription("Executes the segmentation on this file");
        execLanding.setActionCommand("segment");
        execLanding.setEnabled(true);
        execLanding.addActionListener(this);
        
        this.add(execLanding);
        
        outputScreen = new OutputScreen();
        outputScreen.setVisible(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "segment" :
                /*if(pB.executeSegment(vM.getMainFilePath())==0){
                    vM.getGUI().getOlivia().addNewSegmenterVisualisation("/home/oscar.garcia/NetBeansProjects/Olivia_public/olivia_public/jogl/results.xyz", "Segmentation Result");
                };*/
                outputScreen.setVisible(true);
                outputScreen.performSegment(vM.getMainFilePath());
                break;
        }
    }
    
}
