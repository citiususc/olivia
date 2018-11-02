/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A file chooser accessory that allows to select a decimation value to reduce the number of points to be read from the file
 * @author oscar.garcia
 */
public class DecimationFileChooserAccessory extends FileChooserAccessory implements ChangeListener{
    /**
     * The spinner
     */
    protected JSpinner decimationSpinner;
    /**
     * The number model of the spinner
     */
    protected SpinnerNumberModel decimationModel;
    /**
     * The label for the spinner
     */
    protected JLabel decimationLabel;
    /**
     * To save the decimation between uses and when not visible
     */
    protected int decimation;
    
    /**
     * Creates file chooser accessory with a labelled spinner to select the decimation
     */
    public DecimationFileChooserAccessory(){
        //this.menuBar = menuBar;
        decimationModel = new SpinnerNumberModel(1, 0, 30, 1);
        decimationSpinner = new JSpinner(decimationModel);
        decimationSpinner.setToolTipText("Set the decimation of the file");
        decimationSpinner.addChangeListener(this);
        
        decimationLabel = new JLabel("Decimation");
        decimationLabel.setLabelFor(decimationSpinner);
        
        this.add(decimationLabel);
        this.add(decimationSpinner);
       
    }

    /**
     * The decimation selected on the last stateChanged 
     * @return the decimation the last time the sppiner state changed
     */
    public int getDecimation() {
        return decimation;
    }

    /**
     * Sets the decimation to the spinner value
     * @param e an event, not used
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        decimation = decimationModel.getNumber().intValue();
    }
    
    
}
