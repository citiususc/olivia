package PointCloudVisu.core;

import PointCloudVisu.extended.PointI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class manages the reading of the files. It also stores some code to be
 * be reused that may or may not be relevant, up to the implementer to use it
 *
 * @author oscar.garcia
 */
public abstract class InputReader<V extends Visualisation> {

    /**
     * Used to store the file being read or written
     */
    protected File file;
    /**
     * A file reader
     */
    protected FileReader reader;
    /**
     * A buffered reader
     */
    protected BufferedReader buffer;
    /**
     * Stores the number of points read, usually it is needed
     */
    protected Integer numPointsRead;
    /**
     * Stores the number of groups read, your Visualisation may not need it
     */
    protected Integer numGroupsRead;
    /**
     * The default name for a point cloud file
     */
    public static final String DEFAULT_POINT_CLOUD_FILE = "results2.xyz";

    /**
     * Just inits the attributes to null
     */
    public InputReader() {
        file = null;
        reader = null;
        buffer = null;
        numPointsRead = 0;
        numGroupsRead = 0;
    }

    /**
     *
     * @param fileName The name of the file
     * @param filePath The path excluding the name of the file
     * @throws java.io.FileNotFoundException
     */
    public void openFile(String fileName, String filePath) throws FileNotFoundException {
        if (filePath == null) {
            this.file = new File(fileName);
        } else {
            this.file = new File(filePath + "/" + fileName);
        }
        this.reader = new FileReader(file);
        this.buffer = new BufferedReader(reader);
        System.out.println("Reading " + fileName + "...");
    }
    
    /**
     *
     * @param filePath The complete path of the file
     * @throws java.io.FileNotFoundException
     */
    public void openFile(String filePath) throws FileNotFoundException {
        file = new File(filePath);
        reader = new FileReader(file);
        buffer = new BufferedReader(reader);
        System.out.println("Reading " + file.getName() + "...");
    }
    
    /**
     * Return the delimiter of the columns
     *
     * @return The delimiter
     * @throws java.io.IOException
     */
    public String getDelimiter() throws IOException {
        buffer.mark(100);
        String line = buffer.readLine();
        String[] cols = line.split("\t");
        if (cols.length != 1) {
            buffer.reset();
            return "\t";
        }
        cols = line.split("\\s+");
        if (cols.length != 1) {
            buffer.reset();
            return " ";
        }
        cols = line.split(",");
        if (cols.length != 1) {
            buffer.reset();
            return ",";
        } else {
            System.out.println("Error: Unknown delimiter");
        }
        return "";
    }
    
    public String getDelimiter(String line){
        String[] cols= line.split("\t");
        if (cols.length != 1) {
            return "\t";
        }
        cols = line.split("\\s+");
        if (cols.length != 1) {
            return " ";
        }
        cols = line.split(",");
        if (cols.length != 1) {
            return ",";
        }
        System.out.println("Error: Unknown delimiter");
        return "";
    }

    /**
     * Read a XYZI point
     *
     * @param cols The line columns
     * @return The point
     */
    public PointI readPointI(String[] cols) {
        double x = Double.parseDouble(cols[0]);
        double y = Double.parseDouble(cols[1]);
        double z = Double.parseDouble(cols[2]);
        float I = Float.parseFloat(cols[3]);
        return new PointI(x, y, z, I);
    }

    /**
     * This has to be implemented to read from file everything needed for a
     * given Visualisation
     *
     * @param filePath The path where the files are
     * @param visualisation Your own Visualisation
     * @throws FileNotFoundException If file not found
     * @throws IOException If there is an error reading IO
     */
    public abstract void readFromFiles(String filePath, V visualisation) throws FileNotFoundException, IOException;

}
