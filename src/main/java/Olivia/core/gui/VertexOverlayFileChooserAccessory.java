/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui;

import Olivia.core.render.RenderOptions;
import Olivia.extended.overlays.VertexOverlay;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * An accessory to indicate how the vertex file should be read
 * @author oscar.garcia
 * @param <VO> The class of VertexOverlay this can control
 */
public class VertexOverlayFileChooserAccessory<VO extends RenderOptions> extends FileChooserAccessory implements ActionListener{
    
    /**
     * The overlay it will control, were the data from the file will be saved
     */
    protected VO overlay;
    /**
     * A combo box to select the primitive (render) mode
     */
    protected JComboBox<String> primitiveModeComboBox;
    /**
     * A combo box to select the raster mode
     */
    protected JComboBox<String> rasterModeComboBox;
    /**
     * A combo box to select the default colour
     */
    protected JComboBox<String> colourComboBox;
    /**
     * A filed to select the overlay name
     */
    protected JTextField textFieldName;
    /**
     * A label for the primitive (render) combo box
     */
    protected JLabel labelRender;
    /**
     * A label for the raster combo box
     */
    protected JLabel labelRaster;
    /**
     * A label for the colour combo box
     */
    protected JLabel labelColour;
    /**
     * A label for the name field
     */
    protected JLabel labelName;
    
    /**
     * Creates an empty accessory
     */
    public VertexOverlayFileChooserAccessory(){
        super();
    }
    
    /**
     * Creates an accessory to allow selection of render and raster modes, colour and name
     * @param vertexOverlay the overlay that will be modified, must exist previously
     */
    public VertexOverlayFileChooserAccessory(VO vertexOverlay){
        super();
        this.overlay = vertexOverlay;
        this.primitiveModeComboBox = new JComboBox<>(RenderOptions.SUPPORTED_PRIMITIVE_MODES_TEXT);
        this.rasterModeComboBox = new JComboBox<>(RenderOptions.SUPPORTED_RASTER_MODES_TEXT);
        this.colourComboBox = new JComboBox<>(RenderOptions.SUPPORTED_COLOURS_TEXT);
        primitiveModeComboBox.addActionListener(this);
        primitiveModeComboBox.setToolTipText("How the vertices will be rendered");
        rasterModeComboBox.addActionListener(this);
        rasterModeComboBox.setToolTipText("How the faces wil be drawn");
        colourComboBox.addActionListener(this);
        colourComboBox.setToolTipText("The default color to use when the file does not define one");
        textFieldName = new JTextField();
        textFieldName.addActionListener(this);
        textFieldName.addPropertyChangeListener(this);
        labelRender = new JLabel("Render Mode");
        labelRender.setToolTipText("How the vertices will be rendered");
        labelRaster = new JLabel("Raster Mode");
        labelRaster.setToolTipText("How the faces wil be drawn");
        labelColour = new JLabel("Default Colour");
        labelColour.setToolTipText("The default color to use when the file does not define one");
        labelName = new JLabel("Name");
        labelName.setToolTipText("The name that will be shown for this overlay (leave empty for default)");
        
        GridLayout layout = new GridLayout(2,0);
        this.setLayout(layout);
   
        this.add(labelRender);
        this.add(labelRaster);
        this.add(labelColour);
        this.add(labelName);
        this.add(primitiveModeComboBox);
        this.add(rasterModeComboBox);
        this.add(colourComboBox);
        this.add(textFieldName);
        vertexOverlay.setRenderMode(primitiveModeComboBox.getItemAt(primitiveModeComboBox.getSelectedIndex()));
        vertexOverlay.setRasterMode(rasterModeComboBox.getItemAt(rasterModeComboBox.getSelectedIndex()));
        vertexOverlay.setDefaultColour(colourComboBox.getItemAt(colourComboBox.getSelectedIndex()));       
    }

    /**
     * Performs the actions
     * @param e an event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        overlay.setRenderMode(primitiveModeComboBox.getItemAt(primitiveModeComboBox.getSelectedIndex()));
        overlay.setRasterMode(rasterModeComboBox.getItemAt(rasterModeComboBox.getSelectedIndex()));
        overlay.setDefaultColour(colourComboBox.getItemAt(colourComboBox.getSelectedIndex()));
        if(!textFieldName.getText().equals("")){
            overlay.setName(textFieldName.getText());
        }
    }
    
    /**
     * Performs the actions on property changed
     * @param evt an event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        overlay.setRenderMode(primitiveModeComboBox.getItemAt(primitiveModeComboBox.getSelectedIndex()));
        overlay.setRasterMode(rasterModeComboBox.getItemAt(rasterModeComboBox.getSelectedIndex()));
        overlay.setDefaultColour(colourComboBox.getItemAt(colourComboBox.getSelectedIndex()));
        if(!textFieldName.getText().equals("")){
            overlay.setName(textFieldName.getText());
        }
    }
    
}
