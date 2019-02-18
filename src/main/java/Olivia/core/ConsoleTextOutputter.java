/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.gui.controls.ConsoleTextPane;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author oscar.garcia
 */
public class ConsoleTextOutputter implements TextOutputter{
    
    protected JFrame outputFrame;
    protected ConsoleTextPane consolePane;
    protected boolean terminalOutput;
    protected boolean guiOutput;
    
    public ConsoleTextOutputter(){
        outputFrame = new JFrame("Console output");
        outputFrame.setVisible(false);
        outputFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        outputFrame.setSize(500, 600);
        consolePane = new ConsoleTextPane();
        outputFrame.setContentPane(consolePane);
        this.guiOutput = true;
        this.terminalOutput = true;
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
        if(terminalOutput) System.out.println(text);
        if(guiOutput){
            outputFrame.setVisible(true);
            consolePane.addText(text + "\n", color);
        }
    }

    @Override
    public void print(String text, Color color) {
        if(terminalOutput) System.out.print(text);
        if(guiOutput){
            outputFrame.setVisible(true);
            consolePane.addText(text,color);
        }
    }

    public boolean isTerminalOutput() {
        return terminalOutput;
    }

    public void setTerminalOutput(boolean terminalOutput) {
        this.terminalOutput = terminalOutput;
    }

    public boolean isGuiOutput() {
        return guiOutput;
    }

    public void setGuiOutput(boolean guiOutput) {
        this.guiOutput = guiOutput;
    }
    
    

}
