package Olivia.classifier;

import Olivia.core.Olivia;
import Olivia.core.InputReader;
import Olivia.core.data.PointArray;
import Olivia.extended.PointI;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class manages the reading of the classification file
 *
 * @author jorge.martinez.sanchez
 */
public class ClassifierInputReader extends InputReader<ClassifierVisualisationManager> {

    private static final int LABEL_COL = 5;
    private static final int GROUP_COL = 4;

    public void readDetectTXT(String filePath, ClassifierVisualisationManager visualisation) throws FileNotFoundException, IOException {
        PointArray<PointI> points = new PointArray<>();
        ClassifierGroupArray groups = new ClassifierGroupArray();
        openFile(filePath);
        String delimiter = getDelimiter();
        String linea = buffer.readLine();
        String[] cols = linea.split(delimiter);
        int gId = -1;
        int type = Integer.parseInt(cols[LABEL_COL]);
        ClassifierGroup gr = new ClassifierGroup(type);
        while (linea != null) {
            cols = linea.split(delimiter);
            if (gId != Integer.parseInt(cols[GROUP_COL])) {
                numGroupsRead++;
                type = Integer.parseInt(cols[LABEL_COL]);
                gr = new ClassifierGroup(type);
                gr.setId(Integer.parseInt(cols[GROUP_COL]));
                groups.add(gr);
                gId = Integer.parseInt(cols[GROUP_COL]);
            }
            PointI point = readPointI(cols);
            if (point.getX() != 0) { // Ignore small groups
                points.add(point);
                numPointsRead++;
                gr.addPoint(point);
            }
            linea = buffer.readLine();
        }
        Olivia.textOutputter.println("Read " + numPointsRead + " points");
        Olivia.textOutputter.println("Read " + numGroupsRead + " groups");
        visualisation.setPointCloud(points);
        visualisation.setGroups(groups);
    }

    @Override
    public void readFromFiles(String filePath, ClassifierVisualisationManager visualisationManager) throws FileNotFoundException, IOException {
        readDetectTXT(filePath, visualisationManager);
    }

}
