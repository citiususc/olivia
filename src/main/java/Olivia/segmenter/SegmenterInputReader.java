package Olivia.segmenter;

import Olivia.core.InputReader;
import Olivia.core.data.PointArray;
import Olivia.extended.PointI;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the reading of the segmentation file
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterInputReader extends InputReader<SegmenterVisualisationManager> {

    private static final int ID_COL = 9;
    private int numSegmented = 0;
    private int numUnsegmented = 0;

    public int getNumSegmented() {
        return numSegmented;
    }

    public int getNumUnsegmented() {
        return numUnsegmented;
    }

    public void readSegmentTXT(String filePath, SegmenterVisualisationManager visualisationM) throws FileNotFoundException, IOException {
        PointArray<PointI> points = new PointArray<>();
        SegmenterGroupArray groups = new SegmenterGroupArray();
        ArrayList<Integer> groupIds = new ArrayList<>();
        openFile(filePath);
        String delimiter = getDelimiter();
        int gId;
        SegmenterGroup gr;
        String line = buffer.readLine();
        String[] cols;
        while (line != null) {
            cols = line.split(delimiter);
            gId = Integer.parseInt(cols[ID_COL]);
            if (gId >= groups.size()) {
                for (int i = groups.size(); i <= gId; i++) {
                    gr = new SegmenterGroup();
                    gr.setId(i);
                    groups.add(gr);
                }
            }
            PointI point = readPointI(cols);
            if (point.getX() != 0) { // Ignore small groups
                points.add(point);
                numPointsRead++;
                groupIds.add(gId);
                gr = (SegmenterGroup) groups.get(gId);
                gr.addPoint(point);
            }
            line = buffer.readLine();
        }
        SegmenterGroup last = (SegmenterGroup) groups.get(groups.size() - 1);
        numUnsegmented = last.getPoints().size();
        numSegmented = numPointsRead - numUnsegmented;
        System.out.println("Read " + numPointsRead + " points");
        System.out.println("Read " + numUnsegmented + " unsegmented");
        System.out.println("Read " + groups.size() + " groups");
        visualisationM.setPointCloud(points);
        visualisationM.setGroups(groups);
        visualisationM.setGroupIds(groupIds);
    }

    @Override
    public void readFromFiles(String filePath, SegmenterVisualisationManager visualisationM) throws FileNotFoundException, IOException {
        readSegmentTXT(filePath, visualisationM);
    }
}
