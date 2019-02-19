/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.gui.ConsoleFrame;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
public class ConsoleTextOutputter implements TextOutputter{
    
    protected ArrayList<TextOutputter> textOuts;
    protected ArrayList<Boolean> selected;
    
    //protected ConsoleFrame outputFrame;
    protected boolean terminalOutput;
    //protected boolean guiOutput;
    
    public ConsoleTextOutputter(){
        this.terminalOutput = true;
        textOuts = new ArrayList<>();
        selected = new ArrayList<>();
    }
    
    public boolean addConsoleTextOutputter(TextOutputter textOut){
        boolean ret;
        ret = textOuts.add(textOut);
        if(ret) ret = selected.add(true);
        return ret;
    }
    
    public boolean removeConsoleTextOutputter(TextOutputter textOut){
        int ret;
        ret = textOuts.indexOf(textOut);
        if(ret !=-1){
            textOuts.remove(ret);
            selected.remove(ret);
            return true;
        }
        return false;
    }
    
    public int selectConsoleTextOutputter(TextOutputter textOut, boolean isSelected){
        int ret;
        ret = textOuts.indexOf(textOut);
        if(ret !=-1) selected.set(ret, isSelected);
        return ret;
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
        for(int i = 0; i<textOuts.size(); i++){
            if(selected.get(i)==true) textOuts.get(i).println(text, color);
        }
    }

    @Override
    public void print(String text, Color color) {
        if(terminalOutput) System.out.print(text);
        for(int i = 0; i<textOuts.size(); i++){
            if(selected.get(i)==true) textOuts.get(i).print(text, color);
        }
    }
    
    public boolean isTerminalOutput() {
        return terminalOutput;
    }

    public void setTerminalOutput(boolean terminalOutput) {
        this.terminalOutput = terminalOutput;
    }

}
