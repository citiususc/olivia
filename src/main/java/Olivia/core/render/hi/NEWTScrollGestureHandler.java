/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.hi;

import Olivia.core.render.OpenGLScreen;
import com.jogamp.newt.event.DoubleTapScrollGesture;
import com.jogamp.newt.event.InputEvent;

/**
 *
 * @author oscar
 */
public class NEWTScrollGestureHandler extends DoubleTapScrollGesture{
    
    protected OpenGLScreen renderScreen;
    protected float speed = 0.5f;
    
    public NEWTScrollGestureHandler(OpenGLScreen renderScreen, int i, int i1) {
        super(i, i1);
        this.renderScreen = renderScreen;
    }
    

    @Override
    public boolean process(InputEvent ie) {
        if(super.process(ie)){
            //System.out.println("Scrolled " + this.getScrollDistanceXY()[0] + " " + this.getScrollDistanceXY()[1]);
            renderScreen.getCamera().addToTransX( - this.getScrollDistanceXY()[0] * speed);
            renderScreen.getCamera().addToTransY( this.getScrollDistanceXY()[1] * speed);
            return true;
        }else{
            return false;
        }
    }
    
}
