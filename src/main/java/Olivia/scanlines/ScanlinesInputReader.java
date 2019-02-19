package Olivia.scanlines;

import Olivia.core.Olivia;
import Olivia.core.InputReader;
import Olivia.core.data.Group;
import Olivia.core.data.GroupArray;
import Olivia.core.data.PointArray;
import Olivia.extended.PointS;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author oscar
 */
public class ScanlinesInputReader extends InputReader<ScanlinesVisualisationManager> {

    public void readPointCloudFileWithGroups(String fileName, ScanlinesVisualisationManager visu) throws FileNotFoundException, IOException {

        PointArray<PointS> points = new PointArray<>();
        GroupArray groups = new GroupArray();
        openFile(fileName);
        String line;
        String[] cols;
        Group<PointS> g = new Group<>();
        PointS point;
        int i = 0, m = 0, id = 0, last = 0, dir = 0, edge = 0;
        while ((line = buffer.readLine()) != null) {
            cols = line.split(" ");
            double x = Double.parseDouble(cols[0]);
            double y = Double.parseDouble(cols[1]);
            double z = Double.parseDouble(cols[2]);

            if ((x == 0) & (y == 0) & (z == 0)) {

            } else {
                dir = Integer.parseInt(cols[6]);
                edge = Integer.parseInt(cols[7]);
                point = new PointS(x,
                        y,
                        z,
                        Float.parseFloat(cols[3]), //intensity
                        Integer.parseInt(cols[4]), //return number 
                        Integer.parseInt(cols[5]), //number of returns
                        dir, //direction
                        Integer.parseInt(cols[7]) //edge
                );
                if ((last != dir)|(edge==1)) {
                    numGroupsRead++;
                    g = new Group<>();
                    g.setId(id);
                    id++;
                    last = dir;
                    groups.add(g);
                }
                points.add(point);
                g.addPoint(point);

                numPointsRead++;
                i++;
            }
            if (i > 1000000) {
                m++;
                Olivia.textOutputter.println("Read " + m + "M points...");
                i = 0;
            }
        }

        Olivia.textOutputter.println("Read " + numPointsRead + " points");
        Olivia.textOutputter.println("Read " + numGroupsRead + " groups");
        visu.setPointCloud(points);
        visu.setGroups(groups);
    }

    @Override
    public void readFromFiles(String filePath, ScanlinesVisualisationManager visualisation) throws FileNotFoundException, IOException {
        readPointCloudFileWithGroups(filePath, visualisation);
    }
}
