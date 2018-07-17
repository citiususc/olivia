package PointCloudVisu.segmenter;

import PointCloudVisu.core.InputReader;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.extended.PointI;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the reading of the segmentation file
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterInputReader extends InputReader<SegmenterVisualisation> {

    private static final int ID_COL = 9;
    private int numSegmented = 0;
    private int numUnsegmented = 0;

    public int getNumSegmented() {
        return numSegmented;
    }

    public int getNumUnsegmented() {
        return numUnsegmented;
    }

    public void readSegmentTXT(String filePath, SegmenterVisualisation visualisation) throws FileNotFoundException, IOException {
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
        visualisation.setPoints(points);
        visualisation.setGroups(groups);
        visualisation.setGroupIds(groupIds);
    }

    @Override
    public void readFromFiles(String filePath, SegmenterVisualisation visualisation) throws FileNotFoundException, IOException {
        readSegmentTXT(filePath, visualisation);
    }
}
