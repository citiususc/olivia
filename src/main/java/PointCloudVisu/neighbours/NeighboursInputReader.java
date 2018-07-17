package PointCloudVisu.neighbours;

import PointCloudVisu.core.InputReader;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.extended.PointI;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the reading of the neighbours file (xyz + FLANN files)
 *
 * @author jorge.martinez.sanchez
 */
public class NeighboursInputReader extends InputReader<NeighboursVisualisation> {

    public static final String POINT_FILE = "points.xyz";
    public static final String ID_FILE = "NNS_results.dat";
    public static final String DISTANCE_FILE = "NNS_dists.dat";

    private void readPoints(String filePath, String fileName, NeighboursVisualisation visualisation) throws FileNotFoundException, IOException {
        PointArray<PointI> points = new PointArray<>();
        openFile(fileName, filePath);
        String delimiter = getDelimiter();
        String line;
        while ((line = buffer.readLine()) != null) {
            String[] cols = line.split(delimiter);
            points.add(readPointI(cols));
        }
        visualisation.setPoints(points);
    }

    public ArrayList<ArrayList<Integer>> readIdentifiers(String filePath, String fileName) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Integer>> nIds = new ArrayList<>();
        openFile(fileName, filePath);
        String delimiter = getDelimiter();
        String line;
        while ((line = buffer.readLine()) != null) {
            String[] cols = line.split(delimiter);
            ArrayList<Integer> ids = new ArrayList<>();
            for (int i = 1; i < cols.length; i++) {
                ids.add(Integer.parseInt(cols[i]));
            }
            nIds.add(ids);
        }
        System.out.println(nIds.size() + " read");
        return nIds;
    }

    public ArrayList<ArrayList<Double>> readDistances(String filePath, String fileName) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Double>> nDists = new ArrayList<>();
        openFile(fileName, filePath);
        String delimiter = getDelimiter();
        String line;
        while ((line = buffer.readLine()) != null) {
            String[] cols = line.split(delimiter);
            ArrayList<Double> dists = new ArrayList<>();
            for (int i = 1; i < cols.length; i++) {
                dists.add(Double.parseDouble(cols[i]));
            }
            nDists.add(dists);
        }
        System.out.println(nDists.size() + " read");
        return nDists;
    }

    @Override
    public void readFromFiles(String filePath, NeighboursVisualisation visualisation) throws FileNotFoundException, IOException {
        readPoints(filePath, POINT_FILE, visualisation);
        visualisation.setIds(readIdentifiers(filePath, ID_FILE));
        visualisation.setDistances(readDistances(filePath, DISTANCE_FILE));
    }
}
