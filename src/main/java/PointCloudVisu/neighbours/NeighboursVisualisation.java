package PointCloudVisu.neighbours;

import PointCloudVisu.core.Visualisation;
import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.extended.PointI;
import PointCloudVisu.extended.IntensityColourArray;
import com.jogamp.opengl.GL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the visualisation of neighbours
 *
 * @author jorge.martinez.sanchez
 */
public class NeighboursVisualisation extends Visualisation<PointI,PointArray<PointI>> implements ActionListener {

    protected ArrayList<ArrayList<Integer>> ids;
    protected ArrayList<ArrayList<Double>> distances;
    protected NeighboursInputReader inputReader;
    protected ArrayList<ColourArray> colours;
    protected int selectedColours;
    protected Point3D lastSelected;

    public NeighboursVisualisation(OpenGLScreen renderScreen) {
        super(renderScreen);
        super.neighbourhood.setShow(true);
        this.renderScreen.addActionListener(this);
        colours = new ArrayList<>();
        inputReader = new NeighboursInputReader();
        selectedColours = 0;
        lastSelected = null;
    }

    private void drawPointNeighbors() {
        Point3D point = renderScreen.getSelectedPoint();
        if (point == null) {
            point = lastSelected;
        }
        if (point == null) {
            return;
        }
        super.neighbourhood.drawNeighbours();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "pointSelected":
                if (selected && renderScreen.getSelectedPoint() != null) {
                    lastSelected = renderScreen.getSelectedPoint();
                }
                break;
        }
    }

    @Override
    public void draw() {
        points.draw(renderScreen, colours.get(selectedColours), GL.GL_POINTS);
        drawPointNeighbors();
    }

    @Override
    public void readFromFile(String filePath) throws FileNotFoundException, IOException {
        inputReader.readFromFiles(filePath, this);
        super.neighbourhood.setNeighbours(ids);
        super.neighbourhood.setNeighboursDistances(distances);
        colours.add(new IntensityColourArray(points));
        System.out.println("Read " + points.size() + " points");
    }

    public void setDistances(ArrayList<ArrayList<Double>> distances) {
        this.distances = distances;
    }

    public void setIds(ArrayList<ArrayList<Integer>> ids) {
        this.ids = ids;
    }

    public void setIntensityColouring() {
        this.selectedColours = 0;
        this.points.repack();
        System.out.println("set to Intensity colouring");
    }
}
