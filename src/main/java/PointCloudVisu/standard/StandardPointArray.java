/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PointCloudVisu.standard;

import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.extended.PointStandard;

/**
 *
 * @author oscar.garcia
 */
public class StandardPointArray<P extends PointStandard> extends PointArray<P> {
    
    protected boolean have_intensity, have_returns, have_scanlines, have_classification, have_RGB;

    public void setHave_intensity(boolean have_intensity) {
        this.have_intensity = have_intensity;
    }

    public void setHave_returns(boolean have_returns) {
        this.have_returns = have_returns;
    }

    public void setHave_scanlines(boolean have_scanlines) {
        this.have_scanlines = have_scanlines;
    }

    public void setHave_classification(boolean have_classification) {
        this.have_classification = have_classification;
    }

    public void setHave_RGB(boolean have_RGB) {
        this.have_RGB = have_RGB;
    }

    public boolean isHave_intensity() {
        return have_intensity;
    }

    public boolean isHave_returns() {
        return have_returns;
    }

    public boolean isHave_scanlines() {
        return have_scanlines;
    }

    public boolean isHave_classification() {
        return have_classification;
    }

    public boolean isHave_RGB() {
        return have_RGB;
    }
    
    public StandardPointArray(){
        super();
        have_intensity = false;
        have_returns = false;
        have_scanlines = false;
        have_classification = false;
        have_RGB = false;
    }    
    
    
}
