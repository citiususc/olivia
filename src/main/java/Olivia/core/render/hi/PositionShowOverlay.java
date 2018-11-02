/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render.hi;

import Olivia.core.VisualisationManager;
import Olivia.core.render.colours.PointColour;

/**
 *
 * @author oscar.garcia
 */
public class PositionShowOverlay extends PointSelectorOverlay{
    
    public PositionShowOverlay(VisualisationManager visualisationManager) {
        super(visualisationManager);
        arrow.setText("<");
        arrow2.setText("-");
        arrow.setColour(new PointColour(0.0f, 0.0f, 1.0f));
        arrow2.setColour(new PointColour(0.0f, 0.0f, 1.0f));
    }
    
}
