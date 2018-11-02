package Olivia.core.gui;

import static Olivia.core.Olivia.getLoadedVisualisations;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import Olivia.core.gui.controls.overlays.OverlaysOptionsFrame;
import Olivia.core.render.OpenGLScreen;
import Olivia.core.render.hi.NEWTMouseListener;
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
     * The pane holding the render screens
     */
    protected JDesktopPane renderPane;
    /**
     * The array containing the different visualisation panels
     */
    //protected ArrayList<JPanel> visuPanels;
    /**
     * The second split pane where visualisation panels are added
     */
    protected JSplitPane splitPane2;
    /**
     * The frame holding the render screens when they are decoupled from the GUI
     */
    protected JFrame stereoFrame;
    
    protected boolean isMirroring;
    
    private VisualisationManager activeVisualisationManager;
    
    protected OverlaysOptionsFrame overlayOptionsFrame;
    
    private GraphicsDevice device;
    
    private boolean isStereo3D; 
    private boolean isFullScreen;

    /**
     * Initialize the components.
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public void initialize(boolean isStereo3D) {
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (activeVisualisationManager != null && e.getNewState() == Frame.MAXIMIZED_BOTH) {
                    try {
                        MainFrame.this.activeVisualisationManager.getRenderScreen().getFrame().setMaximum(true);
                    }
                    catch (PropertyVetoException ex) {
                    }
                }
            }
        });
        this.isStereo3D = isStereo3D;
        //this.addKeyListener(keyListener);
        setContentPane(splitPane2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (!isStereo3D) {
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

    /**
     * Create the main frame. Must call to initialize() afterwards.
     *
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public MainFrame(boolean isStereo3D) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        device = devices[0];
        this.isFullScreen = false;
   
        menuBar = new MainMenuBar(this);
        menuBar.initialize();
        controlPanel = new MainControl(this);
        controlPanel.initialize();
        //visuPanels = new ArrayList();
        renderPane = new JDesktopPane();
        renderPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        
        overlayOptionsFrame = new OverlaysOptionsFrame(this);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //int renderHeight = screenSize.height - menuBar.getHeight() - controlPanel.getHeight();
        int renderHeight =Math.round(screenSize.height*(3/4));
        renderPane.setMinimumSize(new Dimension(screenSize.width, renderHeight));

        if (!isStereo3D) {
            JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane1.setTopComponent(controlPanel);
            controlPanel.setPreferredSize(new Dimension(screenSize.width, Math.round(screenSize.height*(1/4))));
            //splitPane1.setDividerSize(5);
            splitPane1.setBottomComponent(renderPane);
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(splitPane1);
            splitPane2.setBottomComponent(null);
            splitPane2.setResizeWeight(1);
            //splitPane1.setDividerLocation(0.2);
        } else {
            /*stereoFrame = new JFrame();
            stereoFrame.setContentPane(renderPane);
            stereoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            stereoFrame.setSize(screenSize.width, screenSize.height);
            stereoFrame.setMinimumSize(new Dimension(screenSize.width, renderHeight));
            stereoFrame.setTitle(TITLE);
            stereoFrame.setVisible(true);
            stereoFrame.setLocationRelativeTo(null);*/
            this.buildStereoFrame(screenSize, renderHeight);
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(controlPanel);
            splitPane2.setBottomComponent(null);
            //splitPane2.setResizeWeight(1);
            this.setTitle(TITLE + " control");
        }
        isMirroring = false;
    }
    
    private void buildStereoFrame(Dimension screenSize, int renderHeight){
        stereoFrame = new JFrame();
        stereoFrame.setUndecorated(true);
        stereoFrame.setContentPane(renderPane);
        //stereoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stereoFrame.setSize(screenSize.width, screenSize.height);
        stereoFrame.setMinimumSize(new Dimension(screenSize.width, renderHeight));
        stereoFrame.setTitle(TITLE);
        stereoFrame.setVisible(true);
        stereoFrame.setLocationRelativeTo(null);
        
    }
    
    /*
    protected void buildStereoFrame(){
        stereoFrame = new JFrame();
        stereoFrame.setContentPane(renderPane);
        //stereoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stereoFrame.setTitle(TITLE);
        stereoFrame.setUndecorated(true);
        //stereoFrame.setVisible(true);
        //stereoFrame.setLocationRelativeTo(null);
    }*/

    /**
     * Add the visualisation panel
     *
     * @param title The title of the pane
     * @param panel The control pane
     */
    /*
    public void addVisuPanel(String title, JPanel panel) {
        panel.setPreferredSize(new Dimension(panel.getWidth(), 150));
        TitledBorder border = new TitledBorder(title);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder(border);
        visuPanels.add(panel);
        splitPane2.setBottomComponent(visuPanels.get(visuPanels.size() - 1));
    }
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
        visuManager.getRenderScreen().createRenderFrame();
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
     * Set the given visualisation panel as visible
     *
     * @param index Position of the visualisation panel in the panel list
     */
    /*
    public void setVisibleVisuPanel(int index) {
        splitPane2.setBottomComponent(visuPanels.get(index));
    }
    */

    /**
     * Remove the visualisation panel and set the first one as visible
     *
     * @param index Position of the visualisation panel in the panel list
     */
    /*
    public void removeVisuPanel(int index) {
        visuPanels.remove(index);
        if (!visuPanels.isEmpty()) {
            splitPane2.setBottomComponent(visuPanels.get(0));
        } else {
            splitPane2.setBottomComponent(null);
        }
    }
    */

    /**
     * getter
     *
     * @return The pane holding the render screens
     */
    public JDesktopPane getRenderPane() {
        return renderPane;
    }

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
        int width = this.getWidth() / getLoadedVisualisations().size();
        int height = this.getRenderPane().getHeight();
        for (int i = 0; i < getLoadedVisualisations().size(); i++) {
            getLoadedVisualisations().get(i).getRenderScreen().getFrame().setSize(width, height);
            Point pos = getLoadedVisualisations().get(i).getRenderScreen().getFrame().getLocation();
            getLoadedVisualisations().get(i).getRenderScreen().getFrame().setLocation(i * width, pos.y);
        }
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
    
    
       /**
     * Iconifies or de-iconifies the inactive render screens
     *
     * @param iconify The flag to minimize (true) or de-minize (false) the frame
     */
    public void setInactiveScreensIconify(boolean iconify) {
        for (VisualisationManager visuManager : getLoadedVisualisations()) {
            if (visuManager != activeVisualisationManager) {
                try {
                    visuManager.getRenderScreen().getFrame().setIcon(iconify);
                }
                catch (PropertyVetoException ex) {
                }
            }
        }
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
                this.buildStereoFrame();
                device.setFullScreenWindow(this.stereoFrame);
                device.
                validate();
            } else {
                // Windowed mode
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                //int renderHeight = screenSize.height - menuBar.getHeight() - controlPanel.getHeight();
                int renderHeight =Math.round(screenSize.height*(3/4));
                this.buildStereoFrame(screenSize, renderHeight);
                pack();
                setVisible(true);
            }
        }
    }
*/
    
    public void setFullscreen(boolean isFullScreen){
        if(!device.isFullScreenSupported()) return;
        if(this.isStereo3D){
            if(isFullScreen){
                device.setFullScreenWindow(stereoFrame);
            }else{
                device.setFullScreenWindow(null);
            }
            stereoFrame.validate(); 
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
