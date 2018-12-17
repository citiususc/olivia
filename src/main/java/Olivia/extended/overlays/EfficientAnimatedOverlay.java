/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.extended.overlays;

import Olivia.core.AnimatedOverlay;
import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import java.util.ArrayList;

/**
 * A version of AnimatedOverlay for when a frame (Overlay) repeats many times during the animation, only one copy of each repeated frame (Overlay) is kept in memory UNTESTED
 * @author oscar.garcia
 */
public class EfficientAnimatedOverlay<O extends Overlay<VM>,VM extends VisualisationManager> extends AnimatedOverlay<O,VM> {
    
    protected ArrayList<Integer> frameInTimestamp;
    
    /**
     * Creates an instance of AnimatedOverlay
     * @param visualisationManager it is not directly referenced in this class, see super
     */
    public EfficientAnimatedOverlay(VM visualisationManager) {
        this(visualisationManager, "E Animated Overlay");
    }
    
    public EfficientAnimatedOverlay(VM visualisationManager, String name) {
        super(visualisationManager, name);
        frameInTimestamp = new ArrayList<>();
    }


    @Override
    public boolean add(O e) {
        return this.add(e, true);
    }

    @Override
    public boolean add(O e, boolean isSelected) {
        boolean ret;
        if(this.overlays.contains(e)){
            Long newTimestamp = duration;//starts in 0
            duration += DEFAULT_STEP;
            ret = timestamps.add(newTimestamp);
            if(!ret) return ret;
        }else{
            ret = super.add(e,isSelected);
            if(!ret) return ret;
        }
        int pos = this.overlays.indexOf(e);
        frameInTimestamp.add(pos);
        return true;
    }

    @Override
    public boolean add(O e, long timestamp) {
        boolean ret;
        if(this.overlays.contains(e)){
            ret = timestamps.add(timestamp);
            if(!ret) return ret;
        }else{
            ret = super.add(e,timestamp);
            if(!ret) return ret;
        }
        int pos = this.overlays.indexOf(e);
        frameInTimestamp.add(pos);
        return true;
    }
    
    
    @Override
    public void gotoTime(long timestamp) {
        if(timestamps.size()!=frameInTimestamp.size()){
            System.out.println("Error in efficient animated overlay, timestamps and frames do not match");
            return;
        }
        if((timestamp>duration)||(timestamp<=0)){
            System.out.println("Animation is not that long");
        }else{
            int i;
            for(i=0;i<timestamps.size()-1;i++){
                if(timestamp>=timestamps.get(i)){
                    if(timestamp<timestamps.get(i+1)){
                        this.setCurrentOverlay(frameInTimestamp.get(i));
                    }
                }
            }
            if(timestamp>timestamps.get(timestamps.size()-1)) this.setCurrentOverlay(frameInTimestamp.get(timestamps.size()-1));
        }
    }
    
}
