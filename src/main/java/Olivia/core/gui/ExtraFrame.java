/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author oscar.garcia
 */
public class ExtraFrame extends JFrame{
    protected MainFrame gui;
    
    public ExtraFrame(MainFrame gui){
        this.gui = gui;
        WindowListener exitListener = new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        e.getWindow().setVisible(false);
                        gui.updateAll();
                    }
        };
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(exitListener);
    }
}
