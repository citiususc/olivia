package Olivia.extended.overlays;

import Olivia.core.Olivia;
import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the neighbours data and handles the drawing
 *
 * The neighbours data must follow the FLANN (Fast Library for Approximate
 * Nearest Neighbors) format. To add this drawing capabilty to a visualisation
 * add the appropiate show/hide buttons in the control pane of the visualisation
 *
 * @see <a href="https://www.cs.ubc.ca/research/flann/">FLANN</a>
 *
 * @author jorge.martinez.sanchez
 */
public class NeighbourhoodArray<VM extends VisualisationManager> extends OverlayArray<Neighbourhood<VM>,VM>{

    //protected OpenGLScreen renderScreen;
    public static final String ID_FILE = "NNS_results.dat";
    public static final String DISTANCE_FILE = "NNS_dists.dat";
    

    public NeighbourhoodArray(VM visualisationManager) {
        super(visualisationManager, "neighbourhood");
        if(visualisationManager.getPointCloud().size()<1){
            Olivia.textOutputter.println("There are no points for which to show neighbours!!");
        }else{
            for(int i=0; i<visualisationManager.getPointCloud().size();i++){
                Neighbourhood<VM> neighbourhood = new Neighbourhood<>(visualisationManager,i);
                this.add(neighbourhood,false);
            }
        }
    }

    /*
    private void readPoints(String filePath, String fileName) throws FileNotFoundException, IOException {
        PointArray<PointI> points = new PointArray<>();
        openFile(fileName, filePath);
        String delimiter = getDelimiter();
        String line;
        while ((line = buffer.readLine()) != null) {
            String[] cols = line.split(delimiter);
            points.add(readPointI(cols));
        }
        visualisation.setPointCloud(points);
    }
*/
    
    public void readNeighboursIdentifiers(Path path) throws FileNotFoundException, IOException {
        List<String> lines = Files.readAllLines(path);
        String delimiter = " ";
        String line;
        String[] cols;
        if(lines.size()!=overlays.size()){
            Olivia.textOutputter.println("Read " + lines.size() + ", not enough for " + overlays.size() + " neighbourhoods");
            throw new IOException("Read " + lines.size() + ", not enough for " + overlays.size() + " neighbourhoods");
        }
        Olivia.textOutputter.println("Read " + lines.size() + " lines in neighbour identifiers file");
        for (int i=0;i<lines.size();i++) {
            line = lines.get(i);
            cols = line.split(delimiter);
            ArrayList<Integer> ids = new ArrayList<>();
            for (int j = 1; j < cols.length; j++) {
                ids.add(Integer.parseInt(cols[j]));
            }
            overlays.get(i).setNeighbours(ids);
        }
        Olivia.textOutputter.println("Neigbours for " + overlays.size() + " points read");
    }
    


    public void readDistances(Path path) throws FileNotFoundException, IOException {
        List<String> lines = Files.readAllLines(path);
        String delimiter = " ";
        String line;
        String[] cols;
        if(lines.size()!=overlays.size()){
            Olivia.textOutputter.println("Read " + lines.size() + ", not enough for " + overlays.size() + " neighbourhoods");
            throw new IOException("Read " + lines.size() + ", not enough for " + overlays.size() + " neighbourhoods");
        }
        Olivia.textOutputter.println("Read " + lines.size() + " lines in neighbour distances file");
        for (int i=0;i<lines.size();i++) {
            line = lines.get(i);
            cols = line.split(delimiter);
            ArrayList<Double> dists = new ArrayList<>();
            for (int j = 1; j < cols.length; j++) {
                dists.add(Double.parseDouble(cols[j]));
            }
            overlays.get(i).setNeighboursDistances(dists);
        }
        Olivia.textOutputter.println("Distances to neigbours for " + overlays.size() + " points read");
    }


    public void readFromFiles(Path filePath) throws FileNotFoundException, IOException {
        Path path = Paths.get(filePath +"/"+ID_FILE);
        readNeighboursIdentifiers(path);
        path = Paths.get(filePath +"/"+DISTANCE_FILE);
        readDistances(path);
    }
    
    @Override
    public void draw(){
        drawShapeCurrent(visualisationManager.getRenderScreen());
    }

}
