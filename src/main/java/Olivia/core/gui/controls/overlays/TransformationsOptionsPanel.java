/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui.controls.overlays;

import Olivia.core.render.Renderable;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author oscar.garcia
 */
public class TransformationsOptionsPanel extends JPanel implements ChangeListener{
    protected JSpinner rotXSpinner;
    protected JSpinner rotYSpinner;
    protected JSpinner rotZSpinner;
    protected JSpinner transXSpinner;
    protected JSpinner transYSpinner;
    protected JSpinner transZSpinner;
    
    protected SpinnerNumberModel rotXModel;
    protected SpinnerNumberModel rotYModel;
    protected SpinnerNumberModel rotZModel;
    protected SpinnerNumberModel transXModel;
    protected SpinnerNumberModel transYModel;
    protected SpinnerNumberModel transZModel;
    
    
    protected JLabel rotXLabel;
    protected JLabel rotYLabel;
    protected JLabel rotZLabel;
    protected JLabel transXLabel;
    protected JLabel transYLabel;
    protected JLabel transZLabel;
    
    protected Renderable renderable;
    
    public TransformationsOptionsPanel(Renderable renderable){     
        this.setName("Transformations");
        this.renderable = renderable;
        
        transXModel = new SpinnerNumberModel(0.0,-1000000.0,1000000.0,1.0);
        transYModel = new SpinnerNumberModel(0.0,-1000000.0,1000000.0,1.0);
        transZModel = new SpinnerNumberModel(0.0,-1000000.0,1000000.0,1.0);
        rotXModel = new SpinnerNumberModel(0.0,-360.0, 360.0, 1.0);
        rotYModel = new SpinnerNumberModel(0.0,-360.0, 360.0, 1.0);
        rotZModel = new SpinnerNumberModel(0.0,-360.0, 360.0, 1.0);
        
        rotXModel.setValue((double) renderable.getTransformations().getRotX());
        rotYModel.setValue((double) renderable.getTransformations().getRotY());
        rotZModel.setValue((double) renderable.getTransformations().getRotZ());
        transXModel.setValue((double) renderable.getTransformations().getTransX());
        transYModel.setValue((double) renderable.getTransformations().getTransY());
        transZModel.setValue((double) renderable.getTransformations().getTransZ());
        
        rotXSpinner = new JSpinner(rotXModel);
        rotXSpinner.setToolTipText("Set the rotation on X, in degrees");
        rotXSpinner.addChangeListener(this);
        rotYSpinner = new JSpinner(rotYModel);
        rotYSpinner.setToolTipText("Set the rotation on Y, in degrees");
        rotYSpinner.addChangeListener(this);
        rotZSpinner = new JSpinner(rotZModel);
        rotZSpinner.setToolTipText("Set the rotation on Z, in degrees");
        rotZSpinner.addChangeListener(this);
        transXSpinner = new JSpinner(transXModel);
        transXSpinner.setToolTipText("Set the translation on X");
        transXSpinner.addChangeListener(this);
        transYSpinner = new JSpinner(transYModel);
        transYSpinner.setToolTipText("Set the translation on Y");
        transYSpinner.addChangeListener(this);
        transZSpinner = new JSpinner(transZModel);
        transZSpinner.setToolTipText("Set the translation on Z");
        transZSpinner.addChangeListener(this);
        
        rotXLabel = new JLabel("Rotation X");
        rotXLabel.setLabelFor(rotXSpinner);
        rotYLabel = new JLabel("Rotation Y");
        rotYLabel.setLabelFor(rotYSpinner);
        rotZLabel = new JLabel("Rotation Z");
        rotZLabel.setLabelFor(rotZSpinner);
        transXLabel = new JLabel("Translation X");
        transXLabel.setLabelFor(transXSpinner);
        transYLabel = new JLabel("Translation Y");
        transYLabel.setLabelFor(transYSpinner);
        transZLabel = new JLabel("Translation Z");
        transZLabel.setLabelFor(transZSpinner);
        
        
        GridLayout layout = new GridLayout(2,0);
        this.setLayout(layout);
        this.add(transXLabel);
        this.add(transYLabel);
        this.add(transZLabel);
        this.add(rotXLabel);
        this.add(rotYLabel);
        this.add(rotZLabel);
        this.add(transXSpinner);
        this.add(transYSpinner);
        this.add(transZSpinner);
        this.add(rotXSpinner);
        this.add(rotYSpinner);
        this.add(rotZSpinner);     
    }
    
    public void setRenderable(Renderable renderable){
        this.renderable = renderable;
        rotXModel.setValue((double) renderable.getTransformations().getRotX());
        rotYModel.setValue((double) renderable.getTransformations().getRotY());
        rotZModel.setValue((double) renderable.getTransformations().getRotZ());
        transXModel.setValue((double) renderable.getTransformations().getTransX());
        transYModel.setValue((double) renderable.getTransformations().getTransY());
        transZModel.setValue((double) renderable.getTransformations().getTransZ());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if((renderable!=null)&(this.isShowing())){//To avoid passing argument before being displayed
            renderable.getTransformations().setRotX(rotXModel.getNumber().floatValue());
            renderable.getTransformations().setRotY(rotYModel.getNumber().floatValue());
            renderable.getTransformations().setRotZ(rotZModel.getNumber().floatValue());
            renderable.getTransformations().setTransX(transXModel.getNumber().floatValue());
            renderable.getTransformations().setTransY(transYModel.getNumber().floatValue());
            renderable.getTransformations().setTransZ(transZModel.getNumber().floatValue());
            //System.out.println("doing transformations:" + renderable.getBounds().getCentre() + "  " + renderable.getTransformations().getCentre());
        }
    }
    
}
