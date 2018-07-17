package PointCloudVisu.core.gui;

import PointCloudVisu.core.PointCloudVisu;
import static PointCloudVisu.core.PointCloudVisu.*;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.neighbours.NeighboursInputReader;
import com.jogamp.opengl.GL4;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This is the menu bar holding the main options of the visualisation tool
 *
 * @author oscar.garcia
 */
public class MainMenuBar extends JMenuBar implements ActionListener {

    protected OpenGLScreen renderScreen;

    protected JMenu fileMenu;
    //protected JMenuItem openBasicVisualisation;
    protected JMenuItem openStandardVisualisation;
    //protected JMenuItem openStandardVisualisationG;
    protected JMenuItem openScanlineVisualisation;
    protected JMenuItem openSegmenterVisualisation;
    protected JMenuItem openClassifierVisualisation;
    protected JMenuItem openNeighboursVisualisation;

    protected JMenu loadMenu;
    protected JMenuItem loadNeighbours;

    protected JMenu drawMenu;
    protected JMenuItem drawLines;
    protected JMenuItem drawLineLoop;
    protected JMenuItem drawTriangles;
    protected JMenuItem drawTriangleFan;
    protected JMenuItem drawTriangleStrip;
    protected JMenuItem drawQuads;
    protected JMenuItem clearAll;

    protected JMenu cameraMenu;
    protected JMenuItem loadCamera;
    protected JMenuItem saveCamera;
    protected JMenuItem mirroring;

    protected JMenu captureMenu;
    protected JMenuItem takeScreenshot;
    protected JMenuItem recordVideo;

    protected JMenuItem stereoActive;

    final JFileChooser directoryFC;
    final JFileChooser fileFC;

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

