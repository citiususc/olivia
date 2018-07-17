package PointCloudVisu.core;

import PointCloudVisu.basic.BasicVisualisation;
import PointCloudVisu.basic.BasicVisualisationControlPane;
import PointCloudVisu.classifier.ClassifierControlPane;
import PointCloudVisu.classifier.ClassifierVisualisation;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.gui.MainFrame;
import PointCloudVisu.core.render.hi.OpenGLMouseListener;
import PointCloudVisu.neighbours.NeighboursControlPane;
import PointCloudVisu.neighbours.NeighboursVisualisation;
import PointCloudVisu.scanlines.ScanlinesVisualisation;
import PointCloudVisu.scanlines.ScanlinesVisualisationControlPane;
import PointCloudVisu.segmenter.SegmenterControlPane;
import PointCloudVisu.segmenter.SegmenterVisualisation;
import PointCloudVisu.standard.StandardVisualisation;
import PointCloudVisu.standard.StandardVisualisationControlPane;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

/**
 * Here be main Here is where new visualisation are added, for now.
 *
 * @author oscar.garcia
 */
public class PointCloudVisu {

    private static MainFrame mainFrame;
    private static ArrayList<OpenGLScreen> renderScreens;
    private static OpenGLScreen activeRenderScreen;
    private static String visulisationType;
    private static boolean isStereo3D;
    private static boolean isMirroring;
    private static int idCount;

    /**
     * Here you add new Visualisations, do new Visu(), readFromFile(), new
     * Pane(), addNewVisualisation() and handle arguments. Check also PointCloudVisu.core.gui.MainManuBar
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        renderScreens = new ArrayList<>();
        visulisationType = null;
        isStereo3D = false;
        isMirroring = false;
        idCount = 0;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SubstanceLookAndFeel.setSkin(new GraphiteSkin());
                String path = System.getProperty("user.dir");
                File file = new File(path);
                for (int i = args.length - 1; i >= 0; i--) {
                    switch (args[i]) {
                        case "-stereo":
                            isStereo3D = true;
                            break;
                        case "-neighbours":
                        case "-basic":
                        case "-standard":
                        case "-segmenter":
                        case "-classifier":
                            visulisationType = args[i];
                            break;
                        default:
                            path = args[i];
                            file = new File(path);
                            break;
                    }
                }
                mainFrame = new MainFrame(isStereo3D);
                mainFrame.initialize(isStereo3D);
                if (visulisationType != null) {
                    switch (visulisationType) {
                        case "-basic":
                            addNewBasicVisualisation(file.getAbsolutePath());
                            break;
                        case "-standard":
                            addNewStandardVisualisation(file.getAbsolutePath());
                            break;
                        case "-neighbours":
                            addNewNeighboursVisualisation(file.getAbsolutePath());
                            break;
                        case "-segmenter":
                            addNewSegmenterVisualisation(file.getAbsolutePath());
                            break;
                        case "-classifier":
                            addNewClassifierVisualisation(file.getAbsolutePath());
                            break;
                        default:
                            System.out.println("Error: " + visulisationType + "is not a correct visualisation");
                            System.exit(0);
                    }
                }
            }
        });
    }

    /**
     * Adds a new Visualisation
     *
     * @param title The title to give this visualisation in the GUI
     * @param visu The visualisation, will be adder to the render screen
     * @param controlPane The control pane for the visualisation, will be added
     * as a tab in the control panel
     */
    private static void addNewVisualisation(String title, Visualisation visu, JPanel controlPane) {
        activeRenderScreen.createRenderFrame();
        activeRenderScreen.setVisualisation(visu);
        mainFrame.addVisuPanel(title, controlPane);
        updateRenderFrameLayout();
    }

    /**
     * Resizes the render screens to fit them all inside the render frame
     */
    public static void updateRenderFrameLayout() {
        if (renderScreens.isEmpty()) {
            return;
        }
        int width = mainFrame.getWidth() / renderScreens.size();
        int height = mainFrame.getRenderPane().getHeight();
        for (int i = 0; i < renderScreens.size(); i++) {
            renderScreens.get(i).getFrame().setSize(width, height);
            Point pos = renderScreens.get(i).getFrame().getLocation();
            renderScreens.get(i).getFrame().setLocation(i * width, pos.y);
        }
    }

    /**
     * Adds a new render screen, set it as the active and update the layout
     */
    private static void addRenderScreen() {
        renderScreens.add(new OpenGLScreen(idCount++, mainFrame, isStereo3D));
        activeRenderScreen = renderScreens.get(renderScreens.size() - 1);
        mainFrame.setRenderScreen(activeRenderScreen);
    }

    /**
     * Removes the given render screen
     *
     * @param renderScreen The render screen to remove
     */
    public static void removeScreen(OpenGLScreen renderScreen) {
        mainFrame.removeVisuPanel(renderScreens.indexOf(renderScreen));
        renderScreens.remove(renderScreen);
        renderScreen.destroy();
        updateRenderFrameLayout();
    }

    /**
     * Enables/disables camera mirroring
     */
    public static void toogleMirroring() {
        isMirroring = !isMirroring;
        mainFrame.logIntoConsole("Camera mirroring: " + isMirroring);
    }

