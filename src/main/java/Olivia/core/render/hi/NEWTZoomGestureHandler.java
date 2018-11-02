/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.hi;

import Olivia.core.render.OpenGLScreen;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.PinchToZoomGesture;

/**
 *
 * @author oscar
 */
public class NEWTZoomGestureHandler extends PinchToZoomGesture{
    
    protected OpenGLScreen renderScreen;
    //protected float previous_zoom;
    
    public NEWTZoomGestureHandler(OpenGLScreen renderScreen, NativeSurface ns, boolean bln) {
        super(ns, bln);
        this.renderScreen = renderScreen;
        //this.previous_zoom = this.getZoom();
    }
    
    @Override
    public boolean process(InputEvent ie) {
        if(super.process(ie)){
            //System.out.println("Zoom done: " + this.getZoom());
            float step = 800f;
            //float my_zoom = previous_zoom - this.getZoom();
            //previous_zoom = this.getZoom();
            float my_zoom = this.getZoom() - 1.0f;
            renderScreen.getCamera().setTransZ(my_zoom*step);
            return true;
        }else{
            return false;
        }
    }
    
}
