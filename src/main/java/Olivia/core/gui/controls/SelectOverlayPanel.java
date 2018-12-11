package Olivia.core.gui.controls;

import Olivia.core.gui.MainFrame;
import Olivia.core.render.OpenGLScreen;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * This panel controls the eye distance for 3D stereoscopic visualisation
 * 
 * @author jorge.martinez.sanchez
 */
public class SelectOverlayPanel extends BasicArrowPanel implements ActionListener {

    protected MainFrame gui;
    protected JCheckBox checkBox;

    public SelectOverlayPanel(MainFrame gui) {
        super("Overlays", "Show next overlay", "Show previous overlay");
        this.gui = gui;
        
        checkBox = new JCheckBox();
        checkBox.setSelected(false);
        add(checkBox, BorderLayout.EAST);
        checkBox.setToolTipText("Select wheter to show this overlay");
        checkBox.setActionCommand("ticked");
        
        super.buttonUp.addActionListener(this);
        super.buttonDown.addActionListener(this); 
        checkBox.addActionListener(this);
        this.setEnabled(false);
        buttonUp.setEnabled(false);
        buttonDown.setEnabled(false);
        checkBox.setEnabled(false);
        nameLabel.setEnabled(false);
    }

    /*
    public void setupRenderScreen(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
    }
    */
    
    public void update(){
        if(gui.getActiveVisualisation() != null){
                buttonUp.setEnabled(!gui.getActiveVisualisation().getOvelays().isEmpty());
                buttonDown.setEnabled(!gui.getActiveVisualisation().getOvelays().isEmpty());
                checkBox.setEnabled(!gui.getActiveVisualisation().getOvelays().isEmpty());
                nameLabel.setEnabled(!gui.getActiveVisualisation().getOvelays().isEmpty());
            if(gui.getActiveVisualisation().getOvelays().getCurrentOverlay()!=null){
                this.nameLabel.setText(gui.getActiveVisualisation().getOvelays().getCurrentOverlay().getName());
                this.checkBox.setSelected(gui.getActiveVisualisation().getOvelays().isSelected());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gui.getActiveVisualisation() == null) {
            return;
        }
        switch (e.getActionCommand()) {
            case "up":
                gui.getActiveVisualisation().getOvelays().getNextOverlay();
                update();
                gui.updateControlPanes();
                break;
            case "down":
                gui.getActiveVisualisation().getOvelays().getPreviousOverlay();
                update();
                gui.updateControlPanes();
                break;
            case "ticked":
                gui.getActiveVisualisation().getOvelays().select(checkBox.isSelected());
                update();
                gui.updateControlPanes();
                break;
        }
    }
}
