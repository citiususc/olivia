/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.exec;

import Olivia.core.gui.controls.ConsoleTextPane;
import Olivia.core.gui.controls.TextPaneOutputStream;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import static javax.swing.SwingWorker.StateValue.DONE;

/**
 *
 * @author oscar.garcia
 */
public class OutputScreen extends JFrame implements PropertyChangeListener{
    
    protected JPanel panel;
    protected ConsoleTextPane consoleTextPane;
    //protected JTextArea tA;
    //protected TextPaneOutputStream tpOStream;
    protected OliviaProcesses pB;
    
    public OutputScreen(){
        super("Execution");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        consoleTextPane = new ConsoleTextPane();
        //tA = new JTextArea();
        //tpOStream = new TextPaneOutputStream(consoleTextPane);
        panel.add(consoleTextPane);
        //panel.add(tA);
        this.setContentPane(panel);
        //this.add(panel);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(600, 400);
        pB = new OliviaProcesses(this,"/home/oscar.garcia/Nextcloud/LiDAR/rule-based-classifier-master/bin/");
    }
    
    public void addText(String text){
        consoleTextPane.addText(text);
        consoleTextPane.repaint();
        //tA.append(text);
    }
    
    public void performSegment(String filePath){
        pB.executeSegment(filePath);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "progress" : 
                //addText(((Integer)evt.getNewValue()).toString() + ": ");
                 //System.out.println((Integer)evt.getNewValue());
                break;
            case "state":
                SwingWorker worker = (SwingWorker) evt.getSource();
                switch (worker.getState()) {
                    case DONE: 
                        addText("\n***\nCOMMAND EXECUTED\n***\n");
                        break;
                }
        }
    }
    
}
