/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import java.awt.Color;

/**
 *
 * @author oscar.garcia
 */
public class Message {
    protected String text;
    protected Color color;
    
    public Message(String text, Color color){
        this.text = text;
        this.color = color;
    }
    
    public Message(String text){
        this(text,Color.white);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
