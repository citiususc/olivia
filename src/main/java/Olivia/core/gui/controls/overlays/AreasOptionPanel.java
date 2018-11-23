/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.render.RenderOptions;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.overlays.AreasArray;
import Olivia.extended.overlays.RenderableOverlay;
import Olivia.extended.overlays.TextOverlay;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author oscar.garcia
 */
public class AreasOptionPanel extends JPanel implements ActionListener{
    protected AreasArray overlay;
    
    protected JComboBox<String> colourComboBox;
    protected JComboBox<String> fontComboBox;
    protected JTextField textFieldName;
    
    protected JLabel labelColour;
    protected JLabel labelFont;
    protected JLabel labelName;
    
    public AreasOptionPanel(AreasArray areasArray){
        super();
        this.setName("Areas");
        this.overlay = areasArray;
        
        this.colourComboBox = new JComboBox<>(RenderOptions.SUPPORTED_COLOURS_TEXT);
        colourComboBox.addActionListener(this);
        colourComboBox.setToolTipText("The default color to use when the file does not define one");
        //this.fontComboBox = new JComboBox<>((String[]) TextOverlay.AVAILABLE_FONTS_TEXT.toArray());
        this.fontComboBox = new JComboBox<>(TextOverlay.AVAILABLE_FONTS_TEXT_A);
        fontComboBox.addActionListener(this);
        fontComboBox.setToolTipText("The default font to use in the labels");
        textFieldName = new JTextField();
        textFieldName.addActionListener(this);
        
        labelColour = new JLabel("Default Colour");
        labelColour.setToolTipText("The default color to use when the file does not define one");
        labelFont = new JLabel("Default Font");
        labelFont.setToolTipText("The default font to use in the labels");
        labelName = new JLabel("Name");
        labelName.setToolTipText("The name that will be shown for this overlay (leave empty for default)");
        
        GridLayout layout = new GridLayout(2,0);
        this.setLayout(layout);
   
        this.add(labelColour);
        this.add(labelFont);
        this.add(labelName);
        this.add(colourComboBox);
        this.add(fontComboBox);
        this.add(textFieldName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((overlay!=null)&(this.isShowing())){//To avoid passing argument before being displayed
            overlay.changeColour( new PointColour(
                                        RenderOptions.getColor(
                                                colourComboBox.getItemAt(colourComboBox.getSelectedIndex())
                                        )
                                )
            );
            overlay.setFont(fontComboBox.getSelectedIndex());
            if(!textFieldName.getText().equals("")){
                overlay.setName(textFieldName.getText());
            }
        }
    }
    
}
