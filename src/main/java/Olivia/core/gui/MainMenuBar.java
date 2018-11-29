package Olivia.core.gui;

import static Olivia.core.Olivia.*;
import Olivia.extended.overlays.AreasArray;
import Olivia.extended.overlays.CircleAnimatedOverlay;
import Olivia.extended.overlays.CircleOverlayArray;
import Olivia.extended.overlays.NeighbourhoodArray;
import Olivia.extended.overlays.NormalsOverlay;
import Olivia.extended.overlays.VertexAnimatedOverlay;
import Olivia.extended.overlays.VertexOverlay;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * This is the menu bar holding the main options of the visualisation tool
 *
 * @author oscar.garcia
 */
public class MainMenuBar extends JMenuBar implements ActionListener {

    //protected OpenGLScreen renderScreen;
    
    protected MainFrame gui;

    protected JMenu fileMenu;
    //protected JMenuItem openBasicVisualisation;
    //protected JMenuItem openEmptyVisualisation;
    protected JMenuItem openStandardVisualisation;
    //protected JMenuItem openStandardVisualisationG;
    protected JMenuItem openScanlineVisualisation;
    protected JMenuItem openSegmenterVisualisation;
    protected JMenuItem openClassifierVisualisation;

    protected JMenu loadMenu;
    protected JMenuItem loadNeighbours;
    protected JMenuItem loadNormals;
    protected JMenuItem loadAreas;

    protected JMenu drawMenu;
    protected JMenuItem drawVertex;
    protected JMenuItem drawAnimatedVertex;
    protected JMenuItem drawCircles;
    protected JMenuItem drawAnimatedCircles;
    protected JMenuItem clearAll;

    protected JMenu cameraMenu;
    protected JMenuItem loadCamera;
    protected JMenuItem saveCamera;
    protected JMenuItem mirroring;

    protected JMenu captureMenu;
    protected JMenuItem takeScreenshot;
    protected JMenuItem recordVideo;

    protected JMenu optionsMenu;
    protected JCheckBoxMenuItem showSelectedPoint;
    protected JCheckBoxMenuItem showOverlayOptions;
    protected JCheckBoxMenuItem fullscreen;
    protected JCheckBoxMenuItem stereoActive;
    protected JMenuItem showAll;
    protected JMenuItem newWindow;
    
    
    protected JMenu activeVisualisationMenu;
    protected JMenu clearVisualisationMenu;

    protected final JFileChooser directoryFC;
    protected final JFileChooser fileC;
    protected final  FileChooserAccessory defaultFileCA;
    protected VertexOverlayFileChooserAccessory vertexFileCA;
    protected DecimationFileChooserAccessory decimationFileCA;

    private JMenu addMenu(String name, String description, boolean enabled) {
        JMenu menu = new JMenu(name);
        menu.getAccessibleContext().setAccessibleDescription(description);
        menu.setEnabled(enabled);
        menu.addActionListener(this);
        return menu;
    }

    private JMenuItem addMenuItem(String name, String description, String command, boolean enabled) {
        JMenuItem item = new JMenuItem(name);
        item.getAccessibleContext().setAccessibleDescription(description);
        item.setActionCommand(command);
        item.setEnabled(enabled);
        item.addActionListener(this);
        return item;
    }
    
    private JCheckBoxMenuItem addCheckBoxMenuItem(String name, String description, String command, boolean state, boolean enabled) {
        JCheckBoxMenuItem item = new JCheckBoxMenuItem(name,state);
        item.getAccessibleContext().setAccessibleDescription(description);
        item.setActionCommand(command);
        item.setEnabled(enabled);
        item.addActionListener(this);
        return item;
    }

