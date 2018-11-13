package Olivia.core.gui;

import Olivia.core.Olivia;
import Olivia.core.gui.renderGUI.DesktopPane;
import static Olivia.core.Olivia.getLoadedVisualisations;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.gui.controls.overlays.OverlaysOptionsFrame;
import Olivia.core.gui.renderGUI.DetachedFrames;
import Olivia.core.gui.renderGUI.FrameAdapter;
import Olivia.core.gui.renderGUI.FrameEventListener;
import Olivia.core.gui.renderGUI.IndependentFrames;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.hi.NEWTMouseListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

/**
 * This is the main frame for the controls, takes care of adding the components
 *
 * @author oscar.garcia
 */
public class MainFrame extends JFrame{
    
    public static int SINGLE_WINDOW = 0;
    public static int DETACHED_DESKTOP = 1;
    public static int DETACHED_INDEPENDENT = 2;
    

    /**
     * Title of the frame.
     */
    public static final String TITLE = "Olivia";
    /**
     * The OpenGL render screen.
     */
    //protected OpenGLScreen renderScreen;
    /**
     * The menu bar.
     */
    protected MainMenuBar menuBar;
    /**
     * The main controls
     */
    protected MainControl controlPanel;
    /**
     * The pane holding the render screen or screens
     */
    protected RenderGUI renderGUI;
    /**
     * The second split pane where visualisation panels are added
     */
    protected JSplitPane splitPane2;
    
    protected boolean isMirroring;
    
    private VisualisationManager activeVisualisationManager;
    
    protected OverlaysOptionsFrame overlayOptionsFrame;
    
    protected GraphicsDevice device;
    
    protected Dimension screenSize;
    protected int renderHeight;
    
    protected boolean isStereo3D; 
    protected boolean isSingleWindow;
    protected boolean isDetachedIndependent;
    protected boolean isDetachedDesktop;
    protected boolean isUndecorated;
    