    /**
     * Create the main menu bar. Must call to initialize() afterwards.
     */
    public MainMenuBar() {

        // File menu. This has to be changed every time you want ot add a new visualisation type
        fileMenu = addMenu("File", "File Menu", true);
        //openAuto = addMenuItem("Open Auto", "Opens a file according to the standard LAS fields", "openAuto", true);
        //openBasicVisualisation = addMenuItem("Open Basic", "Opens a Basic Visualisation", "openBasic", true);
        openStandardVisualisation = addMenuItem("Open Standard", "Opens a Standard Visualisation", "openStandard", true);
        //openStandardVisualisationG = addMenuItem("Open Standard G", "Opens a Standard Visualisation with Guava", "openStandardG", true);
        openScanlineVisualisation = addMenuItem("Open Scanline", "Opens a Scanline Visualisation", "openScanline", true);
        openClassifierVisualisation = addMenuItem("Open Classifier", "Opens a Classifier Visualisation", "openClassifier", true);
        openSegmenterVisualisation = addMenuItem("Open Segmenter", "Opens a Segmenter Visualisation", "openSegmenter", true);
        openNeighboursVisualisation = addMenuItem("Open Neighbours", "Opens a Neighbours Visualisation", "openNeighbours", true);

        //fileMenu.add(openBasicVisualisation);
        fileMenu.add(openStandardVisualisation);
        //fileMenu.add(openStandardVisualisationG);
        fileMenu.add(openScanlineVisualisation);
        fileMenu.add(openClassifierVisualisation);
        fileMenu.add(openSegmenterVisualisation);
        fileMenu.add(openNeighboursVisualisation);

        // Load menu. To load common preprocessed stuff of LiDAR clouds for later displaying/tweaking
        loadMenu = addMenu("Load", "Load Menu", true);
        loadNeighbours = addMenuItem("Neighbours", "Loads a neighbours file", "loadNeighbours", true);
        loadMenu.add(loadNeighbours);

        // Draw menu. To draw OpenGL primitives on top of the point cloud inmediately
        drawMenu = addMenu("Draw", "Geometry Menu", false);
        drawLines = addMenuItem("Lines", "", "drawLines", true);
        drawLineLoop = addMenuItem("Line loop", "", "drawLineLoop", true);
        drawTriangles = addMenuItem("Triangles", "", "drawTriangles", true);
        drawTriangleFan = addMenuItem("Triangle Fan", "", "drawTriangleFan", true);
        drawTriangleStrip = addMenuItem("Triangle Strip", "", "drawTriangleStrip", true);
        drawQuads = addMenuItem("Quads", "", "drawQuads", true);
        clearAll = addMenuItem("Clear All", "Remove all drawings", "clearAll", true);

        drawMenu.add(drawLines);
        drawMenu.add(drawLineLoop);
        drawMenu.add(drawTriangles);
        drawMenu.add(drawTriangleFan);
        drawMenu.add(drawTriangleStrip);
        drawMenu.add(drawQuads);
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

        // 3D Indicator. To indicate if 3D is currently enabled   
        if (renderScreen != null) {
            stereoActive = addMenuItem("3DS", "Indicates if stereoscopic 3D is enabled", "3d", renderScreen.isStereo3D());
        }

        directoryFC = new JFileChooser();
        directoryFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileFC = new JFileChooser();
        fileFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public void initialize() {
        setBorder(BorderFactory.createEtchedBorder());
        add(fileMenu);
        add(loadMenu);
        add(drawMenu);
        add(cameraMenu);
        add(captureMenu);
        if (renderScreen != null) {
            add(stereoActive);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int returnVal;
        switch (ae.getActionCommand()) {
            case "openScanline":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    addNewScanlinesVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openBasic":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    addNewBasicVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openStandard":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    addNewStandardVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
//            case "openStandardG":
//                returnVal = fileFC.showOpenDialog(null);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = fileFC.getSelectedFile();
//                    addNewStandardVisualisation(file);
//                } else {
//                    System.out.println("Open command cancelled by user.");
//                }
//                break;
            case "openClassifier":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    addNewClassifierVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openSegmenter":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    addNewSegmenterVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "openNeighbours":
                returnVal = directoryFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = directoryFC.getSelectedFile();
                    addNewNeighboursVisualisation(file.getAbsolutePath());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
                break;
            case "loadNeighbours":
                returnVal = directoryFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = directoryFC.getSelectedFile();
                    NeighboursInputReader nir = new NeighboursInputReader();
                    try {
                        System.out.println("Loading neighbours...");
                        renderScreen.getVisualisation().getNeighbourhood().setNeighbours(nir.readIdentifiers(file.getAbsolutePath(), NeighboursInputReader.ID_FILE));
                        renderScreen.getVisualisation().getNeighbourhood().setNeighboursDistances(nir.readDistances(file.getAbsolutePath(), NeighboursInputReader.DISTANCE_FILE));
                    }
                    catch (IOException ex) {
                        System.out.println("Exception:" + ex);
                    }
                }
                break;
            case "drawLines":
            case "drawLineLoop":
            case "drawTriangles":
            case "drawTriangleFan":
            case "drawTriangleStrip":
            case "drawQuads":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    System.out.println("Opening point file: " + file.getParent() + "/" + file.getName());
                    int drawType = -1;
                    switch (ae.getActionCommand()) {
                        case "drawLines":
                            drawType = GL4.GL_LINES;
                            break;
                        case "drawLineLoop":
                            drawType = GL4.GL_LINE_LOOP;
                            break;
                        case "drawTriangles":
                            drawType = GL4.GL_TRIANGLES;
                            break;
                        case "drawTriangleFan":
                            drawType = GL4.GL_TRIANGLE_FAN;
                            break;
                        case "drawTriangleStrip":
                            drawType = GL4.GL_TRIANGLE_STRIP;
                            break;
                        case "drawQuads":
                            drawType = GL4.GL_QUADS;
                            break;
                    }
                    renderScreen.getVisualisation().getGeometry().readVertices(file.getParent(), file.getName(), drawType);
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                break;
            case "clearAll":
                renderScreen.getVisualisation().getGeometry().removeGeometry();
                break;
            case "loadCamera":
                returnVal = fileFC.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    System.out.println("Opening camera file: " + file.getParent() + "/" + file.getName());
                    renderScreen.getCamera().readFromFile(file.getParent(), file.getName());
                } else {
                    System.out.println("Load command cancelled by user.");
                }
                break;
            case "saveCamera":
                returnVal = fileFC.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileFC.getSelectedFile();
                    System.out.println("Saving camera file to: " + file.getParent() + "/" + file.getName());
                    renderScreen.getCamera().writeToFile(file.getParent(), file.getName());
                } else {
                    System.out.println("Save command cancelled by user.");
                }
                break;
            case "mirroring":
                PointCloudVisu.toogleMirroring();
                break;
            case "takeScreenshot":
                renderScreen.getCapture().setCaptureImage(true);
                break;
            case "recordVideo":
                renderScreen.getCapture().toggleCaptureVideo();
                break;
            case "visuActive":
                drawMenu.setEnabled(true);
                cameraMenu.setEnabled(true);
                captureMenu.setEnabled(true);
                break;
            case "noVisuActive":
                drawMenu.setEnabled(false);
                cameraMenu.setEnabled(false);
                captureMenu.setEnabled(false);
                break;
        }
    }
}
