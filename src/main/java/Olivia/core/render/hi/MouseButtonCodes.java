/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.hi;

import com.jogamp.newt.event.MouseEvent;

/**
 *
 * @author oscar.garcia
 */
public class MouseButtonCodes {
    
    public static boolean isMouseLeft(MouseEvent me){
        return(me.getButton()==1);
    }
    
    public static boolean isMouseRight(MouseEvent me){
        return(me.getButton()==3);
    }
    
    public static boolean isMouseCenter(MouseEvent me){
        return(me.getButton()==2);
    }
    
}
