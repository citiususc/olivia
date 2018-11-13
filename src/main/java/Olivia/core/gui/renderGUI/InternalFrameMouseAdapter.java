/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.renderGUI;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author oscar.garcia
 */
public class InternalFrameMouseAdapter extends MouseAdapter{
    protected InternalFrame frame;
    protected int h_add,w_add;
    protected boolean pressed_right,pressed_bottom,pressed_top,pressed_left;
    
    public InternalFrameMouseAdapter(InternalFrame frame){
        this.frame = frame;
        h_add=w_add=40;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) { 
        if(me.getClickCount()==2){
            int w = frame.getWidth();
            int h = frame.getHeight();
            int x = me.getPoint().x;
            int y = me.getPoint().y;
            Insets ins = frame.getInsets();
            //System.out.println(ins);
            int width_add=0;
            int height_add=0;
            if(y>h-ins.bottom){
                height_add = h_add; //bottom
            }else if(y<ins.top){
                height_add = h_add; //top
            }
            if(x<ins.left){
                width_add = w_add; //left
            }else if(x>w-ins.right){
                width_add = w_add;//right
            }
            //System.out.println(me.getPoint() + " w_add" + width_add + " h_add" + height_add);

            if(SwingUtilities.isRightMouseButton(me)){
              frame.setSize(new Dimension(frame.getWidth()-width_add, frame.getHeight()-height_add));
            }
            if(SwingUtilities.isLeftMouseButton(me)){
              frame.setSize(new Dimension(frame.getWidth()+width_add, frame.getHeight()+height_add));
            }
        }
        /*
        x=me.getX();
        y=me.getY();*/
    } 
            
    @Override
    public void mousePressed(MouseEvent me) {
        pressed_right=pressed_bottom=pressed_top=pressed_left=false;
        int w = frame.getWidth();
        int h = frame.getHeight();
        int x = me.getPoint().x;
        int y = me.getPoint().y;
        Insets ins = frame.getInsets();
        //System.out.println(ins);
        if(y>h-ins.bottom){
            pressed_bottom = true; //bottom
        }else if(y<ins.top){
            pressed_top = true; //top
        }
        if(x<ins.left){
            pressed_left = true; //left
        }else if(x>w-ins.right){
            pressed_right = true;//right
        }
        //System.out.println("Pressed in " +me.getPoint());
        
    }
    
    
    @Override
    public void mouseReleased(MouseEvent me) {
        int x = me.getPoint().x;
        int y = me.getPoint().y;
        int width_set=frame.getWidth();
        int height_set=frame.getHeight();
        int loc_x,loc_y;
        Point location = frame.getLocation();
        loc_x = location.x;
        loc_y = location.y;
        
        //System.out.println("Frame_location " +location);
        //System.out.println("Released in " +me.getPoint());
        
        if(pressed_bottom){
            height_set = y;
        }else if(pressed_top){
            loc_y = loc_y+y;
            height_set = frame.getHeight()-y;
        }
        
        if(pressed_right){
            width_set = x;
        }else if(pressed_left){
            loc_x = loc_x+x;
            width_set = frame.getWidth()-x;
        }
        
        if(width_set<frame.getMinimumSize().width) width_set = frame.getMinimumSize().width;
        if(width_set>frame.getMaximumSize().width) width_set = frame.getMaximumSize().width;
        if(height_set<frame.getMinimumSize().height) height_set = frame.getMinimumSize().height;
        if(height_set>frame.getMaximumSize().height) height_set = frame.getMaximumSize().height;
        
        if(loc_x > location.x+frame.getWidth()-frame.getMinimumSize().width) loc_x = location.x+frame.getWidth()-frame.getMinimumSize().width;
        if(loc_y > location.y+frame.getHeight()-frame.getMinimumSize().height) loc_y = location.y+frame.getHeight()-frame.getMinimumSize().height;
         
        frame.setSize(new Dimension(width_set, height_set));
        frame.setLocation(loc_x, loc_y);
    }
    
    
    
}
