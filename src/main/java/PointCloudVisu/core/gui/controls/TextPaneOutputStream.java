/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PointCloudVisu.core.gui.controls;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JEditorPane;

/**
 *
 * @author oscar.garcia
 */
public class TextPaneOutputStream extends OutputStream{
    
    protected ConsoleTextPane textP;
    
    public TextPaneOutputStream(ConsoleTextPane textP) {
        this.textP = textP;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textP.addText(String.valueOf((char)b));
    }
    
}
