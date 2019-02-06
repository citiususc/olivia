package Olivia.core;

import Olivia.basic.BasicVisualisationManager;
import Olivia.classifier.ClassifierVisualisationManager;
import Olivia.core.gui.MainFrame;
import Olivia.extended.overlays.NeighbourhoodArray;
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

    protected MainFrame gui;
    protected ArrayList<VisualisationManager> visualisationManagers;
    //protected VisualisationManager activeVisualisationManager;
    protected String visulisationType;
    protected boolean isStereo3D;
    protected int desktopMode;
    protected boolean isUndecorated;
    //private static boolean isMirroring;
    protected int idCount;
    protected TextOutputter textOutputter;
    protected CommandParser commandParser;
    
    /**
     * TODO
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        Olivia olivia = new Olivia();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SubstanceLookAndFeel.setSkin(new GraphiteSkin());
                //String path = System.getProperty("user.dir");
                //File file = new File(path);
                for (int i = args.length - 1; i >= 0; i--) {
                    switch (args[i]) {
                        case "-stereo":
                            olivia.isStereo3D = true;
                            break;
                        case "-detachedD":
                            olivia.desktopMode = MainFrame.DETACHED_DESKTOP;
                            break;
                        case "-detachedI":
                            olivia.desktopMode = MainFrame.DETACHED_INDEPENDENT;
                            break;
                        case "-U":
                            olivia.isUndecorated = true;
                            break;
                        /*case "-neighbours":
                        case "-basic":
                        case "-standard":
                        case "-segmenter":
                        case "-classifier":
                            visulisationType = args[i];
                            break;
                        default:
                            path = args[i];
                            file = new File(path);
                            break;*/
                        default:
                            break;
                    }
                }
                olivia.gui = new MainFrame(olivia,olivia.isStereo3D, olivia.desktopMode, olivia.isUndecorated);
                olivia.gui.initialize();
                /*if (visulisationType != null) {
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
                            System.out.textOutputter.println("Error: " + visulisationType + "is not a correct visualisation");
                            System.exit(0);
                    }
                }*/
            }
        });
    }
    
    public Olivia(){
        textOutputter = new ConsoleTextOutputter();
        commandParser = new CommandParser(this);
        visualisationManagers = new ArrayList<>();
        visulisationType = null;
        isStereo3D = false;
        desktopMode = MainFrame.SINGLE_WINDOW;
        isUndecorated = false;
        //isMirroring = false;
        idCount = 0;
    }
    
    
    public TextOutputter getOutputter(){
        return textOutputter;
    }
    
    /**
     * Gets all the visualisationManger that have been loaded
     * @return an arrayList with all the visualisationManagers, it may be empty
     */
    public ArrayList<VisualisationManager> getLoadedVisualisations(){
        return visualisationManagers;
    }

    
    private void addNewVisualisationManager(VisualisationManager visuManager) {
        //visuManager.getRenderScreen().createRenderFrame();
        //activeVisualisation.getRenderScreen().setVisualisation(visu);
        //mainFrame.addVisuPanel(visuManager.getName(), visuManager.getControlPane());
        if(visuManager.checkCorrectness()){
            textOutputter.println("Adding " + visuManager.getName() + " to loaded visualisations"
                    + "\n\tDisplacement:" + visuManager.getDisplacement().toString()
                                );
            visualisationManagers.add(visuManager);
            gui.initVisualisation(visuManager);
            gui.setActiveVisualisationManager(visuManager);
            gui.updateRenderFrameLayout();
        }else{
            textOutputter.println("Problem detected adding " + visuManager.getName() + " to loaded visualisations");
        }
    }

    /**
     * Removes the given visualisationManager
     * @param visualisationManager the visualisationManager to remove
     */
    public void removeVisualisation(VisualisationManager visualisationManager) {
        //mainFrame.removeVisuPanel(visualisationManagers.indexOf(visualisationManager));
        textOutputter.println("Removing visualisation " + visualisationManager.getName());
        //visualisationManager.getRenderScreen().animatorPause();
        visualisationManagers.remove(visualisationManager);
        gui.removeActiveVisualisationManager(visualisationManager);
        visualisationManager.destroy();
        gui.updateRenderFrameLayout();
        //System.gc();
    }

    /**
     * Adds a new BasicVisualisationManger, reading its data from the folder in filePath
     * @param filePath The folder where all the basic data are
     */
    public void addNewBasicVisualisation(String filePath) {   
        try {
            textOutputter.println("Creating Basic Visualisation");
            BasicVisualisationManager visuMan = new BasicVisualisationManager(idCount++, gui, isStereo3D);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            textOutputter.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            textOutputter.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    /**
     * Adds an empty visualisation, used to show overlays without point cloud data (not working well at the moment)
     */
    public void addNewEmptyVisualisation(){
        textOutputter.println("Creating Empty Visualisation");
        EmptyVisualisationManager visuMan = new EmptyVisualisationManager(idCount++, gui, isStereo3D);
        addNewVisualisationManager(visuMan);
    }
    
    /**
     * Adds a new StandardVisualisationManger, used to show the point cloud with offical LAS fields, reading its data from the folder in filePath; it name will be Standard+id
     * @param filePath The folder where all the data are
     * @param decimation the decimation to be done to the file, for example, a decimation of 5 would pick 1 in 5 points int he file for the visualisation
     */
    public void addNewStandardVisualisation(String filePath, int decimation) {
        addNewStandardVisualisation(filePath, "Standard" + (idCount+1), decimation);
    }
    
        /**
     * Adds a new StandardVisualisationManger, used to show the point cloud with offical LAS fields, reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     * @param decimation the decimation to be done to the file, for example, a decimation of 5 would pick 1 in 5 points int he file for the visualisation
     * @param name the name of this standard visualisation 
     */
    public void addNewStandardVisualisation(String filePath, String name, int decimation) {
        try {
            textOutputter.println("Creating Standard Visualisation");
            StandardVisualisationManager visuMan = new StandardVisualisationManager(idCount++, gui, isStereo3D,filePath,name,decimation);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            textOutputter.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            textOutputter.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    /**
     * Adds a new ScanlinesVisualisationManger reading its data from the folder in filePath
     * @param filePath The file where all the data are
     */
    public void addNewScanlinesVisualisation(String filePath) {
        addNewScanlinesVisualisation(filePath, "Scanlines" + (idCount+1));
    }

    /**
     * Adds a new ScanlinesVisualisationManger reading its data from the folder in filePath
     * @param filePath The file where all the data are
     * @param name the name of this scanlines visualisation
     */
    public void addNewScanlinesVisualisation(String filePath, String name) {
        try {
            textOutputter.println("Creating Scanlines Visualisation");
            ScanlinesVisualisationManager visuMan = new ScanlinesVisualisationManager(idCount++, gui, isStereo3D, name);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            textOutputter.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            textOutputter.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    /**
     * Adds a new SegmenterVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     */    
    public void addNewSegmenterVisualisation(String filePath) {
        addNewSegmenterVisualisation(filePath, "Segmenter" + (idCount+1));
    }
        
    /**
     * Adds a new SegmenterVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     * @param name The name of this segmenter visualisation
     */    
    public void addNewSegmenterVisualisation(String filePath, String name) {
        try {
            textOutputter.println("Creating Segementer Visualisation");
            SegmenterVisualisationManager visuMan = new SegmenterVisualisationManager(idCount++, gui, isStereo3D, name);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            textOutputter.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            textOutputter.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    /**
     * Adds a new ClassifierVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     */
    public void addNewClassifierVisualisation(String filePath){
        addNewClassifierVisualisation(filePath, "Classifier" + (idCount+1));
    }
    
    /**
     * Adds a new ClassifierVisualisationManger reading its data from the folder in filePath
     * @param filePath The folder where all the data are
     * @param name The name of this classifier visualisation
     */
    public void addNewClassifierVisualisation(String filePath, String name) {   
        try {
            textOutputter.println("Creating Classifier Visualisation");
            ClassifierVisualisationManager visuMan = new ClassifierVisualisationManager(idCount++, gui, isStereo3D,filePath,name);
            visuMan.readFromFiles(filePath);
            addNewVisualisationManager(visuMan);
        }
        catch (FileNotFoundException e) {
            textOutputter.println("Cannot add visualisation, cannot read File" + e);
        }
        catch (IOException e) {
            textOutputter.println("Cannot add visualisation, cannot read from File" + e);
        }
    }
    
    public CommandParser getCommandParser(){
        return commandParser;
    }
    
    public MainFrame getGUI(){
        return gui;
    }
}