    protected boolean isFullScreen;
    
    
    /**
     * Initialize the components.
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public void initialize(){
        /*addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (activeVisualisationManager != null && e.getNewState() == Frame.MAXIMIZED_BOTH) {
                    try {
                        //MainFrame.this.activeVisualisationManager.getRenderScreen().getFrame().setMaximum(true);
                    }
                    catch (PropertyVetoException ex) {
                    }
                }
            }
        });*/
        //this.addKeyListener(keyListener);
        setContentPane(splitPane2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                renderGUI.close();
            }
        });
        setJMenuBar(menuBar);
        if (isSingleWindow) {
            //setSize(screenSize.width, screenSize.height);
            setSize(1600, 900); // HD+
            setTitle(TITLE);
        } else {
            setSize(screenSize.width, screenSize.height / 3);
            setTitle(TITLE + " control");
        }
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public MainFrame(){
        this(false,SINGLE_WINDOW,false);
    }

    /**
     * Create the main frame. Must call to initialize() afterwards.
     *
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public MainFrame(boolean isStereo3D, int mode, boolean isUndecorated) {
        this.isStereo3D = isStereo3D;
        this.isSingleWindow = this.isDetachedDesktop = this.isDetachedIndependent = false;
        if(mode==SINGLE_WINDOW){
            this.isSingleWindow = true;
        }else if(mode==DETACHED_DESKTOP){
            this.isDetachedDesktop = true;
        }else if(mode==DETACHED_INDEPENDENT){
            this.isDetachedIndependent = true;
        }else{
            this.isSingleWindow = true;
        }
        this.isUndecorated = isUndecorated;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        device = devices[0];
        this.isFullScreen = false;
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //int renderHeight = screenSize.height - menuBar.getHeight() - controlPanel.getHeight();
        renderHeight =Math.round(screenSize.height*0.75f);
   
        menuBar = new MainMenuBar(this);
        menuBar.initialize();
        controlPanel = new MainControl(this);
        controlPanel.initialize();
        //buildRenderPane();
        
        overlayOptionsFrame = new OverlaysOptionsFrame(this);

        if (isSingleWindow) {
            DesktopPane desktopPane = new DesktopPane(this);
            desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
            desktopPane.setMinimumSize(new Dimension(Math.round(screenSize.width*0.25f), Math.round(renderHeight*0.25f)));
            desktopPane.setPreferredSize(new Dimension(screenSize.width, renderHeight));
            desktopPane.setSize(new Dimension(screenSize.width, renderHeight));
            renderGUI = desktopPane;
            JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane1.setTopComponent(controlPanel);
            controlPanel.setPreferredSize(new Dimension(screenSize.width, Math.round(screenSize.height*0.25f)));
            controlPanel.setSize(new Dimension(screenSize.width, Math.round(screenSize.height*0.25f)));
            //splitPane1.setDividerSize(5);
            splitPane1.setBottomComponent((Component)renderGUI);
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(splitPane1);
            splitPane2.setBottomComponent(null);
            splitPane2.setResizeWeight(1);
            //splitPane1.setDividerLocation(0.2);
        } else if(isDetachedDesktop){
            DetachedFrames detachedFrames = new DetachedFrames(this,screenSize,isUndecorated);
            detachedFrames.scaleMinimumSize(0.2f);
            detachedFrames.scalePreferredSize(0.8f);
            renderGUI = detachedFrames;
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(controlPanel);
            splitPane2.setBottomComponent(null);
            //splitPane2.setResizeWeight(1);
            this.setTitle(TITLE + " control");
        }else if(isDetachedIndependent){
            IndependentFrames independentFrames = new IndependentFrames(this,screenSize,isUndecorated);
            independentFrames.scaleMinimumSize(0.2f);
            independentFrames.scalePreferredSize(0.8f);
            renderGUI = independentFrames;
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(controlPanel);
            splitPane2.setBottomComponent(null);
            //splitPane2.setResizeWeight(1);
            this.setTitle(TITLE + " control");
        }
        isMirroring = false;
    }
    
    public void createNewRenderWindow(){
        if(isDetachedDesktop){
            //buildRenderPane();
            //buildDetachedFrame(isUndecorated);
            renderGUI.createNewWindow();
        }
    }
    
    public void initVisualisation(VisualisationManager visuManager) {
        System.out.println("Main Frame inits " + visuManager.getName());
        visuManager.getControlPane().setPreferredSize(new Dimension(visuManager.getControlPane().getWidth(), 150));
        TitledBorder border = new TitledBorder(visuManager.getName());
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        visuManager.getControlPane().setBorder(border);
        //visuPanels.add(visuManager.getControlPane());
        //splitPane2.setBottomComponent(visuPanels.get(visuPanels.size() - 1));
        //visuManager.getRenderScreen().createRenderFrame();
        renderGUI.addVisualisation(visuManager);
    }

    /**
     * setter
     *
     * @param renderScreen The OpenGL render screen
     */
    public void setRenderScreen(OpenGLScreen renderScreen) {
        //this.renderScreen = renderScreen;
        //menuBar.renderScreen = renderScreen;
        //controlPanel.setRenderScreen(renderScreen);
        renderScreen.addActionListener(controlPanel);
        renderScreen.addActionListener(menuBar);
    }
    
        /**
     * getter
     *
     * @return The active render screen
     */
    public VisualisationManager getActiveVisualisation() {
        return activeVisualisationManager;
    }

    /**
     * getter
     *
     * @return The pane holding the render screens
     */
    /*
    public JDesktopPane getRenderPane() {
        return renderGUI;
    }
    */

    /**
     * Add the line into the output console (with line break)
     *
     * @param line A string
     */
    public void logIntoConsole(String line) {
        controlPanel.getConsolePane().addText(line + '\n');
    }
    
        /**
     * Resizes the render screens to fit them all inside the render frame
     */
    public void updateRenderFrameLayout() {
        if (getLoadedVisualisations().isEmpty()) {
            return;
        }
        renderGUI.updateRenderLayout();
    }
    
     /**
     * getter
     *
     * @return The flag to indicate if camera mirroring is enabled
     */
    public boolean isMirroring() {
        return isMirroring;
    }
    
     /**
     * Enables/disables camera mirroring
     */
    public void toogleMirroring() {
        isMirroring = !isMirroring;
        if(!isMirroring) undoCameraMirroring();
        this.logIntoConsole("Camera mirroring: " + isMirroring);
    }

    /**
     * Syncronize all the cameras with the active render screen camera, sets the selected point in other visualisations as the closest one;
     * depending on the behaviour of Olivia.core.render.Camera.copyActiveCamera() it may displace the other visualisations (changes their data)
     * @see Olivia.core.render.Camera.copyActiveCamera()
     */
    protected void doCameraMirroring() {
        Point3D_id point = activeVisualisationManager.getRenderScreen().getSelectedPoint();
        activeVisualisationManager.getRenderScreen().setShowPosition(false);
        for (VisualisationManager visuManager : getLoadedVisualisations()) {
            if (visuManager != activeVisualisationManager) {
                visuManager.getRenderScreen().getCamera().copyActiveCamera();
                //visuManager.getRenderScreen().setSelectedPoint(point);
                visuManager.getRenderScreen().setPosition(point);
                visuManager.getRenderScreen().setShowPosition(true);
            }
        }
    }
    
    /**
     * Makes sure all visualisations know the camera is not being mirrored anymore
     */
    protected void undoCameraMirroring(){
        for (VisualisationManager visuManager : getLoadedVisualisations()) {
                visuManager.getRenderScreen().setShowPosition(false);
        }
    }
    
    /*
    * To invoke when the camera has moved
    */
    public void cameraMoved(){
        if(isMirroring){
            doCameraMirroring();
        }
    }
    
        /**
     * Syncronize the selection events with the active render screen selection
     * <p>
     * Note: This only works right if the point cloud is the same, otherwise the
     * centre of mass will differ thus window coordinates will differ
     * </p>
     *
     * @param eventNames The array with the event names
     */
    public void doSelectionMirroring(String[] eventNames) {
        for (VisualisationManager visuManager : getLoadedVisualisations()) {
            if (visuManager != activeVisualisationManager) {
                /*activeVisualisationManager.getRenderScreen().getMouseListener().getMouseSelection().pick(activeVisualisationManager.getRenderScreen().getMouseListener().getWindowX(),
                        activeVisualisationManager.getRenderScreen().getMouseListener().getWindowY(),
                        NEWTMouseListener.MOUSE_PICK_RADIUS,
                        NEWTMouseListener.MOUSE_PICK_EPSILON);*/
                activeVisualisationManager.getRenderScreen().doMouseSelection();
                for (String eventName : eventNames) {
                    activeVisualisationManager.getRenderScreen().fireEvent(eventName);
                }
            }
        }
    }
    
    /**
     * setter
     *
     */
    public void setActiveVisualisationManager(VisualisationManager visualisationManager) {
        activeVisualisationManager = visualisationManager;
        if (getLoadedVisualisations().size() > 0) {
            //this.renderScreen = activeVisualisationManager.getRenderScreen();
            //menuBar.renderScreen = activeVisualisationManager.getRenderScreen();
            //controlPanel.setRenderScreen(activeVisualisationManager.getRenderScreen());
            activeVisualisationManager.getRenderScreen().addActionListener(controlPanel);
            activeVisualisationManager.getRenderScreen().addActionListener(menuBar);
            //mainFrame.setRenderScreen(activeVisualisationManager);
            splitPane2.setBottomComponent(activeVisualisationManager.getControlPane());
            //mainFrame.setVisibleVisuPanel(visualisations.indexOf(activeVisualisationManager));
            menuBar.setEnabledVisualisationMenus(true);
        }
    }
    
    public void removeActiveVisualisationManager(VisualisationManager visualisationManager) {
        if(activeVisualisationManager == visualisationManager){
            if (getLoadedVisualisations().size() > 0){
                setActiveVisualisationManager(getLoadedVisualisations().get(0));
            }else{
                activeVisualisationManager = null;
                splitPane2.setBottomComponent(null);
                menuBar.setEnabledVisualisationMenus(false);
            }
        }
    }
    

    
    public boolean isActiveVisualisationManager(VisualisationManager visualisationManager) {
        System.out.println("Main Frame setting " + visualisationManager.getName() + " as active");
        return visualisationManager == activeVisualisationManager;
    }
    
    
    
    public void setOverlayOptionsVisible(boolean visible){
        this.overlayOptionsFrame.setVisible(visible);
    }
    
    public boolean isOverlayOptionsVisible(){
        return this.overlayOptionsFrame.isVisible();
    }
    
    public void updateControlPanes(){
        this.controlPanel.update();
        this.overlayOptionsFrame.update();
    }
    
    public void updateAll(){
        this.controlPanel.update();
        this.menuBar.update();
        this.overlayOptionsFrame.update();
    }

    public boolean isDetached() {
        return isDetachedDesktop;
    }
    
    /**
     * To be used with MainFRame the main frame should be a different class
     * @param isFullScreen 
     */
    /*
    public void setFullscreen(boolean isFullScreen){
        if(isStereo3D){
            if(!device.isFullScreenSupported()) return;
            //this.dispose();
            //setUndecorated(isFullScreen);
            //setResizable(!isFullScreen);
            if (isFullScreen) {
                // Full-screen mode
                this.buildDetachedFrame();
                device.setFullScreenWindow(this.detachedFrame);
                device.
                validate();
            } else {
                // Windowed mode
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                //int renderHeight = screenSize.height - menuBar.getHeight() - controlPanel.getHeight();
                int renderHeight =Math.round(screenSize.height*(3/4));
                this.buildDetachedFrame(screenSize, renderHeight);
                pack();
                setVisible(true);
            }
        }
    }
*/
    
    
    
    public void setFullscreen(boolean isFullScreen){
        if(!device.isFullScreenSupported()) return;
        if(this.isDetachedDesktop){
            /*if(isFullScreen){
                device.setFullScreenWindow(detachedFrames.get(detachedFrames.size()-1));
            }else{
                device.setFullScreenWindow(null);
            }
            detachedFrames.get(detachedFrames.size()-1).validate(); */
        }else{
            if(isFullScreen){
                device.setFullScreenWindow(this);
            }else{
                device.setFullScreenWindow(null);
            }
            validate();    
        }
        this.isFullScreen = isFullScreen;
        menuBar.checkFullScreenMenu(isFullScreen);
    }
    /*
    public void toggleFullScreen(){
        setFullscreen(!isFullScreen);
    }
    */
    
    public boolean isFullScreenEnabled(){
        return(device.isFullScreenSupported());
    }
    
    
    
}
