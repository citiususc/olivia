package PointCloudVisu.basic;

import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.extended.PointI;
import PointCloudVisu.core.InputReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class manages the reading of the a basic XYZ or XYZI point cloud file
 * 
 * @author oscar.garcia
 */
public class BasicInputReader extends InputReader<BasicVisualisation> {

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
        points.centerPoints();
        return points;
    }

    @Override
    public void readFromFiles(String filePath, BasicVisualisation visualisation) throws FileNotFoundException, IOException {
        PointArray<PointI> points = readPointCloudFile(filePath);
        visualisation.setPoints(points);
    }
}
