/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.AnimatedOverlay;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author oscar.garcia
 */
public class AnimationOptionsPanel extends JPanel implements ActionListener{
    
    protected AnimatedOverlay overlay;
    protected JToggleButton playB;
    protected JComboBox<String> speedComboBox;
    protected JLabel labelSpeed;
    
    public static final String[] SPEED_TEXT = new String[] {
        "1x",
        "2x",
        "10x",
        "20x",
        "50x"
    };
    
    public static long getSpeed(String name){
        switch(name){
            case "1x" : return 1l;
            case "2x" : return 2l;
            case "10x" : return 10l;
            case "20x" : return 20l;
            case "50x" : return 50l;
            default : return 1l;
        }
    }
    
    public static String getSpeedName(Long speed){
        if(speed>=50l) return "50x";
        if(speed>=20l) return "20x";
        if(speed>=10l) return "10x";
        if(speed>=2l) return "2x";
        if(speed>=1l) return "1x";
        return "1x";
    }
    
    public AnimationOptionsPanel(AnimatedOverlay overlay){
        this.overlay = overlay;
        this.setName("Animation");
        
        playB = new JToggleButton("play/pause");
        //playB = new JToggleButton("\u25b6"); //play button
        playB.setSelected(overlay.isPlay());
        playB.setActionCommand("play");
        playB.addActionListener(this);
        
        this.speedComboBox = new JComboBox<>(SPEED_TEXT);
        speedComboBox.setToolTipText("How fast the animation will play");
        speedComboBox.addActionListener(this);
        speedComboBox.setSelectedItem(getSpeedName(overlay.getSpeed()));
        
        labelSpeed = new JLabel("Speed");
        labelSpeed.setToolTipText("How fast the animation will play");
        
            
        this.add(playB);
        this.add(labelSpeed);
        this.add(speedComboBox);
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        overlay.setPlay(playB.isSelected());
        overlay.setPause(!playB.isSelected());
        if(playB.isSelected()){
            //playB.setText("playing...");
        }else{
            //playB.setText("paused");
        }
        overlay.setSpeed(getSpeed(speedComboBox.getItemAt(speedComboBox.getSelectedIndex())));
    }
    
    
}
