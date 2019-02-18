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
public interface TextOutputter {
    
    public void println(String text);
    public void print(String text);
    public void println(String text, Color color);
    public void print(String text, Color color);
    
}