    /**
     * Syncronize all the cameras with the active render screen camera
     */
    public static void doCameraMirroring() {
        for (OpenGLScreen renderScreen : renderScreens) {
            if (renderScreen != activeRenderScreen) {
                renderScreen.getCamera().copyActiveCamera();
            }
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
    public static void doSelectionMirroring(String[] eventNames) {
        for (OpenGLScreen renderScreen : renderScreens) {
            if (renderScreen != activeRenderScreen) {
                renderScreen.getMouseListener().getMouseSelection().pick(
                        activeRenderScreen.getMouseListener().getWindowX(),
                        activeRenderScreen.getMouseListener().getWindowY(),
                        OpenGLMouseListener.MOUSE_PICK_RADIUS,
                        OpenGLMouseListener.MOUSE_PICK_EPSILON);
                for (String eventName : eventNames) {
                    renderScreen.fireEvent(eventName);
                }
            }
        }
    }

    /**
     * getter
     *
     * @return The flag to indicate if camera mirroring is enabled
     */
    public static boolean isMirroring() {
        return isMirroring;
    }

    /**
     * setter
     *
     * @param renderScreen The render screen to set as active
     */
    public static void setActiveScreen(OpenGLScreen renderScreen) {
        activeRenderScreen = renderScreen;
        if (renderScreens.size() > 0) {
            mainFrame.setRenderScreen(activeRenderScreen);
            mainFrame.setVisibleVisuPanel(renderScreens.indexOf(activeRenderScreen));
        }
    }

    public static boolean isScreenAdded(OpenGLScreen renderScreen) {
        return renderScreens.indexOf(renderScreen) != -1;
    }

    /**
     * getter
     *
     * @return The active render screen
     */
    public static OpenGLScreen getActiveScreen() {
        return activeRenderScreen;
    }
    
    /**
     * Iconifies or de-iconifies the inactive render screens
     *
     * @param iconify The flag to minimize (true) or de-minize (false) the frame
     */
    public static void setInactiveScreensIconify(boolean iconify) {
        for (OpenGLScreen renderScreen : renderScreens) {
            if (renderScreen != activeRenderScreen) {
                try {
                    renderScreen.getFrame().setIcon(iconify);
                }
                catch (PropertyVetoException ex) {
                }
            }
        }
    }

    public static void addNewBasicVisualisation(String filePath) {
        try {
            System.out.println("Opening directory: " + filePath + " for a Basic Visualisation");
            addRenderScreen();
            BasicVisualisation visu = new BasicVisualisation(activeRenderScreen);
            visu.readFromFile(filePath);
            BasicVisualisationControlPane pane = new BasicVisualisationControlPane(visu);
            addNewVisualisation("basic", visu, pane);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    public static void addNewStandardVisualisation(String filePath) {
        try {
            System.out.println("Opening directory: " + filePath + " for a Standard Visualisation");
            addRenderScreen();
            StandardVisualisation visu = new StandardVisualisation(activeRenderScreen);
            visu.readFromFile(filePath);
            StandardVisualisationControlPane pane = new StandardVisualisationControlPane(visu);
            addNewVisualisation("basic", visu, pane);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
//    public static void addNewStandardVisualisation(File file) {
//        try {
//            System.out.println("Opening file: " + file + " for a Standard Visualisation");
//            addRenderScreen();
//            StandardVisualisation visu = new StandardVisualisation(activeRenderScreen);
//            visu.readFromFile(file);
//            StandardVisualisationControlPane pane = new StandardVisualisationControlPane(visu);
//            addNewVisualisation("basic", visu, pane);
//        }
//        catch (FileNotFoundException e) {
//            System.out.println("Cannot add visualisation, cannot read File" + e);
//        }
//        catch (IOException e) {
//            System.out.println("Cannot add visualisation, cannot read from File" + e);
//        }
//    }
    
        public static void addNewScanlinesVisualisation(String filePath) {
        try {
            System.out.println("Opening directory: " + filePath + " for a Scanlines Visualisation");
            addRenderScreen();
            ScanlinesVisualisation visu = new ScanlinesVisualisation(activeRenderScreen);
            visu.readFromFile(filePath);
            ScanlinesVisualisationControlPane pane = new ScanlinesVisualisationControlPane(visu);
            addNewVisualisation("scanlines", visu, pane);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }

    public static void addNewSegmenterVisualisation(String filePath) {
        try {
            System.out.println("Opening directory: " + filePath + " for a Segmenter Visualisation");
            addRenderScreen();
            SegmenterVisualisation visu = new SegmenterVisualisation(activeRenderScreen);
            visu.readFromFile(filePath);
            SegmenterControlPane pane = new SegmenterControlPane(visu);
            addNewVisualisation("segmenter", visu, pane);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }

    public static void addNewClassifierVisualisation(String filePath) {
        try {
            System.out.println("Opening directory: " + filePath + " for a Classifier Visualisation");
            addRenderScreen();
            ClassifierVisualisation visu = new ClassifierVisualisation(activeRenderScreen);
            visu.readFromFile(filePath);
            ClassifierControlPane pane = new ClassifierControlPane(visu);
            addNewVisualisation("classifier", visu, pane);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }

    public static void addNewNeighboursVisualisation(String filePath) {
        try {
            System.out.println("Opening directory: " + filePath + " for a Neighbours Visualisation");
            addRenderScreen();
            NeighboursVisualisation visu = new NeighboursVisualisation(activeRenderScreen);
            visu.readFromFile(filePath);
            NeighboursControlPane pane = new NeighboursControlPane(visu);
            addNewVisualisation("neighbours", visu, pane);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
}
