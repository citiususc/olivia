package Olivia.core;

import Olivia.basic.BasicVisualisationManager;
import Olivia.classifier.ClassifierVisualisationManager;
import Olivia.core.gui.MainFrame;
import Olivia.scanlines.ScanlinesVisualisationManager;
import Olivia.segmenter.SegmenterVisualisationManager;
import Olivia.standard.StandardVisualisationManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

/**
 * Here be main Here is where new visualisation are added, for now.
 *
 * @author oscar.garcia
 */
public class Olivia {

    private static MainFrame mainFrame;
    private static ArrayList<VisualisationManager> visualisationManagers;
    //private static VisualisationManager activeVisualisationManager;
    private static String visulisationType;
    private static boolean isStereo3D;
    private static boolean isDetached;
    private static boolean isUndecorated;
    //private static boolean isMirroring;
    private static int idCount;
    
    /**
     * TODO
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        visualisationManagers = new ArrayList<>();
        visulisationType = null;
        isStereo3D = false;
        isDetached = false;
        isUndecorated = false;
        //isMirroring = false;
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
                        case "-detached":
                            isDetached = true;
                            isUndecorated = false;
                            break;
                        case "-detachedU":
                            isDetached = true;
                            isUndecorated = true;
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
                mainFrame = new MainFrame(isStereo3D, isDetached, isUndecorated);
                mainFrame.initialize(isStereo3D, isDetached, isUndecorated);
                if (visulisationType != null) {
                    switch (visulisationType) {
                        case "-basic":
                            //addNewBasicVisualisation(file.getAbsolutePath());
                            break;
                        case "-standard":
                            addNewStandardVisualisation(file.getAbsolutePath(),1);
                            break;
                        case "-segmenter":
                            //addNewSegmenterVisualisation(file.getAbsolutePath());
                            break;
                        case "-classifier":
                            //addNewClassifierVisualisation(file.getAbsolutePath());
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
     * Gets all the visualisationManger that have been loaded
     * @return an arrayList with all the visualisationManagers, it may be empty
     */
    public static ArrayList<VisualisationManager> getLoadedVisualisations(){
        return visualisationManagers;
    }

    
    private static void addNewVisualisationManager(VisualisationManager visuManager) {
        //visuManager.getRenderScreen().createRenderFrame();
        //activeVisualisation.getRenderScreen().setVisualisation(visu);
        //mainFrame.addVisuPanel(visuManager.getName(), visuManager.getControlPane());
        if(visuManager.checkCorrectness()){
            System.out.println("Adding " + visuManager.getName() + " to loaded visualisations"
                    + "\n\tDisplacement:" + visuManager.getDisplacement().toString()
                                );
            visualisationManagers.add(visuManager);
            mainFrame.initVisualisation(visuManager);
            mainFrame.setActiveVisualisationManager(visuManager);
            mainFrame.updateRenderFrameLayout();
        }else{
            System.out.println("Problem detected adding " + visuManager.getName() + " to loaded visualisations");
        }
    }

    /**
     * Removes the given visualisationManager
     * @param visualisationManager the visualisationManager to remove
     */
    public static void removeVisualisation(VisualisationManager visualisationManager) {
        //mainFrame.removeVisuPanel(visualisationManagers.indexOf(visualisationManager));
        visualisationManagers.remove(visualisationManager);
        mainFrame.removeActiveVisualisationManager(visualisationManager);
        visualisationManager.destroy();
        mainFrame.updateRenderFrameLayout();
    }

    /**
     * Adds a new BasicVisualisationManger, reading its data from the folder in filePath
     * @param filePath The folder where all the basic data are
     */
    public static void addNewBasicVisualisation(String filePath) {   
        try {
            System.out.println("Creating Basic Visualisation");
            BasicVisualisationManager visuMan = new BasicVisualisationManager(idCount++, mainFrame, isStereo3D);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    /**
     * Adds an empty visualisation, used to show overlays without point cloud data (not working well at the moment)
     */
    public static void addNewEmptyVisualisation(){
        System.out.println("Creating Empty Visualisation");
        EmptyVisualisationManager visuMan = new EmptyVisualisationManager(idCount++, mainFrame, isStereo3D);
        addNewVisualisationManager(visuMan);
    }
    
    /**
     * Adds a new StandardVisualisationManger, used to show the point cloud with offical LAS fields, reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     * @param decimation the decimation to be done to the file, for example, a decimation of 5 would pick 1 in 5 points int he file for the visualisation
     */
    public static void addNewStandardVisualisation(String filePath, int decimation) {
        try {
            System.out.println("Creating Standard Visualisation");
            StandardVisualisationManager visuMan = new StandardVisualisationManager(idCount++, mainFrame, isStereo3D,filePath,decimation);
            addNewVisualisationManager(visuMan);
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
    
    /**
     * Adds a new ScanlinesVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     */
        public static void addNewScanlinesVisualisation(String filePath) {
        try {
            System.out.println("Creating Scanlines Visualisation");
            ScanlinesVisualisationManager visuMan = new ScanlinesVisualisationManager(idCount++, mainFrame, isStereo3D);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
        
    /**
     * Adds a new SegmenterVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     */    
    public static void addNewSegmenterVisualisation(String filePath) {
        try {
            System.out.println("Creating Scanlines Visualisation");
            SegmenterVisualisationManager visuMan = new SegmenterVisualisationManager(idCount++, mainFrame, isStereo3D);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    /**
     * Adds a new ClassifierVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     */
    public static void addNewClassifierVisualisation(String filePath) {   
        try {
            System.out.println("Creating Classifier Visualisation");
            ClassifierVisualisationManager visuMan = new ClassifierVisualisationManager(idCount++, mainFrame, isStereo3D,filePath);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            System.out.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
}
