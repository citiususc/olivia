/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core;

import Olivia.core.gui.controls.overlays.AnimationOptionsPanel;
import Olivia.core.render.OpenGLScreen;
import java.util.ArrayList;

/**
 * A especial case of OverlayArray which cycles through it overlays with the passage of time, making an animation.
 * In this class each overlay in the array can be considered a "frame" of the animation
 * It counts time in milliseconds.
 * CAREFUL: If you add the same overlay to many frames move and displace operations will be done many times.
 * May need to change this behaviour by separating frames (overlays) from the actual animation frames
 * @author oscar.garcia
 * @param <O> In some cases it may be needed to restrict the kind of overlays to use
 * @param <VM> In case it only works with an specific VisualisationManager
 */
public class AnimatedOverlay<O extends Overlay<VM>, VM extends VisualisationManager> extends OverlayArray<O,VM>{
    
    /**
    * The default time separation between frames, in milliseconds
    */
    public final static long DEFAULT_STEP=1000;
    
    /**
    * An overlay that will be rendered in all frames
    */
    protected O constantOverlay;
    
    /**
    * The timestamps of each frame are stored here,  must be a number between 0 and this.duration, and be in order, (this is not checked!) if the last timestamp is equal to the duration that frame will not be rendered
    */
    protected ArrayList<Long> timestamps;
    
    /**
    * Variables to save the animation times
    */
    protected Long currentTime, lastTime, duration, lastSystemTime;
    
    /**
     * The speed of the animation, it multimplies the elapsed time between calls to advanceTime() in drawShape()
     */
    protected Long speed;
    
    /**
     * Variables to control the animation
     */
    protected boolean loop, play, pause, ended;
    
    /**
     * Creates an instance of AnimatedOverlay
     * @param visualisationManager it is not directly referenced in this class, see super
     */
    public AnimatedOverlay(VM visualisationManager) {
        this(visualisationManager, "Animated Overlay");
    }
    
    /**
     * Creates an instance of AnimatedOverlay
     * @param visualisationManager it is not directly referenced in this class, see super
     * @param name The name of the Overlay
     */
    public AnimatedOverlay(VM visualisationManager, String name) {
        super(visualisationManager, name);
        timestamps = new ArrayList<>();
        loop = true;
        play = true;
        pause = false;
        ended = false;
        currentTime = 0l;
        lastTime = 0l;
        duration = 0l;
        lastSystemTime = 0l;
        speed = 1l;
    }

    /**
     * Returns the overlays that is rendered in all the frames, if any
     * @return The constant overlay, may be null
     */
    public O getConstantOverlay() {
        return constantOverlay;
    }

    /**
     * Returns the current time in the animation, between 0 and duration
     * @return The current time in milliseconds
     */
    public Long getCurrentTime() {
        return currentTime;
    }

    /**
     * Returns the duration of the animation, the time after which it will stop or loop
     * @return the duration of the animation, may be zero
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * Returns the speed of the animation, which multiplyies the elepased time
     * @return The current speed of the animation
     */
    public Long getSpeed() {
        return speed;
    }

    /**
     * Return whether it is looping, if true the animation will restart after reaching its duration
     * @return whether it is looping
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Return whether it is playing, if true the animation will advance in time
     * @return whether it is playing
     */
    public boolean isPlay() {
        return play;
    }

    /**
     * Return whether it is paused, if true the call to advanceTime() will just return
     * @return whether it is playing
     */
    public boolean isPause() {
        return pause;
    }

    /**
     * Sets an overlay that will be renderend in all the frames
     * @param constantOverlay The overlays that will always be rendered
     */
    public void setConstantOverlay(O constantOverlay) {
        this.constantOverlay = constantOverlay;
    }

    /**
     * Sets whether to loop the animation
     * @param loop True or false
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * Sets if the animation is playing, if true this.pause is set to false and, if the animation had ended, its currentTime is reset to zero and plays again 
     * @param play wheter to play or not
     */
    public void setPlay(boolean play) {
        this.play = play;
        if(play){
            this.pause = false;
            this.setLastSystemTime();
            if(ended){
                ended = false;
                currentTime = 0l;
            }
        }
    }

    /**
     * Sets if the animation is paused, if false this.play is set to true and, if the animation had ended, its currentTime is reset to zero and plays again 
     * @param pause 
     */
    public void setPause(boolean pause) {
        this.pause = pause;
        if(pause){
            this.play = true;
            this.setLastSystemTime();
            if(ended){
                ended = false;
                currentTime = 0l;
            }
        }
    }

