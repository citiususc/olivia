package Olivia.landing;


import Olivia.extended.PointI;
import Olivia.core.data.PointArray;
import Olivia.core.InputReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author oscar.garcia
 */
public class LandingInputReader extends InputReader<LandingVisualisationManager> {

    //public static final String APPROX_OBSTACLES_TXT = "obstacles.txt";

    private final double[] offsets;

    public LandingInputReader() {
        super();
        offsets = new double[3];
        offsets[0] = 0.0;
        offsets[1] = 0.0;
        offsets[2] = 0.0;
    }

    public void readPointCloudFileWithGroups(String filePath, String fileName, LandingVisualisationManager visuM) throws FileNotFoundException, IOException {

        PointArray<PointI> points = new PointArray<>();
        LandingGroupArray groups = new LandingGroupArray();
        openFile(fileName, filePath);
        String line;
        String[] cols;
        PointI point;
        int i = 0, m = 0, numGroup;
        while ((line = buffer.readLine()) != null) {
            cols = line.split("\t");
            float x = Float.parseFloat(cols[0]);
            float y = Float.parseFloat(cols[1]);
            float z = Float.parseFloat(cols[2]);
            if ((x == 0) & (y == 0) & (z == 0)) {
                //clear zero points
            } else {
                    numGroup = Integer.parseInt(cols[4]);
                    point = new PointI(x,
                            y,
                            z,
                            Float.parseFloat(cols[3]));
                    points.add(point);
                    groups.get(numGroup).addPoint(point);

                    numPointsRead++;
                    i++;
            }
            if (i > 1000000) {
                m++;
                System.out.println("Read " + m + "M points...");
                i = 0;
            }
        }

        System.out.println("Read " + numPointsRead + " points");
        //System.out.println("Read " + numGroupsRead + " groups");
        offsets[0] = points.getBounds().getCentre().getX();
        offsets[1] = points.getBounds().getCentre().getY();
        offsets[2] = points.getBounds().getCentre().getZ();
        visuM.setPointCloud(points);
        visuM.setGroups(groups);
    }
      

    /*
    public LandingApproachOverlay readLandingApproximations(String filePath) throws FileNotFoundException, IOException {

        LandingApproachOverlay approxs = new LandingApproachOverlay();
        openFile(APPROX_OBSTACLES_TXT, filePath);
        //  String linea=br.readLine();
        String linea;
        int i = 0, num_approx = 0;
        while ((linea = buffer.readLine()) != null) {
            String[] line = linea.split("\t");
            LandingApproach la = new LandingApproach(Float.parseFloat(line[0]) - (float) offsets[0], Float.parseFloat(line[1]) - (float) offsets[1], Float.parseFloat(line[2]) - (float) offsets[2]);
            //LandingApprox la = new LandingApproach(Float.parseFloat(line[0]), Float.parseFloat(line[1]),Float.parseFloat(line[2]));

            for (i = 3; i < line.length; i = i + 2) {
                la.addOcluddedPair(Float.parseFloat(line[i]), Float.parseFloat(line[i + 1]));
            }
            approxs.add(la);
            num_approx++;
        }

        System.out.println("Read " + num_approx + "approximations");

        return approxs;
    }*/

    @Override
    public void readFromFiles(String filePath, LandingVisualisationManager visuM) throws FileNotFoundException, IOException {
        readPointCloudFileWithGroups(filePath, DEFAULT_POINT_CLOUD_FILE, visuM);
    }
}
