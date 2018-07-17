package PointCloudVisu.core;

import PointCloudVisu.core.render.OpenGLScreen;
import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.Geometry;
import PointCloudVisu.core.render.Neighbourhood;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the template for a Visualisation of a point cloud Because it is for
 * point cloud visualisation, it needs a point cloud, if you need to render
 * something else, either create 1 point data and do nothing with it or change
 * many basic things in the visualisation app You have to define what kind of
 * points you are using Note that you do not have to read the point cloud from
 * file, you can just pass a reference, in case you want many visualisations
 * (even different) for the same data; this is open to implementation It needs a
 * Camera to store translations and rotations, so use it if you want It has a
 * selected attribute, how its value is changed is a little iffy right now, done
 * in the OpenGLScreen class when visualisations are changed/added; I think it
 * makes sense there, it may not
 *
 * @author oscar.garcia
 */
public abstract class Visualisation<P extends Point3D, A extends PointArray<P>> {

    /**
     * The point cloud
     */
    protected A points;
    /**
     * Where data is being rendered
     */
    protected OpenGLScreen renderScreen;
    /**
     * Says wheter it is currently being rendered or not
     */
    protected boolean selected;
    /**
     * Its own Geometry, to draw OpenGL primitives on top of the point cloud
     */
    protected Geometry geometry;
    /**
     * Text that a visualisation can provide
     */
    protected String textInfo;
    /**
     *
     */
    protected Neighbourhood neighbourhood;

    /**
     * Inits points (which may be overriden with a new instance, remember),
     * camera and sets selected to false
     *
     * @param renderScreen The render screen it will be rendered
     */
    public Visualisation(OpenGLScreen renderScreen) {
        //points = new PointArray<>();
        selected = false;
        geometry = new Geometry(renderScreen);
        textInfo = new String();
        neighbourhood = new Neighbourhood(renderScreen);
        this.renderScreen = renderScreen;
    }

    /**
     * Create here all the openGL drawing (such as a call to points.draw())
     */
    public abstract void draw();

    public void fireEvent(String groupSelected) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Read all data from files here, however you want, just note the exceptions
     *
     * @param filePath
     * @throws FileNotFoundException Only if the file was necessary for the
     * visualisation, non necessary files can be treated inside and may not
     * propagate their exception upwards; if this is thrown the visualisation
     * cannot be rendered and is not added.
     * @throws IOException Only if the error was somewhere necessary for the
     * visualisation, non necessary errors can be treated inside and may not
     * propagate their exception upwards; if this is thrown the visualisation
     * cannot be rendered and is not added.
     */
    public abstract void readFromFile(String filePath) throws FileNotFoundException, IOException;

    /**
     * Gets the points, it is nedded somewhere, I think; if I am wrong maybe
     * take it out
     *
     * @return The points
     */
    public A getPoints() {
        return points;
    }

    /**
     * Usually needed for reading the points, maybe should be implemented by
     * each one, but since it is so common ...
     *
     * @param points
     */
    public void setPoints(A points) {
        points.centerPoints();
        this.points = points;
    }

    /**
     * getter
     *
     * @return the render screen
     */
    public OpenGLScreen getRenderScreen() {
        return renderScreen;
    }

    /**
     * getter
     *
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * This is a toggle, needed because Visualisations are selected or
     * deselected from outside
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * getter
     *
     * @return The text information
     */
    public String getTextInfo() {
        return textInfo;
    }

    /**
     * getter
     *
     * @return The neighbourhood
     */
    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }
}
