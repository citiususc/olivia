package PointCloudVisu.core.render;

import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.extended.SingleColourArray;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the drawing of OpenGL primitives on top of the point cloud
 *
 * @author jorge.martinez.sanchez
 */
public class Geometry {

    /**
     * The render screen
     */
    protected OpenGLScreen renderScreen;
    /**
     * The list containing the point arrays
     */
    protected List<PointArray> pointsList;
    /**
     * The list containing the colours of each point array
     */
    protected List<ColourArray> coloursList;
    /**
     * The list contaning the OpenGL draw type of each point array
     */
    protected List<Integer> drawTypes;
    /**
     * The list of flags to indicate which point arrays to display
     */
    protected List<Boolean> activeLists;
    /**
     * The index of the point array to show (show only first by default)
     */
    protected int currentActiveIndex;

    public Geometry(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
        pointsList = new ArrayList<>();
        coloursList = new ArrayList<>();
        drawTypes = new ArrayList<>();
        activeLists = new ArrayList<>();
        currentActiveIndex = 0;
    }

    /**
     * Show the next point array
     */
    public void activeNextList() {
        if (currentActiveIndex < activeLists.size() - 1) {
            activeLists.set(currentActiveIndex, Boolean.FALSE);
            activeLists.set(++currentActiveIndex, Boolean.TRUE);
            renderScreen.getMainFrame().logIntoConsole("Display geometry " + currentActiveIndex + "/" + (activeLists.size() - 1));
        }
    }

    /**
     * Show the previous point array
     */
    public void activePreviousList() {
        if (currentActiveIndex > 0) {
            activeLists.set(currentActiveIndex, Boolean.FALSE);
            activeLists.set(--currentActiveIndex, Boolean.TRUE);
            renderScreen.getMainFrame().logIntoConsole("Display geometry " + currentActiveIndex + "/" + (activeLists.size() - 1));
        }
    }

    /**
     * Remove all geometry
     */
    public void removeGeometry() {
        pointsList.clear();
        coloursList.clear();
        drawTypes.clear();
        activeLists.clear();
    }

    /**
     * Remove the specified geometry
     *
     * @param index Position in the geometry list
     */
    public void removeGeometry(int index) {
        pointsList.remove(index);
        coloursList.remove(index);
        drawTypes.remove(index);
        activeLists.remove(index);
    }

    /**
     * Read the vertices and store them for drawing
     *
     * @param filePath Path of the vertex file
     * @param fileName Name of the vertex file
     * @param drawType OpenGL draw type
     */
    public void readVertices(String filePath, String fileName, int drawType) {
        try {
            File file = new File(filePath + "/" + fileName);
            renderScreen.getMainFrame().logIntoConsole("Reading lines from " + file.getName() + "...");
            List<String> lines = Files.readAllLines(Paths.get(file.toURI()), Charset.defaultCharset());
            String delimiter = "\t";
            double center[] = renderScreen.getVisualisation().getPoints().getCenterOfMassOriginal();
            pointsList.add(new PointArray<>());
            int numVertices = 0;
            for (String line : lines) {
                String[] cols = line.split(delimiter);
                if (cols.length != 1) {
                    double x = Double.parseDouble(cols[0]);
                    double y = Double.parseDouble(cols[1]);
                    double z = Double.parseDouble(cols[2]);
                    pointsList.get(pointsList.size() - 1).add(new Point3D(x - center[0], y - center[1], z - center[2]));
                } else {  // Blank line to separate point arrays
                    ColourArray colours = new SingleColourArray(pointsList.get(pointsList.size() - 1), Color.WHITE);
                    coloursList.add(colours);
                    drawTypes.add(drawType);
                    activeLists.add(Boolean.FALSE);
                    numVertices += pointsList.get(pointsList.size() - 1).size();
                    pointsList.add(new PointArray<>());
                }
            }

            Color color = Color.WHITE;
            if (fileName.contains("octree")) {
                color = Color.GREEN;
            }
            ColourArray colours = new SingleColourArray(pointsList.get(pointsList.size() - 1), color);

            coloursList.add(colours);
            drawTypes.add(drawType);
            activeLists.add(Boolean.FALSE);
            numVertices += pointsList.get(pointsList.size() - 1).size();
            renderScreen.getMainFrame().logIntoConsole(numVertices + " vertices read");

            activeLists.set(currentActiveIndex, Boolean.TRUE);
            if (fileName.contains("normal") || fileName.contains("snakes")) {
                parseNormals(pointsList.get(pointsList.size() - 1));
            }

        }
        catch (IOException ex) {
            Logger.getLogger(Geometry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Convert from (point, normal components) to normal vector (point, point)
    private void parseNormals(PointArray points) {
        float length = 0.5f;
        double[] center = renderScreen.getVisualisation().getPoints().getCenterOfMassOriginal();
        renderScreen.getMainFrame().logIntoConsole("Parsing normals...");
        for (int i = 0; i < points.size() - 1; i += 2) {
            Point3D p = (Point3D) points.get(i);
            Point3D v = (Point3D) points.get(i + 1);
            v.setX(v.getX() + center[0]);
            v.setY(v.getY() + center[1]);
            v.setZ(v.getZ() + center[2]);
            Point3D n = new Point3D(p.getX() + v.getX() * length, p.getY() + v.getY() * length, p.getZ() + v.getZ() * length);
            points.set(i + 1, n);
        }
    }

    /**
     * Draw active geometry
     */
    public void draw() {
        for (int i = 0; i < pointsList.size(); i++) {
            if (!pointsList.get(i).isEmpty() && activeLists.get(i)) {
                pointsList.get(i).draw(renderScreen, coloursList.get(i), drawTypes.get(i));
            }
        }
    }
}
