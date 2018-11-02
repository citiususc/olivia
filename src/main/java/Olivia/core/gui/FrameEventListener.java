/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.gui;

import Olivia.core.Olivia;
import Olivia.core.VisualisationManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author oscar.garcia
 */
public class FrameEventListener implements InternalFrameListener {
        protected MainFrame gui;
        protected VisualisationManager visualisationManager;
    
        public FrameEventListener(VisualisationManager visualisationManager){
            this.gui = visualisationManager.getGUI(); //only acts with the visualisation gui
            this.visualisationManager = visualisationManager;
        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
            System.out.println(visualisationManager.getId() + " activated");
            if (Olivia.getLoadedVisualisations().contains(visualisationManager) && visualisationManager != gui.getActiveVisualisation()) {
                gui.setActiveVisualisationManager(visualisationManager);
                gui.updateAll();
            }
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
            System.out.println(visualisationManager.getId() + " closed");
            gui.updateAll();
        }

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            System.out.println(visualisationManager.getId() + " closing");
            Olivia.removeVisualisation(visualisationManager);
            gui.updateAll();
            //animator.stop();
            //frame.dispose();
            //removeScreen(OpenGLScreen.this);
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {
            System.out.println(visualisationManager.getId() + " deactivated");
            gui.updateAll();
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {
            //OpenGLScreen.this.frame.setSize(640, 480);
            if (visualisationManager != null) {
                visualisationManager.repack();
            }
            gui.updateAll();
            System.out.println(visualisationManager.getId() + " deiconified");
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {
            System.out.println(visualisationManager.getId() + " iconified");
            gui.updateAll();
        }

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
            System.out.println(visualisationManager.getId() + " opened");
            gui.updateAll();
        }
    }
