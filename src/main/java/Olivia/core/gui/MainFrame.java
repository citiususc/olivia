package Olivia.core.gui;

import Olivia.core.Olivia;
import Olivia.core.gui.renderGUI.DesktopPane;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.gui.controls.overlays.OverlaysOptionsFrame;
import Olivia.core.gui.renderGUI.DetachedDesktopFrames;
import Olivia.core.gui.renderGUI.IndependentFrames;
import Olivia.core.render.OpenGLScreen;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

/**
 * This is the main frame for the Graphical User Interface, GUI
 * It has three modes, SINGLE_WINDOW, DETACHED_DESKTOP and DETACHED_INDEPENDENT
 * SINGLE_WINDOW has the entire GUI in one window, each visualisation render panel is added to an internal desktop
 * DETACHED_DESKTOP has the GUI controls in one window and other windows may have internal desktops with many visualisations each one
 * DETACHED_INDEPENDENT has the GUI controls in one window and other windows may have at most one visualisation each one
 * @author oscar.garcia
 */
public class MainFrame extends JFrame{
    
    /**
     * The SINGLE_WINDOW mode
     */
    public static int SINGLE_WINDOW = 0;
    /**
     * The DETACHED_DESKTOP mode
     */
    public static int DETACHED_DESKTOP = 1;
    /**
     * The DETACHED_INDEPENDENT mode
     */
    public static int DETACHED_INDEPENDENT = 2;
    

    /**
     * Title of the frame.
     */
    public static final String TITLE = "Olivia";
    
    protected Olivia olivia;
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
    /**
     * To indicate whether it is mirroring the cameras of all the visualisations
     */
    protected boolean isMirroring;
    /**
     * The currently active visualisation that is being controlled
     */
    private VisualisationManager activeVisualisationManager;
    /**
     * The Frame that contains the controls for whichever overlay is being controlled
     */
    protected OverlaysOptionsFrame overlayOptionsFrame;
    /**
     * The device (screen, usually) where the GUI is being rendered
     */
    protected GraphicsDevice device;
    /**
     * The size of the screen
     */
    protected Dimension screenSize;
    /**
     * The height of the OpenGL rendering window
     */
    protected int renderHeight;
    /**
     * Whether it is stereo 3D
     */
    protected boolean isStereo3D;
    /**
     * Whether it is in single window mode
     */
    protected boolean isSingleWindow;
    /**
     * Whether it is in detached independent mode
     */
    protected boolean isDetachedIndependent;
    /**
     * Whether it is in detached desktop mode
     */
    protected boolean isDetachedDesktop;
    /**
     * Whether the windows are decorated
     */
    protected boolean isUndecorated;
    /**
     * Whether it is in fullscreen mode
     */
    protected boolean isFullScreen;
    
    
    /**
     * Initialize the components.
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
        renderGUI.init();
    }
    
    /**
     * Creates a GUI in single window mode, in 2D and decorated, must call to initialize() afterwards.
     */
    public MainFrame(Olivia olivia){
        this(olivia,false,SINGLE_WINDOW,false);
    }

