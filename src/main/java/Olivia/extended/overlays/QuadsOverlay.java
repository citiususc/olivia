/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.VisualisationManager;
import Olivia.core.render.OpenGLScreen;

/**
 *
 * @author oscar.garcia
 */
public class QuadsOverlay<VM extends VisualisationManager> extends VertexOverlay<VM>{
    
    public QuadsOverlay(VM visualisationM) {
        super(visualisationM);
    }

    public QuadsOverlay(VM visualisationM, String name) {
        super(visualisationM, name);
    }

    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        super.drawShape(GL_QUADS,GL_FILL);
    }
    
}
