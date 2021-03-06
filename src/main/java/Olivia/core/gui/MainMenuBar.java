package Olivia.core.gui;

import static Olivia.core.Olivia.*;
import Olivia.extended.overlays.AreasArray;
import Olivia.extended.overlays.LabeledCellArray;
import Olivia.extended.overlays.CircleAnimatedOverlay;
import Olivia.extended.overlays.CircleOverlayArray;
import Olivia.extended.overlays.DensitiesOverlay;
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
    /**
     * The GUI
     */
    protected MainFrame gui;

    /**
     * The menu to open visualisations
     */
    protected JMenu fileMenu;
    //protected JMenuItem openBasicVisualisation;
    //protected JMenuItem openEmptyVisualisation;
    /**
     * The item to open an standard visualisation
     */
    protected JMenuItem openStandardVisualisation;
    //protected JMenuItem openStandardVisualisationG;
    /**
     * The item to open an scanline visualisation
     */
    protected JMenuItem openScanlineVisualisation;
    /**
     * The item to open a segmenter visualisation
     */
    protected JMenuItem openSegmenterVisualisation;
    /**
     * The item to open a classifier visualisation
     */
    protected JMenuItem openClassifierVisualisation;
    /**
     * The item to open a command file
     */
    protected JMenuItem openCommand;

    /**
     * The menu to load complex overlays
     */
    protected JMenu loadMenu;
    /**
     * The item to load a neighbours overlay
     */
    protected JMenuItem loadNeighbours;
    /**
     * The item to load a normals overlay
     */
    protected JMenuItem loadNormals;
    /**
     * The item to load a densities (2d and 3D) overlay
     */
    protected JMenuItem loadDensities;
    /**
     * The item to load an areas overlay
     */
    protected JMenuItem loadAreas;
    /**
     * The item to load a labeled cells overlay
     */
    protected JMenuItem loadLabeledCells;
    /**
     * The menu to draw simple overlays
     */
    protected JMenu drawMenu;
    /**
     * The item to draw vertex arrays
     */
    protected JMenuItem drawVertex;
    /**
     * The item to draw animated vertex arrays
     */
    protected JMenuItem drawAnimatedVertex;
    /**
     * The item to draw circles
     */
    protected JMenuItem drawCircles;
    /**
     * The item to draw animated circles
     */
    protected JMenuItem drawAnimatedCircles;
    /**
     * The item to clear all the overlays
     */
    protected JMenuItem clearAll;

    /**
     * The menu for the camera
     */
    protected JMenu cameraMenu;
    /**
     * The item to load a camera from a file
     */
    protected JMenuItem loadCamera;
    /**
     * The item to save a camera to a file
     */
    protected JMenuItem saveCamera;
    /**
     * The item to start camera mirroring
     */
    protected JMenuItem mirroring;

    /**
     * The menu for capture
     */
    protected JMenu captureMenu;
    /**
     * The item to take an image capture
     */
    protected JMenuItem takeScreenshot;
    /**
     * The item to capture video
     */
    protected JMenuItem recordVideo;

    /**
     * The menu for the general options
     */
    protected JMenu optionsMenu;
    /**
     * The item to toggle show selected point
     */
    protected JCheckBoxMenuItem showSelectedPoint;
    /**
     * The item to toggle show the overlay options frame
     */
    protected JCheckBoxMenuItem showOverlayOptions;
    /**
     * The item to toggle fullscreen
     */
    protected JCheckBoxMenuItem fullscreen;
    /**
     * The item to show (in the future toggle?) 3D rendering
     */
    protected JCheckBoxMenuItem stereoActive;
    /**
     * The item to show all the loaded visualisations
     */
    protected JMenuItem showAll;
    /**
     * The item to create a new render window
     */
    protected JMenuItem newWindow;
    
    /**
     * The menu for visualisation specific items
     */
    protected JMenu activeVisualisationMenu;
    /**
     * The menu for when there are no visualisation specific items
     */
    protected JMenu clearVisualisationMenu;

    /**
     * The standard file chooser for directories
     */
    protected final JFileChooser directoryFC;
    /**
     * The standard file chooser for files
     */
    protected final JFileChooser fileC;
    /**
     * A default file chooser accessory that adds nothing
     */
    protected final  FileChooserAccessory defaultFileCA;
    /**
     * A file chooser accessory for opening vertex overlays
     */
    protected VertexOverlayFileChooserAccessory vertexFileCA;
    /**
     * A file chooser accessory to allow the decimation of files
     */
    protected DecimationFileChooserAccessory decimationFileCA;

    /**
     * To easy add a menu
     * @param name the menu name
     * @param description the menu description
     * @param enabled whether the menu is enabled
     * @return the menu
     */
    private JMenu addMenu(String name, String description, boolean enabled) {
        JMenu menu = new JMenu(name);
        menu.getAccessibleContext().setAccessibleDescription(description);
        menu.setEnabled(enabled);
        menu.addActionListener(this);
        return menu;
    }

    /**
     * To easy add an item
     * @param name the menu name
     * @param description the menu description
     * @param command the command it triggers
     * @param enabled whether the menu is enabled
     * @return the item
     */
    private JMenuItem addMenuItem(String name, String description, String command, boolean enabled) {
        JMenuItem item = new JMenuItem(name);
        item.getAccessibleContext().setAccessibleDescription(description);
        item.setActionCommand(command);
        item.setEnabled(enabled);
        item.addActionListener(this);
        return item;
    }
    
    /**
     * To easy add a check box item 
     * @param name the menu name
     * @param description the menu description
     * @param command the command it triggers
     * @param state whether it is checked (true) or unchecked (false) 
     * @param enabled whether the menu is enabled
     * @return the item
     */
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
     * @param gui the main GUI
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
        openCommand = addMenuItem("Command File", "Opens a command file", "openCommand",true);
        
        //fileMenu.add(openBasicVisualisation);
        //fileMenu.add(openEmptyVisualisation);
        fileMenu.add(openStandardVisualisation);
        //fileMenu.add(openStandardVisualisationG);
        fileMenu.add(openScanlineVisualisation);
        fileMenu.add(openClassifierVisualisation);
        fileMenu.add(openSegmenterVisualisation);
        fileMenu.add(openCommand);

        // Load menu. To load common preprocessed stuff of LiDAR clouds for later displaying/tweaking
        loadMenu = addMenu("Load", "Load Menu", true);
        loadNeighbours = addMenuItem("Neighbours", "Loads a neighbours file", "loadNeighbours", true);
        loadNormals = addMenuItem("Normals", "Loads a Normals file, format one line for point, the next for vector", "drawNormals", true);
        loadDensities = addMenuItem("Densities", "Loads a Densities file, format first line min max and mean, next lines x, y, z, density_2d, density 3d", "drawDensities", true);
        loadAreas = addMenuItem("Areas", "Loads areas, as many files as there are on folder, format one  line for label, next lines for polygon", "loadAreas", true);
        loadLabeledCells = addMenuItem("Labeled Cells", "Loads cells (quad) with a label (string), format one line for label, next four lines for quads", "loadLabeledCells", true);
        loadMenu.add(loadNeighbours);
        loadMenu.add(loadNormals);
        loadMenu.add(loadDensities);
        loadMenu.add(loadAreas);
        loadMenu.add(loadLabeledCells);

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

    /**
     * Initialises the menu
     */
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

    /**
     * Performs the menu actions
     * @param ae an action event
     */
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
            case "openCommand":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    try {
                        getCommandParser().readFromFile(file.toPath());
                    }catch (IOException ex) {
                        Olivia.core.Olivia.println("Exception:" + ex);
                    }
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
            case "loadLabeledCells":
                LabeledCellArray cells = new LabeledCellArray(gui.getActiveVisualisation());
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    System.out.println("Opening cell path file: " + file.getParent() + "/" + file.getName());
                    try {
                        cells.readFromFile(file.toPath());
                        gui.getActiveVisualisation().addOverlay(cells);
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
            case "drawDensities":
                returnVal = fileC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileC.getSelectedFile();
                    DensitiesOverlay overlay = new DensitiesOverlay(gui.getActiveVisualisation(),"Densities");
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
    
    /**
     * Sets whether the visualisation menus (draw,camera,capture,options) are enabled
     * @param enabled true for enable
     */
    public void setEnabledVisualisationMenus(boolean enabled){
        drawMenu.setEnabled(enabled);
        cameraMenu.setEnabled(enabled);
        captureMenu.setEnabled(enabled);
        optionsMenu.setEnabled(enabled);
    }
    
    /**
     * Changes the menus to reflect the active visualisation in the GUI
     */
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
    
    /**
     * Checks or unchecks the fullscreen menu item 
     * @param fullScreen 
     */
    public void checkFullScreenMenu(boolean fullScreen){
        if(fullscreen.isSelected()!=fullScreen){
            fullscreen.setSelected(fullScreen);
        }
    }
    
    /**
     * Updates the menus taking into account the active visualisation in the GUI
     */
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