    /**
     * Creates a GUI , must call to initialize() afterwards.
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     * @param mode The mode, can be SINGLE_WINDOW, DETACHED_DESKTOP and DETACHED_INDEPENDENT
     * @param isUndecorated true to show windows without decoration
     */
    public MainFrame(Olivia olivia, boolean isStereo3D, int mode, boolean isUndecorated) {
        this.olivia = olivia;
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
            DetachedDesktopFrames detachedFrames = new DetachedDesktopFrames(this,screenSize,isUndecorated);
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
    
    /**
     * Creates a new render window (only in DETACHED_DESKTOP  mode)
     */
    public void createNewRenderWindow(){
        if(isDetachedDesktop){
            //buildRenderPane();
            //buildDetachedFrame(isUndecorated);
            renderGUI.createNewWindow();
        }
    }
    
    /**
     * Shows all the loaded visualisations, whether minimised or obscured
     */
    public void showAll(){
        renderGUI.showAll();
    }
    
    /**
     * Inits a visualistion, adding it to the GUI
     * @param visuManager The visualisation to visualise
     */
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
     * Sets up the render screen, adding the GUI listeners
     * @param renderScreen The OpenGL render screen
     */
    public void setupRenderScreen(OpenGLScreen renderScreen) {
        //this.renderScreen = renderScreen;
        //menuBar.renderScreen = renderScreen;
        //controlPanel.setupRenderScreen(renderScreen);
        renderScreen.addActionListener(controlPanel);
        renderScreen.addActionListener(menuBar);
    }
    
     /**
     * Gets the active render screen
     *
     * @return The active render screen, may be null
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
     * Updates the layout of the renderGUI, behaviour depends on the mode
     */
    public void updateRenderFrameLayout() {
        if (olivia.getLoadedVisualisations().isEmpty()) {
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
        for (VisualisationManager visuManager : olivia.getLoadedVisualisations()) {
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
        for (VisualisationManager visuManager : olivia.getLoadedVisualisations()) {
                visuManager.getRenderScreen().setShowPosition(false);
        }
    }
    
    /**
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
        for (VisualisationManager visuManager : olivia.getLoadedVisualisations()) {
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
     * Sets the active visualisation manager, the visualisation has to be loaded, the GUI controls are changed to reflect the new visualisation being controlled
     * @param visualisationManager the visualisation manager that will be active
     */
    public void setActiveVisualisationManager(VisualisationManager visualisationManager) {
        activeVisualisationManager = visualisationManager;
        if (olivia.getLoadedVisualisations().size() > 0) {
            //this.renderScreen = activeVisualisationManager.getRenderScreen();
            //menuBar.renderScreen = activeVisualisationManager.getRenderScreen();
            //controlPanel.setupRenderScreen(activeVisualisationManager.getRenderScreen());
            activeVisualisationManager.getRenderScreen().addActionListener(controlPanel);
            activeVisualisationManager.getRenderScreen().addActionListener(menuBar);
            //mainFrame.setupRenderScreen(activeVisualisationManager);
            splitPane2.setBottomComponent(activeVisualisationManager.getControlPane());
            //mainFrame.setVisibleVisuPanel(visualisations.indexOf(activeVisualisationManager));
            menuBar.setEnabledVisualisationMenus(true);
        }
    }
    
    /**
     * Removes the active visualisation manager from the GUI (does not unload it)
     * @param visualisationManager 
     */
    public void removeActiveVisualisationManager(VisualisationManager visualisationManager) {
        if(activeVisualisationManager == visualisationManager){
            if (olivia.getLoadedVisualisations().size() > 0){
                setActiveVisualisationManager(olivia.getLoadedVisualisations().get(0));
            }else{
                activeVisualisationManager = null;
                splitPane2.setBottomComponent(null);
                menuBar.setEnabledVisualisationMenus(false);
            }
        }
    }
    
    /**
     * Checks whether a visualisation is being controlled in this this moment
     * @param visualisationManager
     * @return true if visualisationManager is the active visualisation
     */
    public boolean isActiveVisualisationManager(VisualisationManager visualisationManager) {
        //System.out.println("Main Frame setting " + visualisationManager.getName() + " as active");
        return visualisationManager == activeVisualisationManager;
    }
    
    /**
     * Sets whether the overlay options frame is visible or not
     * @param visible whether the overlay options frame is visible or not
     */
    public void setOverlayOptionsVisible(boolean visible){
        this.overlayOptionsFrame.setVisible(visible);
    }
    
    /**
     * Gets whether the overlay options frame is visible or not
     * @return whether the overlay options frame is visible or not
     */
    public boolean isOverlayOptionsVisible(){
        return this.overlayOptionsFrame.isVisible();
    }
    
    /**
     * Updates the control panel and the overlay options frame to reflect changes
     */
    public void updateControlPanes(){
        this.controlPanel.update();
        this.overlayOptionsFrame.update();
    }
    
    /**
     * Updates the control panel, the menu bar and the overlay options frame to reflect changes
     */
    public void updateAll(){
        this.controlPanel.update();
        this.menuBar.update();
        this.overlayOptionsFrame.update();
    }

    /**
     * Checks whether DETACHED_DESKTOP mode is active
     * @return whether DETACHED_DESKTOP mode is active
     */
    public boolean isDetachedDesktop() {
        return isDetachedDesktop;
    }
    
    /**
     * Sets the MainFrame to fullscreen, does not work with detached windows
     * @param isFullScreen whether the GUI is fullscreen
     */
    public void setFullscreen(boolean isFullScreen){
        if(!device.isFullScreenSupported()) return;
        if(this.isSingleWindow){
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
    
    /**
     * Checks whether fullscreen mode is enabled in the graphics device
     * @return 
     */
    public boolean isFullScreenEnabled(){
        return(device.isFullScreenSupported());
    }
    
    public Olivia getOlivia(){
        return olivia;
    }
    
    public void loadCamera(File file){
        olivia.println("Opening camera file: " + file.getParent() + "/" + file.getName());
        getActiveVisualisation().getRenderScreen().getCamera().readFromFile(file.getParent(), file.getName());
    }
    
    public void saveCamera(File file){
        olivia.println("Saving camera file to: " + file.getParent() + "/" + file.getName());
        getActiveVisualisation().getRenderScreen().getCamera().writeToFile(file.getParent(), file.getName());
    }
    
    public void showSelectedPoint(boolean showSelectedPoint){
        getActiveVisualisation().setDrawMouseSelection(showSelectedPoint);
    }
    
    
}
