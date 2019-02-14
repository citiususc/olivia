/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.exec;

import Olivia.core.Olivia;
import Olivia.core.gui.controls.ConsoleTextPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import static javax.swing.SwingWorker.StateValue.DONE;

/**
 *
 * @author oscar.garcia
 */
public class OutputScreen extends JFrame implements PropertyChangeListener, ActionListener{
    protected Olivia olivia;
            
    protected JPanel panel;
    protected ConsoleTextPane consoleTextPane;
    //protected JTextArea tA;
    //protected TextPaneOutputStream tpOStream;
    protected OliviaProcesses pB;
    protected JTextField inputField;
    protected JLabel inputLabel;
    protected JTextField outputField;
    protected JLabel outputLabel;
    protected JTextField argsField;
    protected JLabel argsLabel;
    protected JLabel executeLabel;
    protected JButton executeButton;
    protected JButton showButton;
    protected JButton exitButton;
    
    public OutputScreen(Olivia olivia){
        super("Execution");
        this.olivia = olivia;
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        //int fill = GridBagConstraints.HORIZONTAL;
        int fill = GridBagConstraints.BOTH;
        int anchor = GridBagConstraints.CENTER;
        int ipadx = 10;
        float labelWeight = 0.2f;
        
        inputField = new JTextField("input");
        inputField.setEditable(false);
        inputLabel = new JLabel("Input:");
        inputLabel.setLabelFor(inputField);
  
        outputField = new JTextField("output");
        outputField.setEditable(true);
        outputLabel = new JLabel("Ouput Folder:");
        outputLabel.setLabelFor(outputField);
        
        argsField = new JTextField("args");
        argsField.setEditable(true);
        argsLabel = new JLabel("Arguments:");
        argsLabel.setLabelFor(argsField);

        executeButton = new JButton("Execute");
        executeButton.setEnabled(true);
        executeButton.setActionCommand("execute");
        executeButton.addActionListener(this);
        executeLabel = new JLabel("exec");
        executeLabel.setLabelFor(executeButton);
        
        consoleTextPane = new ConsoleTextPane();
        
        showButton = new JButton("show");
        showButton.setEnabled(false);
        showButton.setActionCommand("show");
        showButton.addActionListener(this);
        exitButton = new JButton("exit");
        exitButton.setEnabled(true);
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = labelWeight;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = ipadx;
        panel.add(inputLabel, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = ipadx;
        panel.add(inputField, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = labelWeight;
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = ipadx;
        panel.add(outputLabel, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = ipadx;
        panel.add(outputField, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = labelWeight;
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = ipadx;
        panel.add(argsLabel, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = ipadx;
        panel.add(argsField, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = labelWeight;
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = ipadx;
        panel.add(executeLabel, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = ipadx;
        panel.add(executeButton, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        c.ipadx = ipadx;
        panel.add(showButton, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        c.ipadx = ipadx;
        panel.add(exitButton, c);
        
        c.fill = fill;
        c.anchor = anchor;
        c.weightx = 0.0;
        c.weighty = 1.0;   //request any extra vertical space
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        //c.ipady = 40;      //make this component tall
        c.ipadx = ipadx;
        panel.add(consoleTextPane, c);
        
        
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
    
    public void setUp(String command, String inputFile){
        executeLabel.setText(command);
        argsField.setText(pB.getArgsForCommand(command));
        inputField.setText(inputFile);
        executeButton.setEnabled(true);
        showButton.setEnabled(false);
        outputField.setEditable(true);
        argsField.setEditable(true);
    }
    
    protected void performSegment(String filePath){
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
                        showButton.setEnabled(true);
                        break;
                }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "execute":
                executeButton.setEnabled(false);
                outputField.setEditable(false);
                argsField.setEditable(false);
                pB.setOutputFolder(outputField.getText());
                switch(executeLabel.getText()){
                    case "segment":
                        pB.executeSegment(inputField.getText(),argsField.getText());
                        break;
                }
                break;
            case "show":
                showButton.setEnabled(false);
                switch(executeLabel.getText()){
                    case "segment":
                        if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                            olivia.addNewSegmenterVisualisation(outputField.getText() + "\results.xyz", "Segment Results");
                        }else{
                            olivia.addNewSegmenterVisualisation(outputField.getText() + "/results.xyz", "Segment Results");
                        }
                        break;
                }
                break;
            case "exit":
                this.setVisible(false);
                break;
        }
    }
    
}
