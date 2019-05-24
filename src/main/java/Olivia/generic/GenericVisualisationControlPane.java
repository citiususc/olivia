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
    
    public static final String[] SUPPORTED_PALETTES = new String[] {
        "RGB",
        "Classification",
        "Random",
        "Greysacle",
        "BlueRed"
    };
    
    protected JLabel labelColours;
    protected JComboBox<String> comboColours;
    
    //protected ArrayList<String> colours;
    //protected String[] coloursA;
    protected DefaultComboBoxModel<String> coloursModel;
    
    protected JLabel labelFields;
    protected JComboBox<String> comboFields;
    protected DefaultComboBoxModel<String> fieldsModel;
    protected JButton createDefinedColourButton;
    //protected JButton createClassificationColourButton;
    protected JButton createGradientColourButton;
    
    protected JLabel labelPalettes;
    protected JComboBox<String> comboPalettes; 
    
    
    public GenericVisualisationControlPane(GenericVisualisationManager visualisationM) {
        this.visualisationM = visualisationM;
        
        //colours = new ArrayList<>();
        //coloursA = new String[0];
        coloursModel = new DefaultComboBoxModel();
        
        labelColours = new JLabel("Colour");
        comboColours = new JComboBox<>(coloursModel);
        comboColours.addActionListener(this);
        labelColours.setLabelFor(comboColours);
        comboColours.setLightWeightPopupEnabled(false);
        
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
        
        comboFields.setLightWeightPopupEnabled(false);
        
        this.add(labelFields);
        this.add(comboFields);     
        
        
        labelPalettes = new JLabel("Palettes");
        comboPalettes = new JComboBox<>(SUPPORTED_PALETTES);
        comboPalettes.addActionListener(this);
        labelPalettes.setLabelFor(comboPalettes);
        comboPalettes.setLightWeightPopupEnabled(false);
        
        this.add(labelPalettes);
        this.add(comboPalettes); 
        
        createDefinedColourButton = GUIManager.createButton("Create defined colour", "Creates a defined colour asignation depending on the field and the palette", "defined_colour_button", this);
        this.add(createDefinedColourButton);
        
        //createClassificationColourButton = GUIManager.createButton("Create classification colour", "Creates a colour asignation depending on the field, with colours from estandard LiDAR classification fields", "class_colour_button", this);
        //this.add(createClassificationColourButton);
        
        createGradientColourButton = GUIManager.createButton("Create gradient colour", "Creates a gradient colour asignation depending on the field", "gradient_colour_button", this);
        this.add(createGradientColourButton);
        
    }
    
    public void AddColour(String name){
        //colours.add(name);
        coloursModel.addElement(name);
        comboColours.setSelectedIndex(coloursModel.getSize()-1);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        visualisationM.setColour(comboColours.getSelectedIndex());
        switch (e.getActionCommand()) {
            case "defined_colour_button":
                switch(comboPalettes.getSelectedIndex()){
                    case 0 : visualisationM.createRGBColourFromField(comboFields.getSelectedIndex());
                        break;
                    case 1: visualisationM.createClassificationColourFromField(comboFields.getSelectedIndex());
                        break;
                    case 2 : visualisationM.createRandomColourFromField(comboFields.getSelectedIndex());
                        break;
                    case 3: visualisationM.createGreyscaleColourFromField(comboFields.getSelectedIndex());
                        break;
                    case 4: visualisationM.createBlueRedColourFromField(comboFields.getSelectedIndex());
                        break;
                }
                break;
            case "gradient_colour_button":
                switch(comboPalettes.getSelectedIndex()){
                    case 0 : visualisationM.createGradientColourFromField(comboFields.getSelectedIndex());
                        break;
                    case 1: 
                        break;
                    case 2 : 
                        break;
                    case 3: visualisationM.createGreyscaleGradientFromField(comboFields.getSelectedIndex());
                        break;
                    case 4: visualisationM.createBlueRedGradientFromField(comboFields.getSelectedIndex());
                        break;
                }
                break;
        }
    }
    
}