    /**
     * Create the main menu bar. Must call to initialize() afterwards.
     */
    public MainMenuBar(MainFrame gui) {
        this.gui = gui;
        // File menu. This has to be changed every time you want ot add a new visualisation type
        fileMenu = addMenu("File", "File Menu", true);
        //openAuto = addMenuItem("Open Auto", "Opens a file according to the standard LAS fields", "openAuto", true);
        //openBasicVisualisation = addMenuItem("Open Basic", "Opens a Basic Visualisation", "openBasic", true);
        //openEmptyVisualisation = addMenuItem("Open Empty", "Opens an Empty Visualisation (with no points)", "openEmpty", true);
        openStandardVisualisation = addMenuItem("Open Standard", "Opens a Standard Visualisation", "openStandard", true);
        //openStandardVisualisationG = addMenuItem("Open Standard G", "Opens a Standard Visualisation with Guava", "openStandardG", true);
        openScanlineVisualisation = addMenuItem("Open Scanline", "Opens a Scanline Visualisation", "openScanline", true);
        openClassifierVisualisation = addMenuItem("Open Classifier", "Opens a Classifier Visualisation", "openClassifier", true);
        openSegmenterVisualisation = addMenuItem("Open Segmenter", "Opens a Segmenter Visualisation", "openSegmenter",true);
        
        //fileMenu.add(openBasicVisualisation);
        //fileMenu.add(openEmptyVisualisation);
        fileMenu.add(openStandardVisualisation);
        //fileMenu.add(openStandardVisualisationG);
        fileMenu.add(openScanlineVisualisation);
        fileMenu.add(openClassifierVisualisation);
        fileMenu.add(openSegmenterVisualisation);

        // Load menu. To load common preprocessed stuff of LiDAR clouds for later displaying/tweaking
        loadMenu = addMenu("Load", "Load Menu", true);
        loadNeighbours = addMenuItem("Neighbours", "Loads a neighbours file", "loadNeighbours", true);
        loadNormals = addMenuItem("Normals", "Loads a Normals file, format one line for point, the next for vector", "drawNormals", true);
        loadAreas = addMenuItem("Areas", "Loads areas, as many files as there are on folder, format one  line for label, next lines for polygon", "loadAreas", true);
        loadMenu.add(loadNeighbours);
        loadMenu.add(loadNormals);
        loadMenu.add(loadAreas);

        // Draw menu. To drawShape OpenGL primitives on top of the point cloud inmediately
        drawMenu = addMenu("Draw", "Geometry Menu", false);
        drawVertex = addMenuItem("Vertex", "", "drawVertex", true);
        drawAnimatedVertex = addMenuItem("Animated Vertex", "", "drawAnimatedVertex", true);
        drawCircles = addMenuItem("Circles", "", "drawCircles", true);
        drawAnimatedCircles = addMenuItem("Animated Circles", "", "drawAnimatedCircles", true);
        clearAll = addMenuItem("Clear All", "Remove all drawings", "clearAll", false);

        drawMenu.add(drawVertex);
        drawMenu.add(drawAnimatedVertex);
        drawMenu.add(drawCircles);
        drawMenu.add(drawAnimatedCircles);
        drawMenu.add(clearAll);

        // Camera menu. To change all related with the camera movement
        cameraMenu = addMenu("Camera", "Camera Menu", false);
        loadCamera = addMenuItem("Load", "Loads a camera from a file", "loadCamera", true);
        saveCamera = addMenuItem("Save", "Saves a camera to a file", "saveCamera", true);
        mirroring = addMenuItem("Mirror", "Synchronices all cameras", "mirroring", true);

        cameraMenu.add(loadCamera);
        cameraMenu.add(saveCamera);
        cameraMenu.add(mirroring);

        // Capture menu. To record the stuff that is displayed  
        captureMenu = addMenu("Capture", "Capture Menu", false);
        takeScreenshot = addMenuItem("Screen", "Save the screen to an image", "takeScreenshot", true);
        recordVideo = addMenuItem("Video", "Save the screen to an video", "recordVideo", true);

        captureMenu.add(takeScreenshot);
        captureMenu.add(recordVideo);

        optionsMenu = addMenu("Options", "Options Menu", false);
        showSelectedPoint = addCheckBoxMenuItem("Show Selected Point", "Show a highlight over the selected point", "showSelectedPoint", true, true);
        showSelectedPoint.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_P, ActionEvent.ALT_MASK)
        );
        showOverlayOptions = addCheckBoxMenuItem("Show Overlay Options", "Show a window where overlays can be moved and configured", "showOverlayOptions", false, true);
        showOverlayOptions.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_O, ActionEvent.ALT_MASK)
        );
        fullscreen = addCheckBoxMenuItem("Fullscreen", "Toggles between fullscreen or windowed mode", "fullscreen", false, !gui.isDetachedDesktop());
        //fullscreen.setMnemonic(KeyEvent.VK_F);
        fullscreen.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_F, ActionEvent.ALT_MASK)
        );
        fullscreen.setEnabled(gui.isFullScreenEnabled());
        // 3D Indicator. To indicate if 3D is currently enabled   
        if (gui.getActiveVisualisation() != null) {
            stereoActive = addCheckBoxMenuItem("3DS", "Indicates if stereoscopic 3D is enabled", "3d", gui.getActiveVisualisation().isStereo3D(), gui.getActiveVisualisation().isStereo3D());
        }else{
            stereoActive = addCheckBoxMenuItem("3DS", "Indicates if stereoscopic 3D is enabled", "3d", false, false);
        }
        showAll = addMenuItem("Show All", "Shows all visualisations", "showAll", true);
        newWindow = addMenuItem("New Window", "Creates a new window for rendering", "newWindow", gui.isDetachedDesktop());
        
        optionsMenu.add(showSelectedPoint);
        optionsMenu.add(showOverlayOptions);
        optionsMenu.add(fullscreen);
        optionsMenu.add(stereoActive);
        optionsMenu.add(showAll);
        optionsMenu.add(newWindow);
        
        clearVisualisationMenu = new JMenu("");
        clearVisualisationMenu.getAccessibleContext().setAccessibleDescription("Menu for the especific visualisation being rendered");
        clearVisualisationMenu.setEnabled(false);
        clearVisualisationMenu.addActionListener(this);
        activeVisualisationMenu = clearVisualisationMenu;

        directoryFC = new JFileChooser();
        directoryFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileC = new JFileChooser();
        fileC.setFileSelectionMode(JFileChooser.FILES_ONLY);
        defaultFileCA = new FileChooserAccessory(); //does nothing
        vertexFileCA = new VertexOverlayFileChooserAccessory(); //Only so there are no nulls, does nothing
        decimationFileCA = new DecimationFileChooserAccessory();
        fileC.setAccessory(defaultFileCA);
    }

    public void initialize() {
        setBorder(BorderFactory.createEtchedBorder());
        add(fileMenu);
        add(loadMenu);
        add(drawMenu);
        add(cameraMenu);
        add(captureMenu);
        add(optionsMenu);
        add(activeVisualisationMenu);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int returnVal;
        VertexOverlay vOverlay;
        switch (ae.getActionCommand()) {
            case "openScanline":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    addNewScanlinesVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openBasic":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    //addNewBasicVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openEmpty":
                addNewEmptyVisualisation();
                break;
            case "openStandard":
                fileC.setAccessory(decimationFileCA);
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    addNewStandardVisualisation(file.getAbsolutePath(),decimationFileCA.getDecimation());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                fileC.setAccessory(defaultFileCA);
                break;
//            case "openStandardG":
//                returnVal = fileC.showOpenDialog(null);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = fileC.getSelectedFile();
//                    addNewStandardVisualisation(file);
//                } else {
//                    System.out.println("Open command cancelled by user.");
//                }
//                break;
            case "openClassifier":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    addNewClassifierVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openSegmenter":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    addNewSegmenterVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openNeighbours":
                returnVal = directoryFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = directoryFC.getSelectedFile();
                    //addNewNeighboursVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
//            case "loadNeighbours":
//                returnVal = directoryFC.showOpenDialog(null);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = directoryFC.getSelectedFile();
//                    NeighboursInputReader nir = new NeighboursInputReader();
//                    try {
//                        System.out.println("Loading neighbours...");
//                        renderScreen.getVisualisation().getNeighbourhood().setNeighbours(nir.readIdentifiers(file.getAbsolutePath(), NeighboursInputReader.ID_FILE));
//                        renderScreen.getVisualisation().getNeighbourhood().setNeighboursDistances(nir.readDistances(file.getAbsolutePath(), NeighboursInputReader.DISTANCE_FILE));
//                    }
//                    catch (IOException ex) {
//                        System.out.println("Exception:" + ex);
//                    }
//                }
//                break;
             case "loadNeighbours":
                NeighbourhoodArray neighs = new  NeighbourhoodArray(gui.getActiveVisualisation());
                returnVal = directoryFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = directoryFC.getSelectedFile();
                    System.out.println("Opening point file: " + file.getParent() + "/" + file.getName());
                    try {
                        neighs.readFromFiles(file.toPath());
                        gui.getActiveVisualisation().addOverlay(neighs);
                        neighs.listenToActionsOnScreen();
                        neighs.setSelectingCurrentByMouse(true);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "loadAreas":
                AreasArray areas = new  AreasArray(gui.getActiveVisualisation());
                returnVal = directoryFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = directoryFC.getSelectedFile();
                    System.out.println("Opening areas file: " + file.getParent() + "/" + file.getName());
                    try {
                        areas.readFromFiles(file.toPath());
                        gui.getActiveVisualisation().addOverlay(areas);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "drawVertex":
                vOverlay = new VertexOverlay(gui.getActiveVisualisation(),ae.getActionCommand());
                vertexFileCA = new VertexOverlayFileChooserAccessory(vOverlay);
                fileC.setAccessory(vertexFileCA);
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Opening point file: " + file.getParent() + "/" + file.getName());
                    try {
                        vOverlay.readFromFile(file.toPath());
                        gui.getActiveVisualisation().addOverlay(vOverlay);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                fileC.setAccessory(defaultFileCA);
                break;
            case "drawAnimatedVertex":
                VertexAnimatedOverlay vAOverlay = new VertexAnimatedOverlay(gui.getActiveVisualisation(),ae.getActionCommand());
                vertexFileCA = new VertexAnimatedOverlayFileChooserAccessory(vAOverlay);
                fileC.setAccessory(vertexFileCA);
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Opening point file: " + file.getParent() + "/" + file.getName());
                    try {
                        vAOverlay.readFromFile(file.toPath());
                        gui.getActiveVisualisation().addOverlay(vAOverlay);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                fileC.setAccessory(defaultFileCA);
                break;
            case "drawCircles":
                CircleOverlayArray cOverlays = new CircleOverlayArray(gui.getActiveVisualisation(),ae.getActionCommand());
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Opening circles file: " + file.getParent() + "/" + file.getName());
                    try {
                        cOverlays.readFromFile(file.toPath());
                        gui.getActiveVisualisation().addOverlay(cOverlays);
                        cOverlays.setDrawModesToFalse();
                        cOverlays.setDrawAll(true);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                break;
            case "drawAnimatedCircles":
                CircleAnimatedOverlay caOverlays = new CircleAnimatedOverlay(gui.getActiveVisualisation(),ae.getActionCommand());
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Opening circles file: " + file.getParent() + "/" + file.getName());
                    try {
                        caOverlays.readFromFile(file.toPath());
                        gui.getActiveVisualisation().addOverlay(caOverlays);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                break;
            case "drawNormals":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    NormalsOverlay overlay = new NormalsOverlay(gui.getActiveVisualisation(),"Normals");
                    try {
                        overlay.readFromFile(file.toPath());
                        gui.getActiveVisualisation().addOverlay(overlay);
                    }catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "clearAll":
                //renderScreen.getVisualisation().getGeometry().removeGeometry();
                break;
            case "loadCamera":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Opening camera file: " + file.getParent() + "/" + file.getName());
                    gui.getActiveVisualisation().getRenderScreen().getCamera().readFromFile(file.getParent(), file.getName());
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                break;
            case "saveCamera":
                returnVal = fileC.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Saving camera file to: " + file.getParent() + "/" + file.getName());
                    gui.getActiveVisualisation().getRenderScreen().getCamera().writeToFile(file.getParent(), file.getName());
                } else {
                    System.out.println("Save command cancelled by user.");
                }
                break;
            case "mirroring":
                gui.toogleMirroring();
                break;
            case "takeScreenshot":
                gui.getActiveVisualisation().getRenderScreen().getCapture().setCaptureImage(true);
                break;
            case "recordVideo":
                gui.getActiveVisualisation().getRenderScreen().getCapture().toggleCaptureVideo();
                break;
            case "showSelectedPoint":
                gui.getActiveVisualisation().setDrawMouseSelection(showSelectedPoint.isSelected());
                break;
            case "showOverlayOptions":
                gui.setOverlayOptionsVisible(showOverlayOptions.isSelected());
                break;
            case "fullscreen":
                gui.setFullscreen(fullscreen.isSelected());
                break;
            case "showAll":
                gui.showAll();
                break;
            case "newWindow":
                gui.createNewRenderWindow();
                break;
//            case "visuActive":
//                drawMenu.setEnabled(true);
//                cameraMenu.setEnabled(true);
//                captureMenu.setEnabled(true);
//                break;
//            case "noVisuActive":
//                drawMenu.setEnabled(false);
//                cameraMenu.setEnabled(false);
//                captureMenu.setEnabled(false);
//                break;//            case "visuActive":
//                drawMenu.setEnabled(true);
//                cameraMenu.setEnabled(true);
//                captureMenu.setEnabled(true);
//                break;
//            case "noVisuActive":
//                drawMenu.setEnabled(false);
//                cameraMenu.setEnabled(false);
//                captureMenu.setEnabled(false);
//                break;
        }
    }
    
    public void setEnabledVisualisationMenus(boolean enabled){
        drawMenu.setEnabled(enabled);
        cameraMenu.setEnabled(enabled);
        captureMenu.setEnabled(enabled);
        optionsMenu.setEnabled(enabled);
    }
    
    protected void changeVisualisationMenu(){
        if(gui.getActiveVisualisation()!=null){
            this.remove(activeVisualisationMenu);
            this.add(activeVisualisationMenu = gui.getActiveVisualisation().getjMenu());
            showSelectedPoint.setSelected(gui.getActiveVisualisation().isDrawMouseSelection());
        }else{
            this.remove(activeVisualisationMenu);
            this.add(activeVisualisationMenu = clearVisualisationMenu);
        }
        this.revalidate();
        this.repaint();
        
    }
    
    public void checkFullScreenMenu(boolean fullScreen){
        if(fullscreen.isSelected()!=fullScreen){
            fullscreen.setSelected(fullScreen);
        }
    }
    
    public void update(){
        if(gui.getActiveVisualisation()!=null){
            setEnabledVisualisationMenus(true);
            changeVisualisationMenu();
        }else{
            setEnabledVisualisationMenus(false);
            changeVisualisationMenu();
        }
        showOverlayOptions.setSelected(gui.isOverlayOptionsVisible());
    }
    
}
