/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import java.util.ArrayList;

/**
 * UNTESTED An animation that keeps a "path", an ArrayList of Points3D, corresponding one to one with the timestamps
 * to move the frames (Overlay), so a same frame can move as an animation. A better version should probably keep an
 * array of Transformation to allow for more control
 * @author oscar.garcia
 */
public class PathAnimatedOverlay<O extends Overlay<VM>,VM extends VisualisationManager> extends EfficientAnimatedOverlay<O,VM> {
    
    protected ArrayList<Point3D> positions;
    
    public PathAnimatedOverlay(VM visualisationManager, String name) {
        super(visualisationManager, name);
        positions = new ArrayList<>();
    }

    @Override
    public boolean add(O e) {
        return this.add(e, true);
    }

    /**
     * Adds a new frame (or an existing frame in a new timestamp) with a position on its centre
     * @param e the frame (Overlay)
     * @param isSelected whether it is selected
     * @return true if everything ok
     */
    @Override
    public boolean add(O e, boolean isSelected) {
        if(super.add(e,isSelected)){
            return positions.add(e.getBounds().getCentre());
        }
        return false;
    }

    @Override
    public boolean add(O e, long timestamp) {
        if(super.add(e,timestamp)){
            return positions.add(e.getBounds().getCentre());
        }
        return false;
    }
    
    /**
     * Adds a new frame (or an existing frame in a new timestamp) that will move to position in timestamp
     * @param e the frame (Overlay)
     * @param timestamp The timestamp when this frame will be visible
     * @param position The position this frame will have in the specified timestamp
     * @return 
     */
    public boolean add(O e, long timestamp, Point3D position) {
        if(super.add(e,timestamp)){
            return positions.add(position);
        }
        return false;
    }
    
    public boolean add(O e, Point3D position) {
        if(super.add(e,true)){
            return positions.add(position);
        }
        return false;
    }
    
    /**
     * Sets the animation to a determined timestamp (in milliseconds), the current frame will be set as the one with
     * the closed timestamp, rounding down; checks that it is a valid timestamp; the current frame will be moved to
     * the position in the same index as the timestamp
     * @param timestamp a timestamp (in milliseconds) between zero and duration
     */
    @Override
    public void gotoTime(long timestamp) {
        if((timestamps.size()!=frameInTimestamp.size())&(timestamps.size()!=positions.size())){
            Olivia.textOutputter.println("Error in path animated overlay, timestamps and positions do not match");
            return;
        }
        if((timestamp>duration)||(timestamp<0)){
            Olivia.textOutputter.println("Animation is not that long");
        }else{
            int i;
            for(i=0;i<timestamps.size()-1;i++){
                if(timestamp>=timestamps.get(i)){
                    if(timestamp<timestamps.get(i+1)){
                        this.setCurrentOverlay(frameInTimestamp.get(i));
                        overlays.get(current).moveTo(positions.get(i));
                    }
                }
            }
            if(timestamp>timestamps.get(timestamps.size()-1)){
                this.setCurrentOverlay(frameInTimestamp.get(timestamps.size()-1));
                overlays.get(current).moveTo(positions.get(timestamps.size()-1));
            }
        }
    }


}
