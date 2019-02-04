/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.render.RenderOptions;
import Olivia.extended.overlays.VertexOverlay;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author oscar.garcia
 */
public class VertexOptionsPanel extends JPanel implements ActionListener{
    
    protected VertexOverlay overlay;
    
    protected JComboBox<String> primitiveModeComboBox;
    protected JComboBox<String> rasterModeComboBox;
    protected JComboBox<String> colourComboBox;
    protected JTextField textFieldName;
    protected JLabel labelRender;
    protected JLabel labelRaster;
    protected JLabel labelColour;
    protected JLabel labelName;
    
    public VertexOptionsPanel(VertexOverlay vertexOverlay){
        super();
        this.setName("Render");
        this.overlay = vertexOverlay;
        this.primitiveModeComboBox = new JComboBox<>(overlay.SUPPORTED_PRIMITIVE_MODES_TEXT);
        this.rasterModeComboBox = new JComboBox<>(overlay.SUPPORTED_RASTER_MODES_TEXT);
        this.colourComboBox = new JComboBox<>(overlay.SUPPORTED_COLOURS_TEXT);
        primitiveModeComboBox.addActionListener(this);
        primitiveModeComboBox.setToolTipText("How the vertices will be rendered");
        rasterModeComboBox.addActionListener(this);
        rasterModeComboBox.setToolTipText("How the faces wil be drawn");
        colourComboBox.addActionListener(this);
        colourComboBox.setToolTipText("The default color to use when the file does not define one");
        textFieldName = new JTextField();
        textFieldName.addActionListener(this);
        //textFieldName.addPropertyChangeListener(this);
        labelRender = new JLabel("Render Mode");
        labelRender.setToolTipText("How the vertices will be rendered");
        labelRaster = new JLabel("Raster Mode");
        labelRaster.setToolTipText("How the faces wil be drawn");
        labelColour = new JLabel("Default Colour");
        labelColour.setToolTipText("The default color to use when the file does not define one");
        labelName = new JLabel("Name");
        labelName.setToolTipText("The name that will be shown for this overlay (leave empty for default)");
        
        primitiveModeComboBox.setSelectedItem(RenderOptions.getModeName(overlay.getRenderMode()));
        rasterModeComboBox.setSelectedItem(RenderOptions.getModeName(overlay.getRasterMode()));
        //colourComboBox.setSelectedItem(RenderOptions.getColorName(overlay.getDefaultColour().toColor()));
        labelName.setText(overlay.getName());
        
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
        
        /*
        vertexOverlay.setRenderMode(primitiveModeComboBox.getItemAt(primitiveModeComboBox.getSelectedIndex()));
        vertexOverlay.setRasterMode(rasterModeComboBox.getItemAt(rasterModeComboBox.getSelectedIndex()));
        vertexOverlay.setDefaultColour(colourComboBox.getItemAt(colourComboBox.getSelectedIndex())); 
        */
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((overlay!=null)&(this.isShowing())){//To avoid passing argument before being displayed
            overlay.setRenderMode(primitiveModeComboBox.getItemAt(primitiveModeComboBox.getSelectedIndex()));
            overlay.setRasterMode(rasterModeComboBox.getItemAt(rasterModeComboBox.getSelectedIndex()));
            overlay.changeColour(colourComboBox.getItemAt(colourComboBox.getSelectedIndex()));
            if(!textFieldName.getText().equals("")){
                overlay.setName(textFieldName.getText());
            }
        }
    }
    
    /*
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        overlay.setRenderMode(primitiveModeComboBox.getItemAt(primitiveModeComboBox.getSelectedIndex()));
        overlay.setRasterMode(rasterModeComboBox.getItemAt(rasterModeComboBox.getSelectedIndex()));
        overlay.setDefaultColour(colourComboBox.getItemAt(colourComboBox.getSelectedIndex()));
        if(!textFieldName.getText().equals("")){
            overlay.setName(textFieldName.getText());
        }
    }
    */
    
}
