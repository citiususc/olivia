/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.generic;

import Olivia.core.gui.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author oscar.garcia
 */
public class GenericVisualisationControlPane extends JPanel implements ActionListener {

    protected GenericVisualisationManager visualisationM;
    
    protected JLabel labelColours;
    protected JComboBox<String> comboColours;
    
    //protected ArrayList<String> colours;
    //protected String[] coloursA;
    protected DefaultComboBoxModel<String> coloursModel;
    
    protected JLabel labelFields;
    protected JComboBox<String> comboFields;
    protected DefaultComboBoxModel<String> fieldsModel;
    protected JButton createRandomColourButton;
    
    
    public GenericVisualisationControlPane(GenericVisualisationManager visualisationM) {
        this.visualisationM = visualisationM;
        
        //colours = new ArrayList<>();
        //coloursA = new String[0];
        coloursModel = new DefaultComboBoxModel();
        
        labelColours = new JLabel("Colour");
        comboColours = new JComboBox<>(coloursModel);
        comboColours.addActionListener(this);
        labelColours.setLabelFor(comboColours);
        
        this.add(labelColours);
        this.add(comboColours);
        
        fieldsModel = new DefaultComboBoxModel();
        for(int i=0; i < visualisationM.getPointCloud().getNumberOfFields();i++){
            fieldsModel.addElement(visualisationM.getPointCloud().getNames().get(i));
        }
        
        labelFields = new JLabel("Fields");
        comboFields = new JComboBox<>(fieldsModel);
        comboFields.addActionListener(this);
        labelFields.setLabelFor(comboFields);
        
        this.add(labelFields);
        this.add(comboFields);     
        
        
        createRandomColourButton = GUIManager.createButton("Create random colour", "Creates a random colour asignation depending on the field", "random_colour_button", this);
        this.add(createRandomColourButton);
        
    }
    
    public void AddColour(String name){
        //colours.add(name);
        coloursModel.addElement(name);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        visualisationM.setColour(comboColours.getSelectedIndex());
        switch (e.getActionCommand()) {
            case "random_colour_button":
                visualisationM.createRandomColourFromField(comboFields.getSelectedIndex());
                break;
        }
    }
    
}
