/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui;

import Olivia.core.TextOutputter;
import Olivia.core.gui.controls.ConsoleTextPane;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author oscar.garcia
 */
public class ConsoleFrame extends ExtraFrame implements TextOutputter{
    protected ConsoleTextPane consolePane;
    
    public ConsoleFrame(MainFrame gui){
        super(gui);
        this.setTitle("Console Output");
        //this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(500, 600);
        consolePane = new ConsoleTextPane();
        this.setContentPane(consolePane);
    }

    @Override
    public void println(String text) {
        println(text, Color.white);
    }

    @Override
    public void print(String text) {
        print(text, Color.white);
    }

    @Override
    public void println(String text, Color color) {
        consolePane.addText(text + "\n", color);
    }

    @Override
    public void print(String text, Color color) {
        consolePane.addText(text, color);
    }
    
    
    
}
