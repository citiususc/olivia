package Olivia.basic;

import Olivia.core.data.PointArray;
import Olivia.extended.PointI;
import Olivia.core.InputReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class manages the reading of the a basic XYZ or XYZI point cloud file
 * 
 * @author oscar.garcia
 */
public class BasicInputReader extends InputReader<BasicVisualisationManager> {

    /**
     * Reads the point cloud file
     * @param filePath The path of the file, as a string
     * @return The points read
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public PointArray<PointI> readPointCloudFile(String filePath) throws FileNotFoundException, IOException {

        openFile(filePath);
        String delimiter = getDelimiter();
        PointArray<PointI> points = new PointArray<>();
        String line;
        String[] cols;
        while ((line = buffer.readLine()) != null) {
            cols = line.split(delimiter);
            if (cols.length > 3) { // If intensity available (XYZI)
                points.add(readPointI(cols));
            } else {
                double x = Double.parseDouble(cols[0]);
                double y = Double.parseDouble(cols[1]);
                double z = Double.parseDouble(cols[2]); 
                points.add(new PointI(x, y, z, 254));
            }
            numPointsRead++;
        }
        System.out.println("Read " + numPointsRead + " points");
        return points;
    }

    @Override
    public void readFromFiles(String filePath, BasicVisualisationManager visualisationManager) throws FileNotFoundException, IOException {
        PointArray<PointI> points = readPointCloudFile(filePath);
        visualisationManager.setPointCloud(points);
    }
}
