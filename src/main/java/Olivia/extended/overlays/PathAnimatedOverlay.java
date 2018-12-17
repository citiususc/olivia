/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import java.util.ArrayList;

/**
 * UNTESTED
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
    
    
    @Override
    public void gotoTime(long timestamp) {
        if((timestamps.size()!=frameInTimestamp.size())&(timestamps.size()!=positions.size())){
            System.out.println("Error in path animated overlay, timestamps and positions do not match");
            return;
        }
        if((timestamp>duration)||(timestamp<0)){
            System.out.println("Animation is not that long");
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
