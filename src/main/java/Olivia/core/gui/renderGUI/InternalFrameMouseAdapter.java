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
 * An Mouse listener for the internal frames, needed mainly because there are issues with resize under Ubuntu 16 at least, maybe in more.
 * @author oscar.garcia
 */
public class InternalFrameMouseAdapter extends MouseAdapter{
    /**
     * The internal frame this is listening to
     */
    protected InternalFrame frame;
    /**
     * To save how much width and height will be added or subtracted in one operation
     */
    protected int h_add,w_add;
    /**
     * The size of the title bar (best guess)
     */
    protected int barSize = 20;
    /**
     * To save where in the border the mouse pressed
     */
    protected boolean pressed_right,pressed_bottom,pressed_top,pressed_left,pressed_move;
    /**
     * To save whether a resize operation has been started
     */
    protected boolean resize_started;
    /**
     * To save whether a move operation has been started
     */
    protected boolean move_started;
    /**
     * To save where a mouse event started
     */
    protected int mouse_start_x,mouse_start_y;
    
    /**
     * Creates a new instance of InternalFrameMouseAdapter
     * @param frame the frame it is listening to
     */
    public InternalFrameMouseAdapter(InternalFrame frame){
        this.frame = frame;
        h_add=w_add=100;
        resize_started=false;
    }
    
    /**
     * Increases the size with a Right double click, decreases the size with a Middle double click, in increments and depending where in the border it was clicked
     * @param me a mouse event
     */
    @Override
    public void mouseClicked(MouseEvent me) { 
        if(me.getClickCount()==2){
            resize_started=false;
            int w = frame.getWidth();
            int h = frame.getHeight();
            int width_set=frame.getWidth();
            int height_set=frame.getHeight();
            int x = me.getPoint().x;
            int y = me.getPoint().y;
            Insets ins = frame.getInsets();
            int loc_x,loc_y;
            loc_x = 0;
            loc_y = 0;
            //Olivia.textOutputter.println(ins);
            int width_add=0;
            int height_add=0;
            if(y>h-ins.bottom){
                height_add = h_add; //bottom
            }else if(y<ins.top){
                height_add = h_add; //top
                loc_y = h_add;
            }
            if(x<ins.left){
                width_add = w_add; //left
                loc_x = w_add;
            }else if(x>w-ins.right){
                width_add = w_add;//right
            }
            //Olivia.textOutputter.println(me.getPoint() + " w_add" + width_add + " h_add" + height_add);

            if(SwingUtilities.isRightMouseButton(me)){
                width_set = frame.getWidth()-width_add;
                height_set = frame.getHeight()-height_add;
                loc_x = frame.getLocation().x + loc_x;
                loc_y = frame.getLocation().y + loc_y;
            }
            if(SwingUtilities.isMiddleMouseButton(me)){
                width_set = frame.getWidth()+width_add;
                height_set = frame.getHeight()+height_add;
                loc_x = frame.getLocation().x - loc_x;
                loc_y = frame.getLocation().y - loc_y;
            }
            
            
            resize(width_set,height_set, loc_x, loc_y);
        }
    } 
            
    /**
     * Begins a resize or move operation with the left button, detects where in the border it was pressed
     * @param me 
     */
    @Override
    public void mousePressed(MouseEvent me) {
        if( (me.getClickCount()==1)&SwingUtilities.isLeftMouseButton(me)){
            pressed_right=pressed_bottom=pressed_top=pressed_left=false;
            int w = frame.getWidth();
            int h = frame.getHeight();
            int x = me.getPoint().x;
            int y = me.getPoint().y;
            Insets ins = frame.getInsets();
            //Olivia.textOutputter.println(ins);
            if(y>h-ins.bottom){
                pressed_bottom = true; //bottom
                resize_started=true;
            }else if(y<ins.top){
                pressed_top = true; //top
                resize_started=true;
            }else if(y<(ins.top+barSize)){
                pressed_move = true; //top
                move_started = true;
                //Olivia.textOutputter.println("moved");
            }         
            if(x<ins.left){
                pressed_left = true; //left
                resize_started=true;
            }else if(x>w-ins.right){
                pressed_right = true;//right
                resize_started=true;
            }
            mouse_start_x=x;
            mouse_start_y=y;
        }
        //Olivia.textOutputter.println("Pressed in " +me.getPoint());
        
    }
    
    /**
     * Ends the resize or move operation, depending on where the mouse stopped
     * @param me 
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        int x = me.getPoint().x;
        int y = me.getPoint().y;
        Point location = frame.getLocation();
        int loc_x = location.x;
        int loc_y = location.y;
        if(resize_started&SwingUtilities.isLeftMouseButton(me)){
            resize_started=false;
            int width_set=frame.getWidth();
            int height_set=frame.getHeight();

            //Olivia.textOutputter.println("Frame_location " +location);
            //Olivia.textOutputter.println("Released in " +me.getPoint());

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

            resize(width_set,height_set,loc_x,loc_y);
        }else if(move_started&SwingUtilities.isLeftMouseButton(me)){
            loc_x = loc_x+(x-mouse_start_x);
            loc_y = loc_y+(y-mouse_start_y);
            frame.setLocation(loc_x, loc_y);
        }
    }
    
    /**
     * Resizes the InternalFrame, checks for bound so minimum and maximum sizes are no exceeded
     * @param width The new width
     * @param height The new Height
     * @param loc_x The new location on x
     * @param loc_y  The new location on y
     */
    protected void resize(int width, int height, int loc_x, int loc_y){
        if(width<frame.getMinimumSize().width) width = frame.getMinimumSize().width;
        if(width>frame.getMaximumSize().width) width = frame.getMaximumSize().width;
        if(height<frame.getMinimumSize().height) height = frame.getMinimumSize().height;
        if(height>frame.getMaximumSize().height) height = frame.getMaximumSize().height;
        
        if(loc_x > frame.getLocation().x+frame.getWidth()-frame.getMinimumSize().width) loc_x = frame.getLocation().x+frame.getWidth()-frame.getMinimumSize().width;
        if(loc_y > frame.getLocation().y+frame.getHeight()-frame.getMinimumSize().height) loc_y = frame.getLocation().y+frame.getHeight()-frame.getMinimumSize().height;
         
        frame.setSize(new Dimension(width, height));
        frame.setLocation(loc_x, loc_y);
    }
    
    /**
     * Resizes the InternalFrame, checks for bound so minimum and maximum sizes are no exceeded
     * @param width The new width
     * @param height The new Height
     */
    protected void resize(int width, int height){
        resize(width,height,frame.getLocation().x,frame.getLocation().y);
    }
    
}