    /**
     * Sets the current time , in the reference of the animation, does not check for inconsistencies!
     * @param currentTime Milliseconds between zero and duration
     */
    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Sets the duration of the animation, how long will it play
     * @param duration the duration of the animation
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     * Sets the speed of the animation, multiplies elapsed time
     * @param speed the speed of the animation
     */
    public void setSpeed(Long speed) {
        this.speed = speed;
    }
    
    /**
     * Sets the last system time, used, for example, to not count the time while the animation was paused
     */
    protected void setLastSystemTime(){
        lastSystemTime = System.currentTimeMillis();
    }
    
    /**
     * Gets how many milliseconds have elapsed since the lastSystemTime
     * @return how many milliseconds have elapsed since the lastSystemTime
     */
    protected long getElapsedTime(){
        return System.currentTimeMillis() - lastSystemTime;
    }

    /**
     * Sets the animation to a determined timestamp (in milliseconds), the current frame will be set as the one with the closed timestamp, rounding down; checks that it is a valid timestamp
     * @param timestamp a timestamp (in milliseconds) between zero and duration
     */
    public void gotoTime(long timestamp) {
        if((timestamp>duration)||(timestamp<=0)){
            System.out.println("Animation is not that long");
        }else{
            int i;
            for(i=0;i<timestamps.size()-1;i++){
                if(timestamp>=timestamps.get(i)){
                    if(timestamp<timestamps.get(i+1)){
                        this.setCurrentOverlay(i);
                    }
                }
            }
        }
    }

    /**
     * Adds a new Overlay, its timestamp is set to the current duration, and the duration is increased by DEFAULT_STEP
     * @param e The Overlay to be a frame in the animation
     * @return success
     */
    @Override
    public boolean add(O e) {
        if(super.add(e)){
            Long newTimestamp = duration;//starts in 0
            duration += DEFAULT_STEP;
            return timestamps.add(newTimestamp);
        }
        return false;
    }
    
    /**
     * Adds a new Overlay with its timestamp, duration is not increased!! needs to be done manually after, timestamps are not checked for consistency
     * @param e The Overlay to be a frame in the animation
     * @param timestamp the timestamp to give e, it is not checked for consistency
     * @return success
     */
    public boolean add(O e, long timestamp) {
        if(super.add(e)){
            return timestamps.add(timestamp);
        }
        return false;
    }
    
    /**
     * Advances the animation time (if it is play, !paused, !ended), sets the current overlay accoding to their timestamps;
     * if it reaches duration and is not loop, sets ended to true, if it is loop resets the currentTime to zero
     */
    protected void advanceTime(){
        if(!play) return;
        if(pause) return;
        if(ended) return;
        if(overlays.isEmpty()) return;
        Long elapsed = getElapsedTime();
        setLastSystemTime();
        currentTime += elapsed*speed;
        //reached the end
        if(currentTime >= duration){
            if(loop){
                currentTime = 0l;
            }else{
                ended = true;
                return;
            }
        } 
        if(overlays.size()>1){
            for(int i=0;i<overlays.size()-1;i++){
                if(currentTime>=timestamps.get(i)){
                    if(currentTime<timestamps.get(i+1)){
                        current = i;
                    }
                }
            }
            if(currentTime>timestamps.get(timestamps.size()-1)) current = timestamps.size()-1;
        }else{
            current = 0;
        }
    }

    /**
     * Draws the constantOverlay, advances time (which sets the current overlay depending on their timestamp) and then draws the current overlay
     * @param renderScreen The render screen where it will be rendered
     */
    @Override
    protected void drawShape(OpenGLScreen renderScreen){
        if(constantOverlay!=null){
            constantOverlay.draw(renderScreen);
        }
        advanceTime();
        if(!overlays.isEmpty()) overlays.get(current).draw(renderScreen);
    }


    /**
     * Creates an AnimationOptionsPanel to control the animation, this method is called by getOptionsPanel() if the panle does not exist
     */
    @Override
    protected void createOptionPanels() {
        super.createOptionPanels();
        AnimationOptionsPanel panel = new AnimationOptionsPanel(this);
        this.optionPanels.add(panel);
    }

 
        
}
